package cn.nuaa.spicydick.server.handler.usr;

import cn.nuaa.spicydick.server.RequestHandler;
import cn.nuaa.spicydick.server.Server;
import cn.nuaa.spicydick.server.Token;
import cn.nuaa.spicydick.server.Tools;
import cn.nuaa.spicydick.server.msg.ErrorCode;
import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.List;

//用户登录
/**     接收报文格式
{
"params": {
    "username": "#",
    "passwd": "#",
    },
}
        响应报文格式
{
"result": {
    "username":"#",
    "token":"#",
    },
}
*/
public class Login extends RequestHandler
{
    public static Logger logger = Logger.getLogger((Class)Login.class);

    //检验用户名格式
    private boolean isValidRequest(RoutingContext routingContext, Request request)
    {
        String userName = request.getParams().getString("username");

        if (userName.length()>16 || userName.length()<3)
        {
            routingContext.response().end(ResponseFactory.error(request.getID(), ErrorCode.USERNAME_FORMAT_ERROR, "用户名格式错误").toString());
            return false;
        }
        return true;
    }

    @Override
    public void handle(final RoutingContext routingcontext, final Request request)
    {
        routingcontext.response().putHeader("content-type", "application/json");
        final MongoClient mongoClient = Server.getInstance().getMongoClient();

        //校验请求
        if (!this.isValidRequest(routingcontext, request)) {
            return;
        }

        final JsonObject query = new JsonObject().put("username", request.getParams().getValue("username"));
        //数据库查询用户信息
        mongoClient.find("users", query, queryResult -> {
            //查询失败
            if (queryResult.failed()) {
                routingcontext.response().end(ResponseFactory.error(request.getID(), ErrorCode.DECODE_ERROR, "服务器内部错误").toString());
                logger.error((Object)String.format("query:%s exception:%s", query, Tools.getTrace(queryResult.cause())));
                return;
            }
            //未查询到信息
            if (queryResult.result().isEmpty()) {
                logger.info((Object)String.format("query:%s succeed no result", query));
                routingcontext.response().end(ResponseFactory.error(request, ErrorCode.USERNAME_NOT_FOUND, "用户名不存在").toString());
                return;
            }
            //查询成功
            JsonObject userRes = queryResult.result().get(0);
            String truePasswd;      //正确密码
            String inputPasswd;     //用户输入密码

            try
            {
                truePasswd = userRes.getValue("passwd").toString();        //从数据库获取的正确的经过SHA256加密后的密码
                inputPasswd = Tools.encodeSHA256(request.getParams().getValue("passwd").toString());        //将用户输入的密码进行加密
                logger.info((Object)String.format("%s",inputPasswd));
            } catch (Exception err)
            {
                logger.info((Object)String.format("query:%s succeed no result", query));
                routingcontext.response().end(ResponseFactory.error(request, ErrorCode.INVALID_PARAMETERS, "非法参数").toString());
                return;
            }

            //校验密码
            if(truePasswd!=null && truePasswd.equals(inputPasswd)) {
                //登录成功 更新token
                Token myToken = new Token();
                myToken.updateToken(userRes.getValue("username").toString());

                //构建response报文result字段
                JsonObject res = new JsonObject();
                res.put("username",userRes.getValue("username")).put("token",myToken.getToken()).put("tokenEndTime", myToken.getTokenEndTime());

                logger.info((Object)String.format("query:%s succeed result:%s", query, ((List)queryResult.result()).get(0)));
                routingcontext.response().end(ResponseFactory.success(request, res).toString());
            }
            else
            {
                //密码错误
                logger.info((Object)String.format("query:%s succeed no result", query));
                routingcontext.response().end(ResponseFactory.error(request, ErrorCode.PASSWORD_ERROR, "密码错误").toString());
            }
        });
    }
}
