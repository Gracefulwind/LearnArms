package com.gracefulwind.learnarms.module_test_dagger.mvp.model;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.DaggerPage1Presenter;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

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
@ActivityScope
public class DaggerPage1Model implements DaggerPage1Contract.Model {

    String from = "null";

    @Inject
    public DaggerPage1Model() {
        //get view and do something
        from = "from default";
    }

//    @Inject
//    public DaggerPage1Model(DaggerPage1Contract.View view) {
//        //get view and do something
//        from = view.toString();
//    }

    public DaggerPage1Model(String from) {
        this.from = from;
    }

    @Override
    public void onDestroy() {

    }
}
