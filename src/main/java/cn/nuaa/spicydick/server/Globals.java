package cn.nuaa.spicydick.server;

public class Globals
{
    private static boolean DEBUG = false;
    //数据库配置文件路径
    private static String databaseConfFile = "cloud/conf/database.conf";
    private static String localDatabaseConfFile = "cloud/conf/databaseLocalDebug.conf";
    private static String remoteDatabaseConfFile = "cloud/conf/databaseRemoteDebug.conf";

    //设置不同模式数据库配置文件路径
    public static void setDatabaseConfFile(final String databaseConfFile) { Globals.databaseConfFile = databaseConfFile; }      //设置为输入路径
    public static void setLocalDatabaseConfFile() { Globals.databaseConfFile = localDatabaseConfFile; }         //本地数据库配置路径
    public static void setRemoteDatabaseConfFile() { Globals.databaseConfFile = remoteDatabaseConfFile; }       //远程数据库配置路径

    //获取数据库配置文件路径
    public static String getDatabaseConfFile() { return Globals.databaseConfFile; }
}
