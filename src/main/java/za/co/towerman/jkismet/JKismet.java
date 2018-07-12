/**
 * za.co.towerman.jkismet.JKismet
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

/*
 *
 *
 * */
package za.co.towerman.jkismet;

import java.io.IOException;

import za.co.towerman.jkismet.message.AlertMessage;
import za.co.towerman.jkismet.message.BSSIDMessage;
import za.co.towerman.jkismet.message.BSSIDSourceMessage;
import za.co.towerman.jkismet.message.BatteryMessage;
import za.co.towerman.jkismet.message.InfoMessage;
import za.co.towerman.jkismet.message.KismetMessage;
import za.co.towerman.jkismet.message.PacketMessage;
import za.co.towerman.jkismet.message.PluginMessage;
import za.co.towerman.jkismet.message.SSIDMessage;
import za.co.towerman.jkismet.message.SourceMessage;
import za.co.towerman.jkismet.message.StatusMessage;
import za.co.towerman.jkismet.message.TimeMessage;

/**
 *
 * @author espeer
 */

public class JKismet {

    public static void main(String[] args) throws IOException {
        //KismetConnection conn = new KismetConnection(args[0], Integer.parseInt(args[1])); //建立端口连接
        KismetConnection conn = new KismetConnection("10.211.55.15", 2501);    // 暂时使用假数据建立端口连接

        System.out.println("Server: " + conn.getServerName() + " Started: " + conn.getStartTime());// 输出服务名和时间

        // 实现一个监听类的实例，同时实现方法onMessage(),onTerminated()
        KismetListener listener = new KismetListener() {                      // 开始监听网络

            @Override
            public void onMessage(KismetMessage message) {                    // 输出信息
                System.out.println(message);
            }

            @Override
            public void onTerminated(String reason) {                        // 显示监听停止原因
                System.out.println("Connection terminated by server: " + reason);
            }
        };

        //开启监听功能，根据协议分析收到的包
        listener.subscribe(TimeMessage.class, "time");
        listener.subscribe(BatteryMessage.class, "percentage, mainsPowered, charging, remainingSeconds");
        listener.subscribe(StatusMessage.class, "flags, text");
        listener.subscribe(AlertMessage.class, "time, timeMicroseconds, header, bssid, source, destination, other, channel, text");
        listener.subscribe(InfoMessage.class, "networks, packets, noise, droppedPackets, filteredPackets");
        listener.subscribe(BSSIDSourceMessage.class, "mac, packets, uuid, lastTime");
        listener.subscribe(SourceMessage.class, "iface,type,user,channel,channels,uuid,packets,hop,velocity,dwell,hopTimeSeconds,hopTimeMicroSeconds,error,warning");
        listener.subscribe(PluginMessage.class, "name, description, version, file, unloadable, root");
        listener.subscribe(SSIDMessage.class, "name, mac, type, checksum, beaconInfo, cryptographies, cloaked, firstTime, lastTime, maxRate, packets, beacons, channels");
        listener.subscribe(BSSIDMessage.class, "mac, channel, frequencies, networkType, addressType, dataBytes, carriers, encodings, cryptographies");
        listener.subscribe(PacketMessage.class, "type, subType");

        conn.register(listener);    // 建立连接

    }
}
