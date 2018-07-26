package cn.nuaa.spicydick.server.handler.report;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

// 获取报表配置
public class GetReportConf extends RequestHandler{
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //

        JsonObject result = new JsonObject();

        result.put("wifiList", 1);
        result.put("clientList", 1);
        result.put("warningList", 1);
        result.put("wifiWhiteList", 1);
        result.put("clientWhiteList", 1);

        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
