package com.gracefulwind.learnarms.face.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: FaceEntity
 * @Author: Gracefulwind
 * @CreateDate: 2022/3/10
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/3/10
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class FaceEntity {

    @Expose
    @SerializedName("face_token")
    String faceToken;
    @Expose
    @SerializedName("face_rectangle")
    FaceRectangle faceRectangle;

    @Override
    public String toString() {
        return "FaceEntity{" +
                "faceToken='" + faceToken + '\'' +
                ", faceRectangle=" + faceRectangle +
                '}';
    }

    public String getFaceToken() {
        return faceToken;
    }

    public void setFaceToken(String faceToken) {
        this.faceToken = faceToken;
    }

    public FaceRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public static class FaceRectangle{

        @Expose
        @SerializedName("top")
        int top;
        @Expose
        @SerializedName("left")
        int left;
        @Expose
        @SerializedName("width")
        int width;
        @Expose
        @SerializedName("height")
        int height;

        @Override
        public String toString() {
            return "FaceRectangle{" +
                    "top=" + top +
                    ", left=" + left +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
