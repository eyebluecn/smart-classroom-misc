package com.smart.classroom.misc.utility.util;


import com.smart.classroom.misc.utility.exception.UtilException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 时间通用转换
 */
public class DateUtil {
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SLASH_DATE_FORMAT = "yyyy/MM/dd";
    public static final String SLASH_DATE_TIME_FORMAT = "yyyy/MM/dd";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
    public static final String COMPACT_DATETIME_FORMAT = "yyyyMMddHHmmss";
    public static final String COMPACT_FULL_DATETIME_FORMAT = "yyyyMMddHHmmssSSS";
    public static final String COMPACT_DATE_MINUTE_FORMAT = "yyyyMMddHHmm";
    public static final String COMPACT_DATE_HOUR_FORMAT = "yyyyMMddHH";
    public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
    public static final String LOG_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String COMPACT_YEAR_MONTH_FORMAT = "yyyyMM";
    //返回今天是星期几
    public static String dateToWeek(Date date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        return dateFm.format(date);
    }

    public static String dateToWeek(Calendar calendar) {

        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");

        return dateFm.format(calendar.getTime());
    }

    //根据unix timestamp * 1000返回时间
    public static String timestamp2time(Long timestamp) {
        if (timestamp == null) {
            return "未知";
        }
        Date date = new Date(timestamp);
        SimpleDateFormat dateFm = new SimpleDateFormat("HH:mm");

        return dateFm.format(date);
    }

    //根据unix timestamp * 1000返回日期
    public static String timestamp2date(Long timestamp) {
        if (timestamp == null) {
            return "未知";
        }
        Date date = new Date(timestamp);
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");

        return dateFm.format(date);
    }

    //以下的四个方法都是方便人类阅读的方法。
    public static String humanDateTime(Date then) {
        if (then == null) {
            return null;
        }
        return humanDateWithFormat("yyyy-MM-dd HH:mm", then);
    }

    public static String humanDate(Date then) {
        if (then == null) {
            return null;
        }
        return humanDateWithFormat("yyyy-MM-dd", then);
    }

    public static String humanDateWithFormat(String formatKey, Date then) {
        if (then == null) {
            return null;
        }
        return humanStampWithFormat(formatKey, then.getTime() / 1000);

    }

    public static String humanStamp(long timeStamp) {
        return humanStampWithFormat("yyyy-MM-dd HH:mm", timeStamp);
    }

    public static String humanStampWithFormat(String formatKey, long timeStamp) {
        SimpleDateFormat tempDate = new SimpleDateFormat(formatKey);
        String res = null;
        long currentTimestamp = System.currentTimeMillis() / 1000;
        long interval = currentTimestamp - timeStamp;
        if (interval >= 0) {
            if (interval < 60) {
                res = "刚刚";
            } else if (interval > 60 && interval < 3600) {
                res = (long) Math.ceil(interval / 60) + "分钟前";
            } else if (interval > 3600 && interval < (3600 * 6)) {
                long hour = (long) (Math.floor(interval / 3600));

                long minute = (long) (Math.ceil((interval % 3600) / 60));

                res = hour + "小时" + minute + "分钟前";

            } else {

                res = tempDate.format(new Date(timeStamp * 1000));

            }
        } else {
            interval = -interval;
            if (interval < 60) {
                res = interval + "秒后";
            } else if (interval > 60 && interval < 3600) {
                res = (long) Math.ceil(interval / 60) + "分钟后";
            } else if (interval > 3600 && interval < (3600 * 6)) {
                long hour = (long) (Math.floor(interval / 3600));

                long minute = (long) (Math.ceil((interval % 3600) / 60));

                res = hour + "小时" + minute + "分钟后";
            } else {
                res = tempDate.format(new Date(timeStamp * 1000));
            }
        }
        return res;
    }

