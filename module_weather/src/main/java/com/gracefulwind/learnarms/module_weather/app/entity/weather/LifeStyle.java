package com.gracefulwind.learnarms.module_weather.app.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: LifeStyle
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 15:19
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 15:19
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class LifeStyle {
    /**
     *
     * {
     *           "type": "comf",
     *           "brf": "较不舒适",
     *           "txt": "白天天气多云，并且空气湿度偏大，在这种天气条件下，您会感到有些闷热，不很舒适。"
     * */
    @SerializedName("type")
    String type;
    @SerializedName("brf")
    String brf;
    @SerializedName("txt")
    String txt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "LifeStyle{" +
                "type='" + type + '\'' +
                ", brf='" + brf + '\'' +
                ", txt='" + txt + '\'' +
                '}';
    }
}
