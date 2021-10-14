package com.gracefulwind.learnarms.reader.utils;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

/**
 * @ClassName: ChildTest
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class ChildTest extends TestUtil{
    public static final String TAG = "ChildTest";

    @Override
    protected void logdd() {
//        super.logdd();
        LogUtil.e(TAG, "554321");
    }
}
