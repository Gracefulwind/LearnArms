package com.gracefulwind.learnarms.module_test_dagger.mvp.presenter;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;
import com.jess.arms.di.scope.ActivityScope;

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

@ActivityScope
public class DaggerPage1Presenter {
//    public class DaggerPage1Presenter extends BasePresenter<TestDaggerMainContract.Model, TestDaggerMainContract.View> {


    private DaggerPage1Contract.View mView;
    private DaggerPage1Contract.Model mModel;

    @Inject
    public DaggerPage1Presenter() {
//        this.mView = view;
//        this.mModel = model;
    }

    public DaggerPage1Presenter(DaggerPage1Contract.View view, DaggerPage1Contract.Model model) {
        this.mView = view;
        this.mModel = model;
    }

    public void test1(){
        System.out.println("--- test1 ---");
        System.out.println("--- mView == " + mView);
        System.out.println("--- mModel == " + mModel);
    }
}
