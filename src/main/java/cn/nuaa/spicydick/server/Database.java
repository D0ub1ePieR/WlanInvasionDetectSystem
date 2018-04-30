package cn.nuaa.spicydick.server;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.apache.log4j.Logger;

public class Database
{
    private static Logger logger = Logger.getLogger((Class)Database.class);     //日志记录
    private Vertx vertx;        //vertx交互接口
    private MongoClient mongoClient;    //mongodb交互接口
    private final String databaseConfFile;     //数据库配置文件

    //连接数据库
    public Database(final Vertx vertx)
    {
        this.databaseConfFile = Globals.getDatabaseConfFile();
        this.vertx = vertx;     //报文交互
        final JsonObject config = new JsonObject(Tools.readFileToString(this.databaseConfFile));    //数据库交互
        (this.mongoClient = MongoClient.createShared(vertx, config)).getCollections( res -> {
            if (res.succeeded())
                Database.logger.info((Object)String.format("mongodb database:%s init success", config.toString()));
            else
            {
                Database.logger.error((Object)String.format("mongodb database:%s init failed", config.toString()));
                this.vertx.close();
            }
        });
    }

    //获取数据库终端
    public MongoClient getMongoClient() { return this.mongoClient; }
}
