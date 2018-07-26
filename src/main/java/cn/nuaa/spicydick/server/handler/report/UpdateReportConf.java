package cn.nuaa.spicydick.server.handler.report;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;


import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;

// 更新报表配置
public class UpdateReportConf extends RequestHandler{
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        int wifiList = request.getParams().getValue("wifiList") != null ?
                StringToInt(request.getParams().getValue("wifiList").toString()) : 1;
        int clientList = request.getParams().getValue("clientList") != null ?
                StringToInt(request.getParams().getValue("clientList").toString()) : 1;
        int warningList = request.getParams().getValue("warningList") != null ?
                StringToInt(request.getParams().getValue("warningList").toString()) : 1;
        int wifiWhiteList = request.getParams().getValue("wifiWhiteList") != null ?
                StringToInt(request.getParams().getValue("wifiWhiteList").toString()) : 1;
        int clientWhiteList = request.getParams().getValue("clientWhiteList") != null ?
                StringToInt(request.getParams().getValue("clientWhiteList").toString()) : 1;

        if(!formDetect(wifiList, 0,1) || !formDetect(clientList, 0, 1) ||
                !formDetect(warningList, 0,1) || !formDetect(wifiWhiteList, 0,1) ||
                !formDetect(clientWhiteList, 0,1)){
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object)String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        result = null;
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
