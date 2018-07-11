package cn.nuaa.spicydick.server;

import java.util.HashMap;
import java.util.regex.Pattern;

//匹配正则表达式，检验各数据类型的正确性
public class RegExprFix
{
    //TODO 添加正则匹配
    private static final HashMap <String, String> RegExprMap = new HashMap<String, String>()
    {{
        put("URL_RegExpr","");
        put("MAC_RegExpr","");
        put("INT_RegExpr","");
        put("KeyWord_RegExpr","");
    }};

    /**
     * value 值
     * RegExpr 正则表达式类型(0/1/2/3)
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
        System.out.println("Res="+res);
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
        System.out.println("Res="+res);
        return res;
    }
    //检验长度
    public static boolean formDetect(String value, int minLength, int maxLength)
    {
        if (value==null)
            return false;
        return (value.length()<=maxLength && value.length()>=minLength);
    }
    //检验数值范围
    public static boolean formDetect(int value, int minvalue, int maxvalue)
    {
        return (value<=maxvalue && value>=minvalue);
    }
}
