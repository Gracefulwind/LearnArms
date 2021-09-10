package com.gracefulwind.learnarms.reader.widget.doodle;

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
     * */
    public static final int DOODLE = 0x00000000;
    public static final int CORP = 0x00000001;


//== operate ============================
    //操作类型，暂时就doodle
    int type = DOODLE;
    int paintColor;
    int paintWidth = 14;


}
