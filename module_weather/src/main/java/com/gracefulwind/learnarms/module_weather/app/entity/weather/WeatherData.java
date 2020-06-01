package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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


public class WeatherData {
    public static String STATUS_OK = "ok";

    //可能存在一个以上同名城市，所以这里是list
    //一般取第一个data
    //建议使用cityCode。以防重复
    @SerializedName("HeWeather6")
    List<WeatherEntity> weatherList = new ArrayList<>();

    public List<WeatherEntity> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherEntity> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "weatherList=" + weatherList +
                '}';
    }

}
