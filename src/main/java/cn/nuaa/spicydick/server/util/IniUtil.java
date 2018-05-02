package cn.nuaa.spicydick.server.util;

import io.netty.util.internal.StringUtil;
import org.apache.log4j.Logger;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class IniUtil {
    private static Logger logger = Logger.getLogger(IniUtil.class);     //记录日志
    private static IniUtil instance;        //用于构建、转化类实例
    private static String initFilePath = System.getenv("PROJECT_HOME")==null ? "./conf/config.ini" : System.getenv("PROJECT_HOME")+"/conf/config.ini"; //?????

    //配置文件子类
    private static String Server = "server";        //服务器信息
    private static String HTTPServer = "http";      //http服务信息
    private static String SocketIO = "socketIO";    //socket交互信息
    private static String Other = "other";          //其他
    private static String Debug = "debug";          //debug信息
    private static String Log = "log";              //log信息
    //...

    private static final String CODES = "codes";
    private  Wini ini;
    private String hostID;      //主机id

    //运行模式
    public static final int TEST_MODE = 0;          //测试模式
    public static final int LOCAL_DEBUG_MODE = 1;   //本地调试模式
    public static final int PRODUCT_MODE = 2;       //最终版本模式

    private IniUtil()
    {
        try
        {
            ini = new Wini(new File(initFilePath));
            hostID = this.getServerHostName()+":"+this.getServerPort();
        }
        catch (InvalidFileFormatException err) { err.printStackTrace(); }   //无效文件格式
        catch (IOException err) { err.printStackTrace(); }                  //IO错误
    }

    //debug
    public IniUtil(String initFilePath)
    {
        try
        {
            ini = new Wini(new File(initFilePath));
            hostID = this.getServerHostName()+":"+this.getServerPort();
            System.err.println(String.format("initFilePath:%s", initFilePath));
        }
        catch (InvalidFileFormatException err) { err.printStackTrace(); }   //无效文件格式
        catch (IOException err) { err.printStackTrace(); }                  //IO错误
    }

    //构建实例
    public static void setInstance(IniUtil iniUtil) { IniUtil.instance = iniUtil; }

    //模式转换
    public static void changeIniUtil(String initFilePath)
    {
        IniUtil iniUtil = new IniUtil(initFilePath);
        IniUtil.setInstance(iniUtil);
    }

    //转换为在线测试模式
    public static void changeToTestServer() { IniUtil.changeIniUtil("./cloud/conf/configTestNode.ini"); }
    //转换为最终模式
    public static void changeToProductServer() { IniUtil.changeIniUtil("./cloud/conf/configProductNode.ini"); }
    //转换为本地测试模式
    public static void changeToLocalDebugServer() { IniUtil.changeIniUtil("./cloud/conf/configLocalDebug.ini"); }

    public static void changeMode(int mode)
    {
        if (mode == IniUtil.TEST_MODE)
            IniUtil.changeToTestServer();
        else if(mode == IniUtil.LOCAL_DEBUG_MODE)
            IniUtil.changeToLocalDebugServer();
        else if(mode == IniUtil.PRODUCT_MODE)
            IniUtil.changeToProductServer();
    }

    //debug 获取实例
    public static IniUtil getInstance()
    {
        if(instance==null)
            instance =  new IniUtil();
        return IniUtil.instance;
    }

    //获取服务器端口
    public int getServerPort()
    {
        try
        {
            int port = Integer.parseInt(ini.get(Server,"port"));
            return port;
        } catch (NumberFormatException err)     //无法从配置文件获取，返回默认80端口，并抛出异常
        {
            err.printStackTrace();
            return 80;
        }
    }
    //获取http服务端口
    public int getHTTPServerPort()
    {
        try
        {
            int port = Integer.parseInt(ini.get(HTTPServer, "port"));
            return port;
        } catch (NumberFormatException err)     //无法从配置文件获取，返回默认80端口，并抛出异常
        {
            err.printStackTrace();
            return 80;
        }
    }
    //获取socket交互端口
    public int getSocketIOServerPort()
    {
        try
        {
            int port = Integer.parseInt(ini.get(SocketIO, "port"));
            return port;
        } catch (NumberFormatException err)     //无法从配置文件获取，返回默认49090端口，并抛出异常
        {
            err.printStackTrace();
            return 49090;
        }
    }
    //获取ssl端口
    public int getServerPortSsl()
    {
        try
        {
            return Integer.parseInt(ini.get(Server, "portSsl"));
        } catch (NumberFormatException err)     //无法从配置文件获取，返回默认80端口，并抛出异常
        {
            err.printStackTrace();
            return 80;
        }
    }

    //获取服务器ip
    public String getServerHostName()
    {
        String serverHostName = ini.get(Server, "hostName");
        return serverHostName;
    }
}
