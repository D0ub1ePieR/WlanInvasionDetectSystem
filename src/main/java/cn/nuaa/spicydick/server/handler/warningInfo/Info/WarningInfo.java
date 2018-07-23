package cn.nuaa.spicydick.server.handler.warningInfo.Info;

public class WarningInfo {
    public String eventType;                // 警告时间类型
    public String lastOccurTime;            // 最近发生时间
    public String firstOccurTime;           // 首次发生时间
    public String eventContent;             // 事件内容
    public String relatedNetworkName;       // 相关网络名
    public String relatedNetworkAddress;    // 相关网络地址
    public String sourceInfo;               // 源信息
    public String targetInfo;               // 目的信息

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLastOccurTime() {
        return lastOccurTime;
    }

    public void setLastOccurTime(String lastOccurTime) {
        this.lastOccurTime = lastOccurTime;
    }

    public String getFirstOccurTime() {
        return firstOccurTime;
    }

    public void setFirstOccurTime(String firstOccurTime) {
        this.firstOccurTime = firstOccurTime;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public String getRelatedNetworkName() {
        return relatedNetworkName;
    }

    public void setRelatedNetworkName(String relatedNetworkName) {
        this.relatedNetworkName = relatedNetworkName;
    }

    public String getRelatedNetworkAddress() {
        return relatedNetworkAddress;
    }

    public void setRelatedNetworkAddress(String relatedNetworkAddress) {
        this.relatedNetworkAddress = relatedNetworkAddress;
    }

    public String getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public String getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(String targetInfo) {
        this.targetInfo = targetInfo;
    }


}