package cn.nuaa.spicydick.server.handler.whiteList.Info;

public class WifiWhiteListInfo {
    public String wifiBssid;    //wifimac地址
    public String wifiSsid;     //wifi名
    public String timeout;      //是否超时
    public String remark;       //备注名
    public String location;     //常用位置
    public String manager;      //负责人
    public String contact;      //联系方式

    public String getWifiBssid() { return wifiBssid; }
    public String getWifiSsid() { return wifiSsid; }
    public String getTimeout() { return timeout; }
    public String getRemark() { return remark; }
    public String getLocation() { return location; }
    public String getManager() { return manager; }
    public String getContact() { return contact; }

    public void setWifiBssid(String wifiBssid) { this.wifiBssid = wifiBssid; }
    public void setWifiSsid(String wifiSsid) { this.wifiSsid = wifiSsid; }
    public void setTimeout(String timeout) { this.timeout = timeout; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setLocation(String location) { this.location = location; }
    public void setManager(String manager) { this.manager = manager; }
    public void setContact(String contact) { this.contact = contact; }

}
