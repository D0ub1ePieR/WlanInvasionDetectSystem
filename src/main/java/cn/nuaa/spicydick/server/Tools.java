package cn.nuaa.spicydick.server;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.SocketAddress;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Logger;

public class Tools
{
    private static final int LINE_BINARY_LENGTH = 30;
    private static Logger logger = Logger.getLogger("Tools");

    //将一个字符c重复length次组成对应长度的字符串
    public static String sameCharWithLength(final char c,final int length)
    {
        final StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < length; i++)
            stringbuffer.append(c);
        return stringbuffer.toString();
    }

    //根据文件名，将文件内容读取为String类型
    public static String readFileToString(final String fileName)
    {
        final File file = new File(fileName);   //打开文件
        final byte[] filecontent = new byte[(int)file.length()];    //文件内容缓冲
        try
        {
            //读取文件
            final FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException err)
        {
            err.printStackTrace();
        } catch (IOException err2)
        {
            err2.printStackTrace();
        }
        return new String(filecontent);
    }

    //验证有效手机
    public static boolean isValidPhoneNumber(final String phoneNumber) { return true; }
    //验证日期格式
    public static boolean isValidYYYYMMDDString(final String dateString)
    {
        if (dateString==null)
            return false;
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try     //进行格式匹配
        { format.parse(dateString); }
        catch (Exception err){ return false;}
        return true;    //无错误，符合格式
    }
    //验证时间格式
    public static boolean isValidHHmmString(final String dateString)
    {
        if (dateString==null)
            return false;
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try     //进行格式匹配
        { format.parse(dateString); }
        catch (Exception err) { err.printStackTrace(); }
        return true;    //无错误，符合格式
    }

    //抛出异常
    public static String getTrace(final Throwable table)        //不含参数的异常
    {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        table.printStackTrace(printWriter);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
    public static String getTrace(final String request, final Throwable table)      //携带request请求的异常
    {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        if (request != null)
        {
            writer.append("request:");
            writer.append(request);
            writer.append('\n');
        }
        table.printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
    public static String getTrace(final String request, final String context, final Throwable table)        //携带request请求以及内容的异常
    {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(stringWriter);
        if (request != null)
        {
            writer.append("request:");
            writer.append(request);
            writer.append('\n');
        }
        if (context != null)
        {
            writer.append("context:");
            writer.append(context);
            writer.append('\n');
        }
        table.printStackTrace(writer);
        final StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
