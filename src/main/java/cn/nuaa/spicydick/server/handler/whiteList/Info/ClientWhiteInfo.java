package cn.nuaa.spicydick.server.handler.whiteList.Info;

public class ClientWhiteInfo {
    public String clientBssid;  //终端mac地址
    public String timeout;      //超时情况，0未超时，1超时
    public String remark;       //备注名
    public String manager;      //负责人
    public String contact;      //联系方式

    public void setClientBssid(String clientBssid) { this.clientBssid = clientBssid; }
    public void setTimeout(String timeout) { this.timeout = timeout; }
    public void setRemark(String remark) { this.remark = remark; }
    public void setManager(String manager) { this.manager = manager; }
    public void setContact(String contact) { this.contact = contact; }

    public String getClientBssid() { return clientBssid; }
    public String getTimeout() { return timeout; }
    public String getRemark() { return remark; }
    public String getManager() { return manager; }
    public String getContact() { return contact; }

}
