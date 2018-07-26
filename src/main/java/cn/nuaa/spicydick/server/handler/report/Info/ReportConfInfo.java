package cn.nuaa.spicydick.server.handler.report.Info;

// 报表配置信息
public class ReportConfInfo {
    public boolean wifiList;        // Wifi热点列表
    public boolean clientList;      // 无线终端列表
    public boolean warningList;     // 警告信息列表
    public boolean wifiWhiteList;   // Wifi白名单列表
    public boolean clientWhiteList; // 无线终端白名单列表

    public boolean isWifiList() {
        return wifiList;
    }

    public boolean isClientList() {
        return clientList;
    }

    public boolean isWarningList() {
        return warningList;
    }

    public boolean isWifiWhiteList() {
        return wifiWhiteList;
    }

    public boolean isClientWhiteList() {
        return clientWhiteList;
    }

    public void setWifiList(boolean wifiList) {
        this.wifiList = wifiList;
    }

    public void setClientList(boolean clientList) {
        this.clientList = clientList;
    }

    public void setWarningList(boolean warningList) {
        this.warningList = warningList;
    }

    public void setWifiWhiteList(boolean wifiWhiteList) {
        this.wifiWhiteList = wifiWhiteList;
    }

    public void setClientWhiteList(boolean clientWhiteList) {
        this.clientWhiteList = clientWhiteList;
    }
}
