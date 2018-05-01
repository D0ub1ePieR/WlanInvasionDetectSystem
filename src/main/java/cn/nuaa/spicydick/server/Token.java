package cn.nuaa.spicydick.server;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import java.util.Date;
import static cn.nuaa.spicydick.server.Tools.myMD5;

//生成、检验token
public class Token
{
    private String token;
    private String tokenEndTime;

    public String getToken() { return token; }
    public String getTokenEndTime() { return tokenEndTime; }

    public void setToken()
    {
        Date date = new Date();
        Long ldate = date.getTime();
        token = myMD5(ldate.toString());
    }
    public void setTokenEndTime()
    {
        final int persistTime = 3600 * 12 * 1000;       //token有效时间12小时
        Date date = new Date();
        Long ldate = date.getTime() + persistTime;
        tokenEndTime = ldate.toString();
    }

    //更新token
    public boolean updateToken(String username)
    {
        setToken();
        setTokenEndTime();
        final MongoClient mongoClient = Server.getInstance().getMongoClient();
        final JsonObject query = new JsonObject().put("username", username);    //用户查询
        //设置updateJson-包含新的token以及新的token失效时间
        final JsonObject update = new JsonObject().put("$set", new JsonObject().put("token", this.getToken()).put("tokenEndTime", this.getTokenEndTime()));

        //检验是否更新成功
        mongoClient.updateCollection("users", query, update, result->{
           if (result.failed())
               return;
        });
        return true;
    }

    //检验token是否已经失效
    public static int isTokenValid(String tokenEndTime)
    {
        //0-token有效，1-token失效
        Long ltokenEndTime = Long.parseLong(tokenEndTime);
        Date date = new Date();
        Long nowTime = date.getTime();
        if (ltokenEndTime<nowTime)
            return 1;
        return 0;
    }
}
