/**
 * za.co.towerman.jkismet.message.KismetMessage
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

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 *
 * @author espeer
 */

/**
 * 警告信息格式：
 * AlertMessage{time,timeMicroseconds,header,bssid,source,destination,other,channel,text}
 * {时间，微秒数时间，头部信息，MAC地址，源地址，目的地址，可选项，信道，报文内容}
 * */

@Protocol("ALERT")
public class AlertMessage implements KismetMessage {

    private Date time;              //时间
    private int timeMicroseconds;   //微秒数时间
    private String header;          //头部信息
    private String bssid;           //MAC地址
    private String source;          //源地址
    private String destination;     //目的地址
    private String other;           //可选项
    private int channel;            //无线信道
    private String text;            //报文

    public String getBssid() {
        return bssid;
    }

    @Capability("bssid")
    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getChannel() {
        return channel;
    }

    @Capability("channel")
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getDestination() {
        return destination;
    }

    @Capability("dest")
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHeader() {
        return header;
    }

    @Capability("header")
    public void setHeader(String header) {
        this.header = header;
    }

    public String getOther() {
        return other;
    }

    @Capability("other")
    public void setOther(String other) {
        this.other = other;
    }

    public String getSource() {
        return source;
    }

    @Capability("source")
    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    @Capability("text")
    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    @Capability("sec")
    public void setTime(Date time) {
        this.time = time;
    }

    public int getTimeMicroseconds() {
        return timeMicroseconds;
    }

    @Capability("usec")
    public void setTimeMicroseconds(int timeMicroseconds) {
        this.timeMicroseconds = timeMicroseconds;
    }

    @Override
    public String toString() {
        return "AlertMessage{" + "time=" + time + ", timeMicroseconds=" + timeMicroseconds + ", header=" + header + ", bssid=" + bssid + ", source=" + source + ", destination=" + destination + ", other=" + other + ", channel=" + channel + ", text=" + text + '}';
    }
}
