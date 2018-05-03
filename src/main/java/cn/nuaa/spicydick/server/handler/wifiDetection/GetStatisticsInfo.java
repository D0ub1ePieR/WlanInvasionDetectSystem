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

//用于返回网络安全信息统计数据
public class GetStatisticsInfo extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);


    //RoutingContext:Represents the context for the handling of a request in Vert.x-Web.
    //请求上下文，可以理解为servlet中的httprequest和httpresponse

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {

        routingContext.response().putHeader("content-type", "application/json");

        //设置假数据
        JsonObject result = new JsonObject();
        JsonObject statisticsInfo = new JsonObject();   //网络安全信息统计数据集合对象

        statisticsInfo.put("totalEventNum",1);          //警报事件/网络事件总数
        statisticsInfo.put("unauthorizedWifiNum",1);    //当前未授权wifi热点数
        statisticsInfo.put("unauthorizedClientNum",1);  //当前未授权网络终端数
        statisticsInfo.put("totalWifiNum",1);           //当前WiFi热点总数
        statisticsInfo.put("intranetWifiNum",1);        //当前接入内网WiFi热点数
        statisticsInfo.put("nonIntranetWifiNum",1);     //当前未接入内网WiFi热点数
        statisticsInfo.put("authorizedWifiNum",1);      //当前授权WiFi热点数
        statisticsInfo.put("onlineWhiteListWifiNum",1); //当前在线白名单WiFi数
        statisticsInfo.put("totalClientNum",1);         //当前无线终端总数
        statisticsInfo.put("intranetClientNum",1);      //当前接入内网无线终端数
        statisticsInfo.put("nonIntranetClientNum",1);   //当前未接入内网无线终端数
        statisticsInfo.put("authorizedClientNum",1);    //当前授权无线终端数
        statisticsInfo.put("onlineWhiteListClientNum",1);//当前在线白名单无线终端数
        statisticsInfo.put("alertNum",1);               //警报事件数
        statisticsInfo.put("warningNum",1);             //提醒事件数
        statisticsInfo.put("dosAttackAlertNum",1);      //Dos攻击警报事件数
        statisticsInfo.put("forgedWifiAlertNum",1);     //伪造WiFi热点警报事件数
        statisticsInfo.put("unauthorizedWifiAlertNum",1);//未授权WiFi热点警报事件数
        statisticsInfo.put("unauthorizedClientAlertNum",1);//未授权无线终端警报事件数
        statisticsInfo.put("authorizedClientUnauthorizedWifiAlertNum",1);//授权无线终端连接未授权WiFi热点警报事件数
        statisticsInfo.put("longAbsenceWarningNum",1);  //检测长期未出现设备提醒事件数
        statisticsInfo.put("weakEncryptionWifiWarningNum",1);//弱加密WiFi热点提醒事件数
        statisticsInfo.put("authorizedWifiIntranetWarningNum",1);//授权WiFi热点接入内网提醒事件数
        //少一个authorizedClientIntranetWarningNum	Int	授权无线终端接入内网提醒事件数？
        statisticsInfo.put("statusGrade",1);            //当前状态评分
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
