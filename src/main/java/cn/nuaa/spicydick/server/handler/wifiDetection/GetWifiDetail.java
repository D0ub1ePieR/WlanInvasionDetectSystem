package cn.nuaa.spicydick.server.handler.wifiDetection;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

//import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

//返回该bssid下热点的详细信息
public class GetWifiDetail extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //表单验证
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ? request.getParams().getValue("wifiBssid").toString() : null;

        if (!formDetect(wifiBssid, "MAC_RegEx")) {
            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //使用假数据
        JsonObject wifiDetail = new JsonObject();
        JsonObject result = new JsonObject();

        wifiDetail.put("singal",66);
        wifiDetail.put("ssid","TPLINK");
        wifiDetail.put("remark","test");                     //备注名称，1-20个字符
        wifiDetail.put("securityStatus",1);
        wifiDetail.put("encryption",1);
        wifiDetail.put("intranet",1);
        wifiDetail.put("whiteList",1);
        wifiDetail.put("timeout",1);
        wifiDetail.put("timeoutTime",1);                    //超时时间，String，单位秒
        wifiDetail.put("connectionNum",1);
        wifiDetail.put("appearTime",1);
        wifiDetail.put("firstSeen","2017-12-12 11:11:11");
        wifiDetail.put("lastSeen","2017-11-11 11:11:11");
        wifiDetail.put("channel","ccc");                     //无线信道，String
        wifiDetail.put("bssid","00-00-00-00-00-00");
        wifiDetail.put("company","huawei");
        wifiDetail.put("flow","123456");

        result.put("wifiDetail", wifiDetail);

        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}
