package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 更新终端白名单
public class UpdateClientWhiteList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        // 更新终端信息
        String clientBssid = request.getParams().getValue("clientBssid") != null ?
                request.getParams().getValue("clientBssid").toString() : null;

        String remark = request.getParams().getValue("remark") != null ?
                request.getParams().getValue("remark").toString() : "#NULL#";

        String manager = request.getParams().getValue("manager") != null ?
                request.getParams().getValue("manager").toString() : "#NULL#";

        String contact = request.getParams().getValue("contact") != null ?
                request.getParams().getValue("contact").toString() : "#NULL#";

        //表单检验
        if(!formDetect(clientBssid, "MAC_RegEx") || !formDetect(remark, 1, 20) ||
                !formDetect(manager, 1, 20) || !formDetect(contact, 1, 12)){
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
