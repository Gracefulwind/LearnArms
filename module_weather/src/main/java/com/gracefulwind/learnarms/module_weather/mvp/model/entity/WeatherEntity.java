package com.gracefulwind.learnarms.module_weather.mvp.model.entity;

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


public class WeatherEntity {
    public static String STATUS_OK = "ok";

    @SerializedName("HeWeather6")
    List<WeatherBean> weatherList = new ArrayList<>();

    public List<WeatherBean> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherBean> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "weatherList=" + weatherList +
                '}';
    }

    public static class WeatherBean{

        @SerializedName("basic")
        Basic basic;
        @SerializedName("update")
        Update update;
        @SerializedName("status")
        String status;
        @SerializedName("now")
        Now now;

        public Basic getBasic() {
            return basic;
        }

        public void setBasic(Basic basic) {
            this.basic = basic;
        }

        public Update getUpdate() {
            return update;
        }

        public void setUpdate(Update update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        @Override
        public String toString() {
            return "WeatherBean{" +
                    "basic=" + basic +
                    ", update=" + update +
                    ", status='" + status + '\'' +
                    ", now=" + now +
                    '}';
        }
    }

    public static class Basic{
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
            return "Basic{" +
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

    public static class Update{
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
        String winddeg;
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

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWinddeg() {
            return winddeg;
        }

        public void setWinddeg(String winddeg) {
            this.winddeg = winddeg;
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
            return "Now{" +
                    "cloud='" + cloud + '\'' +
                    ", cond_code='" + cond_code + '\'' +
                    ", cond_txt='" + cond_txt + '\'' +
                    ", fl='" + fl + '\'' +
                    ", hum='" + hum + '\'' +
                    ", pcpn='" + pcpn + '\'' +
                    ", pres='" + pres + '\'' +
                    ", tmp='" + tmp + '\'' +
                    ", vis='" + vis + '\'' +
                    ", winddeg='" + winddeg + '\'' +
                    ", windDir='" + windDir + '\'' +
                    ", windSc='" + windSc + '\'' +
                    ", windSpd='" + windSpd + '\'' +
                    '}';
        }
    }

}
