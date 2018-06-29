package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.handler.whiteList.Info.ClientInfo;
import cn.nuaa.spicydick.server.handler.whiteList.Info.singleResult;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

public class AddClientToWhiteList extends RequestHandler {

    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        List<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();
        try {
            Gson gson = new Gson();
            String json = request.getParams().getValue("clientInfoList").toString();
            clientInfoList = gson.fromJson(json, new TypeToken<List<ClientInfo>>() {
            }.getType());
        } catch (Exception e) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }
        JsonObject result = new JsonObject();
        List<singleResult> addWifiToWhiteListResultList = new ArrayList<singleResult>();

        for (Iterator it = clientInfoList.iterator(); it.hasNext(); ) {
            ClientInfo clientInfo = (ClientInfo) it.next();

            String clientBssid = clientInfo.getClientBssid();
            String remark = clientInfo.getRemark() != null ? clientInfo.getRemark() : "#NULL#";
            String manager = clientInfo.getManager() != null ? clientInfo.getManager() : "#NULL#";
            String contact = clientInfo.getContact() != null ? clientInfo.getContact() : "#NULL#";

            // System.out.println("wifiSsid="+wifiSsid+ " wifiBssid="+wifiBssid+ " remark="+remark+ " " + " location="+ location+ " manager="+manager+ " contact="+contact);

            if (!formDetect(clientBssid, "MAC_RegEx") || !formDetect(remark, 1, 20) ||
                    !formDetect(manager, 1, 20) || !formDetect(contact, 6, 12)) {
                routingContext.response()
                        .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
                cn.nuaa.spicydick.server.handler.usr.Login.logger
                        .error((Object) String.format("user:%s exception:%s", "非法参数"));
                return;
            }

            //

            singleResult Info = new singleResult();
            Info.setSingCode(0);
            Info.setSingleMessage("aa");
            addWifiToWhiteListResultList.add(Info);

        }
        result.put("resultList", addWifiToWhiteListResultList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }


}
