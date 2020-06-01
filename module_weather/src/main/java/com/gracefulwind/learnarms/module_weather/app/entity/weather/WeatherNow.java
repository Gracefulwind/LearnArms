package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: WeatherNow
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:07
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:07
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class WeatherNow {

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