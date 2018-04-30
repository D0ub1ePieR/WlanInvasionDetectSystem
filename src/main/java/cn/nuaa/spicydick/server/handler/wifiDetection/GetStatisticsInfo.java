package cn.nuaa.spicydick.server.handler.wifiDetection;

import cn.nuaa.spicydick.server.RequestHandler;
//import cn.nuaa.spicydick.server.Server;
//import cn.nuaa.spicydick.server.Tools;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

public class GetStatisticsInfo extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);


    //RoutingContext:Represents the context for the handling of a request in Vert.x-Web.
    //请求上下文，可以理解为servlet中的httprequest和httpresponse

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {

        routingContext.response().putHeader("content-type", "application/json");

        //设置假数据
        JsonObject result = new JsonObject();
        JsonObject statisticsInfo = new JsonObject();

        statisticsInfo.put("totalEventNum",1);
        statisticsInfo.put("unauthorizedWifiNum",1);
        statisticsInfo.put("unauthorizedClientNum",1);
        statisticsInfo.put("totalWifiNum",1);
        statisticsInfo.put("intranetWifiNum",1);
        statisticsInfo.put("nonIntranetWifiNum",1);
        statisticsInfo.put("authorizedWifiNum",1);
        statisticsInfo.put("onlineWhiteListWifiNum",1);
        statisticsInfo.put("totalClientNum",1);
        statisticsInfo.put("intranetClientNum",1);
        statisticsInfo.put("nonIntranetClientNum",1);
        statisticsInfo.put("authorizedClientNum",1);
        statisticsInfo.put("onlineWhiteListClientNum",1);
        statisticsInfo.put("alertNum",1);
        statisticsInfo.put("warningNum",1);
        statisticsInfo.put("dosAttackAlertNum",1);
        statisticsInfo.put("forgedWifiAlertNum",1);
        statisticsInfo.put("unauthorizedWifiAlertNum",1);
        statisticsInfo.put("unauthorizedClientAlertNum",1);
        statisticsInfo.put("authorizedClientUnauthorizedWifiAlertNum",1);
        statisticsInfo.put("longAbsenceWarningNum",1);
        statisticsInfo.put("weakEncryptionWifiWarningNum",1);
        statisticsInfo.put("authorizedWifiIntranetWarningNum",1);
        statisticsInfo.put("statusGrade",1);
        result.put("statisticsInfo",statisticsInfo);



        //    context.Response.End()的用法和本质：
        //
        //    用法：可以用来终止进程，即当前HttpHandler的执行,
        //
        //　　也可以在子方法中终止HttpHandler的执行，
        //
        //　　实际在子方法中终止程序，只有一种可能，那就是程序抛异常，所以context.Response.End()得本质就是抛出了线程异常
        //
        //　　由于抛异常的效率较低，所以能不能就不用，在主方法中最好还是使用return;终止程序比较好，
        //
        //　　但是在子方法中只能使用抛异常终止程序，即使用context.Response.End()终止程序。
        routingContext.response().end(ResponseFactory.success(request,result).toString());
    }
}
