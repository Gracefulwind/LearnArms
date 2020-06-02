package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: WeatherBean
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:02
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:02
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherEntity {

    //生成实例的时候自动产生
    long createTime = new Date().getTime();
    //更新数据时刷新
    long updateTime;
    //4个开关判断数据对不对。。。全拿到了再一次更新呗。。。
    //todo:加个isOk()方法
    boolean hasNow;
    boolean hasDailyForecast;
    boolean hasLifeStyle;
    boolean hasHourly;


    @SerializedName("basic")
    WeatherBasic basic;
    @SerializedName("update")
    WeatherUpdate update;
    @SerializedName("status")
    String status;
    @SerializedName("now")
    WeatherNow now;
    @SerializedName("daily_forecast")
    List<DailyForecast> dailyForecast = new ArrayList<>();
    @SerializedName("lifestyle")
    List<LifeStyle> lifeStyle = new ArrayList<>();
    @SerializedName("hourly")
    List<WeatherHourly> hourly = new ArrayList<>();

    public WeatherBasic getBasic() {
        return basic;
    }

    public void setBasic(WeatherBasic basic) {
        this.basic = basic;
    }

    public WeatherUpdate getUpdate() {
        return update;
    }

    public void setUpdate(WeatherUpdate update) {
        this.update = update;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WeatherNow getNow() {
        return now;
    }

    public void setNow(WeatherNow now) {
        this.now = now;
    }

    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public List<LifeStyle> getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(List<LifeStyle> lifeStyle) {
        this.lifeStyle = lifeStyle;
    }

    public List<WeatherHourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherHourly> hourly) {
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "basic=" + basic +
                ", update=" + update +
                ", status='" + status + '\'' +
                ", now=" + now +
                ", dailyForecast=" + dailyForecast +
                ", lifeStyle=" + lifeStyle +
                ", hourly=" + hourly +
                '}';
    }
}
