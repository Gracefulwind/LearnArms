package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: DailyForecast
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 10:49
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 10:49
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class DailyForecast {

   /**
    *     "cond_code_d": "101",
    *           "cond_code_n": "104",
    *           "cond_txt_d": "多云",
    *           "cond_txt_n": "阴",
    *           "date": "2020-06-01",
    *           "hum": "79",
    *           "mr": "13:58",
    *           "ms": "01:44",
    *           "pcpn": "1.2",
    *           "pop": "56",
    *           "pres": "1004",
    *           "sr": "04:57",
    *           "ss": "18:57",
    *           "tmp_max": "30",
    *           "tmp_min": "19",
    *           "uv_index": "5",
    *           "vis": "25",
    *           "wind_deg": "128",
    *           "wind_dir": "东南风",
    *           "wind_sc": "3-4",
    *           "wind_spd": "16"
    * */

   @SerializedName("cond_code_d")
   String condCodeD;
   @SerializedName("cond_code_n")
   String condCodeN;
   @SerializedName("cond_txt_d")
   String condTxtD;
   @SerializedName("cond_txt_n")
   String condTxtN;
   @SerializedName("date")
   String date;
   @SerializedName("hum")
   String hum;
   @SerializedName("mr")
   String mr;
   @SerializedName("ms")
   String ms;
   @SerializedName("pcpn")
   String pcpn;
   @SerializedName("pop")
   String pop;
   @SerializedName("pres")
   String pres;
   @SerializedName("sr")
   String sr;
   @SerializedName("ss")
   String ss;
   @SerializedName("tmp_max")
   String tmpMax;
   @SerializedName("tmp_min")
   String tmpMin;
   @SerializedName("uv_index")
   String uvIndex;
   @SerializedName("vis")
   String vis;
   @SerializedName("wind_deg")
   String windDeg;
   @SerializedName("wind_dir")
   String windDir;
   @SerializedName("wind_sc")
   String windSc;
   @SerializedName("wind_spd")
   String windSpd;

    public String getCondCodeD() {
        return condCodeD;
    }

    public void setCondCodeD(String condCodeD) {
        this.condCodeD = condCodeD;
    }

    public String getCondCodeN() {
        return condCodeN;
    }

    public void setCondCodeN(String condCodeN) {
        this.condCodeN = condCodeN;
    }

    public String getCondTxtD() {
        return condTxtD;
    }

    public void setCondTxtD(String condTxtD) {
        this.condTxtD = condTxtD;
    }

    public String getCondTxtN() {
        return condTxtN;
    }

    public void setCondTxtN(String condTxtN) {
        this.condTxtN = condTxtN;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getTmpMax() {
        return tmpMax;
    }

    public void setTmpMax(String tmpMax) {
        this.tmpMax = tmpMax;
    }

    public String getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(String tmpMin) {
        this.tmpMin = tmpMin;
    }

    public String getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(String uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
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
        return "DailyForecast{" +
                "condCodeD='" + condCodeD + '\'' +
                ", condCodeN='" + condCodeN + '\'' +
                ", condTxtD='" + condTxtD + '\'' +
                ", condTxtN='" + condTxtN + '\'' +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", mr='" + mr + '\'' +
                ", ms='" + ms + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", sr='" + sr + '\'' +
                ", ss='" + ss + '\'' +
                ", tmpMax='" + tmpMax + '\'' +
                ", tmpMin='" + tmpMin + '\'' +
                ", uvIndex='" + uvIndex + '\'' +
                ", vis='" + vis + '\'' +
                ", windDeg='" + windDeg + '\'' +
                ", windDir='" + windDir + '\'' +
                ", windSc='" + windSc + '\'' +
                ", windSpd='" + windSpd + '\'' +
                '}';
    }
}
