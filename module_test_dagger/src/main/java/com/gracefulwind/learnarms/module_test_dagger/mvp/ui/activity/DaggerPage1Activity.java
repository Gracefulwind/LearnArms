package com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.module_test_dagger.R;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.entity.DaggerPage1Item1;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.DaggerPage1Presenter;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.TestDaggerMainPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.LogUtils;

import javax.inject.Inject;


/**
 * @ClassName: DaggerPage1Activity
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/14 16:58
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/14 16:58
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@Route(path = RouterHub.TEST_DAGGER.DAGGER_PAGE_1_ACTIVITY)
public class DaggerPage1Activity extends BaseActivity<TestDaggerMainPresenter> implements DaggerPage1Contract.View {

//    @Inject
    DaggerPage1Item1 item1Test;

    @Inject
    DaggerPage1Presenter testPresenter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //注入dagger2的量
//        DaggerDaggerPage1Component //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
        //完成ARouter注解标记的实例
        ARouter.getInstance().inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dagger_page1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo("item1Test == " + item1Test);
        LogUtils.debugInfo("do something");
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}
