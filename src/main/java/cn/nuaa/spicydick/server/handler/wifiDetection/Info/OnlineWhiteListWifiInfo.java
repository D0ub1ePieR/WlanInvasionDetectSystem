package cn.nuaa.spicydick.server.handler.wifiDetection.Info;

public class OnlineWhiteListWifiInfo {
    public int wifiSignal;      //wifi信号
    public String wifiSsid;     //wifi名称
    public String company;      //设备厂商
    public int securityStatus;  //安全状态,0不安全，1安全
    public String wifiBssid;    //wifimac地址

    public int getWifiSignal() { return wifiSignal; }
    public String getWifiSsid() { return wifiSsid; }
    public String getCompany() { return company; }
    public int getSecurityStatus() { return securityStatus; }
    public String getWifiBssid() { return wifiBssid; }

    public void setWifiSignal(int wifiSignal) { this.wifiSignal = wifiSignal; }
    public void setWifiSsid(String wifiSsid) { this.wifiSsid = wifiSsid; }
    public void setCompany(String company) { this.company = company; }
    public void setSecurityStatus(int securityStatus) { this.securityStatus = securityStatus; }
    public void setWifiBssid(String wifiBssid) { this.wifiBssid = wifiBssid; }


}

