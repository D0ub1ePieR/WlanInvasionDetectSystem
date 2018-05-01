package cn.nuaa.spicydick.server.msg;

import io.vertx.core.json.JsonObject;

//响应成功，返回报文
/**
 * {
 * "version": 1,
 *  "result": {
 *     "#": "#",
 *     ...
 *     },
 * "id": "#"
 * }*/
public class Success extends Response
{
    JsonObject result;

    //构造报文
    public Success(final int id, final JsonObject result)
    {
        this.result=new JsonObject();
        this.id=id;
        this.result=result;
    }

    public JsonObject getResult() { return this.result; }       //获取结果
    public  void setResult(final JsonObject result) { this.result=result; }     //设置结果

    @Override
    public JsonObject toJsonObject()
    {
        final JsonObject tmp=new JsonObject();
        tmp.put("version", "1.0");
        tmp.put("result", this.result);
        tmp.put("id", this.id);
        return tmp;
    }
}
