package report;

import cn.nuaa.spicydick.server.util.IniUtil;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;


public class UpdateReportConfTest {
    public static void main(final String[] args) {
        IniUtil.changeMode(IniUtil.LOCAL_DEBUG_MODE);

        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        JsonObject params = new JsonObject();
        params.put("wifiList", 0);
        params.put("clientList", 1);
        params.put("warningList", 0);
        params.put("wifiWhiteList", 1);
        params.put("clientWhiteList", 0);


        JsonObject form = new JsonObject();
        form.put("version", 1.0);
        form.put("method", "report.updateReportConf");
        form.put("params", params);
        form.put("token", "");
        form.put("id", 1);
        client
                .post(6060, "127.0.0.1", "/")
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