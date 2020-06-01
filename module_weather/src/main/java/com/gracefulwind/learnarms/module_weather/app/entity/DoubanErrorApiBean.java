package com.gracefulwind.learnarms.module_weather.app.entity;

import com.google.gson.annotations.SerializedName;

public class DoubanErrorApiBean {

    @SerializedName("msg")
    String msg;
    @SerializedName("code")
    int code;
    @SerializedName("request")
    String request;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
