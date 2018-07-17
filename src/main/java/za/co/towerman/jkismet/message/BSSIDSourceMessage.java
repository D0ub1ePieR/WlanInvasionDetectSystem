/**
 * za.co.towerman.jkismet.message.BSSIDSRCMessage
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

import java.util.Date;
import java.util.UUID;

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 *
 * @author espeer
 */

/**
 * MAC地址源信息格式：
 * "BSSIDSourceMessage{MAC,uuid,lastTime,packets};
 * {MAC地址，唯一识别码，最近出现时间，包}
 * */
@Protocol("BSSIDSRC")
public class BSSIDSourceMessage implements KismetMessage {

    private String mac;
    private UUID uuid;
    private Date lastTime;
    private int packets;

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

    @Capability("bssid")
    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getPackets() {
        return packets;
    }

    @Capability("numpackets")
    public void setPackets(int packets) {
        this.packets = packets;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Capability("uuid")
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "BSSIDSourceMessage{" + "mac=" + mac + ", uuid=" + uuid + ", lastTime=" + lastTime + ", packets=" + packets + '}';
    }


}
