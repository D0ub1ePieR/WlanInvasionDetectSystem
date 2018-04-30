/**
 * za.co.towerman.jkismet.message.SourceMessage
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

import java.util.UUID;

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 * @author espeer
 */


/*
 * 源地址信息格式：
 * SourceMessage{iface, type, user, channel, uuid, packets, hop, dwell,
 * velocity, hopTimeSeconds, hopTimeMicroseconds, channels, error, warning};
 *  {接口,类型，用户，频道号，唯一标志位，包，跳频，停留时间，速度，跳频时间，跳频时间毫秒数，频道，错误标志位，警告}
 * */

@Protocol("SOURCE")
public class SourceMessage implements KismetMessage {

    private String iface;
    private String type;
    private String user;
    private int channel;
    private UUID uuid;
    private int packets;
    private int hop;
    private int dwell;
    private int velocity;
    private long hopTimeSeconds;
    private int hopTimeMicroseconds;
    private String channels;
    private boolean error;
    private String warning;

    public int getChannel() {
        return channel;
    }

    @Capability("channel")
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getChannels() {
        return channels;
    }

    @Capability("channellist")
    public void setChannels(String channels) {
        this.channels = channels;
    }

    public int getDwell() {
        return dwell;
    }

    @Capability("dwell")
    public void setDwell(int dwell) {
        this.dwell = dwell;
    }

    public boolean isError() {
        return error;
    }

    @Capability("error")
    public void setError(boolean error) {
        this.error = error;
    }

    public int getHop() {
        return hop;
    }

    @Capability("hop")
    public void setHop(int hop) {
        this.hop = hop;
    }

    public long getHopTimeSeconds() {
        return hopTimeSeconds;
    }

    @Capability("hop_time_sec")
    public void setHopTimeSeconds(long hopTimeSeconds) {
        this.hopTimeSeconds = hopTimeSeconds;
    }

    public int getHopTimeMicroseconds() {
        return hopTimeMicroseconds;
    }

    @Capability("hop_time_usec")
    public void setHopTimeMicroseconds(int hopTimeMicroseconds) {
        this.hopTimeMicroseconds = hopTimeMicroseconds;
    }

    public String getIface() {
        return iface;
    }

    @Capability("interface")
    public void setIface(String iface) {
        this.iface = iface;
    }

    public int getPackets() {
        return packets;
    }

    @Capability("packets")
    public void setPackets(int packets) {
        this.packets = packets;
    }

    public String getType() {
        return type;
    }

    @Capability("type")
    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    @Capability("username")
    public void setUser(String user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Capability("uuid")
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getVelocity() {
        return velocity;
    }

    @Capability("velocity")
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public String getWarning() {
        return warning;
    }

    @Capability("warning")
    public void setWarning(String warning) {
        this.warning = warning;
    }

    @Override
    public String toString() {
        return "SourceMessage{" + "iface=" + iface + ", type=" + type + ", user=" + user + ", channel=" + channel + ", uuid=" + uuid + ", packets=" + packets + ", hop=" + hop + ", dwell=" + dwell + ", velocity=" + velocity + ", hopTimeSeconds=" + hopTimeSeconds + ", hopTimeMicroseconds=" + hopTimeMicroseconds + ", channels=" + channels + ", error=" + error + ", warning=" + warning + '}';
    }

}
