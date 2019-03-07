package com.fmt.ming.paotui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimerDate {
    Date now;

    public TimerDate() {
        now = new Date();
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd
     */
    public String getNowDate() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        return dateFormat.format(now);
    }

    /**
     * 根据输入的日期字符串 和 提前天数 ， 获得 指定日期提前几天的日期对象
     *
     * @param dateString 日期对象 ，格式如 1-31-1900
     * @param lazyDays   倒推的天数
     * @return 指定日期倒推指定天数后的日期对象
     * @throws ParseException
     */
    public String getDate(int beforeDays) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);

        return dateFormat.format(cal.getTime());
    }

    /**
     * 根据输入的日期字符串 和 提前天数 ， 获得 指定日期提前几天的日期对象
     *
     * @param dateString 日期对象 ，格式如 1-31-1900
     * @param lazyDays   倒推的天数
     * @return 指定往前天数
     * @throws ParseException
     */
    public String getBeforeDate(String dateString, int lazyDays) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(dateString));
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - lazyDays);
        return dateFormat.format(cal.getTime());
    }
    /**
     * 根据输入的日期字符串 和 提前天数 ， 获得 指定日期提前几天的日期对象
     *
     * @param dateString 日期对象 ，格式如 1-31-1900
     * @param lazyDays   倒推的天数
     * @return 指定往后天数
     * @throws ParseException
     */
    public String getAfterDate(String dateString, int lazyDays) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(dateString));
        int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear + lazyDays);
        return dateFormat.format(cal.getTime());
    }
}
