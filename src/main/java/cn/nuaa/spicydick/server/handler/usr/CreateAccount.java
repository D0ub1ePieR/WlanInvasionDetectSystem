package cn.nuaa.spicydick.server.handler.usr;			//用户操作包

import cn.nuaa.spicydick.server.RequestHandler;			//响应操作
//import cn.nuaa.spicydick.server.Server;					//服务器配置
//import cn.nuaa.spicydick.server.Tool;
import cn.nuaa.spicydick.server.msg.ErrorCode;			//错误码
import cn.nuaa.spicydick.server.msg.Request;			//响应
import cn.nuaa.spicydick.server.msg.ResponseFactory;	//发送模式
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;
import org.apache.log4j.Logger;

import java.util.Date;

//创建账户
public class CreateAccount extends RequestHandler
{
	public static Logger log = Logger.getLogger((Class)CreateAccount.class);	//log4j 日志记录
	//public static final int username_format_error = -100;		//用户名格式错误
	//public static final int password_format_error = -101;		//密码格式错误
	//public static final int username_existed = -102;			//用户名已存在
	
	@Override
	//vertx 交互
	public void handle(final RoutingContext routingcontext,final Request request) {
		routingcontext.response().putHeader("content-type","application/json");	//设置报文头部
		//final MongoClient mongoclient = Server.getInstance().getMongoClient();	//mongodb数据库
		
		String username = request.getParams().getString("username");
		String password = request.getParams().getString("password");

		//格式检验
		if ( username.length()>16 || username.length()<3 )
		{
			routingcontext.response().end(ResponseFactory.error(request.getID(), ErrorCode.USERNAME_FORMAT_ERROR, "用户名格式错误").toString());
			return;
		}
	}
}