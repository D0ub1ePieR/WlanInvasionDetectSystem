package cn.nuaa.spicydick.server.msg;

//错误码
public class ErrorCode 
{
	public static final int SERVER_ERROR = -500;			//服务器错误
	public static final int DECODE_ERROR = -501;			//服务器解码错误
	public static final int METHOD_NOT_FOUND = -401;		//无该请求方式
	public static final int INVALID_PARAMETERS = -402;		//无效参数
	public static final int CLIENT_NOT_LOGIN = -403;		//未登录
	public static final int USERNAME_FORMAT_ERROR = -100;	//用户名格式错误
	public static final int PASSWORD_FORMAT_ERROR = -101;	//密码格式错误
	public static final int USERNAME_EXISTED = -102;		//用户名已存在
	public static final int USERNAME_NOT_FOUND = -103;		//不存在该用户
	public static final int PASSWORD_ERROR = -104;			//密码错误
	public static final int TOKEN_OUT_OF_DATE = -105;		//token失效
	public static final int MACADDRESS_FORMET_ERROR = -200;	//mac地址格式错误
}