package com.gracefulwind.learnarms.commonsdk.utils.xunfei.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FlowSpeakEntity1
 * @Author: Gracefulwind
 * @CreateDate: 2021/12/28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/12/28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class FlowSpeakEntity1 {
    int code;
    String message;
    String sid;
    Data data;

    public static class Data{
        int status;
        Result result;

        public static class Result{
            int sn;
            boolean ls;
            int bg;
            int ed;
            List<Ws> ws = new ArrayList<>();

            public static class Ws{
                int bg;
                List<Cw> cw = new ArrayList<>();

                public static class Cw{
                    int sc;
                    String w;
                }
            }
        }
    }

    public String getData(){
        StringBuilder speakData = new StringBuilder();
        if(null != data && data.result != null && data.result.ws != null){
            for (Data.Result.Ws wsBean: data.result.ws) {
                if(null != wsBean.cw && 0 < wsBean.cw.size()){
                    speakData.append(wsBean.cw.get(0).w);
                }
            }
        }
        return speakData.toString();
    }
}
