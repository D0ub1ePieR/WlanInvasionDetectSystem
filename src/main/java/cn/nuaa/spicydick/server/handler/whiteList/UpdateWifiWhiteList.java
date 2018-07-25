package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 更新热点白名单
public class UpdateWifiWhiteList extends RequestHandler {

    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");


        // 更新热点信息
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ?
                request.getParams().getValue("wifiBssid").toString() : null;

        String remark = request.getParams().getValue("remark") != null ?
                request.getParams().getValue("remark").toString() : "#NULL#";

        String location = request.getParams().getValue("location") != null ?
                request.getParams().getValue("location").toString() : "#NULL#";

        String manager = request.getParams().getValue("manager") != null ?
                request.getParams().getValue("manager").toString() : "#NULL#";

        String contact = request.getParams().getValue("contact") != null ?
                request.getParams().getValue("contact").toString() : "#NULL#";

        //表单检验
        if (!formDetect(wifiBssid, "MAC_RegExpr") || !formDetect(remark, 1, 20) ||
                !formDetect(location, 1, 40) || !formDetect(manager, 1, 20) ||
                !formDetect(contact, 1, 12)) {

            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        result = null;
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}