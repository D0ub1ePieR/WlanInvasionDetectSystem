package cn.nuaa.spicydick.server.handler.clientDetection;

import cn.nuaa.spicydick.server.RequestHandler;

import cn.nuaa.spicydick.server.handler.clientDetection.Info.ClientDetailInfo;

import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import com.google.gson.Gson;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 获取终端详细信息
public class GetClientDetail extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        String clientBssid = request.getParams().getValue("clientBssid") != null ?
                request.getParams().getValue("clientBssid").toString() : null;

        if (!formDetect(clientBssid, "MAC_RegExpr")) {
            routingContext.response()
                    .end(ResponseFactory.error(-203, ErrorCode.MACADDRESS_FORMET_ERROR, "MAC地址格式不规范").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "MAC地址格式不规范"));
            return;
        }


        //

        JsonObject result = new JsonObject();


        ClientDetailInfo clientDetail = new ClientDetailInfo();

        clientDetail.setWifiRemark("aa");
        clientDetail.setWifiSsid("ab");
        clientDetail.setWifiBssid("00-00-00-00-00-00");
        clientDetail.setClientRemark("bb");
        clientDetail.setSingal("a");
        clientDetail.setEncryption(1);
        clientDetail.setLastSeen("2017-11-11 11:11:11");
        clientDetail.setIsInWhiteList(1);
        clientDetail.setIntranet(1);
        clientDetail.setClientBssid("00-00-00-00-00-00");
        clientDetail.setTimeout(0);
        clientDetail.setTimeoutTime("aaaaa");
        clientDetail.setFlow("bb");

        Gson gson = new Gson();
        JsonObject clientDetailJson = new JsonObject(gson.toJson(clientDetail));

        result.put("clientDetail", clientDetailJson);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
