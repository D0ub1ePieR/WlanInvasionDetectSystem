package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.whiteList.Info.ClientWhiteInfo;
import cn.nuaa.spicydick.server.handler.whiteList.Info.WifiWhiteListInfo;
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

// 获取终端白名单列表
public class GetClientWhiteList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        // 是否分页
        int page = request.getParams().getValue("page") != null ? StringToInt(request.getParams().getValue("page").toString()) : 1;

        //表单检验
        if (!formDetect(page, 1, 100)) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        //

        JsonObject result = new JsonObject();
        List<ClientWhiteInfo> clientWhiteInfoList = new ArrayList<ClientWhiteInfo>();
        ClientWhiteInfo Info1 = new ClientWhiteInfo();

        Info1.setClientBssid("00-00-00-00-00-00");
        Info1.setTimeout("1");
        Info1.setRemark("aa");
        Info1.setManager("aaaaa");
        Info1.setContact("123456");

        clientWhiteInfoList.add(Info1);
        result.put("clientWhiteList", clientWhiteInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
