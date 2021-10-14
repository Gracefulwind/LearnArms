package com.gracefulwind.learnarms.reader.utils;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

/**
 * @ClassName: TestUtil
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TestUtil {
    public static final String TAG = "TestUtil";
    
    public void log(){
        logdd();
    }
    
    protected void logdd(){
        LogUtil.e(TAG, "1123");
    }
}
