package cn.nuaa.spicydick.server.handler.whiteList;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.handler.whiteList.Info.WifiInfo;
import cn.nuaa.spicydick.server.handler.whiteList.Info.singleResult;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.nuaa.spicydick.server.RegExprFix.formDetect;

// 将热点增加到白名单中，支持一条或多条数据
public class AddWifiToWhiteList extends RequestHandler {
    public static Logger logger = Logger.getLogger((Class) cn.nuaa.spicydick.server.handler.wifiDetection.GetStatisticsInfo.class);

    public void handle(final RoutingContext routingContext, final Request request) {
        routingContext.response().putHeader("content-type", "application/json");

        List<WifiInfo> wifiInfoList = new ArrayList<WifiInfo>();
        try {
            Gson gson = new Gson();
            // json接受request返回值
            String json = request.getParams().getValue("wifiInfoList").toString();
            // fromJson实现从json相关对象到JAVA实体的转换，TypeToken是gson提供的数据类型转换器
            wifiInfoList = gson.fromJson(json, new TypeToken<List<WifiInfo>>() {
            }.getType());
        } catch (Exception e) {
            routingContext.response()
                    .end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
            cn.nuaa.spicydick.server.handler.usr.Login.logger
                    .error((Object) String.format("user:%s exception:%s", "非法参数"));
            return;
        }

        JsonObject result = new JsonObject();
        List<singleResult> addWifiToWhiteListResultList = new ArrayList<singleResult>();

        // 将客户端信息使用迭代器输出
        for (Iterator it = wifiInfoList.iterator(); it.hasNext(); ) {
            WifiInfo wifiInfo = (WifiInfo) it.next();

            String wifiBssid = wifiInfo.getWifiBssid();
            String remark = wifiInfo.getRemark() != null ? wifiInfo.getRemark() : "#NULL#";
            String manager = wifiInfo.getManager() != null ? wifiInfo.getManager() : "#NULL#";
            String contact = wifiInfo.getContact() != null ? wifiInfo.getContact() : "#NULL#";

            // System.out.println("wifiSsid="+wifiSsid+ " wifiBssid="+wifiBssid+ " remark="+remark+ " " + " location="+ location+ " manager="+manager+ " contact="+contact);

            if (!formDetect(wifiBssid, "MAC_RegExpr") || !formDetect(remark, 1, 20) ||
                    !formDetect(manager, 1, 20) || !formDetect(contact, 6, 12)) {
                routingContext.response().end(ResponseFactory.error(-3, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
                cn.nuaa.spicydick.server.handler.usr.Login.logger.error((Object) String.format("user:%s exception:%s", "非法参数"));
                return;
            }


            //


            // 返回成功结果，输出单条记录信息
            singleResult Info = new singleResult();
            Info.setSingCode(0);
            Info.setSingleMessage("aa");
            addWifiToWhiteListResultList.add(Info);
        }
        result.put("resultList", addWifiToWhiteListResultList);
        routingContext.response().end(ResponseFactory.success(request, result).toString());

    }
}

