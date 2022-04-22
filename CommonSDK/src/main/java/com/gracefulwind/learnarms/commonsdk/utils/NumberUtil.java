package com.gracefulwind.learnarms.commonsdk.utils;

/**
 * @ClassName: NumberUtils
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/16
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/16
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class NumberUtil {

    /**
     * 与逻辑
     * */
    public static boolean isAnd(int x1, int x2){
        return (0 != (x1 & x2));
    }

    /**
     * 或逻辑
     * */
    public static boolean isOr(int x1, int x2){
        return (0 != (x1 | x2));
    }

    /**
     * 非逻辑
     * */
    public static boolean isNot(int x1, int x2){
        return !isOr(x1, x2);
    }

}
