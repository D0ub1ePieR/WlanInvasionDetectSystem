package cn.nuaa.spicydick.server.handler.wifiDetection.Info;

public class WifiInfo {

    public int signal;  //信号
    public String ssid; //名称
    public int securityStatus;  //安全状态
    public int encryption;      //加密
    public int intranet;        //内联网？
    public int isInWhiteList;   //是否在白名单
    public int timeout;         //超时
    public String lastSeen;     //最后出现
    public int connectionsNum;  //连接设备数
    public String bssid;        //mac地址
    public String flow;         //流量

    public int getSignal() { return signal; }
    public String getSsid() { return ssid; }
    public int getSecurityStatus() { return securityStatus; }
    public int getEncryption() { return encryption; }
    public int getIntranet() { return intranet; }
    public int getIsInWhiteList() { return isInWhiteList; }
    public int getTimeout() { return timeout; }
    public String getLastSeen() { return lastSeen; }
    public int getConnectionsNum() { return connectionsNum; }
    public String getBssid() { return bssid; }
    public String getFlow() { return flow; }

    public void setSignal(int signal) { this.signal = signal; }
    public void setSsid(String ssid) { this.ssid = ssid; }
    public void setSecurityStatus(int securityStatus) { this.securityStatus = securityStatus; }
    public void setEncryption(int encryption) { this.encryption = encryption; }
    public void setIntranet(int intranet) { this.intranet = intranet; }
    public void setIsInWhiteList(int isInWhiteList) { this.isInWhiteList = isInWhiteList; }
    public void setTimeout(int timeout) { this.timeout = timeout; }
    public void setLastSeen(String lastSeen) { this.lastSeen = lastSeen; }
    public void setConnectionsNum(int connectionsNum) { this.connectionsNum = connectionsNum; }
    public void setBssid(String bssid) { this.bssid = bssid; }
    public void setFlow(String flow) { this.flow = flow; }

}
