package cn.nuaa.spicydick.server.handler.clientDetection;

import cn.nuaa.spicydick.server.RequestHandler;

import cn.nuaa.spicydick.server.handler.clientDetection.Info.ClientEventListInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;

// 获取终端事件列表
public class GetClientEventList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        // 终端MAC地址
        String clientBssid = request.getParams().getValue("clientBssid") != null ?
                request.getParams().getValue("clientBssid").toString() : null;

        int page = request.getParams().getValue("page") != null ?
                StringToInt(request.getParams().getValue("page").toString()) : 1;

        // 事件起止时间
        String startTime = request.getParams().getValue("startTime") != null ?
                request.getParams().getValue("startTime").toString() : "1970-01-01 00:00:00";

        String endTime = request.getParams().getValue("endTime") != null ?
                request.getParams().getValue("endTime").toString() : "2070-01-01 00:00:00";

        //表单检验
        if (!formDetect(clientBssid, "MAC_RegExpr") || !formDetect(page, 1, 100) ||
                !formDetect(startTime, "DATE_RegExpr") || !formDetect(endTime, "DATE_RegExpr")) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        List<ClientEventListInfo> clientEventListList = new ArrayList<ClientEventListInfo>();

        ClientEventListInfo Info1 = new ClientEventListInfo();
        Info1.setEventType("aaa");
        Info1.setLastOccurTime("2017-11-11 11:11:11");
        Info1.setFirstOccurTime("2017-11-11 11:11:11");
        Info1.setEventContent("abc");
        Info1.setRelatedNetWorkName("aaa");
        Info1.setRelatedNetWorkAddress("00-00-00-00-00-00");
        Info1.setSourceInfo("abc");
        Info1.setTargetInfo("aaa");
        ClientEventListInfo Info2 = new ClientEventListInfo();
        Info2.setEventType("bbb");
        Info2.setLastOccurTime("2017-11-11 11:11:11");
        Info2.setFirstOccurTime("2017-11-11 11:11:11");
        Info2.setEventContent("abc");
        Info2.setRelatedNetWorkName("aaa");
        Info2.setRelatedNetWorkAddress("00-00-00-00-00-00");
        Info2.setSourceInfo("abc");
        Info2.setTargetInfo("aaa");

        clientEventListList.add(Info1);
        clientEventListList.add(Info2);

        result.put("clientEventList", clientEventListList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }

}
