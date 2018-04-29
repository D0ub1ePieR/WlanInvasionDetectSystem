package cn.nuaa.spicydick.server.msg;

import io.vertx.core.json.JsonObject;

public class ResponseFactory
{
    //后端处理结果 成功
	public static Success success(final Request request, final JsonObject result)
    {
        final Success success = new Success(request.getID(), result);
        return success;
    }

    //后端处理结果 失败

    //调用 错误代码 错误信息 报文id 构造
    public static Error error (final Request request, final int code, final String message)
    {
        final Error error = new Error(request.getID(), code, message);
        return error;
    }

    public static Error error (final int id, final int code, final String message)
    {
        final Error error = new Error(id, code, message);
        return error;
    }

    //调用 错误编码 报文id 构造
    public static Error error (final int id, final int code)
    {
        final Error error = new Error(id, code);
        return error;
    }
}