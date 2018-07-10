package java.usr;

import cn.nuaa.spicydick.server.util.IniUtil;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;


public class LoginTest {
    public static void main(final String[] args) {
        IniUtil.changeMode(IniUtil.LOCAL_DEBUG_MODE);
//        IniUtil.changeMode(IniUtil.PRODUCT_MODE);

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        JsonObject params = new JsonObject();
        params.put("username", "eda");
        params.put("passwd", "123456");

        JsonObject form = new JsonObject();
        form.put("version", 1);
        form.put("method", "user.login");
        form.put("params", params);
        form.put("id", 1);
        client
                .post(IniUtil.getInstance().getHTTPServerPort(), IniUtil.getInstance().getServerHostName(), "/")
                .sendJsonObject(form, ar -> {
                    if (ar.succeeded()) {

                        HttpResponse<Buffer> response = ar.result();

                        // Decode the body as a json object
                        JsonObject body = response.bodyAsJsonObject();

                        System.out.println("Received response with status code: " + response.statusCode() + " with body " + body);
                    } else {
                        System.out.println("Something went wrong " + ar.cause().getMessage());
                    }
                    vertx.close();
                });


    }

}