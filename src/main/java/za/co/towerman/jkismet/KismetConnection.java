/**
 * za.co.towerman.jkismet.KismetConnection
 * Copyright (C) 2012 Edwin Peer
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package za.co.towerman.jkismet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import za.co.towerman.jkismet.message.KismetMessage;
import za.co.towerman.jkismet.message.ValueEnum;

/**
 * @author espeer
 */

public class KismetConnection {

    private final Map<String, Set<String>> supported = new HashMap<String, Set<String>>();    //supported
    private final List<KismetListener> listeners = new LinkedList<KismetListener>();        //listeners
    private final Map<String, List<String>> subscribed = new HashMap<String, List<String>>();//subscribed


	/*
	BufferedReader，字符缓冲输入流，作用是为其他输入流提供缓冲功能。
	BufferedReader从其他字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。
	通常，Reader所作的每个读取请求都会导致对底层字符或字节流进行相应的读取请求。
	因此，建议用BufferedReader包装所有其read()操作可能开销很高的Reader（如FileReader和InputStreamReader）。
	例如， BufferedReader in = new BufferedReader(new FileReader("foo.in"));将缓冲指定文件的输入。
	如果没有缓冲，则每次调用read()或readLine()都会导致从文件中读取字节，并将其转换为字符后返回，而这是极其低效的。

	BufferedWriter，字符缓冲输出流，作用是为其他输出流提供缓冲功能。
	BufferedWriter将文本写入其他字符输出流，缓冲各个字符，从而提供单个字符、数组和字符串的高效写入。
	通常Writer将其输出立即发送到底层字符或字节流。除非要求提示输出，
	否则建议用BufferedWriter包装所有其write()操作可能开销很高的 Writer（如FileWriters和OutputStreamWriters）。
	例如，PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("foo.out")));
	将缓冲PrintWriter对文件的输出。如果没有缓冲，则每次调用print()方法会导致将字符转换为字节，然后立即写入到文件，而这是极其低效的
	*/

    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    private boolean running = true;    //是否运行
    private boolean initialised = false;//是否初始化

    private String version = null;//版本号
    private String build = null;//???
    private Date startTime = null;
    private String serverName = null;


    //建立连接，主机名和端口号为参数
    public KismetConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        out.write("!0 REMOVE TIME\r\n");
        out.flush();

        while (version == null || !initialised) {
            parse(in.readLine());
        }