    //获取昨天此时的时间。
    public static Date getYesterday() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.getTime();
    }

    //获取明天此时的时间。
    public static Date getTomorrow() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    //获取今天 0:00的时间。
    public static Date getTodayStartDate() {

        return getStartDate(new Date());
    }

    //获取今天 23:59的时间
    public static Date getTodayEndDate() {

        return getEndDate(new Date());
    }

    //获取某天 0:00的时间。
    public static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    //获取某天 23:59的时间
    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    //某一年的第一天
    public static Date firstDayOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date convertStringToDate(String dateString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {

            return formatter.parse(dateString);
        } catch (ParseException e) {

            throw new UtilException("{}无法转换成日期格式{}",dateString,format);
        }
    }

    public static String convertDateToString(Date date) {

        return convertDateToString(date, DEFAULT_FORMAT);

    }

    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat tempDate = new SimpleDateFormat(format);

        return tempDate.format(date);

    }

    //拼合时间，成为这种格式：2016-01-23@2016-02-12@2016-03-23
    public static String combineDates(List<Date> dates) {
        List<String> dateStrings = dates.stream().map(date -> DateUtil.convertDateToString(date, "yyyy-MM-dd")).collect(
                Collectors.toList());

        return String.join("@", dateStrings);

    }

    //拼合时间，成为这种格式: 01-23,02-12,03-23
    public static String combineDatesWithComma(List<Date> dates) {
        List<String> dateStrings = dates.stream().map(date -> DateUtil.convertDateToString(date, "MM-dd")).collect(Collectors.toList());

        return String.join(",", dateStrings);
    }

    //两个时间相差的毫秒数
    public static long msInterval(Date date1, Date date2) {
        return date2.getTime() - date1.getTime();
    }

    //两天相差的天数
    public static int dayInterval(Date date1, Date date2) {
        return Math.round((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
    }

    //两天相差的分钟数
    public static int minuteInterval(Date date1, Date date2) {
        return Math.round((date2.getTime() - date1.getTime()) / (1000 * 60));
    }

    //两天相差的周数
    public static int weekInterval(Date date1, Date date2) {
        return Math.round((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24 * 7));
    }

    /**
     * 左边的日期是否在右边的之前
     *
     * @param left
     * @param right
     * @param format 日期格式
     * @return
     */
    public static boolean before(String left, String right, String format) {
        Date leftDate = convertStringToDate(left, format);
        Date rightDate = convertStringToDate(right, format);
        return leftDate.before(rightDate);
    }



    /**
     * 增加/减少xx天
     *
     * @param date 日期
     * @param days 加的天数。可以是负数
     * @return 加后得到的结果。
     */
    public static Date addDays(Date date, int days) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, days);
            return c.getTime();
        }
    }

    /**
     * 日期加法
     *
     * @param ds     日期 yyyyMMdd
     * @param offset 偏移量
     * @return 结果日期 yyyyMMdd
     */
    public static String addDays(String ds, int offset) {

        return DateUtil.convertDateToString(DateUtil.addDays(DateUtil.convertStringToDate(ds, COMPACT_DATE_FORMAT), offset), COMPACT_DATE_FORMAT);
    }

    /**
     * 增加/减少xx小时
     *
     * @param date  时间
     * @param hours 减去的小时数
     * @return 计算后的时间
     */
    public static Date addHours(Date date, int hours) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR_OF_DAY, hours);
            return c.getTime();
        }
    }

    /**
     * 增加/减少xx分钟
     *
     * @param date    时间
     * @param minutes 减去的分钟数
     * @return 计算后的时间
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MINUTE, minutes);
            return c.getTime();
        }
    }

    /**
     * 获取一段日期的string格式，比如近7日时间
     *
     * @param originDate       开始日期
     * @param simpleDateFormat 日期格式
     * @param period           日期范围
     * @return 20190929, 20190928, 20190927...
     */
    public static String getDateOfPeriod(Date originDate, SimpleDateFormat simpleDateFormat, int period) {
        String dateAfterConvert = "";
        for (int i = 1; i <= period; i++) {
            dateAfterConvert += simpleDateFormat.format(DateUtil.addDays(originDate, -i));
            dateAfterConvert += ",";
        }
        dateAfterConvert = dateAfterConvert.substring(0, dateAfterConvert.length() - 1);
        return dateAfterConvert;
    }

    /**
     * 昨天的dt格式
     *
     * @return dt格式
     */
    public static String yesterdayDt() {
        return DateUtil.convertDateToString(DateUtil.getYesterday(), DateUtil.COMPACT_DATE_FORMAT);
    }

    /**
     * 获取今天的日期
     */
    public static String todayDateString() {
        return DateUtil.convertDateToString(new Date(), DateUtil.DATE_FORMAT);
    }

    /**
     * 获取离当前日期最近的一个星期天
     *
     * @return 最近的一个星期天，如果今天就是周日，返回今天
     */
    public static Date getLastSunday() {

        // 获取当bai前日期
        Calendar calendar = Calendar.getInstance();

        // 距离上个周日的差值
        long diffTime = 0L;

        // 今天星期几，注：此处周日 = 1 ，周一 = 2 。。。。
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        System.out.println("toady:" + dayOfWeek);

        // 如果今天是周日，看需求，是取今天的，还是取上周的
        if (dayOfWeek == 1) {
            // 取上周
            dayOfWeek = 8;
        }

        // 计算当前时间距离上周日的时差，此处一周的开始从周日算起，根据需要
        diffTime = (dayOfWeek - 1) * 24 * 60 * 60 * 1000;

        calendar.setTimeInMillis(System.currentTimeMillis() - diffTime);
        //
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        return calendar.getTime();
    }


    /**
     * 获取下个月的日期
     */
    public static Date addMonths(Date date, int offset) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, offset);
        return instance.getTime();
    }

    /**
     * 周一为每周的开始
     */
    public static int getWeekNum(Date date) {

        Calendar instance = Calendar.getInstance();

        instance.setTime(date);
        instance.add(Calendar.DAY_OF_YEAR, -1);
        return instance.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取date所在周的第几天
     *
     * @param date   所在日期
     * @param offset 第几天 e.g. 0 表示周一
     * @return 日期
     */
    public static Date getDayOfWeek(Date date, int offset) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        instance.setFirstDayOfWeek(Calendar.MONDAY);

        instance.set(Calendar.DAY_OF_WEEK, instance.getFirstDayOfWeek() + offset);

        return instance.getTime();
    }


    /**
     * 获取date所在月的第几天
     *
     * @param date   所在日期
     * @param offset 第几天
     * @return 日期
     */
    public static Date getDayOfMonth(Date date, int offset) {

        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        instance.set(Calendar.DAY_OF_MONTH, 1 + offset);

        return instance.getTime();
    }

    /**
     * 获取日期对应的周一
     */
    public static Date getMonday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

    /**
     * 获取日期对应的周日
     */
    public static Date getSunday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return calendar.getTime();
    }

    /**
     * 获取今天的日期
     */
    public static String getCurrentTime() {
        return DateUtil.convertDateToString(new Date(), DateUtil.COMPACT_DATE_MINUTE_FORMAT);
    }

    public static boolean isLegalDate(String dateString, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            Date date = dateFormat.parse(dateString);
            return Objects.equals(dateString, dateFormat.format(date));
        } catch (Exception e) {
            return false;
        }
    }
}

