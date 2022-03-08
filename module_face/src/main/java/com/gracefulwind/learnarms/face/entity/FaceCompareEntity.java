package com.gracefulwind.learnarms.face.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @ClassName: FaceCompareEntity
 * @Author: Gracefulwind
 * @CreateDate: 2022/3/8
 * @Description: ---------------------------
 * 人脸对比接口返回实例
 * @UpdateUser:
 * @UpdateDate: 2022/3/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class FaceCompareEntity extends BaseFaceEntity {

    //比对结果置信度
    @Expose
    @SerializedName("confidence")
    float confidence;

    @Expose
    @SerializedName("confidence")
    Thresholds thresholds;

    //通过 image_url1、image_file1 或 image_base64_1 传入的图片在系统中的标识。
    @Expose
    @SerializedName("image_id1")
    String imageId1;
    @Expose
    @SerializedName("image_id2")
    String imageId2;

    //涉及到face的object，暂时先不放入，等OK了再搞
    Object faces1;
    Object faces2;


    /**
     *
     * 一组用于参考的置信度阈值
     * 如果置信值低于“千分之一”阈值则不建议认为是同一个人
     * 如果置信值超过“十万分之一”阈值，则是同一个人的几率非常高
     * 阈值不是静态的，每次比对返回的阈值不保证相同，所以没有持久化保存阈值的必要，更不要将当前调用返回的 confidence 与之前调用返回的阈值比较。
     *
     * */
    public class Thresholds{

        //误识率为千分之一的置信度阈值；
        @Expose
        @SerializedName("1e-3")
        float f1e3;

        //误识率为万分之一的置信度阈值；
        @Expose
        @SerializedName("1e-4")
        float f1e4;

        //误识率为十万分之一的置信度阈值；
        @Expose
        @SerializedName("1e-5")
        float f1e5;

        @Override
        public String toString() {
            return "Thresholds{" +
                    "f1e3=" + f1e3 +
                    ", f1e4=" + f1e4 +
                    ", f1e5=" + f1e5 +
                    '}';
        }

        public float getF1e3() {
            return f1e3;
        }

        public void setF1e3(float f1e3) {
            this.f1e3 = f1e3;
        }

        public float getF1e4() {
            return f1e4;
        }

        public void setF1e4(float f1e4) {
            this.f1e4 = f1e4;
        }

        public float getF1e5() {
            return f1e5;
        }

        public void setF1e5(float f1e5) {
            this.f1e5 = f1e5;
        }
    }
}
