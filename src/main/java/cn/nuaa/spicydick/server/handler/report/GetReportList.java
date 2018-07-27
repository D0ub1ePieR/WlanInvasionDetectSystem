package cn.nuaa.spicydick.server.handler.report;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.report.Info.ReportInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetReportList extends RequestHandler{
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        //

        JsonObject result = new JsonObject();
        List<ReportInfo> reportInfoList = new ArrayList<ReportInfo>();

        ReportInfo Info1 = new ReportInfo();
        Info1.setReportId(1);
        Info1.setCreateTime("2017-11-11 11:11:11");
        Info1.setReportUrl("http:127.0.0.1");

        reportInfoList.add(Info1);

        result.put("reportList", reportInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
