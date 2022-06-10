package com.sungcor.baobiao.mail;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

@Data
public class TimingInfo {
    private String day;
    private String from;
    private String to;

    public boolean checkDay() {
        return checkDay(day);
    }

    public boolean checkTime() {
        return checkTime(from, to);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String[] JAVA_DAY_TO_ACCEPTED_DAY = {"", "6", "0", "1", "2", "3", "4", "5"};
    public static final String TIME_SEPARATOR = ":";

    private static final boolean checkDay(String dayString){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return null == dayString ? false : dayString.contains(JAVA_DAY_TO_ACCEPTED_DAY[day]);
    }

    private static boolean checkTime(String fromTimeString, String toTimeString) {
        if(StringUtils.isBlank(fromTimeString) || StringUtils.isBlank(toTimeString)){
            return true;
        }
        String[] from = fromTimeString.split(TIME_SEPARATOR, 2);
        String[] to = toTimeString.split(TIME_SEPARATOR, 2);
        if(!StringUtils.isNumeric(from[0]) || !StringUtils.isNumeric(from[1])){
            from[0]= "9"; from[1]="0";
        }
        if(!StringUtils.isNumeric(to[0]) || !StringUtils.isNumeric(to[1])){
            to[0]= "18"; to[1]="0";
        }

        Calendar now = Calendar.getInstance();
        Calendar fromTime = Calendar.getInstance(now.getTimeZone());
        Calendar toTime = Calendar.getInstance(now.getTimeZone());
        fromTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(from[0]));
        fromTime.set(Calendar.MINUTE, Integer.parseInt(from[1]));
        toTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(to[0]));
        toTime.set(Calendar.MINUTE, Integer.parseInt(to[1]));

        if(fromTime.after(toTime)){
            return false;
        }

        if(now.after(fromTime) && now.before(toTime)){
            return true;
        }

        return false;
    }
}
