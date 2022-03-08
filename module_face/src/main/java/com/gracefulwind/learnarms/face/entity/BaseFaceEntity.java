package com.gracefulwind.learnarms.face.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: BaseOcrEntity
 * @Author: Gracefulwind
 * @CreateDate: 2022/3/8
 * @Description: ---------------------------
 * ocr的基类entity，这三个是任何接口都有的
 * @UpdateUser:
 * @UpdateDate: 2022/3/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class BaseFaceEntity {

    @Expose
    @SerializedName("time_used")
    int timeUsed;

    @Expose
    @SerializedName("request_id")
    String requestId;

    //接口返回错误时才有
    @Expose
    @SerializedName("error_message")
    String errorMessage;


    @Override
    public String toString() {
        return "BaseOcrEntity{" +
                "timeUsed='" + timeUsed + '\'' +
                ", requestId='" + requestId + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
