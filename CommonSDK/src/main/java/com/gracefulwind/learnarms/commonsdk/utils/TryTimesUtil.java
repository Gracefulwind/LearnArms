package com.gracefulwind.learnarms.commonsdk.utils;

/**
 * @ClassName: TryTimesUtil
 * @Author: Gracefulwind
 * @CreateDate: 2022/1/14
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/1/14
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TryTimesUtil {

    /**
     * 不做限定默认运行3次
     * */
    public static boolean tryByTimes(Runnable runnable){
        return tryByTimes(runnable, 3);
    }

    /**
     * 限定runnable运行
     * */
    public static boolean tryByTimes(Runnable runnable, int tryTimes){
        int timeCounter = 0;
        boolean isOk = false;
        while(timeCounter < tryTimes && !isOk){
            isOk = runnable.run();
            timeCounter++;
        }
        return isOk;
    }

    public interface Runnable{
        boolean run();
    }
}
