package com.gracefulwind.learnarms.module_test_dagger.mvp.presenter;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;

import javax.inject.Inject;

/**
 * @ClassName: DaggerPage1Presenter
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/14 17:33
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/14 17:33
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */


public class DaggerPage1Presenter {


    private DaggerPage1Contract.View mView;

    @Inject
    public DaggerPage1Presenter(DaggerPage1Contract.View mView) {
        this.mView = mView;
    }

    public void test1(){
        System.out.println("--- test1 ---");
        System.out.println("--- mView == " + mView);
    }
}
