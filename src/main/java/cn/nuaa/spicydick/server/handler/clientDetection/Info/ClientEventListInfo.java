package cn.nuaa.spicydick.server.handler.clientDetection.Info;

public class ClientEventListInfo {
    public String eventType;                // 事件类型
    public String lastOccurTime;            // 最近发生时间
    public String firstOccurTime;           // 最早发生时间
    public String eventContent;             // 时间内容
    public String relatedNetWorkName;       // 相关网络设备名
    public String relatedNetWorkAddress;    // 相关网络地址
    public String sourceInfo;               // 源信息
    public String targetInfo;               // 目标信息

    public String getEventType() { return eventType; }
    public String getLastOccurTime() { return lastOccurTime; }
    public String getFirstOccurTime() { return firstOccurTime; }
    public String getEventContent() { return eventContent; }
    public String getRelatedNetWorkName() { return relatedNetWorkName; }
    public String getRelatedNetWorkAddress() { return relatedNetWorkAddress; }
    public String getSourceInfo() { return sourceInfo; }
    public String getTargetInfo() { return targetInfo; }

    public void setEventType(String eventType) { this.eventType = eventType; }
    public void setLastOccurTime(String lastOccurTime) { this.lastOccurTime = lastOccurTime; }
    public void setFirstOccurTime(String firstOccurTime) { this.firstOccurTime = firstOccurTime; }
    public void setEventContent(String eventContent) { this.eventContent = eventContent; }
    public void setRelatedNetWorkName(String relatedNetWorkName) { this.relatedNetWorkName = relatedNetWorkName; }
    public void setRelatedNetWorkAddress(String relatedNetWorkAddress) { this.relatedNetWorkAddress = relatedNetWorkAddress; }
    public void setSourceInfo(String sourceInfo) { this.sourceInfo = sourceInfo; }
    public void setTargetInfo(String targetInfo) { this.targetInfo = targetInfo; }
}
