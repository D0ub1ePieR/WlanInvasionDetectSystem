package cn.nuaa.spicydick.server.handler.weakPasswd;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 实现密码破解
public class crackWifiPasswd extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        String wifiBssid = request.getParams().getValue("wifiBssid") != null ?
                request.getParams().getValue("wifiBssid").toString() : null;

        // 表单验证
        if (!formDetect(wifiBssid, "MAC_RegEx")) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        JsonObject result = new JsonObject();
        result.put("crackId", 1);       // 密码破解任务
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
