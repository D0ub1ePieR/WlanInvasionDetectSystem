/**
 * za.co.towerman.jkismet.KismetListener
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import za.co.towerman.jkismet.message.KismetMessage;

/**
 *
 * @author espeer
 */

// 该方法从Jkismet中接受参数，进行各参数的初始化配置后启动监听服务
// 主要方法为public void subscribe(Class messageType, String fields)
// messageType表示服务的信息类型，field表示包含的参数
public abstract class KismetListener {
    KismetConnection connection = null;    // 连接
    Map<String, Set<Class>> subscriptions = new HashMap<String, Set<Class>>();   // 启动服务
    Map<Class, Set<String>> capabilities = new HashMap<Class, Set<String>>();    // 参数

    // 根据传入的参数启动服务，messageType表示服务的信息类型，field表示包含的参数
    // 具体调用在文件JKismet中
    // 如 listener.subscribe(BatteryMessage.class, "percentage, mainsPowered, charging, remainingSeconds");

    public void subscribe(Class messageType, String fields) throws IOException {

        // 判断信息格式是否合法
        if (!KismetMessage.class.isAssignableFrom(messageType)) {
            throw new IllegalArgumentException("invalid message type: must implement KismetMessage interface");
        }

        Protocol protocol = (Protocol) messageType.getAnnotation(Protocol.class);
        // 判断协议是否合法
        if (protocol == null) {
            throw new IllegalArgumentException("invalid message type: must declare protocol via annotation");
        }

        // 检测支持的协议和服务
        if (connection != null) {
            this.checkServerSupport(connection);
        }

        // 设置信息类型
        Set<Class> messageSet = subscriptions.get(protocol.value());

        if (messageSet == null) {
            messageSet = new HashSet<Class>();
            subscriptions.put(protocol.value(), messageSet);
        }
        messageSet.add(messageType);

        //设置参数类型
        Set<String> fieldSet = capabilities.get(messageType);

        if (fieldSet == null) {
            fieldSet = new HashSet<String>();
            capabilities.put(messageType, fieldSet);
        }

        // 以','为间隔分解传入的参数字符串，依次设置参数

        for (String field : fields.split(",")) {
            field = field.trim();

            // 设置参数
            Capability capability = this.findCapability(messageType, field);

            if (capability == null) {
                throw new IllegalArgumentException("invalid field: " + field);
            }

            fieldSet.add(capability.value());
        }

        // 连接建立成功，服务启动
        if (connection != null) {
            connection.updateServerSubscriptions();
        }
    }


    // 获取所有参数
    private Capability findCapability(Class target, String field) {
        for (Method method : target.getMethods()) {
            Capability capability = method.getAnnotation(Capability.class);
            if (capability != null && method.getName().equalsIgnoreCase("set" + field)) {
                return capability;
            }
        }
        return null;
    }

    //检验是否支持相关协议和参数
    void checkServerSupport(KismetConnection c) {
        for (Entry<String, Set<Class>> entry : subscriptions.entrySet()) {
            if (!c.getSupportedProtocols().contains(entry.getKey())) {
                throw new IllegalArgumentException("server does not support protocol: " + entry.getKey());
            }

            for (Class messageType : entry.getValue()) {
                for (String capability : capabilities.get(messageType)) {
                    if (!c.getSupportedCapabilities(entry.getKey()).contains(capability)) {
                        throw new IllegalArgumentException("server does not support capability: \"" + capability + "\" for protocol: \"" + entry.getKey() + "\"");
                    }
                }
            }
        }
    }

    public abstract void onMessage(KismetMessage message);

    public abstract void onTerminated(String reason);
}
