package cn.nuaa.spicydick.server.handler.clientDetection;

import cn.nuaa.spicydick.server.RequestHandler;

import cn.nuaa.spicydick.server.handler.clientDetection.Info.ClientListInfo;

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

// 返回终端列表
public class GetClientList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        // 表单验证

        // 是否在线
        int online = request.getParams().getValue("online") != null ?
                StringToInt(request.getParams().getValue("online").toString()) : 0;

        // 设备类型
        int type = request.getParams().getValue("type") != null ?
                StringToInt(request.getParams().getValue("type").toString()) : 0;

        // 是否分页
        int page = request.getParams().getValue("page") != null ?
                StringToInt(request.getParams().getValue("page").toString()) : 1;

        // 搜索关键字
        String keyword = request.getParams().getValue("keyword") != null ?
                request.getParams().getValue("keyword").toString() : "#ALL#";

        // 出现时间（起）
        String firstSeenFrom = request.getParams().getValue("firstSeenFrom") != null ?
                request.getParams().getValue("firstSeenFrom").toString() : "1970-01-01 00:00:00";

        // 出现时间（止）
        String firstSeenTo = request.getParams().getValue("firstSeenTo") != null ?
                request.getParams().getValue("firstSeenTo").toString() : "2070-01-01 00:00:00";

        //表单检验
        if (!formDetect(online, 0, 2) || !formDetect(type, 0, 4) ||
                !formDetect(keyword, 0, 32) || !formDetect(firstSeenFrom, "DATE_RegExpr") ||
                !formDetect(firstSeenTo, "DATE_RegExpr") || !formDetect(page, 1, 100)) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        List<ClientListInfo> clientList = new ArrayList<ClientListInfo>();

        ClientListInfo Info1 = new ClientListInfo();
        Info1.setClientSignal(21);
        Info1.setAccessPointSsid("aa");
        Info1.setAccessPointBssid("00-00-00-00-00-00");
        Info1.setSecurityStatus(1);
        Info1.setConnectionStatus(0);
        Info1.setIntranet(0);
        Info1.setIsInWhiteList(1);
        Info1.setClientBssid("00-00-00-00-00-00");
        Info1.setLastSeen("2017-11-11 11:11:11");


        ClientListInfo Info2 = new ClientListInfo();
        Info2.setClientSignal(111);
        Info2.setAccessPointSsid("ab");
        Info2.setAccessPointBssid("00-f0-00-00-00-00");
        Info2.setSecurityStatus(1);
        Info2.setConnectionStatus(0);
        Info2.setIntranet(0);
        Info2.setIsInWhiteList(1);
        Info2.setClientBssid("00-00-00-00-00-00");
        Info2.setLastSeen("2017-11-11 11:11:11");

        clientList.add(Info1);
        clientList.add(Info2);
        result.put("clientList", clientList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
