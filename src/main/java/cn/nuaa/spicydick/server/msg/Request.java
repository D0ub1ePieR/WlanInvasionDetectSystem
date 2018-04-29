package cn.nuaa.spicydick.server.msg;

import io.vertx.core.json.JsonObject;	//构造json

public class Request extends JsonObject {
    public Request(final String request) throws Exception
    {
        super(request);		//调用json构造
        this.getID();
        this.getJsonObject("params");
        if ( this.containsKey("version") && this.containsKey("method") ) return;
        throw new Exception(request);
    }

    public Request(final int id, final String  method)
    {
        this.put("version","1.0");
        this.setID(id);
        this.setMethod(method);
        this.setParams(new JsonObject());
    }

    //获取参数
    public int getID() { return this.getInteger("id"); }
    public String getMethod() { return this.getString("method"); }
    public String getVersion() { return this.getString("version"); }
    public JsonObject getParams() { return this.getJsonObject("params"); }

    //设置参数
    public void setID(final int id) { this.put("id", id); }
    public void setMethod(final String method) { this.put("method", method); }
    public void setParams(final JsonObject params) { this.put("params", params); }

    public JsonObject copy() { return super.copy(); }
}
