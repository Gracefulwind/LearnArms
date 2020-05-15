package com.gracefulwind.learnarms.module_test_dagger.di.module;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;

import dagger.Module;
import dagger.Provides;

/**
 * @ClassName: DaggerPage1Module
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/15 15:28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/15 15:28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

//@Module
public class DaggerPage1Module {

    private DaggerPage1Contract.View mView;

    public DaggerPage1Module(DaggerPage1Contract.View view) {
        mView = view;
    }

//    @Provides
    DaggerPage1Contract.View provideMainView() {
        return mView;
    }
}
