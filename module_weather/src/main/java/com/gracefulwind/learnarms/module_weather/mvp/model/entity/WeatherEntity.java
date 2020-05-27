package com.gracefulwind.learnarms.module_weather.mvp.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @ClassName: WeatherEntity
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/27 17:10
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/27 17:10
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

/**
 *
 * {
 *   "HeWeather6": [
 *     {
 *       "basic": {
 *         "cid": "CN101210101",
 *         "location": "杭州",
 *         "parent_city": "杭州",
 *         "admin_area": "浙江",
 *         "cnty": "中国",
 *         "lat": "30.28745842",
 *         "lon": "120.15357971",
 *         "tz": "+8.00"
 *       },
 *       "update": {
 *         "loc": "2020-05-27 15:03",
 *         "utc": "2020-05-27 07:03"
 *       },
 *       "status": "ok",
 *       "now": {
 *         "cloud": "91",
 *         "cond_code": "101",
 *         "cond_txt": "多云",
 *         "fl": "28",
 *         "hum": "51",
 *         "pcpn": "0.0",
 *         "pres": "1005",
 *         "tmp": "27",
 *         "vis": "16",
 *         "wind_deg": "175",
 *         "wind_dir": "南风",
 *         "wind_sc": "1",
 *         "wind_spd": "3"
 *       }
 *     }
 *   ]
 * }
 *
 * */


public class WeatherEntity {

    @SerializedName("HeWeather6")
    public List<WeatherBean> weatherList;

    public static class WeatherBean{

        @SerializedName("basic")
        public Basic basic;
        @SerializedName("update")
        public Update update;
        @SerializedName("status")
        public String status;
        @SerializedName("now")
        public Now now;

    }

    public static class Basic{
        @SerializedName("cid")
        String cid;
        @SerializedName("location")
        String location;
        @SerializedName("parent_city")
        String parent_city;
        @SerializedName("admin_area")
        String admin_area;
        @SerializedName("cnty")
        String cnty;
        @SerializedName("lat")
        String lat;
        @SerializedName("lon")
        String lon;
        @SerializedName("tz")
        String tz;
    }

    public static class Update{
        @SerializedName("loc")
        String loc;
        @SerializedName("utc")
        String utc;
    }

    public static class Now{

        @SerializedName("cloud")
        String cloud;
        @SerializedName("cond_code")
        String cond_code;
        @SerializedName("cond_txt")
        String cond_txt;
        @SerializedName("fl")
        String fl;
        @SerializedName("hum")
        String hum;
        @SerializedName("pcpn")
        String pcpn;
        @SerializedName("pres")
        String pres;
        @SerializedName("tmp")
        String tmp;
        @SerializedName("vis")
        String vis;
        @SerializedName("wind_deg")
        String wind_deg;
        @SerializedName("wind_dir")
        String wind_dir;
        @SerializedName("wind_sc")
        String wind_sc;
        @SerializedName("wind_spd")
        String wind_spd;
    }

}
