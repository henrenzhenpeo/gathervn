package com.biel.qmsgatherCgVn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    /**
     * 将日期转换为字符串，按空格分隔后取第一个部分作为batch字符串
     * @param date 传入的Date对象
     * @return batch字符串，若date为null则返回空字符串
     */
    public static String getBatchFromDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

}
