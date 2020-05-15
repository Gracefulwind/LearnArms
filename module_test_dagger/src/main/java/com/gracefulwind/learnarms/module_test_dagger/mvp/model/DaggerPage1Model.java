package com.gracefulwind.learnarms.module_test_dagger.mvp.model;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;

import dagger.Provides;

/**
 * @ClassName: DaggerPage1Model
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/15 8:59
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/15 8:59
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class DaggerPage1Model implements DaggerPage1Contract.Model {



    @Override
    public void onDestroy() {

    }
}
