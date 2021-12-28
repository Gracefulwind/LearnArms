package com.gracefulwind.learnarms.commonsdk.utils.xunfei.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FlowSpeakEntity
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class FlowSpeakEntity {

    @Expose
    @SerializedName("")
    int code;
    @Expose
    @SerializedName("")
    String message;
    @Expose
    @SerializedName("")
    String sid;
    @Expose
    @SerializedName("")
    Data data;

    public static class Data{
        @Expose
        @SerializedName("status")
        int status;
        @Expose
        @SerializedName("result")
        Result result;

        public static class Result{

            @Expose
            @SerializedName("sn")
            int sn;
            @Expose
            @SerializedName("ls")
            boolean ls;
            @Expose
            @SerializedName("bg")
            int bg;
            @Expose
            @SerializedName("ed")
            int ed;
            @Expose
            @SerializedName("ws")
            List<Ws> ws = new ArrayList<>();

            public static class Ws{
                @Expose
                @SerializedName("bg")
                int bg;
                @Expose
                @SerializedName("cw")
                List<Cw> cw = new ArrayList<>();

                public static class Cw{
                    @Expose
                    @SerializedName("sc")
                    int sc;
                    @Expose
                    @SerializedName("w")
                    String w;
                }
            }
        }
    }

    public String getData(){
        String speakData = "";
        if(null != data){
//            data.result
        }
        return speakData;
    }
}
