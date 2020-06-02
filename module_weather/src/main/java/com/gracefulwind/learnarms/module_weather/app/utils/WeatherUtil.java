package com.gracefulwind.learnarms.module_weather.app.utils;

import com.gracefulwind.learnarms.commonsdk.utils.TimeUtil;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.DailyForecast;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherEntity;

import java.util.List;

/**
 * @ClassName: WeatherUtils
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/2 17:10
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/2 17:10
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherUtil {

    public static int getTodayDailyForecastIndex(List<DailyForecast> data){
        int todayIndex = -1;
//        if(!isOK()){
//            return todayIndex;
//        }
        for(int i = 0;i< data.size() ; i++){
            if(TimeUtil.isToday(data.get(i).getDate())){
                todayIndex = i;
                break;
            }
        }
        return todayIndex;
    }

    public static DailyForecast getTodayDailyForecast(List<DailyForecast> data){
//        if(!isOK()){
//            return todayIndex;
//        }
        for(int i = 0;i< data.size() ; i++){
            DailyForecast dailyForecast = data.get(i);
            if(TimeUtil.isToday(dailyForecast.getDate())){
                return dailyForecast;
            }
        }
        return null;
    }
//
//    /**
//     * 出错返回null
//     * @return Today DailyForecast
//     */
//    public DailyForecast getTodayDailyForecast(WeatherEntity weather){
//        List<DailyForecast> data = weather.getDailyForecast();
//        if(null == data || 0 == data.size()){
//            return null;
//        }
//        final int todayIndex = getTodayDailyForecastIndex(data);
//        if(todayIndex != -1){
//            DailyForecast forecast = data.get(todayIndex);
//            return forecast;
//        }
//        return null;
//    }

    /**
     * 今天的气温 6~16°
     * @return
     */
    public static String getTodayTempDescription(WeatherEntity weather){
        List<DailyForecast> data = weather.getDailyForecast();
        if(null == data || 0 == data.size()){
            return "";
        }
        final DailyForecast forecast = getTodayDailyForecast(data);
        if(null != forecast){
            return forecast.getTmpMin() + "~" + forecast.getTmpMax() + "°";
        }
        return "";
    }
}
