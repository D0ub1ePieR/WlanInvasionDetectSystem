package cn.nuaa.spicydick.server.handler.wifiDetection;

//import cn.nuaa.spicydick.server.DataConsumer;
import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.wifiDetection.Info.WifiInfo;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;
import cn.nuaa.spicydick.server.handler.usr.Login;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;
import static cn.nuaa.spicydick.server.Tools.StringToInt;



//返回wifi热点列表（筛选条件组合出现时，为且的关系）
public class GetWifiList extends RequestHandler {

    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    @Override
    public void handle(final RoutingContext routingContext, final Request request){
        routingContext.response().putHeader("content-type", "application/json");

        //据说表单验证有点问题？

        //online,表示是否在线，0所有，1在线，2超时
        //type,表示设备类型，0所有，1接入内网，2未接入内网，3合法，4非法
        //page，表示是否分页，每页10条记录，缺省为第一页数据
        int online = request.getParams().getValue("online") != null ? StringToInt(request.getParams().getValue("online").toString()) : 0;
        int type = request.getParams().getValue("type") != null ? StringToInt(request.getParams().getValue("type").toString()) : 0;
        int page = request.getParams().getValue("page") != null ? StringToInt(request.getParams().getValue("page").toString()) : 1;

        //keyword，搜索关键词，不超过32个字符，根据网络名称查询
        //firstseenfrom,出现时间（起），格式为YYYY-MM-DD，建议用户选择而非输入
        //firstseento，出现时间（止）
        //firstseenfrom < 筛选出现时间 < firstseento
        String keyword = request.getParams().getValue("keyword") != null ? request.getParams().getValue("keyword").toString() : "ALL#";
        String firstSeenFrom = request.getParams().getValue("firstseenfrom") != null ? request.getParams().getValue("firstseenfrom").toString() : "1970-01-01 00:00:00";
        String firstSeenTo = request.getParams().getValue("firstseento") != null ? request.getParams().getValue("firstseento").toString() : "2070-01-01 00:00:00";


        //表单验证
        if(!formDetect(online, 0, 2) || !formDetect(type, 0, 4) ||
                !formDetect(keyword, 0, 32) || !formDetect(firstSeenFrom, "DATE_RegExpr") ||
                !formDetect(firstSeenTo, "DATE_RegExpr") || !formDetect(page, 1, 100)){

            routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            Login.logger.error((Object)String.format("user:%s exception:%s", "非法参数"));

            return;
        }

        JsonObject result = new JsonObject();
        List<WifiInfo> wifiInfoList = new ArrayList<WifiInfo>();
        WifiInfo wifiInfo1 = new WifiInfo();

        //相关数据说明参看WifiInfo
        wifiInfo1.setSignal(66);
        wifiInfo1.setSsid("TPLINK1");
        wifiInfo1.setSecurityStatus(0);
        wifiInfo1.setEncryption(1);
        wifiInfo1.setIntranet(1);
        wifiInfo1.setIsInWhiteList(1);
        wifiInfo1.setTimeout(1);
        wifiInfo1.setLastSeen("2018-01-01 11:11:11");
        wifiInfo1.setConnectionsNum(1);
        wifiInfo1.setBssid("00-01-02-33-ff-22-33");
        wifiInfo1.setFlow("233");

        WifiInfo wifiInfo2 = new WifiInfo();
        wifiInfo2.setSignal(88);
        wifiInfo2.setSsid("TPLINK2");
        wifiInfo2.setSecurityStatus(1);
        wifiInfo2.setEncryption(1);
        wifiInfo2.setIntranet(1);
        wifiInfo2.setIsInWhiteList(1);
        wifiInfo2.setTimeout(1);
        wifiInfo2.setLastSeen("2018-01-01 11:11:11");
        wifiInfo2.setConnectionsNum(1);
        wifiInfo2.setBssid("ff-01-02-33-ff-22-33");
        wifiInfo2.setFlow("666");

        wifiInfoList.add(wifiInfo1);
        wifiInfoList.add(wifiInfo2);

        result.put("wifiList", wifiInfoList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

//        JsonObject result = new JsonObject();
//        List<WifiInfo> wifiInfoList = new ArrayList<WifiInfo>();
//
//        for (Map.Entry<String, JsonObject> tmp : DataConsumer.wifiList.entrySet()){
//
//            WifiInfo wifiInfo = new WifiInfo();
//            JsonObject tmpJson = tmp.getValue();
//
//            int signalDbm = 0;
//            try {
//                String signalDBMStr = tmpJson.getValue("signalDBM").toString();
//                signalDbm = Integer.parseInt(signalDBMStr);
//            }
//            catch (Exception e){
//                //log
//            }
//
//            wifiInfo.setSingal(signalDbm);
//            wifiInfo.setSsid(tmpJson.getValue("ssid").toString());
//            wifiInfo.setSecurityStatus(1);
//            wifiInfo.setEncryption(1);
//            wifiInfo.setIntranet(1);
//            wifiInfo.setIsInWhiteList(1);
//            wifiInfo.setTimeout(1);
//            wifiInfo.setLastSeen("2017-11-11 11:11:11");
//            wifiInfo.setConnectionsNum(1);
//            wifiInfo.setBssid(tmpJson.getValue("mac").toString());
//      //    wifiInfo.setFlow(tmpJson.getValue("checksum").toString());
//            wifiInfoList.add(wifiInfo);
//        }
//
//        result.put("wifiList", wifiInfoList);
//        routingContext.response().end(ResponseFactory.success(request, result).toString());
    }
}
