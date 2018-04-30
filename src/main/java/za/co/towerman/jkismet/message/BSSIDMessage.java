/**
 * za.co.towerman.jkismet.message.BSSIDMessage
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
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import za.co.towerman.jkismet.Capability;
import za.co.towerman.jkismet.Protocol;

/**
 * @author espeer
 */

/*
 * BSSID MAC地址信息
 *
 *
 * */
@Protocol("BSSID")
public class BSSIDMessage implements KismetMessage {

    public enum NetworkType implements ValueEnum {
        MANAGED(0),
        ADHOC(1),
        PROBE(2),
        TURBOCELL(3),
        DATA(4),
        MIXED(255),
        REMOVE(256);

        private int value;

        private NetworkType(int value) {
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }
    }

    public enum AddressType {
        UNKNOWN,
        FACTORY_DEFAULT,
        UDP_TCP,
        ARP,
        DHCP,
        AGGREGATED
    }

    public enum CarrierType {
        UNKNOWN,
        IEEE_80211B,
        IEEE_80211BPLUS,
        IEEE_80211A,
        IEEE_80211G,
        IEEE_80211FHSS,
        IEEE_80211DSSS,
        IEEE_80211N20,
        IEEE_80211N40
    }

    public enum EncodingType {
        UNKNOWN,
        CCK,
        PBCC,
        OFDM,
        DYNAMIC_CCK,
        GFSK
    }

    public class SpectrumUtilization {//频道利用
        private int frequency;
        private int packets;

        public SpectrumUtilization(int frequency, int packets) {
            this.frequency = frequency;
            this.packets = packets;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public int getPackets() {
            return packets;
        }

        public void setPackets(int packets) {
            this.packets = packets;
        }

        @Override
        public String toString() {
            return "SpectrumUtilization{" + "frequency=" + frequency + ", packets=" + packets + '}';
        }
    }

    private String mac;
    private NetworkType networkType;
    private AddressType addressType;
    private int channel;
    private List<SpectrumUtilization> frequencies;
    private int retries;
    private int fragments;
    private int llcPackets;
    private int dataPackets;
    private int dataPacketsNew;
    private long dataBytes;
    private int encryptedPackets;
    private int decryptedPackets;
    private int dupeIVPackets;
    private String manufacturer;
    private Date firstTime;
    private Date lastTime;
    private InetAddress networkIp;
    private InetAddress gatewayIp;
    private InetAddress netmaskIp;
    private int signalDBM;
    private int signalDBMMin;
    private int signalDBMMax;
    private int noiseDBM;
    private int noiseDBMMin;
    private int noiseDBMMax;
    private int signalRSSI;
    private int signalRSSIMin;
    private int signalRSSIMax;
    private int noiseRSSI;
    private int noiseRSSIMin;
    private int noiseRSSIMax;
    private int rateMax;
    private boolean gpsFixed;
    private double latitudeMin;
    private double latitudeMax;
    private double latitudeAggregate;
    private double latitudeBest;
    private double longitudeMin;
    private double longitudeMax;
    private double longitudeAggregate;
    private double longitudeBest;
    private double altitudeMin;
    private double altitudeMax;
    private double altitudeAggregate;
    private double altitudeBest;
    private double speedMin;
    private double speedMax;
    private Set<CarrierType> carriers;
    private Set<EncodingType> encodings;
    private Set<CryptoType> cryptographies;
    private Date bssTimeStamp;
    private String cdpDevice;
    private String cdpPort;

    public int getChannel() {
        return channel;
    }

    @Capability("channel")
    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getMac() {
        return mac;
    }

    @Capability("bssid")
    public void setMac(String mac) {
        this.mac = mac;
    }

    @Capability("type")
    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    public NetworkType getNetworkType() {
        return networkType;
    }

    public int getDataPackets() {
        return dataPackets;
    }

    @Capability("datapackets")
    public void setDataPackets(int dataPackets) {
        this.dataPackets = dataPackets;
    }

    public int getEncryptedPackets() {
        return encryptedPackets;
    }

    @Capability("cryptpackets")
    public void setEncryptedPackets(int encryptedPackets) {
        this.encryptedPackets = encryptedPackets;
    }

    public int getLlcPackets() {
        return llcPackets;
    }

