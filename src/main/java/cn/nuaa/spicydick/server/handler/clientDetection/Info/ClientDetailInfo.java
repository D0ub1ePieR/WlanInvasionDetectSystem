package cn.nuaa.spicydick.server.handler.clientDetection.Info;

public class ClientDetailInfo {
    public String wifiRemark;       // 设备连接的热点备注名
    public String wifiSsid;         // 热点名称
    public String wifiBssid;        // 热点MAC地址
    public String clientRemark;     // 终端设备名
    public String singal;           // 信号强度
    public int encryption;          // 是否加密
    public String lastSeen;         // 最近出现时间
    public int isInWhiteList;       // 是否在白名单
    public int intranet;            // 是否接入内网
    public String clientBssid;      // 终端MAC地址
    public int timeout;             // 连接状态（是否超时）
    public String timeoutTime;      // 超时时间
    public String flow;             // 流量

    public String getWifiRemark() { return wifiRemark; }
    public String getWifiSsid() { return wifiSsid; }
    public String getWifiBssid() { return wifiBssid; }
    public String getClientRemark() { return clientRemark; }
    public String getSingal() { return singal; }
    public int getEncryption() { return encryption; }
    public String getLastSeen() { return lastSeen; }
    public int getIsInWhiteList() { return isInWhiteList; }
    public int getIntranet() { return intranet; }
    public String getClientBssid() { return clientBssid; }
    public int getTimeout() { return timeout; }
    public String getTimeoutTime() { return timeoutTime; }
    public String getFlow() { return flow; }

    public void setWifiRemark(String wifiRemark) { this.wifiRemark = wifiRemark; }
    public void setWifiSsid(String wifiSsid) { this.wifiSsid = wifiSsid; }
    public void setWifiBssid(String wifiBssid) { this.wifiBssid = wifiBssid; }
    public void setClientRemark(String clientRemark) { this.clientRemark = clientRemark; }
    public void setSingal(String singal) { this.singal = singal; }
    public void setEncryption(int encryption) { this.encryption = encryption; }
    public void setLastSeen(String lastSeen) { this.lastSeen = lastSeen; }
    public void setIsInWhiteList(int isInWhiteList) { this.isInWhiteList = isInWhiteList; }
    public void setIntranet(int intranet) { this.intranet = intranet; }
    public void setClientBssid(String clientBssid) { this.clientBssid = clientBssid; }
    public void setTimeout(int timeout) { this.timeout = timeout; }
    public void setTimeoutTime(String timeoutTime) { this.timeoutTime = timeoutTime; }
    public void setFlow(String flow) { this.flow = flow; }
}
