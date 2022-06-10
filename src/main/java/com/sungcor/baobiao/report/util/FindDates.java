package com.sungcor.baobiao.report.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FindDates
{

    public static List<Map> findDates(String dBegin, String dEnd, String type)
    {
        List<Map> list = new ArrayList<Map>();
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin = sdf.parse(dBegin);
            Date end = sdf.parse(dEnd);
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(begin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(end);
            Map map = new HashMap();
            if(type.equals("quarter")){
                int quarter = getQuarter(begin);
                map.put("name",quarter+"季度");
                sdf = new SimpleDateFormat("yyyy");
                map.put("id",sdf.format(begin)+"/"+quarter);
            }else{
                sdf = new SimpleDateFormat(findFormat(type).get("name"));
                map.put("name",sdf.format(begin));
                sdf = new SimpleDateFormat(findFormat(type).get("java"));
                map.put("id",sdf.format(begin));
            }
            list.add(map);
            // 测试此日期是否在指定日期之后
            while (end.after(calBegin.getTime()))
            {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                if(type.equals("hours")){
                    calBegin.add(Calendar.HOUR, 1);
                }else if(type.equals("date")){
                    calBegin.add(Calendar.DATE, 1);
                }else if(type.equals("day")){
                    calBegin.add(Calendar.DATE, 1);
                }else if(type.equals("week")){
                    calBegin.add(Calendar.WEEK_OF_YEAR, 1);
                }else if(type.equals("moon")){
                    calBegin.add(Calendar.MONTH, 1);
                }else if(type.equals("quarter")){
                    calBegin.add(Calendar.MONTH, 3);
                }else if(type.equals("year")){
                    calBegin.add(Calendar.YEAR, 1);
                }
                map = new HashMap();
                if(type.equals("quarter")){
                    int quarter = getQuarter(calBegin.getTime());
                    map.put("name",quarter+"季度");
                    sdf = new SimpleDateFormat("yyyy");
                    map.put("id",sdf.format(calBegin.getTime())+"/"+quarter);
                }else{
                    sdf = new SimpleDateFormat(findFormat(type).get("name"));
                    map.put("name",sdf.format(calBegin.getTime()));
                    sdf = new SimpleDateFormat(findFormat(type).get("java"));
                    map.put("id",sdf.format(calBegin.getTime()));
                }
                list.add(map);
                if(list.size()>72){
                    break;
                }
            }

            list.remove(list.size()-1);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public static HashMap<String,String> findFormat(String type){
        HashMap<String,String> map = new HashMap<String, String>();
        if(type.equals("hours")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y/%m/%d %H')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/mm/dd hh24')");
            map.put("java","yyyy/MM/dd HH");
            map.put("name","d号H时");
        }else if(type.equals("day")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y/%m/%d')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/mm/dd')");
            map.put("java","yyyy/MM/dd");
            map.put("name","E");
        }else if(type.equals("date")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y/%m/%d')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/mm/dd')");
            map.put("java","yyyy/MM/dd");
            map.put("name","M月d号");
        }else if(type.equals("week")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y/%u')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/iw')");
            map.put("java","yyyy/w");
            map.put("name","w周");
        }else if(type.equals("moon")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y/%m')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/mm')");
            map.put("java","yyyy/MM");
            map.put("name","M月");
        }else if(type.equals("quarter")){
            map.put("mysql","CONCAT(DATE_FORMAT(T1.CREATE_TIME,'%Y'),'/',QUARTER(T1.CREATE_TIME))");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy/q')");
            map.put("java","yyyy/MM");
            map.put("name","M季度");
        }else if(type.equals("year")){
            map.put("mysql","DATE_FORMAT(T1.CREATE_TIME,'%Y')");
            map.put("oracle","TO_CHAR(T1.CREATE_TIME,'yyyy')");
            map.put("java","yyyy");
            map.put("name","yyyy年");
        }
        return map;
    }

    public static int getQuarter(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        int month = Integer.parseInt(sdf.format(date));
        int array[][] = {{1,2,3},{4,5,6},{7,8,9},{10,11,12}};
        int season = 1;
        if(month>=1&&month<=3){
            season = 1;
        }
        if(month>=4&&month<=6){
            season = 2;
        }
        if(month>=7&&month<=9){
            season = 3;
        }
        if(month>=10&&month<=12){
            season = 4;
        }
        return  season;
    }
}
