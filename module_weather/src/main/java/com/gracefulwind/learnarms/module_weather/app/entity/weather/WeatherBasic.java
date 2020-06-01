package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: WeatherBasic
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:04
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:04
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherBasic {
    @SerializedName("cid")
    String cid;
    @SerializedName("location")
    String location;
    @SerializedName("parent_city")
    String parentCity;
    @SerializedName("admin_area")
    String adminArea;
    @SerializedName("cnty")
    String cnty;
    @SerializedName("lat")
    String lat;
    @SerializedName("lon")
    String lon;
    @SerializedName("tz")
    String tz;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParentCity() {
        return parentCity;
    }

    public void setParentCity(String parentCity) {
        this.parentCity = parentCity;
    }

    public String getAdminArea() {
        return adminArea;
    }

    public void setAdminArea(String adminArea) {
        this.adminArea = adminArea;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    @Override
    public String toString() {
        return "WeatherBasic{" +
                "cid='" + cid + '\'' +
                ", location='" + location + '\'' +
                ", parentCity='" + parentCity + '\'' +
                ", adminArea='" + adminArea + '\'' +
                ", cnty='" + cnty + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", tz='" + tz + '\'' +
                '}';
    }
}
