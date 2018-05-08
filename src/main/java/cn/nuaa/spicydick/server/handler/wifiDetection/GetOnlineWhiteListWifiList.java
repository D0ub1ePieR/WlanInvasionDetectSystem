package cn.nuaa.spicydick.server.handler.wifiDetection;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.handler.wifiDetection.Info.OnlineWhiteListWifiInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
//import za.co.towerman.jkismet.message.InfoMessage;

import java.util.ArrayList;
import java.util.List;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;

//返回当前在线且在白名单的热点列表
public class GetOnlineWhiteListWifiList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //是否分页,1表示不分页
        int page = request.getParams().getValue("page") != null ? StringToInt(request.getParams().getValue("page").toString()) : 1;

        //表单验证
        if(!formDetect(page, 1, 100)){
            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object)String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        JsonObject result = new JsonObject();
        List<OnlineWhiteListWifiInfo> onlineWhiteListWifiInfoList = new ArrayList<OnlineWhiteListWifiInfo>();

        OnlineWhiteListWifiInfo Info1 = new OnlineWhiteListWifiInfo();
        Info1.setWifiSignal(66);                //wifi信号
        Info1.setWifiSsid("TPLINK1");           //wifi名称
        Info1.setCompany("huawei");             //设备厂商
        Info1.setSecurityStatus(0);             //安全状态，0表示在内网不在白名单中，不安全
        Info1.setWifiBssid("FF-FF-FF-FF-FF-FF");//wifi mac地址

        OnlineWhiteListWifiInfo Info2 = new OnlineWhiteListWifiInfo();
        Info2.setWifiSignal(66);
        Info2.setWifiSsid("TPLINK2");
        Info2.setCompany("huawei");
        Info2.setSecurityStatus(1);             //1表示在内网且在白名单中，安全
        Info2.setWifiBssid("FF-FF-FF-FF-FF-FF");

        OnlineWhiteListWifiInfo Info3 = new OnlineWhiteListWifiInfo();
        Info3.setWifiSignal(66);
        Info3.setWifiSsid("TPLINK#");
        Info3.setCompany("huawei");
        Info3.setSecurityStatus(2);             //2表示不在内网也不在白名单，外部wifi可忽略
        Info3.setWifiBssid("FF-FF-FF-FF-FF-FF");

        onlineWhiteListWifiInfoList.add(Info1);
        onlineWhiteListWifiInfoList.add(Info2);
        onlineWhiteListWifiInfoList.add(Info3);

        result.put("onlineWhiteListWifiInfoList", onlineWhiteListWifiInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}
