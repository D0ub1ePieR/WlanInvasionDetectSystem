package cn.nuaa.spicydick.server.handler.whiteList.Info;

//单条记录结果
public class singleResult {
    public int singCode;            //单条记录错误码 0:插入成功；-105:该bssid热点已经在白名单中
    public String singleMessage;    //添加至白名单成功或失败描述信息

    public int getSingCode() { return singCode; }
    public String getSingleMessage() { return singleMessage; }

    public void setSingCode(int singCode) { this.singCode = singCode; }
    public void setSingleMessage(String singleMessage) { this.singleMessage = singleMessage; }
}
