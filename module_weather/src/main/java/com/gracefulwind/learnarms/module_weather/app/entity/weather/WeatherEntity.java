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
    boolean hasNow, hasDailyForecast, hasLifeStyle, hasHourly;


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

    private void refreshUpdateTime() {
        updateTime = new Date().getTime();
    }

    public boolean isDateOk(){
        return hasNow() && hasDailyForecast() /*&& hasLifeStyle()*/ && hasHourly();
    }

    public boolean hasNow(){
        return null != now;
    }

    public boolean hasDailyForecast(){
        return null != dailyForecast && 0 != dailyForecast.size();
    }

    public boolean hasLifeStyle(){
        return null != lifeStyle && 0 != lifeStyle.size();
    }

    public boolean hasHourly(){
        return null != hourly && 0 != hourly.size();
    }


    //==================================================================================================
    public WeatherBasic getBasic() {
        return basic;
    }

/**
 *
 * 其实对时间要求没这么严谨的，这个synchronized是否可以不要？ == 现在不用4个boolean标志位了
 *
 *
 * */
    public void setBasic(WeatherBasic basic) {
        synchronized (this){
            refreshUpdateTime();
            this.basic = basic;
        }
    }

    public WeatherUpdate getUpdate() {
        return update;
    }

    public void setUpdate(WeatherUpdate update) {
        synchronized (this){
            refreshUpdateTime();
            this.update = update;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        synchronized (this){
            refreshUpdateTime();
            this.status = status;
        }
    }

    public WeatherNow getNow() {
        return now;
    }

    public void setNow(WeatherNow now) {
        synchronized (this){
            refreshUpdateTime();
            this.now = now;
        }

    }

    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        synchronized (this){
            refreshUpdateTime();
            this.dailyForecast = dailyForecast;
        }

    }

    public List<LifeStyle> getLifeStyle() {
        return lifeStyle;
    }

    public void setLifeStyle(List<LifeStyle> lifeStyle) {
        synchronized (this){
            refreshUpdateTime();
            this.lifeStyle = lifeStyle;
        }

    }

    public List<WeatherHourly> getHourly() {
        return hourly;
    }

    public void setHourly(List<WeatherHourly> hourly) {
        synchronized (this){
            refreshUpdateTime();
            this.hourly = hourly;
        }
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
