package me.hp.meutils.utils;

import android.content.Context;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import me.hp.meutils.MeUtils;

/**
 * @author: HePing
 * @created: 2019/4/20
 * @desc: 时间工具类
 */
public class DateUtils {

    private static String UTC_TIME_FORMAT = "yyyyMMdd'T'HHmmss.00'Z'";
    private static HashMap<String, SimpleDateFormat> simpleDateFormatMap = new HashMap<>();

    /**
     * 判断目标时间是否处于开始时间和结束时间之间
     *
     * @param pattern   时间格式
     * @param desTime   目标时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 如果目标时间处于开始时间和结束时间之间则返回 true，否则返回false。
     */
    public static boolean isDesBetweenStartTimeAndEndTime(String pattern, String desTime, String startTime, String endTime) {
        Date startTimeDate = parse(startTime, pattern);
        Date endTimeDate = parse(endTime, pattern);
        Date desc = parse(desTime, pattern);
        return isDesBetweenStartTimeAndEndTime(desc, startTimeDate, endTimeDate);
    }

    /**
     * 判断目标时间是否处于开始时间和结束时间之间
     *
     * @param desTime   目标时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 如果目标时间处于开始时间和结束时间之间则返回 true，否则返回false。
     */
    public static boolean isDesBetweenStartTimeAndEndTime(Date desTime, Date startTime, Date endTime) {
        if (desTime == null || startTime == null || endTime == null) {
            return false;
        }
        if (desTime.before(startTime)) {
            return false;
        } else if (desTime.after(endTime)) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 将时间转换为字符串
     *
     * @param date    时间
     * @param pattern 格式
     * @return 时间字符串
     */
    public static String format(long date, String pattern) {
        return getSimpleDataFormat(pattern, TimeZone.getDefault()).format(new Date(date));
    }

    /**
     * 将时间转换为字符串
     *
     * @param date    时间
     * @param pattern 格式
     * @return 时间字符串
     */
    public static String format(long date, String pattern, TimeZone zone) {
        return getSimpleDataFormat(pattern, zone).format(new Date(date));
    }

    /**
     * 将时间对象转换为字符串
     *
     * @param date    时间
     * @param pattern 格式
     * @return 时间字符串
     */
    public static String format(Date date, String pattern) {
        return getSimpleDataFormat(pattern, TimeZone.getDefault()).format(date);
    }

    /**
     * 将时间字符串转换为时间对象
     *
     * @param dateStr 时间字符串
     * @param pattern 格式
     * @return 时间对象。转换失败则返回 null
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            return getSimpleDataFormat(pattern, TimeZone.getDefault()).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将时间戳转成时分秒形式：00：00：00
     *
     * @param date
     * @return
     */
    public static String getHHmmss(long date) {
        String hhmmss = "";

        long hours = date / (60 * 60 * 1000);
        long minute = (date % (60 * 60 * 1000)) / (60 * 1000);
        long second = ((date % (60 * 60 * 1000)) % (60 * 1000)) / 1000;
        if (hours < 10) {
            hhmmss = hhmmss + "0" + hours;
        } else {
            hhmmss = hhmmss + hours;
        }

        hhmmss = hhmmss + ":";

        if (minute < 10) {
            hhmmss = hhmmss + "0" + minute;
        } else {
            hhmmss = hhmmss + minute;
        }

        hhmmss = hhmmss + ":";

        if (second < 10) {
            hhmmss = hhmmss + "0" + second;
        } else {
            hhmmss = hhmmss + second;
        }

        return hhmmss;
    }

    /**
     * 获取 UTC 时间字符串
     *
     * @param time 要转换的时间
     * @return UTC 时间字符串
     */
    public static String getUTCTimeStr(Date time) {
        return format(getUTCTime(time), UTC_TIME_FORMAT);
    }

    /**
     * 相差量
     *
     * @param date1
     * @param date2
     * @param timeUnit 按什么来算差 比如：TimeUnit.DAYS 就是按天数来求差
     * @return
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取 UTC 时间字符串
     *
     * @param yyyyMMddHHmmss 常用的时间字符串
     * @return
     */
    public static String getUTCTimeStr(String yyyyMMddHHmmss) {
        Date d = parse(yyyyMMddHHmmss, "yyyyMMddHHmmss");
        if (d == null) {
            return yyyyMMddHHmmss;
        }
        return getUTCTimeStr(d);
    }

    /**
     * 获取 UTC 时间
     *
     * @param time 目标时间
     * @return UTC 时间
     */
    public static Date getUTCTime(Date time) {
        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);

        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);

        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        return cal.getTime();
    }

    /**
     * 获取年份
     *
     * @param time 待判断的时间
     * @return
     */
    public static int getYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     *
     * @param time 待判断的时间
     * @return
     */
    public static int getMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取在一年中的日期
     *
     * @param time 待判断的时间
     * @return
     */
    public static int getDayOfYear(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取在一个月中的日期
     *
     * @param time 待判断的时间
     * @return
     */
    public static int getDayOfMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isSameYear(Date date1, Date date2) {
        return getYear(date1) == getYear(date2);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        return isSameYear(date1, date2) && getMonth(date1) == getMonth(date2);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        return isSameYear(date1, date2) && getDayOfYear(date1) == getDayOfYear(date2);
    }

    /**
     * 从表中获取对应的 SimpleDateFormat。如果没有，则新建一个。
     *
     * @param pattern 时间格式
     * @return SimpleDateFormat
     */
    private static synchronized SimpleDateFormat getSimpleDataFormat(String pattern, TimeZone zone) {
        SimpleDateFormat dateFormat = simpleDateFormatMap.get(pattern);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            dateFormat.setTimeZone(zone);
            simpleDateFormatMap.put(pattern, dateFormat);
        }
        return dateFormat;
    }


    /**
     * 是否是24小时制
     * @return
     */
    public static boolean is24HourFormat() {
        return DateFormat.is24HourFormat(MeUtils.getInstance().getContext());
    }

}
