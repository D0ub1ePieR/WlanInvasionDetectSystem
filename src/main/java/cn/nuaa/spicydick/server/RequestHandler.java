package cn.nuaa.spicydick.server;

import cn.nuaa.spicydick.server.msg.Request;
import io.vertx.ext.web.RoutingContext;		//路由信息

public abstract class RequestHandler {
    String name;

    public RequestHandler(final String name) { this.name = name; }

    public RequestHandler(){}

    public String getName() { return this.name; }

    public void setName(final String name) { this.name=name; }

    public abstract void handle(final RoutingContext p0, final Request p1);
}
