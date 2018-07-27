package cn.nuaa.spicydick.server.handler.report;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.report.Info.ReportInfo;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import com.google.gson.Gson;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;

public class GetReportDetail extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");


        int reportId = request.getParams().getValue("reportId") != null ?
                StringToInt(request.getParams().getValue("reportId").toString()) : 0;

        if(!formDetect(reportId, 0, 10000)){
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object)String.format("user:%s exception:%s", "非法参数"));
            return;
        }


        //

        JsonObject result = new JsonObject();

        ReportInfo reportDetail = new ReportInfo();

        reportDetail.setReportId(1);
        reportDetail.setCreateTime("2017-11-11 11:11:11");
        reportDetail.setReportUrl("http:127.0.0.1");


        Gson gson = new Gson();
        JsonObject reportDetailJson = new JsonObject(gson.toJson(reportDetail));

        result.put("reportDetail", reportDetailJson);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
