package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: WeatherUpdate
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:08
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:08
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

/**
 *   "update": {
 *     "loc": "2020-06-01 14:55",
 *     "utc": "2020-06-01 06:55"
 *   }
 * */
public class WeatherUpdate {

    @SerializedName("loc")
    String loc;
    @SerializedName("utc")
    String utc;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    @Override
    public String toString() {
        return "Update{" +
                "loc='" + loc + '\'' +
                ", utc='" + utc + '\'' +
                '}';
    }
}
