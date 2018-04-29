package cn.nuaa.spicydick.server.msg;

import io.vertx.core.json.JsonObject;

public class Response
{
    int id;

    public int getId() { return this.id; }
    public void setId(final int id) { this.id=id; }
    public JsonObject toJsonObject() { return null; }

    @Override
    public String toString() { return this.toJsonObject().toString(); }
}
