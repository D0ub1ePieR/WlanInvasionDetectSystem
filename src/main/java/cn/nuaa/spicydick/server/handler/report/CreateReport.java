package cn.nuaa.spicydick.server.handler.report;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

// 生成报表
public class CreateReport extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //

        JsonObject result = new JsonObject();

        result.put("reportId", 1);
        result.put("reportUrl", "http://127.0.0.1");
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }

}
