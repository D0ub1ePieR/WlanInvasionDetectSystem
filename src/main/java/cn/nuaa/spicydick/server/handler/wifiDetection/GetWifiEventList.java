package cn.nuaa.spicydick.server.handler.wifiDetection;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.handler.wifiDetection.Info.WifiEventInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;


//返回热点事件列表
public class GetWifiEventList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        /**
         *若s为必填项，则request.getParams().getValue(key) == null时，赋值null，这样在表单检测时会报错
         *若s为非填项，则request.getParams().getValue(key) == null时，赋默认值
         *
         *
         * */
        String wifiBssid = request.getParams().getValue("wifiBssid") != null ? request.getParams().getValue("wifiBssid").toString() : null;
        int page = request.getParams().getValue("page") != null ? StringToInt(request.getParams().getValue("page").toString()) : 1;

        String startTime = request.getParams().getValue("startTime") != null ? request.getParams().getValue("startTime").toString() : "1970-01-01 00:00:00";
        String endTime = request.getParams().getValue("endTime") != null ? request.getParams().getValue("endTime").toString() : "2070-01-01 00:00:";

        //表单验证
        if (!formDetect(wifiBssid, "MAC_RegEx") || !formDetect(page, 1, 100) ||
                !formDetect(startTime, "DATE_RegEx") || !formDetect(endTime, "DATE_RegEx")) {
            routingContext.response().end(ResponseFactory.error(-3,ErrorCode.INVALID_PARAMETERS,"非法参数").toString());
            Login.logger.error((Object)String .format("user:%s exception:%s","非法参数"));

            return;
        }


        JsonObject result = new JsonObject();
        List<WifiEventInfo> wifiEventInfoList = new ArrayList<WifiEventInfo>();

        WifiEventInfo Info1 = new WifiEventInfo();
        WifiEventInfo Info2 = new WifiEventInfo();

        Info1.setEventType("alert");
        Info1.setLastOccurTime("2018-01-01 00:00:00");
        Info1.setFirstOccurTime("2018-01-02 00:00:00");
        Info1.setEventContent("alert");
        Info1.setRelatedNetworkName("TPLINK1");
        Info1.setRelatedNetworkAddress("00-00-00-00-00-00");
        Info1.setSourceInfo("src1");
        Info1.setTargetInfo("des1");

        Info2.setEventType("warning");
        Info2.setLastOccurTime("2018-02-01 00:00:00");
        Info2.setFirstOccurTime("2018-02-02 00:00:00");
        Info2.setEventContent("warning");
        Info2.setRelatedNetworkName("TPLINK@");
        Info2.setRelatedNetworkAddress("01-00-00-00-00-00");
        Info2.setSourceInfo("src2");
        Info2.setTargetInfo("des2");

        wifiEventInfoList.add(Info1);
        wifiEventInfoList.add(Info2);
        result.put("wifiEventList", wifiEventInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}
