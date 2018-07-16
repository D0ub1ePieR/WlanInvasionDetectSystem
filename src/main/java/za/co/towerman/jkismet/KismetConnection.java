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

    private final Map<String, Set<String>> supported = new HashMap<String, Set<String>>();      //supported
    private final List<KismetListener> listeners = new LinkedList<KismetListener>();       //listeners
    private final Map<String, List<String>> subscribed = new HashMap<String, List<String>>();    //subscribed

    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    private boolean running = true;         //是否运行
    private boolean initialised = false;    //是否初始化

    private String version = null;          //版本号
    private String build = null;
    private Date startTime = null;          //开始时间
    private String serverName = null;       //服务名称


    // 建立连接，主机名和端口号为参数
    public KismetConnection(String host, int port) throws IOException {
        socket = new Socket(host, port);

        // 新建字节输入输出流用于传输数据
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        out.write("!0 REMOVE TIME\r\n");
        out.flush();

        while (version == null || !initialised) {
            parse(in.readLine());
        }


        // 多线程运行
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


    // 生效连接，在连接建立后检查各参数是否合法并使其生效
    public void register(KismetListener listener) throws IOException {
        if (listener.connection != null) {
            throw new IllegalArgumentException("listener already bound to connection: " + listener.connection.socket);
        }

        listener.checkServerSupport(this);      // 检查支持的服务

        synchronized (listeners) {                 // 设置监听模块
            listeners.add(listener);
        }

        listener.connection = this;

        this.updateServerSubscriptions();          // 生效连接
    }

    // 失效连接，对已生效的连接进行失效处理
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

    // 生效连接具体方法
    void updateServerSubscriptions() throws IOException {
        synchronized (listeners) {
            Map<String, Set<String>> needed = new HashMap<String, Set<String>>();

            // 对于每一个监听模块，设置其中包含的参数以及信息类型
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

            // 设置协议
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


    // 根据输入字符串前若干位判断所属报文类型并进行相应的分析报文操作
    private void parse(String line) throws IOException {

        if (line.startsWith("*KISMET: ") && line.length() > 9) {    // KISMET报文标志
            parseKismet(line.substring(9));                         // 去除前9位标志，留下后面的报文内容，解析报文内容
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

    // 解析kismet报文内容
    private void parseKismet(String kismet) {                            //kismet格式
        List<String> values = this.split(kismet);                        //拆分kismet内容字符串
        version = values.get(0);                                         //第0位为版本号
        startTime = new Date(Long.parseLong(values.get(1)) * 1000);      //第1位表示开始时间，将字符转为long型，毫秒数转为秒乘1000
        serverName = values.get(2);                                      //第2位表示服务名
        build = values.get(3);                                           //第3位表示？？？
    }

    // 解析ACK报文内容，暂时忽略该功能
    private void parseAck(String ack) {
        // ignored for now (not very robust)
    }

    // 解析错误信息报文内容，暂时忽略该功能
    private void parseError(String error) {
        // ignored for now (not very robust)
    }

    // 解析协议组
    private void parseProtocols(String protocols) throws IOException {

        // 由split方法根据','切分字符串，得到传入的所有协议，全部加入到表示支持协议的supported中并将相关信息写入字符流，即输出
        for (String protocol : protocols.split(",")) {
            supported.put(protocol, null);
            out.write("!0 CAPABILITY " + protocol + "\r\n");
        }
        out.flush();
    }

    // 解析相关参数报文内容
    private void parseCapabilities(String capabilities) {

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

    // 解析终止报文内容
    private void parseTerminate(String text) {
        running = false;    // 运行位置false
        // 对所有监听模块进行终止并传入终止信息
        for (KismetListener listener : listeners) {
            listener.onTerminated(text);
        }
    }


    // 解析单个协议及其具体内容
    // 形如如下字符船内容
    // BatteryMessage.class, "percentage, mainsPowered, charging, remainingSeconds"
    // StatusMessage.class, "flags, text"
    // BSSIDSourceMessage.class, "mac, packets, uuid, lastTime"
    // 提取出出message类型数和参数capacity数并调用coerce解析
    private void parseProtocol(String protocol, String value) {
        List<String> capabilities = subscribed.get(protocol);
        List<String> values = this.split(value);

        if (capabilities == null) {
            return;                         // not subscribed to protocol
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
                                    // 通过注解解析后服务和参数不为空，调用coerce方法解析
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


    // 在解析协议具体内容时调用，根据传入的参数target分析所属服务类型，根据参数value分析服务中包含的参数内容
    private Object coerce(Class target, String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        // 分析target类型并做相应处理
        if (target.isAssignableFrom(boolean.class) || target.isAssignableFrom(Boolean.class)) {
            if ("1".equals(value) || "true".equalsIgnoreCase(value) || "t".equalsIgnoreCase(value)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }

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

        // 枚举类型
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

        // IP地址类型
        if (target.isAssignableFrom(InetAddress.class)) {
            try {
                return InetAddress.getByName(value);
            } catch (UnknownHostException ex) {
            }
        }

        // 唯一识别码类型
        if (target.isAssignableFrom(UUID.class)) {
            try {
                return UUID.fromString(value);
            } catch (IllegalArgumentException ex) {
            }
        }

        return value;
    }


    //根据SOH切分一个大字符串，返回值为一个string类型list
    private List<String> split(String str) {
        List<String> result = new ArrayList<String>();      //result用于存储最后的结果，全是字符
        boolean delim = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < str.length(); ++i) {            //先用current接受有效的字符最后将current中的字符逐个赋值给result
            if (str.charAt(i) == 0x01) {                    //0000 0001 SOH(start of headline) 协议中的头部开始标志
                delim = !delim;
            } else if (str.charAt(i) == ' ' && !delim) {    //遇到空格 且遇到另一次SOH，说明进入另一个协议
                result.add(current.toString());             //此时current存储的为上一个协议中全部内容，加入到result中同时清空当前current存下的内容
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
