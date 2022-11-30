package com.example.clouddisk.util;

import java.util.Date;

/**
 * @author R.Q.
 */
public class DateUtil {
    /**
     * function:获取系统当前时间
     * @return 系统当前时间
     */
    public static String getCurrentTime(){
        Date date = new Date();
        return String.format("%tF %<tT", date);
    }
}
