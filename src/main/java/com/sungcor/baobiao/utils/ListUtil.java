package com.sungcor.baobiao.utils;

import java.util.ArrayList;
import java.util.List;


public class ListUtil {

    public static <T> List<T> castToList(Object obj, Class<T> clazz) {
        List<T> resList = new ArrayList<>();
        // 如果不是List<?>对象，是没有办法转换的
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                // 将对应的元素进行类型转换
                resList.add(clazz.cast(o));
            }
            return resList;
        }
        return null;
    }

}
