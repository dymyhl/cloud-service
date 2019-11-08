package com.puvar.cloudservice.common.utils;

import com.puvar.cloudservice.common.enums.TimeCycleEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/***
 * Date工具类
 * @Auther: dingyuanmeng
 * @Date: 2019/9/23
 * @version : 1.0
 */
public final class DateUtil extends DateUtils {

    /**
     * yyyyMMdd
     */
    public static final String SHORT_FORMAT = "yyyyMMdd";

    /**
     * yyyyMMddHHmmss
     */
    public static final String LONG_FORMAT = "yyyyMMddHHmmss";

    /**
     * yyyy-MM-dd
     */
    public static final String WEB_FORMAT = "yyyy-MM-dd";

    /**
     * HHmmss
     */
    public static final String TIME_FORMAT = "HHmmss";

    /**
     * yyyyMM
     */
    public static final String MONTH_FORMAT = "yyyyMM";

    /**
     * yyyy年MM月dd日
     */
    public static final String CHINA_FORMAT = "yyyy年MM月dd日";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String LONG_WEB_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String LONG_WEB_FORMAT_NO_SEC = "yyyy-MM-dd HH:mm";

    private static String[] patterns = {LONG_FORMAT, LONG_WEB_FORMAT, WEB_FORMAT, SHORT_FORMAT};

