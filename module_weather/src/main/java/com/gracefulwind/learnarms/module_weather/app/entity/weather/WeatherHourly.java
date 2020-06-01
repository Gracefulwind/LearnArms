package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: WeatherHourly
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherHourly {
    /**
     *
     *     "cloud": "98",
     *           "cond_code": "101",
     *           "cond_txt": "多云",
     *           "dew": "20",
     *           "hum": "74",
     *           "pop": "7",
     *           "pres": "1006",
     *           "time": "2020-06-01 16:00",
     *           "tmp": "29",
     *           "wind_deg": "159",
     *           "wind_dir": "东南风",
     *           "wind_sc": "3-4",
     *           "wind_spd": "14"
     * */


    @SerializedName("cloud")
    String cloud;
    @SerializedName("cond_code")
    String condCode;
    @SerializedName("cond_txt")
    String condTxt;
    @SerializedName("dew")
    String dew;
    @SerializedName("hum")
    String hum;
    @SerializedName("pop")
    String pop;
    @SerializedName("pres")
    String pres;
    @SerializedName("time")
    String time;
    @SerializedName("tmp")
    String tmp;
    @SerializedName("wind_deg")
    String windDeg;
    @SerializedName("wind_dir")
    String windDir;
    @SerializedName("wind_sc")
    String windSc;
    @SerializedName("wind_spd")
    String windSpd;

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCondCode() {
        return condCode;
    }

    public void setCondCode(String condCode) {
        this.condCode = condCode;
    }

    public String getCondTxt() {
        return condTxt;
    }

    public void setCondTxt(String condTxt) {
        this.condTxt = condTxt;
    }

    public String getDew() {
        return dew;
    }

    public void setDew(String dew) {
        this.dew = dew;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }

    @Override
    public String toString() {
        return "WeatherHourly{" +
                "cloud='" + cloud + '\'' +
                ", condCode='" + condCode + '\'' +
                ", condTxt='" + condTxt + '\'' +
                ", dew='" + dew + '\'' +
                ", hum='" + hum + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", time='" + time + '\'' +
                ", tmp='" + tmp + '\'' +
                ", windDeg='" + windDeg + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windSc='" + windSc + '\'' +
                ", windSpd='" + windSpd + '\'' +
                '}';
    }
}
