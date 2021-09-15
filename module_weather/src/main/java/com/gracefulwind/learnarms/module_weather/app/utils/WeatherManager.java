package com.gracefulwind.learnarms.module_weather.app.utils;

import androidx.annotation.StringRes;

import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherEntity;

import java.util.HashMap;
import java.util.Map;

import static com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment.WEATHER_TYPE_FORECAST;
import static com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment.WEATHER_TYPE_HOURLY;
import static com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment.WEATHER_TYPE_LIFESTYLE;
import static com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment.WEATHER_TYPE_NOW;

/**
 * @ClassName: WeatherManager
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/2 17:26
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/2 17:26
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherManager {

    private static WeatherManager wm;

    public static WeatherManager getInstance(){
        if(null != wm){
            return wm;
        }else synchronized (WeatherManager.class){
           wm = new WeatherManager();
           return wm;
        }
    }
//=================================================
    //用cityCode比cityName作为Key合适
    private Map<String, WeatherEntity> weatherMap = new HashMap<>();

    public void putWeather(String cityName, String weatherType, WeatherEntity weatherEntity){
        //step1:查看是否有，没有直接添加
        if(!weatherMap.containsKey(cityName)){
            weatherMap.put(cityName, weatherEntity);
            return;
        }
        //step2:如果有，根据type更新数据,并更新updateTime
        WeatherEntity oldWeatherEntity = weatherMap.get(cityName);
        switch (weatherType){
            case WEATHER_TYPE_NOW:
                oldWeatherEntity.setNow(weatherEntity.getNow());
                break;
            case WEATHER_TYPE_FORECAST:
                oldWeatherEntity.setDailyForecast(weatherEntity.getDailyForecast());
                break;
            case WEATHER_TYPE_LIFESTYLE:
                oldWeatherEntity.setLifeStyle(weatherEntity.getLifeStyle());
                break;
            case WEATHER_TYPE_HOURLY:
                oldWeatherEntity.setHourly(weatherEntity.getHourly());
                break;
            default:
                break;
        }
    }

    public WeatherEntity getWeather(String cityName){
        return weatherMap.get(cityName);
    }

    public boolean isWeatherOK(String cityName){
        WeatherEntity weatherEntity = weatherMap.get(cityName);
        return (null != weatherEntity) && weatherEntity.isDateOk();
    }

}