    public static Date format(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            return (Date) o;
        } else if (o instanceof String) {
            return parse((String) o);
        } else {
            throw new IllegalArgumentException("For input object: \"" + o + "\"");
        }
    }

    public static Date parse(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE).parse(s);
            } catch (ParseException e) {
                continue;
            }
        }
        return null;
        // throw new RisRuntimeException(RetCodeEnum.FAILED, "Unparseable date: \"" + s + "\"");
    }

    /**
     * 计算两个日期相隔天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int interval(Date d1, Date d2) {
        return Math.abs(daysBetween(d1, d2));
    }

    public static int daysBetween(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        long time1 = cal.getTimeInMillis();
        cal.setTime(date2);
        long time2 = cal.getTimeInMillis();
        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);
        return (int) betweenDays;
    }

    /**
     * 指定日期前一定日期
     *
     * @param date
     * @param daysBefore
     * @param monthBefore
     * @return
     */
    public static Date getBeforeDateCompareDate(Date date, int daysBefore, int monthBefore) {
        Calendar today = Calendar.getInstance();
        today.setTime(date);
        if (daysBefore != 0) {
            today.add(Calendar.DATE, 0 - daysBefore);
        }
        if (monthBefore != 0) {
            today.add(Calendar.MONTH, 0 - monthBefore);
        }
        return today.getTime();
    }

    /**
     * 日期对象解析成日期字符串基础方法，可以据此封装出多种便捷的方法直接使用
     *
     * @param date   待格式化的日期对象
     * @param format 输出的格式
     * @return 格式化的字符串
     */
    public static String format(Date date, String format) {
        if (date == null || StringUtils.isBlank(format)) {
            return StringUtils.EMPTY;
        }

        return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).format(date);
    }

    /**
     * 日期字符串格式化基础方法，可以在此封装出多种便捷的方法直接使用
     *
     * @param dateStr   日期字符串
     * @param formatIn  输入的日期字符串的格式
     * @param formatOut 输出日期字符串的格式
     * @return 已经格式化的字符串
     * @throws ParseException
     */
    public static String format(String dateStr, String formatIn, String formatOut) throws ParseException {

        Date date = parse(dateStr, formatIn);
        return format(date, formatOut);
    }

    /**
     * 格式化当前时间
     *
     * @param format 输出的格式
     * @return
     */
    public static String formatCurrent(String format) {
        if (StringUtils.isBlank(format)) {
            return StringUtils.EMPTY;
        }

        return format(new Date(), format);
    }

    /**
     * 日期字符串解析成日期对象基础方法，可以在此封装出多种便捷的方法直接使用
     *
     * @param dateStr 日期字符串
     * @param format  输入的格式
     * @return 日期对象
     * @throws ParseException
     */
    public static Date parse(String dateStr, String format) {
        try {
            if (StringUtils.isBlank(format)) {
                throw new ParseException("format can not be null.", 0);
            }

            if (dateStr == null || dateStr.length() < format.length()) {
                throw new ParseException("date string's length is too small.", 0);
            }
            return new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE).parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("parse excepion, dateStr=" + dateStr + ", format=" + format);
        }
    }

    /**
     * 把日期对象按照<code>yyyyMMdd</code>格式解析成字符串
     *
     * @param date 待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatShort(Date date) {
        return format(date, SHORT_FORMAT);
    }

    /**
     * 把日期字符串按照<code>yyyyMMdd</code>格式，进行格式化
     *
     * @param dateStr  待格式化的日期字符串
     * @param formatIn 输入的日期字符串的格式
     * @return 格式化的字符串
     */
    public static String formatShort(String dateStr, String formatIn) throws ParseException {
        return format(dateStr, formatIn, SHORT_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyy-MM-dd</code>格式解析成字符串
     *
     * @param date 待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatWeb(Date date) {
        return format(date, WEB_FORMAT);
    }

    /**
     * 把日期字符串按照<code>yyyy-MM-dd</code>格式，进行格式化
     *
     * @param dateStr  待格式化的日期字符串
     * @param formatIn 输入的日期字符串的格式
     * @return 格式化的字符串
     * @throws ParseException
     */
    public static String formatWeb(String dateStr, String formatIn) throws ParseException {
        return format(dateStr, formatIn, WEB_FORMAT);
    }

    /**
     * 把日期对象按照<code>yyyyMM</code>格式解析成字符串
     *
     * @param date 待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatMonth(Date date) {

        return format(date, MONTH_FORMAT);
    }

    /**
     * 把日期对象按照<code>HHmmss</code>格式解析成字符串
     *
     * @param date 待格式化的日期对象
     * @return 格式化的字符串
     */
    public static String formatTime(Date date) {
        return format(date, TIME_FORMAT);
    }

    /**
     * 获取yyyyMMddHHmmss+n位随机数格式的时间戳
     *
     * @param n 随机数位数
     * @return
     */
    public static String getTimestamp(int n) {
        return formatCurrent(LONG_FORMAT) + RandomStringUtils.randomNumeric(n);
    }

    /**
     * 获取yyyyMMddHHmmss
     *
     * @return
     */
    public static String formatFull() {
        return formatCurrent(LONG_FORMAT);
    }

    /**
     * 根据日期格式返回昨日日期
     *
     * @param format 日期格式
     * @return
     */
    public static String getYesterdayDate(String format) {
        return getDateCompareToday(format, -1, 0);
    }

    /**
     * 把当日日期作为基准，按照格式返回相差一定间隔的日期
     *
     * @param format     日期格式
     * @param daysAfter  和当日比相差几天，例如3代表3天后，-1代表1天前
     * @param monthAfter 和当日比相差几月，例如2代表2月后，-3代表3月前
     * @return
     */
    public static String getDateCompareToday(String format, int daysAfter, int monthAfter) {
        Calendar today = Calendar.getInstance();
        if (daysAfter != 0) {
            today.add(Calendar.DATE, daysAfter);
        }
        if (monthAfter != 0) {
            today.add(Calendar.MONTH, monthAfter);
        }
        return format(today.getTime(), format);
    }

    /**
     * 根据日期格式返回上月的日期
     *
     * @param format
     * @return
     */
    public static String getLastMonth(String format) {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -1);
        return format(today.getTime(), format);
    }

    /**
     * 平移当前时间，以分为单元，minutes
     *
     * @param minutes
     * @return
     */
    public static Date addCurMin(long minutes) {
        return DateUtils.addMinutes(new Date(), (int) minutes);
    }

    /**
     * 平移当前时间，以秒为单元，minutes
     *
     * @param secs
     * @return
     */
    public static Date addCurSeconds(long secs) {
        return addSeconds(new Date(), (int) secs);
    }

    /**
     * 平移当前时间，以秒为单元，minutes
     *
     * @param secs
     * @return
     */
    public static Date addCurSeconds(Date date, long secs) {
        return addSeconds(date, (int) secs);
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    /**
     * @param time
     * @param type 年、月、周、日
     * @return
     */
    public static Date[] getFirstAndLastTime(Date time, TimeCycleEnum type) {
        if (time == null) {
            return null;
        }
        Date[] retArr = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        switch (type) {
            case day:
                retArr[0] = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, 1);
                retArr[1] = cal.getTime();
                break;
            case week:
                cal.setFirstDayOfWeek(Calendar.MONDAY);
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                retArr[0] = cal.getTime();
                cal.add(Calendar.DAY_OF_YEAR, 7);
                retArr[1] = cal.getTime();
                break;
            case month:
                cal.set(Calendar.DAY_OF_MONTH, 1);
                retArr[0] = cal.getTime();
                cal.add(Calendar.MONTH, 1);
                retArr[1] = cal.getTime();
                break;
            case year:
                cal.set(Calendar.DAY_OF_YEAR, 1);
                retArr[0] = cal.getTime();
                cal.add(Calendar.YEAR, 1);
                retArr[1] = cal.getTime();
                break;
            default:
                return null;
        }
        return retArr;

    }
}
