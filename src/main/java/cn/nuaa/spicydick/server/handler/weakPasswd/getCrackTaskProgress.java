package cn.nuaa.spicydick.server.handler.weakPasswd;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;


// 获取密码破解进度
public class getCrackTaskProgress extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        int crackTaskId = request.getParams().getValue("crackTaskId") != null ?
                StringToInt(request.getParams().getValue("crackTaskId").toString()) : -1;

        //表单检验
        if(!formDetect(crackTaskId, 0, 10000)){
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object)String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        result.put("crackTaskProgress", 1);
        result.put("wifiBssid","00-00-00-00-00-00");
        result.put("wifiPasswd","??");
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}