package com.gracefulwind.learnarms.module_weather.app.entity;

/**
 * @ClassName: CityEntity
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/28 17:07
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/28 17:07
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class CityEntity {

    String citySearchName = "";

    String cityCode = "";

    String cityName = "";

    public String getCitySearchName() {
        return citySearchName;
    }

    public void setCitySearchName(String citySearchName) {
        this.citySearchName = citySearchName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
