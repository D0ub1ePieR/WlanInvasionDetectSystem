package cn.nuaa.spicydick.server.handler.wifiDetection;

//import cn.nuaa.spicydick.server.DataConsumer;
import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.usr.Login;
import cn.nuaa.spicydick.server.handler.wifiDetection.Info.WifiInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;



//返回wifi热点列表（筛选条件组合出现时，为且的关系）
public class GetWifiList extends RequestHandler {

    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request){
        routingContext.response().putHeader("content-type", "application/json");

        //据说表单验证有点问题？
        int online = request.getParams().getValue("online") != null ? StringToInt(request.getParams().getValue("type").toString()) : 0;
        int type = request.getParams().getValue("type") != null ? StringToInt(request.getParams().getValue("type").toString()) : 0;
        int page = request.getParams().getValue("page") != null ? StringToInt(request.getParams().getValue("page").toString()) : 1;

        String keyword = request.getParams().getValue("keyword") != null ? request.getParams().getValue("keyword").toString() : "ALL#";
        String firstSeenFrom = request.getParams().getValue("firstseenfrom") != null ? request.getParams().getValue("firstseenfrom").toString() : "1970-01-01 00:00:00";
        String firstSeenTo = request.getParams().getValue("firstseento") != null ? request.getParams().getValue("firstseento").toString() : "2070-01-01 00:00:00";


        //表单验证
        if(!formDetect(online, 0, 2) || !formDetect(type, 0, 4) ||
                !formDetect(keyword, 0, 32) || !formDetect(firstSeenFrom, "DATE_RegEx") ||
                !formDetect(firstSeenTo, "DATE_RegEx") || !formDetect(page, 1, 100)){

            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object)String.format("user:%s exception:%s", "非法参数"));

            return;
        }

        JsonObject result = new JsonObject();
        List<WifiInfo> wifiInfoList = new ArrayList<WifiInfo>();
        WifiInfo wifiInfo1 = new WifiInfo();

        wifiInfo1.setSignal(66);        //
        wifiInfo1.setSsid("TPLINK1");
        wifiInfo1.setSecurityStatus(0);
        wifiInfo1.setEncryption(1);
        wifiInfo1.setIntranet(1);
        wifiInfo1.setIsInWhiteList(1);
        wifiInfo1.setTimeout(1);
        wifiInfo1.setLastSeen("2017-11-11 11:11:11");
        wifiInfo1.setConnectionsNum(1);
        wifiInfo1.setBssid("00-01-02-33-ff-22-33");
        wifiInfo1.setFlow("12345");

        WifiInfo wifiInfo2 = new WifiInfo();
       // wifiInfo2.setSingal(72);
        wifiInfo2.setSsid("edb");
        wifiInfo2.setSecurityStatus(1);
        wifiInfo2.setEncryption(1);
        wifiInfo2.setIntranet(1);
        wifiInfo2.setIsInWhiteList(1);
        wifiInfo2.setTimeout(1);
        wifiInfo2.setLastSeen("2017-11-11 11:11:11");
        wifiInfo2.setConnectionsNum(1);
        wifiInfo2.setBssid("ff-01-02-33-ff-22-33");
        wifiInfo2.setFlow("123455");
    }
}
