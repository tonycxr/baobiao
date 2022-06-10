package com.sungcor.baobiao.report.bean;

import lombok.Data;

@Data
public class JobExpression {
    private String year;
    private String month;
    private String day;

    private String week;

    private String hour;
    private String minute;
    private String second;
    /**
     * @用途 构造任意时间执行规则串
     * @update weixm
     * @return
     */
    public String generateCustom(){
        StringBuffer sb = new StringBuffer();

        sb.append(second);  // 秒
        sb.append(" ");
        sb.append(minute);  // 分
        sb.append(" ");
        sb.append(hour);  // 时
        sb.append(" ");

        sb.append(this.day);  // 日
        sb.append(" ");

        sb.append(this.month); // 月
        sb.append(" ");

        sb.append(this.week==null||this.week.equals("")?"?":this.week);  // 周

        if(this.year != null){
            sb.append(" ");
            sb.append(this.year);
        }

        return sb.toString();
    }

    /**
     * @用途 构造每年固定时间执行规则串
     * @return
     */
    public String generateYearFixed(){
        StringBuffer sb = new StringBuffer();

        sb.append(second);  // 秒
        sb.append(" ");
        sb.append(minute);  // 分
        sb.append(" ");
        sb.append(hour);  // 时
        sb.append(" ");

        sb.append(this.day);  // 日
        sb.append(" ");

        sb.append(this.month); // 月
        sb.append(" ");

        sb.append("?");  // 周
        sb.append(" ");

        sb.append("*");
        sb.append(" ");

        return sb.toString();
    }

    /**
     * @用途 构造每月固定时间执行规则串
     * @return
     */
    public String generateMonthlyFixed(){
        StringBuffer sb = new StringBuffer();

        sb.append(second);  // 秒
        sb.append(" ");
        sb.append(minute);  // 分
        sb.append(" ");
        sb.append(hour);  // 时
        sb.append(" ");

        sb.append(this.day);  // 日
        sb.append(" ");

        sb.append("*"); // 月
        sb.append(" ");

        sb.append("?");  // 周

        return sb.toString();
    }

    /**
     * @用途 构造每季度固定时间执行规则串
     * @return
     */
    public String generateQuarterlyFixed(){
        StringBuffer sb = new StringBuffer();

        sb.append(second);  // 秒
        sb.append(" ");
        sb.append(minute);  // 分
        sb.append(" ");
        sb.append(hour);  // 时
        sb.append(" ");

        sb.append(this.day);  // 日
        sb.append(" ");

        sb.append(this.month); // 月
        sb.append(" ");

        sb.append("?");  // 周

        return sb.toString();
    }

    /**
     * @用途 构造每周固定时间执行规则串
     * @return
     */
    public String generateWeeklyFixed(){
        StringBuffer sb = new StringBuffer();

        sb.append(second);  // 秒
        sb.append(" ");
        sb.append(minute);  // 分
        sb.append(" ");
        sb.append(hour);  // 时
        sb.append(" ");

        sb.append("?");  // 日
        sb.append(" ");

        sb.append("*"); // 月
        sb.append(" ");

        sb.append(this.week);  // 周

        return sb.toString();
    }

    /**
     * @用途 每天固定时刻执行
     * @return
     */
    public String generateDailyFixed(){

        StringBuffer sb = new StringBuffer();

        sb.append(second);
        sb.append(" ");
        sb.append(minute);
        sb.append(" ");
        sb.append(hour);
        sb.append(" ");

        sb.append("*");
        sb.append(" ");

        sb.append("*");
        sb.append(" ");

        sb.append("?");


        return   sb.toString();
    }
}