        new Thread() {

            @Override
            public void run() {
                while (running) {
                    try {
                        parse(in.readLine());
                    } catch (IOException ex) {
                        break;
                    } catch (Exception ex) {
                    }
                }

                try {
                    in.close();
                } catch (IOException ex) {
                }
                try {
                    out.close();
                } catch (IOException ex) {
                }
                try {
                    socket.close();
                } catch (IOException ex) {
                }
            }
        }.start();
    }

    public String getBuild() {
        return build;
    }

    public String getServerName() {
        return serverName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getVersion() {
        return version;
    }

    public Set<String> getSupportedProtocols() {
        return supported.keySet();
    }

    public Set<String> getSupportedCapabilities(String protocol) {
        return supported.get(protocol);
    }


    //regieter 同步建立连接的信息
    public void register(KismetListener listener) throws IOException {
        if (listener.connection != null) {
            throw new IllegalArgumentException("listener already bound to connection: " + listener.connection.socket);
        }

        listener.checkServerSupport(this);

        synchronized (listeners) {
            listeners.add(listener);
        }

        listener.connection = this;

        this.updateServerSubscriptions();
    }

    //deregister 取消已建立的连接
    public void deregister(KismetListener listener) throws IOException {
        if (listener.connection != this) {
            throw new IllegalArgumentException("listener is not bound to connection: " + this.socket);
        }

        synchronized (listeners) {
            listeners.remove(listener);
        }

        listener.connection = null;

        this.updateServerSubscriptions();
    }


    //???
    void updateServerSubscriptions() throws IOException {
        synchronized (listeners) {
            Map<String, Set<String>> needed = new HashMap<String, Set<String>>();

            for (KismetListener listener : listeners) {
                for (Entry<String, Set<Class>> entry : listener.subscriptions.entrySet()) {
                    Set<String> capabilities = needed.get(entry.getKey());
                    if (capabilities == null) {
                        capabilities = new HashSet<String>();
                        needed.put(entry.getKey(), capabilities);
                    }

                    for (Class messageType : entry.getValue()) {
                        for (String capability : listener.capabilities.get(messageType)) {
                            capabilities.add(capability);
                        }
                    }
                }
            }

            for (String protocol : subscribed.keySet()) {
                if (!needed.containsKey(protocol)) {
                    out.write("!0 REMOVE " + protocol + "\r\n");
                }
            }

            subscribed.clear();

            for (Entry<String, Set<String>> entry : needed.entrySet()) {
                List<String> capabilities = new ArrayList<String>();
                StringBuilder builder = new StringBuilder();
                for (String capability : entry.getValue()) {
                    if (builder.length() > 0) {
                        builder.append(',');
                    }
                    builder.append(capability);
                    capabilities.add(capability);
                }
                out.write("!0 ENABLE " + entry.getKey() + " " + builder.toString() + "\r\n");
                subscribed.put(entry.getKey(), capabilities);
            }
        }

        out.flush();
    }


    //分析帧类型
    private void parse(String line) throws IOException {
        if (line.startsWith("*KISMET: ") && line.length() > 9) {
            parseKismet(line.substring(9));
        } else if (line.startsWith("*ACK: ") && line.length() > 6) {
            parseAck(line.substring(6));
        } else if (line.startsWith("*ERROR: ") && line.length() > 8) {
            parseError(line.substring(8));
        } else if (line.startsWith("*PROTOCOLS: ") && line.length() > 12) {
            parseProtocols(line.substring(12));
        } else if (line.startsWith("*CAPABILITY: ") && line.length() > 13) {
            parseCapabilities(line.substring(13));
        } else if (line.startsWith("*TERMINATE: ") && line.length() > 12) {
            parseTerminate(line.substring(12));
        } else if (line.startsWith("*") && line.indexOf(':') > 2 && line.length() > line.indexOf(':') + 2) {
            parseProtocol(line.substring(1, line.indexOf(':')), line.substring(line.indexOf(':') + 2));
        }
    }

    private void parseKismet(String kismet) {                            //kismet格式
        List<String> values = this.split(kismet);
        version = values.get(0);                                         //第1位为版本号
        startTime = new Date(Long.parseLong(values.get(1)) * 1000);      //第2位表示开始时间，将字符转为long型，毫秒数转为秒乘1000
        serverName = values.get(2);                                      //第3位表示服务名
        build = values.get(3);                                           //第4位表示build？？？
    }

    private void parseAck(String ack) {
        // ignored for now (not very robust)
    }

    private void parseError(String error) {
        // ignored for now (not very robust)
    }

    private void parseProtocols(String protocols) throws IOException {  //解析协议
        for (String protocol : protocols.split(",")) {           //以，作为分割
            supported.put(protocol, null);                             //将每一段字符串写入到map类型数据supported中，表示支持的协议
            out.write("!0 CAPABILITY " + protocol + "\r\n");
        }
        out.flush();
    }


    private void parseCapabilities(String capabilities) {               //解析性能(服务)
        String protocol = capabilities.substring(0, capabilities.indexOf(' '));
        Set<String> set = new HashSet<String>();
        for (String capability : capabilities.substring(capabilities.indexOf(' ') + 1).split(",")) {
            set.add(capability);
        }
        supported.put(protocol, set);

        for (Set<String> tmp : supported.values()) {
            if (tmp == null) {
                return;
            }
        }
        initialised = true;
    }

    private void parseTerminate(String text) {
        running = false;
        for (KismetListener listener : listeners) {
            listener.onTerminated(text);
        }
    }

    private void parseProtocol(String protocol, String value) {
        List<String> capabilities = subscribed.get(protocol);
        List<String> values = this.split(value);

        if (capabilities == null) {
            return; // not subscribed to protocol
        }

        synchronized (listeners) {
            for (KismetListener listener : listeners) {
                Set<Class> messageTypes = listener.subscriptions.get(protocol);
                if (messageTypes != null) {
                    for (Class messageType : messageTypes) {
                        try {
                            KismetMessage message = (KismetMessage) messageType.newInstance();
                            for (String capability : listener.capabilities.get(messageType)) {
                                for (Method method : messageType.getMethods()) {
                                    Capability annotation = (Capability) method.getAnnotation(Capability.class);
                                    if (annotation != null && capability.equals(annotation.value()) && method.getParameterTypes().length == 1) {
                                        method.invoke(message, this.coerce(method.getParameterTypes()[0], values.get(capabilities.indexOf(capability))));
                                        break;
                                    }
                                }
                            }
                            listener.onMessage(message);
                        } catch (InstantiationException ex) {
                        } catch (IllegalAccessException ex) {
                        } catch (InvocationTargetException ex) {
                        }
                    }
                }
            }
        }

    }


    //控制???
    private Object coerce(Class target, String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        /*值的真假*/
        if (target.isAssignableFrom(boolean.class) || target.isAssignableFrom(Boolean.class)) {
            if ("1".equals(value) || "true".equalsIgnoreCase(value) || "t".equalsIgnoreCase(value)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }

        //分析target后参数的类型
        if (target.isAssignableFrom(double.class) || target.isAssignableFrom(Double.class)) {
            return Double.parseDouble(value);
        }

        if (target.isAssignableFrom(byte.class) || target.isAssignableFrom(Byte.class)) {
            return value.getBytes()[0];
        }

        if (target.isAssignableFrom(char.class) || target.isAssignableFrom(Character.class)) {
            return value.charAt(0);
        }

        if (target.isAssignableFrom(short.class) || target.isAssignableFrom(Short.class)) {
            return Short.parseShort(value);
        }

        if (target.isAssignableFrom(int.class) || target.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(value);
        }

        if (target.isAssignableFrom(long.class) || target.isAssignableFrom(Long.class)) {
            return Long.parseLong(value);
        }

        if (target.isAssignableFrom(float.class) || target.isAssignableFrom(Float.class)) {
            return Float.parseFloat(value);
        }


        if (target.isEnum()) {
            Object[] constants = target.getEnumConstants();
            for (int i = 0; i < constants.length; ++i) {
                if (constants[i] instanceof ValueEnum) {
                    if (Integer.parseInt(value) == ((ValueEnum) constants[i]).value()) {
                        return constants[i];
                    }
                }
            }

            for (int i = 0; i < constants.length; ++i) {
                if (value.equalsIgnoreCase(((Enum) constants[i]).name())) {
                    return constants[i];
                }
            }

            return constants[Integer.parseInt(value)];
        }

        if (target.isAssignableFrom(Date.class)) {
            try {
                return new Date(Long.parseLong(value) * 1000);
            } catch (NumberFormatException ex) {
            }

            try {
                return DateFormat.getInstance().parse(value);
            } catch (ParseException ex) {
            }
        }

        if (target.isAssignableFrom(InetAddress.class)) {
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException ex) {
            }
        }

        if (target.isAssignableFrom(UUID.class)) {
            try {
                return UUID.fromString(value);
            } catch (IllegalArgumentException ex) {
            }
        }

        return value;
    }



	/*
	StringBuilder是一个可变的字符序列。此类提供一个与 StringBuffer 兼容的 API，但不保证同步。
	该类被设计用作 StringBuffer 的一个简易替换，用在字符串缓冲区被单个线程使用的时候（这种情况很普遍）。
	如果可能，建议优先采用该类，因为在大多数实现中，它比 StringBuffer 要快。
	在 StringBuilder 上的主要操作是 append 和 insert 方法。
	在程序开发过程中，我们常常碰到字符串连接的情况，方便和直接的方式是通过"+"符号来实现，
	但是这种方式达到目的的效率比较低，且每执行一次都会创建一个String对象，即耗时，又浪费空间
	*/


    //根据SOH切分一个大字符串，返回值为一个string类型list
    private List<String> split(String str) {
        List<String> result = new ArrayList<String>();      //result用于存储最后的结果，全是字符
        boolean delim = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < str.length(); ++i) {        //先用current接受有效的字符最后将current中的字符逐个赋值给result
            if (str.charAt(i) == 0x01) {                //0000 0001 SOH(start of headline) 协议中的头部开始标志
                delim = !delim;
            } else if (str.charAt(i) == ' ' && !delim) {    //遇到空格 且遇到另一次SOH，说明进入另一个协议
                result.add(current.toString());            //此时current存储的为上一个协议中全部内容，加入到result中同时清空当前current存下的内容
                current.setLength(0);
            } else {
                current.append(str.charAt(i));              //正常字符，追加到current中
            }
        }

        if (current.length() > 0) {                         //之前的循环根据遇到下一个协议的SOH判断当前协议结束，对于最后一个协议没有判断依据，current最后的值即为最后一个协议内容
            result.add(current.toString());
        }
        return result;
    }

}
