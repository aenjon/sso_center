package com.hsjc.ssoCenter.core.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : zga
 * @date : 2015-12-14
 *
 * 日期工具类
 */
public class DateUtil {
    public final static String getCurrentDate(String formatStr) {
        return new SimpleDateFormat(formatStr).format(new Date());
    }

    public final static Long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 判断是否是休息时间
     *
     * @return
     */
    public final static boolean isBreakTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK);

        if (week == Calendar.SATURDAY || week == Calendar.SUNDAY) {
            return true;
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour < 9 || hour > 18) {
            return true;
        }
        return false;
    }
}
