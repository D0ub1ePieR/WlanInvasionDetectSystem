package cn.nuaa.spicydick.server.handler.wifiDetection;


import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
//import cn.nuaa.spicydick.server.handler.wifiDetection.Info.WifiInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

//import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;


//返回连接到指定热点的终端列表
public class GetWifiClientList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //表单验证
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ? request.getParams().getValue("wifiBssid").toString() : null;

        int page = request.getParams().getValue("wifiBssid") != null ? StringToInt(request.getParams().getValue("wifiBssid").toString()) : 1;

        if (!formDetect(wifiBssid, "MAC_RegEx") || !formDetect(page, 1, 100)) {
            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }


        //使用假数据

        JsonArray wifiClientList = new JsonArray();
        JsonObject result = new JsonObject();

        JsonObject wifiClientInfo1 = new JsonObject();
        wifiClientInfo1.put("clientSignal", 12);                //信号强度
        wifiClientInfo1.put("clientRemark", "test1");           //备注名称
        wifiClientInfo1.put("isInWhiteList", 1);
        wifiClientInfo1.put("securityStatus", 1);
        wifiClientInfo1.put("lastSeen", "2017-11-11 11:11:11");
        wifiClientInfo1.put("clientBssid", "00-00-00-00-00-00");//终端mac地址
        wifiClientInfo1.put("currentStatus", 0);                //当前状态，0离线，1在线

        JsonObject wifiClientInfo2 = new JsonObject();
        wifiClientInfo2.put("clientSignal", 62);
        wifiClientInfo2.put("clientRemark", "test2");
        wifiClientInfo2.put("isInWhiteList", 1);
        wifiClientInfo2.put("securityStatus", 1);
        wifiClientInfo2.put("lastSeen", "2017-11-11 11:11:11");
        wifiClientInfo2.put("clientBssid", "00-00-00-00-00-00");
        wifiClientInfo2.put("currentStatus", 0);

        wifiClientList.add(wifiClientInfo1);
        wifiClientList.add(wifiClientInfo2);

        result.put("wifiClientList", wifiClientList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}
