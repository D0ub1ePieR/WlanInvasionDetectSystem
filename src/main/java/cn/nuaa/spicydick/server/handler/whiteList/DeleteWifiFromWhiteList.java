package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;
// import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 将热点从白名单移除
public class DeleteWifiFromWhiteList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext,final Request request){
        routingContext.response().putHeader("content-type","application/json");

        // 获取wifiMAC地址信息
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ?
                request.getParams().getValue("wifiBssid").toString() : null;

        //表单检验
        if(!formDetect(wifiBssid, "MAC_RegEx")){
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object)String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = null;
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
