package com.gracefulwind.learnarms.commonsdk.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
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
}
