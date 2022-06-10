package com.sungcor.baobiao.report.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommReportUtil {
    public static List<String> getIndexByExp(String expression){
        List<String> result=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\$[^\\$]*\\$)");
        Matcher m = p.matcher(expression);
        while(m.find()){
            result.add(m.group().replaceAll("\\$",""));
        }
        return result;
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @param round_mode 指定的舍入模式
     * @return 四舍五入后的结果，以字符串格式返回
     */
    public static String round(String v, int scale, int round_mode)
    {
        if(scale<0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        return b.setScale(scale, round_mode).toString();

    }
}
