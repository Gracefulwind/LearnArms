package com.gracefulwind.learnarms.commonsdk.core;

import android.app.Application;

import com.jess.arms.base.BaseApplication;

/**
 * @ClassName: MyApplication
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/7
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/7
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MyApplication extends BaseApplication {

    static MyApplication mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static MyApplication getInstance(){
        return mApp;
    }
}
