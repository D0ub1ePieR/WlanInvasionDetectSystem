package clientDetection;

import cn.nuaa.spicydick.server.util.IniUtil;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

/**
 * {
 *  "version": "1.0",
 *  "method": "clientDetection.getClientEventList",
 *  "params":
 *  {
 *      "clientBssid": "#",
 *      "page":1,
 *      "startTime" : "#",
 *      "endTime" : "#"
 *  },
 *  "id":"#",
 * }
 */

public class getClientEventListTest {
    public static void main(final String[] args){
        IniUtil.changeMode(IniUtil.LOCAL_DEBUG_MODE);

        //创建vertx实例，建立虚拟客户端
        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        //构建虚拟报文参数params
        JsonObject params= new JsonObject();
        params.put("clientBssid","FF-FF-FF-FF-FF-FF");
        params.put("page",1);
        params.put("startTime","1970-01-01 00:00:00");
        params.put("endTime","1970-01-01 00:00:00");

        //创建报文json
        JsonObject request = new JsonObject();
        request.put("version",1.0);
        request.put("method","clientDetection.getClientEventList");
        request.put("params",params);
        request.put("token","");
        request.put("id",1);

        client
                .post(IniUtil.getInstance().getHTTPServerPort(), IniUtil.getInstance().getServerHostName(), "/")
                .sendJsonObject(request, ar -> {
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
