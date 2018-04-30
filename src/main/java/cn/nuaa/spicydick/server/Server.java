package cn.nuaa.spicydick.server;

import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import cn.nuaa.spicydick.server.util.IniUtil;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class Server
{
    public static Logger logger = Logger.getLogger((Class)Server.class);    //日志记录
    private static Server instance;
    private Router router;

    private int port = IniUtil.getInstance().getHTTPServerPort();
    private String host = IniUtil.getInstance().getServerHostName();
    private HttpServer server;                      //http服务
    private final String urlPathPrefix = "/";       //url前缀
    //接口
    Vertx vertx;        //vertx
    Database database;  //mongodb

    private Server() {}

    //创建实例
    private static Server getInstance()
    {
        if (instance==null)
            instance = new Server();
        return instance;
    }

    //初始化
    public void init(final Vertx vertx)
    {
        //vertx
        this.vertx = vertx;
        this.server = vertx.createHttpServer();
        this.database = new Database(vertx);
        this.router = Router.router(vertx);
        this.router.route().handler((Handler)BodyHandler.create().setBodyLimit(2000000L));  //设置路由最大长度
        //header
        final Set<String> allowHeaders = new HashSet<String>();     //设置进行处理的报文头部识别字符串
        allowHeaders.add("x-request-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-type");
        final Set<HttpMethod> allowMethods = new HashSet<HttpMethod>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PATCH);
        this.router.route().handler((Handler)CorsHandler.create("*").allowedMethods((Set)allowMethods).allowedHeaders((Set)allowHeaders));
        //action
        //this.initTest();
        this.initRequestProcessor();
    }

    //初始化request操作
    private void initRequestProcessor() {
        final String urlPath = "/";
        logger.info((Object)("Router:" + urlPath));     //记录路由信息
        this.router.post(urlPath).handler(routingContext -> {
            routingContext.response().putHeader("content-type", "application/json");
            final MongoClient mongoClient = getInstance().getMongoClient();
            final Buffer buffer = routingContext.getBody();
            try
            {
                final Request request = new Request(buffer.toString());
                logger.info((Object)String.format("server received request:%s", request.toString()));
                RequestProcessor.getInstance().processRequest(routingContext, request);
            }
            catch (Exception err)
            {
                logger.error((Object)String.format("server received:%s to json object error exception:%s", buffer.toString(), Tools.getTrace(err)));
                routingContext.response().end(ResponseFactory.error(-501, ErrorCode.DECODE_ERROR, "请求解析错误").toString());
            }
        });
    }

    public MongoClient getMongoClient() { return this.getDatabase().getMongoClient(); }
    public Database getDatabase() { return this.database; }
}
