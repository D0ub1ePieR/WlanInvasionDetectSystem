package cn.nuaa.spicydick.server;

import java.util.HashMap;
import java.util.regex.Pattern;

//匹配正则表达式，检验各数据类型的正确性
public class RegExprFix
{
    //TODO 添加正则匹配
    private static final HashMap <String, String> RegExprMap = new HashMap<String, String>()
    {{
        //地址匹配 (http(s))
        put("URL_RegExpr","^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
        //mac地址匹配
        put("MAC_RegExpr","^([0-9A-Fa-f]{2}-){5}[0-9A-Fa-f]{2}$");
        put("DATE_RegExpr","^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$");
        put("INT_RegExpr","^\\d{1,6}$");
        put("KeyWord_RegExpr","^\\$");
    }};

    /**
     * value 值
     * RegExpr 正则表达式类型(string)
     * minLength 匹配最短长度
     * maxLength 匹配最大长度
     * return 0-不合法 1-合法
     * */
    //限制长度的正则匹配
    public static boolean formDetect(String value, String RegExpr, int minLength, int maxLength)
    {
        boolean res=false;
        if (value==null)
            return false;
        if (RegExprMap.get(RegExpr)!=null)
        {
            RegExpr = RegExprMap.get(RegExpr);
            System.out.println("RegExpr="+RegExpr);
        }
        if (value.length()<maxLength && value.length()>minLength && Pattern.matches(RegExpr, value))
            res = true;
        System.out.println("限制长度的正则匹配 Res="+res);
        return res;
    }
    //不限制长度的正则匹配
    public static boolean formDetect(String value, String RegExpr)
    {
        boolean res=false;
        if (value==null)
            return false;
        if (RegExprMap.get(RegExpr)!=null)
        {
            RegExpr = RegExprMap.get(RegExpr);
            System.out.println("RegExpr="+RegExpr);
        }
        if (Pattern.matches(RegExpr, value))
            res=true;
        System.out.println("不限制长度的正则匹配 Res="+res);
        return res;
    }
    //检验长度
    public static boolean formDetect(String value, int minLength, int maxLength)
    {
        if (value==null)
            return false;
        System.out.println(value+" 检验长度 res="+(value.length()<=maxLength && value.length()>=minLength));
        return (value.length()<=maxLength && value.length()>=minLength);
    }
    //检验数值范围
    public static boolean formDetect(int value, int minvalue, int maxvalue)
    {
        System.out.println(value+"数值检验范围res="+(value<=maxvalue && value>=minvalue));
        return (value<=maxvalue && value>=minvalue);
    }
}
