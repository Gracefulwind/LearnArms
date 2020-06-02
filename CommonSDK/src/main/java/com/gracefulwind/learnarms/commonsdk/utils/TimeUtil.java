package com.gracefulwind.learnarms.commonsdk.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: TimeUtil
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 16:47
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 16:47
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class TimeUtil {
//    /**
//     * 是否需要更新Weather数据 1小时15分钟之内的return false; 传入null或者有问题的weather也会返回true
//     *
//     * @param weather
//     * @return
//     */
//    public static boolean isNeedUpdate(Weather weather) {
//        if (!acceptWeather(weather)) {
//            return true;
//        }
//        try {
//            final String updateTime = weather.get().basic.update.loc;
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//            Date updateDate = format.parse(updateTime);
//            Date curDate = new Date();
//            long interval = curDate.getTime() - updateDate.getTime();// 时间间隔 ms
//            if ((interval >= 0) && (interval < 75 * 60 * 1000)) {
//                return false;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    /**
     * 是否是今天2015-11-05 04:00 合法data格式： 2015-11-05 04:00 或者2015-11-05
     *
     * @param date
     * @return
     */
    public static boolean isToday(String date) {
        if (TextUtils.isEmpty(date) || date.length() < 10) {// 2015-11-05
            // length=10
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String today = format.format(new Date());
            if (TextUtils.equals(today, date.substring(0, 10))) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 转换日期2015-11-05为今天、明天、昨天，或者是星期几
     *
     * @param date
     * @return
     */
    public static String prettyDate(String date) {
        try {
            final String[] strs = date.split("-");
            final int year = Integer.valueOf(strs[0]);
            final int month = Integer.valueOf(strs[1]);
            final int day = Integer.valueOf(strs[2]);
            Calendar c = Calendar.getInstance();
            int curYear = c.get(Calendar.YEAR);
            int curMonth = c.get(Calendar.MONTH) + 1;// Java月份从0月开始
            int curDay = c.get(Calendar.DAY_OF_MONTH);
            if (curYear == year && curMonth == month) {
                if (curDay == day) {
                    return "今天";
                } else if ((curDay + 1) == day) {
                    return "明天";
                } else if ((curDay - 1) == day) {
                    return "昨天";
                }
            }
            c.set(year, month - 1, day);
            // http://www.tuicool.com/articles/Avqauq
            // 一周第一天是否为星期天
            boolean isFirstSunday = (c.getFirstDayOfWeek() == Calendar.SUNDAY);
            // 获取周几
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            // 若一周第一天为星期天，则-1
            if (isFirstSunday) {
                dayOfWeek = dayOfWeek - 1;
                if (dayOfWeek == 0) {
                    dayOfWeek = 7;
                }
            }
            // 打印周几
            // System.out.println(weekDay);

            // 若当天为2014年10月13日（星期一），则打印输出：1
            // 若当天为2014年10月17日（星期五），则打印输出：5
            // 若当天为2014年10月19日（星期日），则打印输出：7
            switch (dayOfWeek) {
                case 1:
                    return "周一";
                case 2:
                    return "周二";
                case 3:
                    return "周三";
                case 4:
                    return "周四";
                case 5:
                    return "周五";
                case 6:
                    return "周六";
                case 7:
                    return "周日";
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
