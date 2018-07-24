
package WifiDetection;

import cn.nuaa.spicydick.server.util.IniUtil;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
//
public class GetWifiEventListTest {
    public static void main(final String[] args) {
        IniUtil.changeMode(IniUtil.LOCAL_DEBUG_MODE);

        //创建vertx实例，建立虚拟客户端
        Vertx vertx = Vertx.vertx();
        WebClient client = WebClient.create(vertx);

        //构建虚拟报文参数params
        // 实际起止时间是必填项
        JsonObject params= new JsonObject();
        params.put("wifiBssid","1a-2b-3c-4c-5b-6a");
        params.put("page",1);
        params.put("startTime","2017-01-01 11:11:11");  // 此处时间设置非必填项
        params.put("endTime","2017-01-01 11:11:11");

        //创建报文json
        JsonObject request = new JsonObject();
        request.put("version",1.0);
        request.put("method","wifiDetection.getWifiEventList");
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