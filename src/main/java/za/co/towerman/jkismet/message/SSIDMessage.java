/**
 * za.co.towerman.jkismet.message.SSIDMessage
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

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 *
 * @author espeer
 */

@Protocol("SSID")
public class SSIDMessage implements KismetMessage {


    /*
     * 信道信息：
     * Channel{country, base, width, power};
     * {国家，基站，宽度，强度}
     * */
    public class Channel {
        private String country;
        private int base;
        private int width;
        private int power;

        public Channel(String country, int base, int width, int power) {
            this.country = country;
            this.base = base;
            this.width = width;
            this.power = power;
        }

        public int getBase() {
            return base;
        }

        public String getCountry() {
            return country;
        }

        public int getPower() {
            return power;
        }

        public int getWidth() {
            return width;
        }

        @Override
        public String toString() {
            return "Channel{" + "country=" + country + ", base=" + base + ", width=" + width + ", power=" + power + '}';
        }
    }

    public enum Type {
        BEACON,
        PROBE_RESPONSE,
        PROBE_REQUEST,
        FILE
    }


    /*
     * ssid信息
     * */
    private String name;    //名称
    private String mac;        //mac地址
    private Type type;        //网络类型
    private long checksum;    //校验和
    private String beaconInfo;//信标信息
    private boolean cloaked;    //是否隐藏
    private Set<CryptoType> cryptographies;//加密信息
    private Date firstTime;    //起始时间
    private Date lastTime;    //结束时间
    private int maxRate;    //最大速率
    private int beaconRate;    //信标速率
    private int packets;    //包
    private int beacons;    //信标
    private List<Channel> channels;//信道信息

    public String getBeaconInfo() {
        return beaconInfo;
    }

    @Capability("beaconinfo")
    public void setBeaconInfo(String beaconInfo) {
        this.beaconInfo = beaconInfo;
    }

    public int getBeaconRate() {
        return beaconRate;
    }

    @Capability("beaconrate")
    public void setBeaconRate(int beaconRate) {
        this.beaconRate = beaconRate;
    }

    public int getBeacons() {
        return beacons;
    }

    @Capability("beacons")
    public void setBeacons(int beacons) {
        this.beacons = beacons;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    @Capability("dot11d")
    public void setChannels(String channels) {
        this.channels = new ArrayList<Channel>();
        StringBuilder current = new StringBuilder();
        String country = null;
        Integer base = null;
        Integer width = null;
        Integer power = null;
        for (int i = 0; i < channels.length(); ++i) {
            if (channels.charAt(i) != ':' && channels.charAt(i) != '-') {
                current.append(channels.charAt(i));
            } else if (country == null) {
                country = current.toString().trim();
                current.setLength(0);
            } else if (base == null) {
                base = Integer.parseInt(current.toString());
                current.setLength(0);
            } else if (width == null) {
                width = Integer.parseInt(current.toString());
                current.setLength(0);
            } else {
                power = Integer.parseInt(current.toString());
                this.channels.add(new Channel(country, base, width, power));
                base = null;
                width = null;
                power = null;
                current.setLength(0);
            }

        }
    }

    public long getChecksum() {
        return checksum;
    }

    @Capability("checksum")
    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public boolean isCloaked() {
        return cloaked;
    }

    @Capability("cloaked")
    public void setCloaked(boolean cloaked) {
        this.cloaked = cloaked;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    @Capability("firsttime")
    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getLastTime() {
        return lastTime;
    }

    @Capability("lasttime")
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getMac() {
        return mac;
    }

    @Capability("mac")
    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getMaxRate() {
        return maxRate;
    }

    @Capability("maxrate")
    public void setMaxRate(int maxRate) {
        this.maxRate = maxRate;
    }

    public String getName() {
        return name;
    }

    @Capability("ssid")
    public void setName(String name) {
        this.name = name;
    }

    public int getPackets() {
        return packets;
    }

    @Capability("packets")
    public void setPackets(int packets) {
        this.packets = packets;
    }

    public Type getType() {
        return type;
    }

    @Capability("type")
    public void setType(Type type) {
        this.type = type;
    }

    public Set<CryptoType> getCryptographies() {
        return cryptographies;
    }

    @Capability("cryptset")
    public void setCryptographies(int cryptographies) {
        this.cryptographies = EnumSet.noneOf(CryptoType.class);
        for (int i = 0; i < CryptoType.values().length; ++i) {
            if (((cryptographies >>> i) & 0x01) > 0) {
                this.cryptographies.add(CryptoType.values()[i]);
            }
        }
    }

    @Override
    public String toString() {
        return "SSIDMessage{" + "name=" + name + ", mac=" + mac + ", type=" + type + ", checksum=" + checksum + ", beaconInfo=" + beaconInfo + ", cloaked=" + cloaked + ", cryptographies=" + cryptographies + ", firstTime=" + firstTime + ", lastTime=" + lastTime + ", maxRate=" + maxRate + ", beaconRate=" + beaconRate + ", packets=" + packets + ", beacons=" + beacons + ", channels=" + channels + '}';
    }

}