    @Capability("llcpackets")
    public void setLlcPackets(int llcPackets) {
        this.llcPackets = llcPackets;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Capability("manuf")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public AddressType getAddressType() {
        return addressType;
    }

    @Capability("atype")
    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public InetAddress getNetworkIp() {
        return networkIp;
    }

    @Capability("rangeip")
    public void setNetworkIp(InetAddress networkIp) {
        this.networkIp = networkIp;
    }

    public InetAddress getGatewayIp() {
        return gatewayIp;
    }

    @Capability("gatewayip")
    public void setGatewayIp(InetAddress gatewayIp) {
        this.gatewayIp = gatewayIp;
    }

    public InetAddress getNetmaskIp() {
        return netmaskIp;
    }

    @Capability("netmaskip")
    public void setNetmaskIp(InetAddress netmaskIp) {
        this.netmaskIp = netmaskIp;
    }

    public int getNoiseDBM() {
        return noiseDBM;
    }

    @Capability("noise_dbm")
    public void setNoiseDBM(int noiseDBM) {
        this.noiseDBM = noiseDBM;
    }

    public int getNoiseDBMMax() {
        return noiseDBMMax;
    }

    @Capability("maxnoise_dbm")
    public void setNoiseDBMMax(int noiseDBMMax) {
        this.noiseDBMMax = noiseDBMMax;
    }

    public int getNoiseDBMMin() {
        return noiseDBMMin;
    }

    @Capability("minnoise_dbm")
    public void setNoiseDBMMin(int noiseDBMMin) {
        this.noiseDBMMin = noiseDBMMin;
    }

    public int getNoiseRSSI() {
        return noiseRSSI;
    }

    @Capability("noise_rssi")
    public void setNoiseRSSI(int noiseRSSI) {
        this.noiseRSSI = noiseRSSI;
    }

    public int getNoiseRSSIMax() {
        return noiseRSSIMax;
    }

    @Capability("maxnoise_rssi")
    public void setNoiseRSSIMax(int noiseRSSIMax) {
        this.noiseRSSIMax = noiseRSSIMax;
    }

    public int getNoiseRSSIMin() {
        return noiseRSSIMin;
    }

    @Capability("minnoise_rssi")
    public void setNoiseRSSIMin(int noiseRSSIMin) {
        this.noiseRSSIMin = noiseRSSIMin;
    }

    public int getSignalDBM() {
        return signalDBM;
    }

    @Capability("signal_dbm")
    public void setSignalDBM(int signalDBM) {
        this.signalDBM = signalDBM;
    }

    public int getSignalDBMMax() {
        return signalDBMMax;
    }

    @Capability("maxsignal_dbm")
    public void setSignalDBMMax(int signalDBMMax) {
        this.signalDBMMax = signalDBMMax;
    }

    public int getSignalDBMMin() {
        return signalDBMMin;
    }

    @Capability("minsignal_dbm")
    public void setSignalDBMMin(int signalDBMMin) {
        this.signalDBMMin = signalDBMMin;
    }

    public int getSignalRSSI() {
        return signalRSSI;
    }

    @Capability("signal_rssi")
    public void setSignalRSSI(int signalRSSI) {
        this.signalRSSI = signalRSSI;
    }

    public int getSignalRSSIMax() {
        return signalRSSIMax;
    }

    @Capability("maxsignal_rssi")
    public void setSignalRSSIMax(int signalRSSIMax) {
        this.signalRSSIMax = signalRSSIMax;
    }

    public int getSignalRSSIMin() {
        return signalRSSIMin;
    }

    @Capability("minsignal_rssi")
    public void setSignalRSSIMin(int signalRSSIMin) {
        this.signalRSSIMin = signalRSSIMin;
    }

    public int getRateMax() {
        return rateMax;
    }

    @Capability("maxseenrate")
    public void setRateMax(int rateMax) {
        this.rateMax = rateMax;
    }

    public double getAltitudeAggregate() {
        return altitudeAggregate;
    }

    @Capability("aggalt")
    public void setAltitudeAggregate(double altitudeAggregate) {
        this.altitudeAggregate = altitudeAggregate;
    }

    public double getAltitudeBest() {
        return altitudeBest;
    }

    @Capability("bestalt")
    public void setAltitudeBest(double altitudeBest) {
        this.altitudeBest = altitudeBest;
    }

    public double getAltitudeMax() {
        return altitudeMax;
    }

    @Capability("maxalt")
    public void setAltitudeMax(double altitudeMax) {
        this.altitudeMax = altitudeMax;
    }

    public double getAltitudeMin() {
        return altitudeMin;
    }

    @Capability("minalt")
    public void setAltitudeMin(double altitudeMin) {
        this.altitudeMin = altitudeMin;
    }

    public boolean isGpsFixed() {
        return gpsFixed;
    }

    @Capability("gpsfixed")
    public void setGpsFixed(boolean gpsFixed) {
        this.gpsFixed = gpsFixed;
    }

    public double getLatitudeAggregate() {
        return latitudeAggregate;
    }

    @Capability("agglat")
    public void setLatitudeAggregate(double latitudeAggregate) {
        this.latitudeAggregate = latitudeAggregate;
    }

    public double getLatitudeBest() {
        return latitudeBest;
    }

    @Capability("bestlat")
    public void setLatitudeBest(double latitudeBest) {
        this.latitudeBest = latitudeBest;
    }

    public double getLatitudeMax() {
        return latitudeMax;
    }

    @Capability("maxlat")
    public void setLatitudeMax(double latitudeMax) {
        this.latitudeMax = latitudeMax;
    }

    public double getLatitudeMin() {
        return latitudeMin;
    }

    @Capability("minlat")
    public void setLatitudeMin(double latitudeMin) {
        this.latitudeMin = latitudeMin;
    }

    public double getLongitudeAggregate() {
        return longitudeAggregate;
    }

    @Capability("agglon")
    public void setLongitudeAggregate(double longitudeAggregate) {
        this.longitudeAggregate = longitudeAggregate;
    }

    public double getLongitudeBest() {
        return longitudeBest;
    }

    @Capability("bestlon")
    public void setLongitudeBest(double longitudeBest) {
        this.longitudeBest = longitudeBest;
    }

    public double getLongitudeMax() {
        return longitudeMax;
    }

    @Capability("maxlon")
    public void setLongitudeMax(double longitudeMax) {
        this.longitudeMax = longitudeMax;
    }

    public double getLongitudeMin() {
        return longitudeMin;
    }

    @Capability("minlon")
    public void setLongitudeMin(double longitudeMin) {
        this.longitudeMin = longitudeMin;
    }

    public double getSpeedMax() {
        return speedMax;
    }

    @Capability("maxspd")
    public void setSpeedMax(double speedMax) {
        this.speedMax = speedMax;
    }

    public double getSpeedMin() {
        return speedMin;
    }

    @Capability("minspd")
    public void setSpeedMin(double speedMin) {
        this.speedMin = speedMin;
    }

    public Set<CarrierType> getCarriers() {
        return carriers;
    }

    @Capability("carrierset")
    public void setCarriers(int carriers) {
        this.carriers = EnumSet.noneOf(CarrierType.class);
        for (int i = 0; i < CarrierType.values().length; ++i) {
            if (((carriers >>> i) & 0x01) > 0) {
                this.carriers.add(CarrierType.values()[i]);
            }
        }
    }

    public Set<EncodingType> getEncodings() {
        return encodings;
    }

    @Capability("encodingset")
    public void setEncodings(int encodings) {
        this.encodings = EnumSet.noneOf(EncodingType.class);
        for (int i = 0; i < EncodingType.values().length; ++i) {
            if (((encodings >>> i) & 0x01) > 0) {
                this.encodings.add(EncodingType.values()[i]);
            }
        }
    }

    public Set<CryptoType> getCryptographies() {
        return cryptographies;
    }

    @Capability("datacryptset")
    public void setCryptographies(int cryptographies) {
        this.cryptographies = EnumSet.noneOf(CryptoType.class);
        for (int i = 0; i < CryptoType.values().length; ++i) {
            if (((cryptographies >>> i) & 0x01) > 0) {
                this.cryptographies.add(CryptoType.values()[i]);
            }
        }
    }

    public long getDataBytes() {
        return dataBytes;
    }

    @Capability("datasize")
    public void setDataBytes(long dataBytes) {
        this.dataBytes = dataBytes;
    }

    public int getDataPacketsNew() {
        return dataPacketsNew;
    }

    @Capability("newpackets")
    public void setDataPacketsNew(int dataPacketsNew) {
        this.dataPacketsNew = dataPacketsNew;
    }

    public int getDupeIVPackets() {
        return dupeIVPackets;
    }

    @Capability("dupeivpackets")
    public void setDupeIVPackets(int dupeIVPackets) {
        this.dupeIVPackets = dupeIVPackets;
    }

    public int getFragments() {
        return fragments;
    }

    @Capability("fragments")
    public void setFragments(int fragments) {
        this.fragments = fragments;
    }

    public List<SpectrumUtilization> getFrequencies() {
        return frequencies;
    }

    @Capability("freqmhz")
    public void setFrequencies(String frequency) {
        frequencies = new ArrayList<SpectrumUtilization>();
        for (String tmp : frequency.split("\\x2a")) {
            if (tmp.contains(":")) {
                String[] t = tmp.split(":");
                frequencies.add(new SpectrumUtilization(Integer.parseInt(t[0]), Integer.parseInt(t[1])));
            }
        }
    }

    public int getRetries() {
        return retries;
    }

    @Capability("retries")
    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getDecryptedPackets() {
        return decryptedPackets;
    }

    @Capability("decrypted")
    public void setDecryptedPackets(int decryptedPackets) {
        this.decryptedPackets = decryptedPackets;
    }

    public Date getBssTimeStamp() {
        return bssTimeStamp;
    }

    @Capability("bsstimestamp")
    public void setBssTimeStamp(Date bssTimeStamp) {
        this.bssTimeStamp = bssTimeStamp;
    }

    public String getCdpDevice() {
        return cdpDevice;
    }

    @Capability("cdpdevice")
    public void setCdpDevice(String cdpDevice) {
        this.cdpDevice = cdpDevice;
    }

    public String getCdpPort() {
        return cdpPort;
    }

    @Capability("cdpport")
    public void setCdpPort(String cdpPort) {
        this.cdpPort = cdpPort;
    }

    @Override
    public String toString() {
        return "BSSIDMessage{" + "mac=" + mac + ", networkType=" + networkType + ", addressType=" + addressType + ", channel=" + channel + ", frequencies=" + frequencies + ", retries=" + retries + ", fragments=" + fragments + ", llcPackets=" + llcPackets + ", dataPackets=" + dataPackets + ", dataPacketsNew=" + dataPacketsNew + ", dataBytes=" + dataBytes + ", encryptedPackets=" + encryptedPackets + ", decryptedPackets=" + decryptedPackets + ", dupeIVPackets=" + dupeIVPackets + ", manufacturer=" + manufacturer + ", firstTime=" + firstTime + ", lastTime=" + lastTime + ", networkIp=" + networkIp + ", gatewayIp=" + gatewayIp + ", netmaskIp=" + netmaskIp + ", signalDBM=" + signalDBM + ", signalDBMMin=" + signalDBMMin + ", signalDBMMax=" + signalDBMMax + ", noiseDBM=" + noiseDBM + ", noiseDBMMin=" + noiseDBMMin + ", noiseDBMMax=" + noiseDBMMax + ", signalRSSI=" + signalRSSI + ", signalRSSIMin=" + signalRSSIMin + ", signalRSSIMax=" + signalRSSIMax + ", noiseRSSI=" + noiseRSSI + ", noiseRSSIMin=" + noiseRSSIMin + ", noiseRSSIMax=" + noiseRSSIMax + ", rateMax=" + rateMax + ", gpsFixed=" + gpsFixed + ", latitudeMin=" + latitudeMin + ", latitudeMax=" + latitudeMax + ", latitudeAggregate=" + latitudeAggregate + ", latitudeBest=" + latitudeBest + ", longitudeMin=" + longitudeMin + ", longitudeMax=" + longitudeMax + ", longitudeAggregate=" + longitudeAggregate + ", longitudeBest=" + longitudeBest + ", altitudeMin=" + altitudeMin + ", altitudeMax=" + altitudeMax + ", altitudeAggregate=" + altitudeAggregate + ", altitudeBest=" + altitudeBest + ", speedMin=" + speedMin + ", speedMax=" + speedMax + ", carriers=" + carriers + ", encodings=" + encodings + ", cryptographies=" + cryptographies + ", bssTimeStamp=" + bssTimeStamp + ", cdpDevice=" + cdpDevice + ", cdpPort=" + cdpPort + '}';
    }


}
