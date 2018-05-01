package cn.nuaa.spicydick.server.handler.wifiDetection.Info;

public class OnlineWhiteListWifiInfo {
    public String wifiSignal;   //wifi信号
    public String wifiSsid;     //wifi名称
    public String company;      //设备厂商
    public String securityStatus;//安全状态
    public String wifiBssid;    //wifimac地址

    public String getWifiSignal() { return wifiSignal; }
    public String getWifiSsid() { return wifiSsid; }
    public String getCompany() { return company; }
    public String getSecurityStatus() { return securityStatus; }
    public String getWifiBssid() { return wifiBssid; }

    public void setWifiSignal(String wifiSignal) { this.wifiSignal = wifiSignal; }
    public void setWifiSsid(String wifiSsid) { this.wifiSsid = wifiSsid; }
    public void setCompany(String company) { this.company = company; }
    public void setSecurityStatus(String securityStatus) { this.securityStatus = securityStatus; }
    public void setWifiBssid(String wifiBssid) { this.wifiBssid = wifiBssid; }


}

