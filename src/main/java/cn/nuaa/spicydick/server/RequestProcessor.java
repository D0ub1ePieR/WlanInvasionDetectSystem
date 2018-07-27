package cn.nuaa.spicydick.server;

import cn.nuaa.spicydick.server.handler.clientDetection.GetClientDetail;
import cn.nuaa.spicydick.server.handler.clientDetection.GetClientEventList;
import cn.nuaa.spicydick.server.handler.clientDetection.GetClientList;
import cn.nuaa.spicydick.server.handler.usr.*;
import cn.nuaa.spicydick.server.handler.warningInfo.GetWarningList;
import cn.nuaa.spicydick.server.handler.weakPasswd.CrackWifiPasswd;
import cn.nuaa.spicydick.server.handler.weakPasswd.GetCrackTaskProgress;
import cn.nuaa.spicydick.server.handler.whiteList.*;
import cn.nuaa.spicydick.server.handler.wifiDetection.*;
import cn.nuaa.spicydick.server.handler.report.*;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.*;

import static cn.nuaa.spicydick.server.Token.isTokenValid;

public class RequestProcessor
{
    public static Logger logger = Logger.getLogger((Class)RequestProcessor.class);
    private static RequestProcessor instance;
    private Hashtable<String, RequestHandler> requestHandlerMap;

    //构造函数
    private RequestProcessor()
    {
        this.requestHandlerMap = new Hashtable<String, RequestHandler>();
        this.initRequstHandlers();
    }

    public static RequestProcessor getInstance()
    {
        if (RequestProcessor.instance==null)
            RequestProcessor.instance = new RequestProcessor();
        return RequestProcessor.instance;
    }

    //初始化url--method表
    private void initRequstHandlers()
    {
        this.requestHandlerMap.put("user.login", new Login());
        this.requestHandlerMap.put("user.createAccount", new CreateAccount());      //创建账户
        // this.requestHandlerMap.put("user.updatePasswd", new UpdatePasswd());

        this.requestHandlerMap.put("wifiDetection.getStatisticsInfo", new GetStatisticsInfo());
        this.requestHandlerMap.put("wifiDetection.getWifiList", new GetWifiList());
        this.requestHandlerMap.put("wifiDetection.getWifiDetail", new GetWifiDetail());
        this.requestHandlerMap.put("wifiDetection.getWifiClientList", new GetWifiClientList());
        this.requestHandlerMap.put("wifiDetection.getWifiSignals", new GetWifiSignals());
        this.requestHandlerMap.put("wifiDetection.getWifiEventList", new GetWifiEventList());
        this.requestHandlerMap.put("wifiDetection.getOnlineWhiteListWifiList", new GetOnlineWhiteListWifiList());

        this.requestHandlerMap.put("clientDetection.getClientList", new GetClientList());
        this.requestHandlerMap.put("clientDetection.getClientDetail", new GetClientDetail());
        this.requestHandlerMap.put("clientDetection.getClientEventList", new GetClientEventList());

        // this.requestHandlerMap.put("warningInfo.getWarningList", new GetWarningList());

        this.requestHandlerMap.put("whiteList.addWifiToWhiteList", new AddWifiToWhiteList());
        this.requestHandlerMap.put("whiteList.deleteWifiFromWhiteList", new DeleteWifiFromWhiteList());
        this.requestHandlerMap.put("whiteList.getWifiWhiteList", new GetWifiWhiteList());
        this.requestHandlerMap.put("whiteList.updateWifiWhiteList", new UpdateWifiWhiteList());
        this.requestHandlerMap.put("whiteList.addClientToWhiteList", new AddClientToWhiteList());
        this.requestHandlerMap.put("whiteList.deleteClientFromWhiteList", new DeleteClientFromWhiteList());
        this.requestHandlerMap.put("whiteList.getClientWhiteList", new GetClientWhiteList());
        this.requestHandlerMap.put("whiteList.updateClientWhiteList", new UpdateClientWhiteList());

        this.requestHandlerMap.put("report.updateReportConf", new UpdateReportConf());
        this.requestHandlerMap.put("report.getReportConf", new GetReportConf());
        this.requestHandlerMap.put("report.createReport", new CreateReport());
        this.requestHandlerMap.put("report.deleteReport", new DeleteReport());
        this.requestHandlerMap.put("report.getReportList", new GetReportList());
        this.requestHandlerMap.put("report.getReportDetail", new GetReportDetail());


        /*this.requestHandlerMap.put("weakPasswd.crackWifiPasswd", new CrackWifiPasswd());
        this.requestHandlerMap.put("weakPasswd.getCrackTaskProgress", new GetCrackTaskProgress());*/
    }

    //检验
    private boolean isRequestValid(final Request request) { return true; }
    private boolean vertifyTokenOrNot(final String method)
    {
        //用户登录后进行操作需要根据token识别是否登录
        if (method.equals("user.login"))
            return false;
        return true;
    }

    public void processRequest(final RoutingContext routingContext, final Request request)
    {
        final RequestHandler requestHandler = this.requestHandlerMap.get(request.getMethod());
        //无法找到对应方法
        if (requestHandler==null)
        {
            RequestProcessor.logger.error((Object)String.format("request:%s cannot find a handler", request.toString()));
            ResponseHelper.error(routingContext, request, -2, "方法不存在或不可使用");
        }
        //是否识别token
        if (vertifyTokenOrNot(request.getMethod()))
        {
            String token = request.getValue("token").toString();
            final MongoClient mongoClient = Server.getInstance().getMongoClient();
            final JsonObject query = new JsonObject().put("token", token);

            mongoClient.count("user", query, queryResult ->{
                //向数据库查询user
                if (queryResult.failed())
                {
                    //查询失败
                    routingContext.response().end(ResponseFactory.error(-500, ErrorCode.SERVER_ERROR, "服务器内部错误").toString());
                    logger.error((Object)String.format("query:%s exception %s", query, Tools.getTrace(queryResult.cause())));
                    return;
                }
                //未查询到结果
                if (queryResult.result()<-1)
                {
                    //未登录
                    routingContext.response().end(ResponseFactory.error(-403, ErrorCode.CLIENT_NOT_LOGIN, "客户端未登录").toString());
                    logger.error((Object)String.format("query:%s succeed no result", query));
                    return;
                }
                //检验token
                mongoClient.find("users", query, qRequest->{
                   //查询失败
                   if (qRequest.failed())
                   {
                       routingContext.response().end(ResponseFactory.error(-500, ErrorCode.SERVER_ERROR, "服务器内部错误").toString());
                       logger.error((Object)String.format("query:%s exception %s", query, Tools.getTrace(queryResult.cause())));
                       return;
                   }

                   JsonObject userRes = qRequest.result().get(0);
                   if (isTokenValid(userRes.getValue("tokenEndTime").toString())==1)
                   {
                      //token失效
                      routingContext.response().end(ResponseFactory.error(-105, ErrorCode.TOKEN_OUT_OF_DATE, "token失效").toString());
                      logger.error((Object)String.format("query:%s exception", query, Tools.getTrace(qRequest.cause())));
                   }
                   else
                   {
                       //token有效自动登录
                       logger.info((Object)String.format("query:%s find a handler", request.toString()));
                       //进入对应处理类
                       requestHandler.handle(routingContext, request);
                   }
                });
            });
        }

        //不需要识别token，即进入用户登录处理
        logger.info((Object)String.format("request:%s find a handler", request.toString()));
        requestHandler.handle(routingContext, request);
    }
}
