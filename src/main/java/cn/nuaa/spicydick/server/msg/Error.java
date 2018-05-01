package cn.nuaa.spicydick.server.msg;

import io.vertx.core.json.JsonObject;

//构建错误返回报文
/**
 * {
 * "version": 1,
 *  "error": {
 *     "code": "#",
 *     "message": "#",
 *     },
 * "id": "#"
 * }
 * */
public class Error extends Response
{
    int code;       //错误代码
    String msg;     //错误信息
    JsonObject error;   //

    //1-由报文id，错误代码，错误信息构造
    public Error (final int id, final int code, final String msg)
    {
        this.msg=msg;
        this.code=code;

        JsonObject error=new JsonObject();
        error.put("code", this.code);
        error.put("msg", this.msg);

        this.error=error;
        this.id=id;
    }

    //2-由报文id，错误代码构造/无错误信息
    public Error (final int id, final int code)
    {
        this.code=code;

        JsonObject error=new JsonObject();
        error.put("code", this.code);
        error.put("msg", this.msg);

        this.error=error;
        this.id=id;
    }

    public String getMsg() { return this.msg; }
    public int getCode() { return this.code; }
    public JsonObject getError() { return this.error; }

    public void setCode(final int code) { this.code=code; }
    public void setMsg(final String msg) { this.msg=msg; }
    public void setError(final JsonObject error) { this.error=error; }

    @Override
    public JsonObject toJsonObject()
    {
        final JsonObject tmp=new JsonObject();
        tmp.put("version", "1.0");
        tmp.put("error", this.error);
        tmp.put("id", this.id);
        return tmp;
    }
}
