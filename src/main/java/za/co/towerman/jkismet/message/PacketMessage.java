/**
 * za.co.towerman.jkismet.message.PacketMessage
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
package za.co.towerman.jkismet.message;

import java.net.InetAddress;
import java.util.Date;

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 *
 * @author espeer
 */


/**
 * 包信息格式：
 * "PacketMessage{type, subType, time, encrypted, weakIV, beaconRate, sourceMac, sourceIp, sourcePort,
 * destinationMac, destinationIp, destinationPort, bssid, ssid, prototype, sourceName, netbiosType, netbiosSource};
 *
 * */

@Protocol("PACKET")
public class PacketMessage implements KismetMessage {

    public enum Type implements ValueEnum {
        NOISE(-2),
        UNKNOWN(-1),
        MANAGEMENT(0),
        PHY(1),         //802.11物理层PHY是介质访问控制层MAC与无线介质之间的接口，它传输和接收共享无线介质上的数据帧。
        DATA(2);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }
    }

    // 管理帧类型
    public enum ManagementType implements ValueEnum {
        ASSOCIATION_REQUEST(0),         //关联请求帧
        ASSOCIATION_RESPONSE(1),        //关联响应帧
        REASSOCIATION_REQUEST(2),       //重新关联请求帧
        REASSOCIATION_RESPONSE(3),      //重新关联响应帧
        PROBE_REQUEST(4),               //探测请求帧
        PROBE_RESPONSE(5),              //探测响应帧
        BEACON(8),                      //信标帧
        ATIM(9),                        //ATIM帧，ATIM：Announcement Traffic Indication Message。通知传输指示消息
        DISACCOCIATION(10),             //解除关联帧
        AUTHENTICATION(11),             //身份认证帧
        DEAUTHENTICATION(12);           //解除认证帧

        private int value;

        private ManagementType(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }
    }

    // PHY帧（控制帧）类型
    public enum PhyType implements ValueEnum {
        PS_POLL(10),
        RTS(11),
        CTS(12),
        ACK(13),
        CF_END(14),
        CF_END_ACK(15);


        /**
         * PS_POLL省电模式一轮询，当一部移动工作站从省电模式中苏醒，便会发送一个 PS-Poll帧给接入点，以取得任何暂存帧。
         * RTS请求发送帧，可用来取得介质的控制权，以便传输「大型」帧。
         * CTS允许发送帧，起初，CTS帧仅用于应答 RTS帧，如果之前没有RTS出现，就不会产生 CTS 。后来，CTS帧被 802.11g 防护机制用来避免干扰较旧的工作站。
         *
         * CF_END CF_END_ACK
         * 当无竞争周期结束，接入点会送出一个CF-End帧，让工作站脱离PCF访问规则，
         * 同时以DCF开始进行基于竞争的服务。如果接入点必须同时响应之前所收到的数据，
         * 则可用CF-End+CF-ACK帧于结束无竞争周期的同时顺便加以响应。
        */
        private int value;

        private PhyType(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }
    }

    // 数据帧类型
    public enum DataType implements ValueEnum {
        DATA(0),
        DATA_CF_ACK(1),
        DATA_CF_POLL(2),
        DATA_CF_ACK_POLL(3),

        NULL(4),
        NULL_CF_ACK(5),
        NULL_CF_ACK_POLL(6),


        QOS_DATA(8),                // Qos服务质量
        QOS_DATA_CF_ACK(9),
        QOS_DATA_CF_POLL(10),
        QOS_DATA_CF_ACK_POLL(11),
        QOS_NULL(12),
        QOS_NULL_CF_POLL(14),
        QOS_NULL_CF_ACK_POLL(15);

        private int value;

        private DataType(int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }


    private Type type;                  //包类型
    private int subType;                //子类型
    private Date time;                  //时间
    private boolean encrypted;          //是否加密
    private boolean weakIV;             //是否具有虚弱向量

    /**
     * 使用WEP加密的时候，WLAN设备对于每一个报文都会使用初始化向量（IV，Initialization Vector），
     * IV和Key一起作为输入来生成Key Stream，使相同密钥产生不同加密效果。当一个WEP报文被发送时，
     * 用于加密报文的IV也作为报文头的一部分被发送。如果WLAN设备使用不安全的方法生成IV，
     * 例如始终使用固定的IV，就可能会暴露共享的密钥，如果潜在的攻击者获得了共享的密钥，攻击者将能够控制网络资源。
     * 检测IDS攻击可以通过识别每个WEP报文的IV来预防这种攻击，当一个Weak IV的报文被检测到时，这个检测将立刻被记录到日志中。
     * */

    private int beaconRate;                 //信标帧速率
    private String sourceMac;               //源mac地址
    private InetAddress sourceIp;           //源IP
    private int sourcePort;                 //源端口
    private String destinationMac;          //目的mac地址
    private InetAddress destinationIp;      //目的IP
    private int destinationPort;            //目的端口
    private String bssid;                   //路由广播地址
    private String ssid;                    //路由mac地址
    private String prototype;               //协议类型
    private String sourceName;              //源地址名
    private int netbiosType;                //NetBIOS是网络的基本输入输出系统。
    //NetBIOS 定义了一种软件接口以及在应用程序和连接介质之间提供通信接口的标准方法
    private String netbiosSource;           //netbios源

    public Type getType() {
        return type;
    }

    @Capability("type")
    public void setType(Type type) {
        this.type = type;
    }

    public Enum getSubType() {
        Enum[] values = null;
        switch (type) {
            case MANAGEMENT: {
                values = ManagementType.values();
                break;
            }
            case DATA: {
                values = DataType.values();
                break;
            }
            case PHY: {
                values = PhyType.values();
                break;
            }
        }

        for (Enum value : values) {
            if (subType == ((ValueEnum) value).value()) {
                return value;
            }
        }

        return null;
    }

    @Capability("subtype")
    public void setSubType(int subType) {
        this.subType = subType;
    }

    public Date getTime() {
        return time;
    }

    @Capability("timesec")
    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    @Capability("encrypted")
    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean isWeakIV() {
        return weakIV;
    }

    @Capability("weak")
    public void setWeakIV(boolean weakIV) {
        this.weakIV = weakIV;
    }

    public int getBeaconRate() {
        return beaconRate;
    }

    @Capability("beaconrate")
    public void setBeaconRate(int beaconRate) {
        this.beaconRate = beaconRate;
    }

    public String getBssid() {
        return bssid;
    }

    @Capability("bssid")
    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getDestinationMac() {
        return destinationMac;
    }

    @Capability("destmac")
    public void setDestinationMac(String destinationMac) {
        this.destinationMac = destinationMac;
    }

    public String getSourceMac() {
        return sourceMac;
    }

    @Capability("sourcemac")
    public void setSourceMac(String sourceMac) {
        this.sourceMac = sourceMac;
    }

    public String getSsid() {
        return ssid;
    }

    @Capability("ssid")
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public InetAddress getDestinationIp() {
        return destinationIp;
    }

    @Capability("destip")
    public void setDestinationIp(InetAddress destinationIp) {
        this.destinationIp = destinationIp;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    @Capability("destport")
    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public InetAddress getSourceIp() {
        return sourceIp;
    }

    @Capability("sourceip")
    public void setSourceIp(InetAddress sourceIp) {
        this.sourceIp = sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    @Capability("sourceport")
    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getNetbiosSource() {
        return netbiosSource;
    }

    @Capability("nbsource")
    public void setNetbiosSource(String netbiosSource) {
        this.netbiosSource = netbiosSource;
    }

    public int getNetbiosType() {
        return netbiosType;
    }

    @Capability("nbtype")
    public void setNetbiosType(int netbiosType) {
        this.netbiosType = netbiosType;
    }

    public String getPrototype() {
        return prototype;
    }

    @Capability("protoype")
    public void setPrototype(String prototype) {
        this.prototype = prototype;
    }

    public String getSourceName() {
        return sourceName;
    }

    @Capability("sourcename")
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public String toString() {
        return "PacketMessage{" + "type=" + type + ", subType=" + subType + ", time=" + time + ", encrypted=" + encrypted + ", weakIV=" + weakIV + ", beaconRate=" + beaconRate + ", sourceMac=" + sourceMac + ", sourceIp=" + sourceIp + ", sourcePort=" + sourcePort + ", destinationMac=" + destinationMac + ", destinationIp=" + destinationIp + ", destinationPort=" + destinationPort + ", bssid=" + bssid + ", ssid=" + ssid + ", prototype=" + prototype + ", sourceName=" + sourceName + ", netbiosType=" + netbiosType + ", netbiosSource=" + netbiosSource + '}';
    }

}
