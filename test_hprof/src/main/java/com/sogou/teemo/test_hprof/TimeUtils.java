package com.sogou.teemo.test_hprof;

import android.content.Context;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by baixuefei on 17/6/14.
 */
public class TimeUtils {
    private static final String TAG = "TimeUtils";

//    // 由 秒数 转换为 分:秒
//    public static String getTime(float time) {
//        StringBuilder sb = new StringBuilder();
//        int min = (int) (time / 60);
//        if (min < 10) {
//            sb.append("0");
//            sb.append(min);
//        } else {
//            sb.append(min);
//        }
//        sb.append(":");
//        int sec = (int) (time - min * 60);
//        if (sec < 10) {
//            sb.append("0");
//            sb.append(sec);
//        } else {
//            sb.append(sec);
//        }
//        return sb.toString();
//    }
//
//    /*
//      由秒数 转换为时:分:秒, 若小时为0,则省略小时位
//      01:09:11  或者09:11
//     */
//    public static String getHHMMSS(float time) {
//        StringBuilder sb = new StringBuilder();
//
//        int hour = (int) (time / (60 * 60));
////        if(hour > 0){
//        if (hour < 10) {
//            sb.append("0");
//            sb.append(hour);
//        } else {
//            sb.append(hour);
//        }
//        sb.append(":");
////        }
//
//        time = time - hour * 60 * 60;
//
//        int min = (int) (time / 60);
//        if (min < 10) {
//            sb.append("0");
//            sb.append(min);
//        } else {
//            sb.append(min);
//        }
//        sb.append(":");
//        int sec = (int) (time - min * 60);
//        if (sec < 10) {
//            sb.append("0");
//            sb.append(sec);
//        } else {
//            sb.append(sec);
//        }
//        return sb.toString();
//    }
//
//    public static String getHHMMSS_1(float time) {
//        StringBuilder sb = new StringBuilder();
//
//        int hour = (int) (time / (60 * 60));
//        if (hour > 0) {
//            if (hour < 10) {
//                sb.append("0");
//                sb.append(hour);
//            } else {
//                sb.append(hour);
//            }
//            sb.append(":");
//        }
//
//        time = time - hour * 60 * 60;
//
//        int min = (int) (time / 60);
//        if (min < 10) {
//            sb.append("0");
//            sb.append(min);
//        } else {
//            sb.append(min);
//        }
//        sb.append(":");
//        int sec = (int) (time - min * 60);
//        if (sec < 10) {
//            sb.append("0");
//            sb.append(sec);
//        } else {
//            sb.append(sec);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 由分钟数 转换成 HH:MM （08:10）格式
//     *
//     * @param minutes
//     * @return
//     */
//    public static String getHHMM(int minutes) {
//        StringBuilder sb = new StringBuilder();
//        int hour = (int) (minutes / 60);
//        if (hour < 10) {
//            sb.append("0");
//            sb.append(hour);
//        } else {
//            sb.append(hour);
//        }
//        sb.append(":");
//        int min = (int) (minutes - hour * 60);
//        if (min < 10) {
//            sb.append("0");
//            sb.append(min);
//        } else {
//            sb.append(min);
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 有分钟数 转化成 HH:mm 格式
//     *
//     * @param minites
//     * @return
//     */
//    public static String getTime_HH_MM(int minites) {
//        StringBuilder sb = new StringBuilder();
//        int hour = (int) (minites / 60);
//        if (hour < 10) {
//            sb.append("0");
//            sb.append(hour);
//        } else {
//            sb.append(hour);
//        }
//        sb.append(":");
//        int min = (int) (minites - hour * 60);
//        if (min < 10) {
//            sb.append("0");
//            sb.append(min);
//        } else {
//            sb.append(min);
//        }
//        return sb.toString();
//    }
//
//    public static String getRecentTime(long stamp) {
//        String str = null;
//        long timeStamp, timeCur;
//        long dayTime = 1000 * 60 * 60 * 24;
//        timeStamp = getTime(dayStr(stamp));
//        timeCur = getTime(dayStr(System.currentTimeMillis()));
//        Calendar cdStamp = Calendar.getInstance();
//        cdStamp.setTime(new Date(stamp));
//        int dayStamp = cdStamp.get(Calendar.DAY_OF_WEEK);
//
//        //相差天数
//        int days = (int) ((timeCur - timeStamp) / dayTime);
//        if (days == 0) {
//            //显示分钟
//            str = timeHourFormat(stamp);
//        } else if (days == 1) {
//            str = "昨天";
//        } else if (days > 1 & days < 8) {
//            //一周内显示星期几
//            str = getWeek(dayStamp);
//        } else if (days > 8 & days < 365) {
//            //如果不是一星期内，从月份开始显示
//            str = timeMonthFormat(stamp);
//        } else if (days > 365) {
//            //如果不是一年内，直接按照格式显示全部
//            str = timeAllFormat(stamp);
//        }
//        return str;
//    }
//
//    /**
//     * @param stamp
//     * @return
//     */
////    public static String getTimeFormat(Context context, long stamp) {
////        String str = null;
////        long timeStamp, timeCur;
////        long dayTime = 1000 * 60 * 60 * 24;
////        timeStamp = getTime(dayStr(stamp));
////        timeCur = getTime(dayStr(System.currentTimeMillis()));
////        Calendar cdStamp = Calendar.getInstance();
////        cdStamp.setTime(new Date(stamp));
////        int weeks = cdStamp.get(Calendar.DAY_OF_WEEK);
////        String localeLanguage = context.getResources().getConfiguration().locale.getLanguage();
////        //相差天数
////        int days = (int) ((timeCur - timeStamp) / dayTime);
////        if (days == 0) {
////            //显示分钟
////            str = context.getString(R.string.tv_today) + " " + timeHourFormat(stamp);
////        } else if (days == 1) {
////            if (localeLanguage.endsWith("zh")) {
////                // 中文环境（含繁体）
////                str = context.getString(R.string.tv_yestoday) + " " + timeHourFormat(stamp);
////            } else {
////                // 其他语言环境
////                str = getWeekFromDay(context, weeks) + " " + timeHourFormat(stamp);
////            }
////        } else if (days == 2) {
////            if (localeLanguage.endsWith("zh")) {
////                // 中文环境（含繁体）
////                str = context.getString(R.string.tv_the_day_before_yestoday) + " " + timeHourFormat(stamp);
////            } else {
////                // 其他语言环境
////                str = getWeekFromDay(context, weeks) + " " + timeHourFormat(stamp);
////            }
////        } else if (days < 365) {//今年
////            str = timeYearMonthHourFormat(stamp);
////        } else {
////            //如果不是一年内，直接按照格式显示全部
////            str = timeAllFormat(stamp);
////        }
////        return str;
////    }
//
//    public static String getWeekFromDay(Context context, int week) {
//        switch (week) {
//            case Calendar.MONDAY:
//                return context.getString(R.string.tv_monday);
//            case Calendar.TUESDAY:
//                return context.getString(R.string.tv_tuesday);
//            case Calendar.WEDNESDAY:
//                return context.getString(R.string.tv_wednesday);
//            case Calendar.THURSDAY:
//                return context.getString(R.string.tv_thursday);
//            case Calendar.FRIDAY:
//                return context.getString(R.string.tv_friday);
//            case Calendar.SATURDAY:
//                return context.getString(R.string.tv_saturday);
//            case Calendar.SUNDAY:
//                return context.getString(R.string.tv_sunday);
//            default:
//                return "";
//        }
//    }
//
//    /**
//     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2017-04-01"）
//     *
//     * @param time
//     * @return
//     */
//    public static String timeAllFormat(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
//        String times = sdr.format(new Date(time));
//        return times;
//    }
//
//    /**
//     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"04-01"）
//     *
//     * @param time
//     * @return
//     */
//    public static String timeMonthFormat(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd");
//        String times = sdr.format(new Date(time));
//        return times;
//    }
//
//    /**
//     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"15：30"）
//     *
//     * @param time
//     * @return
//     */
//    public static String timeHourFormat(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm");
//        String times = sdr.format(new Date(time));
//        return times;
//    }
//
//    public static String timeYearMonthHourFormat(long time) {
//        SimpleDateFormat sdr = new SimpleDateFormat("MM-dd HH:mm");
//        String times = sdr.format(new Date(time));
//        return times;
//    }
//
//    /**
//     * 转换为星期几
//     *
//     * @param mydate
//     * @return
//     */
//    private static String getWeek(int mydate) {
//        String week = null;
//        // 获取指定日期转换成星期几
//        if (mydate == 1) {
//            week = "星期日";
//        } else if (mydate == 2) {
//            week = "星期一";
//        } else if (mydate == 3) {
//            week = "星期二";
//        } else if (mydate == 4) {
//            week = "星期三";
//        } else if (mydate == 5) {
//            week = "星期四";
//        } else if (mydate == 6) {
//            week = "星期五";
//        } else if (mydate == 7) {
//            week = "星期六";
//        }
//        return week;
//    }
//
//    /**
//     * 时间转化为年月日
//     *
//     * @param time
//     * @return
//     */
//    public static String dayStr(long time) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String str = sdf.format(new Date(time));
//        return str + "0000";
//    }
//
//    /**
//     * 获取时间戳
//     *
//     * @param time
//     * @return
//     */
//    public static long getTime(String time) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
//            Date date = sdf.parse(time);
//            return date.getTime();
//        } catch (ParseException e) {
//
//        }
//        return System.currentTimeMillis();
//    }
//
//    /**
//     * 显示 - 年月日
//     *
//     * @param time
//     * @return
//     */
//    public static String getDate(long time) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String date = sdf.format(new Date(time));
//            return date;
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//    /**
//     * 得到当前时间
//     *
//     * @return
//     */
//    public static String getHMTime() {
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        String timeStr = sdf.format(new Date(System.currentTimeMillis()));
//        return timeStr;
//    }
//
//    /**
//     * 由时间戳 得到 mm:ss 的时间格式
//     *
//     * @param time
//     * @return
//     */
//    public static String getTimeStringMS(long time) {
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        return formatter.format(new Date(time));
//    }
//
//    //将毫秒数转换，格式为 9月16日 11:35
//    public static String getTimeString(long time) {
//        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm");
//        return formatter.format(new Date(time));
//    }
//
//    //将毫秒数转换，格式为 9月16日 11:35:40,870
//    public static String getWholeTimeString(long time) {
//        String str = String.valueOf(time);
//        if (str.length() < 13) {
//            time = time * 1000;
//        }
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss,SSS");
//        return formatter.format(new Date(time));
//    }
//
//    /**
//     * 定位时间格式
//     *
//     * @param oldtime
//     * @return
//     * @throws ParseException
//     */
//    public static String viewLocationDate(long oldtime) throws ParseException {
//        String result = "";
//        long currenttime = System.currentTimeMillis();
//        long delta = currenttime - oldtime;
//        Calendar c = Calendar.getInstance();
//        Date dd = new Date(oldtime);
//        c.setTime(dd);
//        long time = currenttime - oldtime;
//        time = time / 1000; // 将毫秒转化为秒
//        time = time / 60;
//        long minute = time % 60; // 分
//        time = time / 60;
//        time = time / 24;
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//        SimpleDateFormat formatter2 = new SimpleDateFormat("MM-dd HH:mm");
//        if (delta < 0) {
//            result = formatter.format(dd);
//        } else if (delta >= 0) {
//            if (delta < 10 * 1000) {
//                result = "10秒内";
//            } else if (delta >= 10 * 1000 && delta < 20 * 1000) {
//                result = "20秒内";
//            } else if (delta >= 20 * 1000 && delta < 30 * 1000) {
//                result = "30秒内";
//            } else if (delta >= 30 * 1000 && delta < 60 * 1000) {
//                result = "1分钟内";
//            } else if (delta >= 60 * 1000 && delta < 60 * 60 * 1000) {
//                result = minute + "分钟前";
//            } else if (delta >= 60 * 60 * 1000 && delta < 24 * 60 * 60 * 1000) {
//                result = formatter.format(dd);
//            } else if (delta >= 24 * 60 * 60 * 1000) {
//                result = formatter2.format(dd);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * 如果月、日、时、分显示是一位则补0
//     *
//     * @param c
//     * @return
//     */
//    public static String pad(int c) {
//        if (c >= 10)
//            return String.valueOf(c);
//        else
//            return "0" + String.valueOf(c);
//    }
//
//    public static boolean isDate(String str_input, String rDateFormat) {
//        if (!isNull(str_input)) {
//            SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
//            formatter.setLenient(false);
//            try {
//                formatter.format(formatter.parse(str_input));
//            } catch (Exception e) {
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public static boolean isNull(String str) {
//        if (str == null)
//            return true;
//        else
//            return false;
//    }
//
//    // 返回日期 格式:2006-07-05
//    public static Timestamp date(String str) {
//        Timestamp tp = null;
//        if (str.length() <= 10) {
//            String[] string = str.trim().split("-");
//            int one = Integer.parseInt(string[0]) - 1900;
//            int two = Integer.parseInt(string[1]) - 1;
//            int three = Integer.parseInt(string[2]);
//            tp = new Timestamp(one, two, three, 0, 0, 0, 0);
//        }
//        return tp;
//    }
//
//    public static boolean checkDate(String date) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        Date d = null;
//        try {
//            d = df.parse(date);
//        } catch (Exception e) {
//            //如果不能转换,肯定是错误格式
//            return false;
//        }
//        String s1 = df.format(d);
//        // 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
//        // "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
//        // 逻辑上不对.
//        return date.equals(s1);
//    }
//
//    // 当前时间
//    public static Timestamp crunttime() {
//        return new Timestamp(System.currentTimeMillis());
//    }
//
//    /**
//     * 将当前时间转换成字符串
//     *
//     * @return String（时:分:秒）
//     */
//    public static String date2String2() {
//        return dateFormater2.get().format(Calendar.getInstance().getTime());
//    }
//
//    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
//        @Override
//        protected SimpleDateFormat initialValue() {
//            return new SimpleDateFormat("HH:mm:ss");
//        }
//    };
//
//    /**
//     * 日期显示规则
//     *
//     * @param when
//     * @return
//     */
//    public static String getCurrentTime(long when) {
//        Calendar last = Calendar.getInstance();
//        last.setTime(new Date(when));
//
//        Calendar current = Calendar.getInstance();
//        current.setTime(new Date(System.currentTimeMillis()));
//
//        long delta = System.currentTimeMillis() - when;
//        int hourCurrent = current.get(Calendar.HOUR_OF_DAY);
//        int hourLast = last.get(Calendar.HOUR_OF_DAY);
//
//        int dayCurrent = current.get(Calendar.DAY_OF_YEAR);
//        int dayLast = last.get(Calendar.DAY_OF_YEAR);
//        if (delta <= 60 * 60 * 1000) {//1小时内
//            long min = delta / 1000 / 60;
//            if (min == 0) {
//                return "刚刚";
//            } else {
//                return min + "分钟前";
//            }
//        } else if (dayLast == dayCurrent && delta <= 3 * 60 * 60 * 1000 && delta > 60 * 60 * 1000) { //大于1小时，小于3小时
//            return (hourCurrent - hourLast) + "小时前";
//        } else if (dayLast == dayCurrent && delta <= 24 * 60 * 60 * 1000 && delta > 3 * 60 * 60 * 1000) { // 大于3小时，小于24小时
//            StringBuilder builder = new StringBuilder();
//            if (hourLast < 10) {
//                builder.append("0").append(hourLast);
//            } else {
//                builder.append(hourLast);
//            }
//            builder.append(":");
//            int min = last.get(Calendar.MINUTE);
//            if (min < 10) {
//                builder.append("0").append(min);
//            } else {
//                builder.append(last.get(Calendar.MINUTE));
//            }
//            return builder.toString();
//        } else {
//            if (current.get(Calendar.YEAR) == last.get(Calendar.YEAR)) {
//                if (dayCurrent - dayLast == 0) { //当天
//                    StringBuilder builder = new StringBuilder();
//                    if (hourLast < 10) {
//                        builder.append("0").append(hourLast);
//                    } else {
//                        builder.append(hourLast);
//                    }
//                    builder.append(":");
//                    int min = last.get(Calendar.MINUTE);
//                    if (min < 10) {
//                        builder.append("0").append(min);
//                    } else {
//                        builder.append(last.get(Calendar.MINUTE));
//                    }
//                    return builder.toString();
//                } else if (dayCurrent - dayLast == 1) { //昨天
//                    StringBuilder builder = new StringBuilder();
//                    if (hourLast < 10) {
//                        builder.append("0").append(hourLast);
//                    } else {
//                        builder.append(hourLast);
//                    }
//                    builder.append(":");
//                    int min = last.get(Calendar.MINUTE);
//                    if (min < 10) {
//                        builder.append("0").append(min);
//                    } else {
//                        builder.append(last.get(Calendar.MINUTE));
//                    }
//                    return "昨天 " + builder.toString();
//                } else if (dayCurrent - dayLast < 7) { //周几
//                    int week = last.get(Calendar.DAY_OF_WEEK);
//                    switch (week) {
//                        case Calendar.MONDAY:
//                            return "星期一";
//                        case Calendar.TUESDAY:
//                            return "星期二";
//                        case Calendar.WEDNESDAY:
//                            return "星期三";
//                        case Calendar.THURSDAY:
//                            return "星期四";
//                        case Calendar.FRIDAY:
//                            return "星期五";
//                        case Calendar.SATURDAY:
//                            return "星期六";
//                        case Calendar.SUNDAY:
//                            return "星期日";
//                        default:
//                            return "";
//                    }
//                } else { //当年
//                    int month = last.get(Calendar.MONTH) + 1;
//                    int day = last.get(Calendar.DAY_OF_MONTH);
//                    StringBuilder sb = new StringBuilder();
//                    if (month < 10) {
//                        sb.append("0").append(month);
//                    } else {
//                        sb.append(month);
//                    }
//                    sb.append("-");
//                    if (day < 10) {
//                        sb.append("0").append(day);
//                    } else {
//                        sb.append(day);
//                    }
//                    return sb.toString();
//                }
//            } else { //非当年
//                StringBuilder sb = new StringBuilder();
//                sb.append(last.get(Calendar.YEAR));
//                sb.append("-");
//                int month = last.get(Calendar.MONTH) + 1;
//                int day = last.get(Calendar.DAY_OF_MONTH);
//                if (month < 10) {
//                    sb.append("0").append(month);
//                } else {
//                    sb.append(month);
//                }
//                sb.append("-");
//                if (day < 10) {
//                    sb.append("0").append(day);
//                } else {
//                    sb.append(day);
//                }
//                return sb.toString();
//            }
//        }
//    }
//
//    public static int getAgeFromBirthDay(String birthday) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        int age = 0;
//        Date d = null;
//        try {
//            d = df.parse(birthday);
//            age = getAge(d);
//        } catch (Exception e) {
//            //如果不能转换,肯定是错误格式
//            age = -1;
//            return age;
//        }
//
//        return age;
//    }
//
//    public static int getAge(Date birthDay) throws Exception {
//        Calendar cal = Calendar.getInstance();
//        if (cal.before(birthDay)) {
//            throw new IllegalArgumentException(
//                    "The birthDay is before Now.It's unbelievable!");
//        }
//
//        int yearNow = cal.get(Calendar.YEAR);
//        int monthNow = cal.get(Calendar.MONTH);
//        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
//        cal.setTime(birthDay);
//
//        int yearBirth = cal.get(Calendar.YEAR);
//        int monthBirth = cal.get(Calendar.MONTH);
//        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
//        int age = yearNow - yearBirth;
//
//        if (monthNow <= monthBirth) {
//            if (monthNow == monthBirth) {
//                //monthNow==monthBirth
//                if (dayOfMonthNow < dayOfMonthBirth) {
//                    age--;
//                } else {
//                    //do nothing
//                }
//            } else {
//                //monthNow>monthBirth
//                age--;
//            }
//        } else {
//            //monthNow<monthBirth
//            //donothing
//        }
//        return age;
//    }
}
