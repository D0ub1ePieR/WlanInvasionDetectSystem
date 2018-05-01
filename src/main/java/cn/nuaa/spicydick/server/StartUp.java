package cn.nuaa.spicydick.server;

import io.vertx.core.Vertx;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Date;
import java.util.logging.Level;

//启动后端服务器
public class StartUp
{
    private static String LOG_FILE = ((System.getenv("PROJECT_HOME")==null) ? "./conf/log4j.properties" : (System.getenv("PROJECT_HOME")+"/conf/log4j.properties"));
    public static Logger logger = Logger.getLogger((Class)StartUp.class);

    public static void main(final String[] args)
    {
        System.out.println(System.getenv("PROJECT_HOME"));
        Tools.initSystemProperties();
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4jLogDelegateFactory");
        PropertyConfigurator.configure(LOG_FILE);
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        logger.info((Object)("###########"+Tools.dateToStr(new Date())+"server start"));
        try
        {
            final Vertx vertx = Vertx.vertx();
            final Server server = Server.getInstance();

            server.init(vertx);
            server.run();
        } catch (Throwable err)
        {
            final String errorTable = Tools.getTrace(err);
            logger.error((Object)errorTable);
            logger.info((Object)("##########"+Tools.dateToStr(new Date())+"server exit with exception"));
        }
    }
}
