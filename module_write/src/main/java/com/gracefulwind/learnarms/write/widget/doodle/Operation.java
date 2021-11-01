package com.gracefulwind.learnarms.write.widget.doodle;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * @ClassName: Operation
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/9
 * @Description: ---------------------------
 * 记录所有涂鸦画板层操作的类
 * @UpdateUser:
 * @UpdateDate: 2021/9/9
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class Operation {
//== static ===========================
    public static final String TAG = Operation.class.getName();
    /**
     * 操作类，用于拓展
     * 可以删了。。。
     * */
    public static final int DOODLE = 0x00000000;
    public static final int CORP = 0x00000001;
    public static final int ERASER = 0x00000002;


//== operate ============================
////这几个存在paint里就可以了，不用再单独存
//    //操作类型，暂时就doodle
//    int type = DOODLE;
//    int paintColor;
//    int paintWidth = 14;

    Paint paint;
    Path path;
    int operationType = DOODLE;
    boolean isFinished = false;

    public Operation() {
    }

    public Operation(Path path, Paint paint) {
        this.paint = paint;
        this.path = path;
    }



}
