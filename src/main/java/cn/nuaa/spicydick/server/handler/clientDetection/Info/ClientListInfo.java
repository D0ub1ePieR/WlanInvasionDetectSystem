package cn.nuaa.spicydick.server.handler.clientDetection.Info;

// 终端列表信息
public class ClientListInfo {
    public int clientSignal;        // 信号强度
    public String accessPointSsid;  // 接入点名称
    public String accessPointBssid; // 接入点MAC地址
    public int securityStatus;      // 安全状态
    public int connectionStatus;    // 连接状态（0未超时，1超时）
    public int intranet;            // 接入内网（0未接入，1接入）
    public int isInWhiteList;       // 是否在白名单
    public String clientBssid;      // 终端MAC地址
    public String lastSeen;         // 最近出现时间

    public int getClientSignal() { return clientSignal; }
    public String getAccessPointSsid() { return accessPointSsid; }
    public String getAccessPointBssid() { return accessPointBssid; }
    public int getSecurityStatus() { return securityStatus; }
    public int getConnectionStatus() { return connectionStatus; }
    public int getIntranet() { return intranet; }
    public int getIsInWhiteList() { return isInWhiteList; }
    public String getClientBssid() { return clientBssid; }
    public String getLastSeen() { return lastSeen; }

    public void setClientSignal(int clientSignal) { this.clientSignal = clientSignal; }
    public void setAccessPointSsid(String accessPointSsid) { this.accessPointSsid = accessPointSsid; }
    public void setAccessPointBssid(String accessPointBssid) { this.accessPointBssid = accessPointBssid; }
    public void setSecurityStatus(int securityStatus) { this.securityStatus = securityStatus; }
    public void setConnectionStatus(int connectionStatus) { this.connectionStatus = connectionStatus; }
    public void setIntranet(int intranet) { this.intranet = intranet; }
    public void setIsInWhiteList(int isInWhiteList) { this.isInWhiteList = isInWhiteList; }
    public void setClientBssid(String clientBssid) { this.clientBssid = clientBssid; }
    public void setLastSeen(String lastSeen) { this.lastSeen = lastSeen; }

}
