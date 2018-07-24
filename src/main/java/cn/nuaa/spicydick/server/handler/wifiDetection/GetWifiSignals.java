package cn.nuaa.spicydick.server.handler.wifiDetection;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.handler.wifiDetection.Info.WifiSignalsInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;


//返回热点最近信号强度
public class GetWifiSignals extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "applocation/json");

        //表单验证
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ? request.getParams().getValue("wifiBssid").toString() : null;

        if (!formDetect(wifiBssid, "MAC_RegExpr")) {
            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //使用假数据

        JsonObject result = new JsonObject();
        List<WifiSignalsInfo> wifiSignalsInfoList = new ArrayList<WifiSignalsInfo>();

        WifiSignalsInfo Info1 = new WifiSignalsInfo();
        Info1.setTime("2017-11-27 11:11:11");
        Info1.setWifiSignal(1);

        WifiSignalsInfo Info2 = new WifiSignalsInfo();
        Info2.setTime("2017-11-27 12:12:12");
        Info2.setWifiSignal(2);

        wifiSignalsInfoList.add(Info1);
        wifiSignalsInfoList.add(Info2);

        result.put("wifiSignals", wifiSignalsInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}
