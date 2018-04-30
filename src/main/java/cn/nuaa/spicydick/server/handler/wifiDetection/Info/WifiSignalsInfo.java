package cn.nuaa.spicydick.server.handler.wifiDetection.Info;

public class WifiSignalsInfo {
    public  int wifiSignal; //信号
    public String time;     //时间

    public int getWifiSignal() { return wifiSignal; }
    public String getTime() { return time; }

    public void setWifiSignal(int wifiSignal) { this.wifiSignal = wifiSignal; }
    public void setTime(String time) { this.time = time; }

}
