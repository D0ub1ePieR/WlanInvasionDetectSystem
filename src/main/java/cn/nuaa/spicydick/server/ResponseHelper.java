package cn.nuaa.spicydick.server;

import cn.nuaa.spicydick.server.msg.Request;
import cn.nuaa.spicydick.server.msg.Response;
import cn.nuaa.spicydick.server.msg.ResponseFactory;
import io.vertx.ext.web.RoutingContext;

public class ResponseHelper {
    public static void error(final RoutingContext routingContext, final Request request, final int code, final String message) {
        final Response response = ResponseFactory.error(request.getID(), code, message);
        routingContext.response().end(response.toString());
    }
}
