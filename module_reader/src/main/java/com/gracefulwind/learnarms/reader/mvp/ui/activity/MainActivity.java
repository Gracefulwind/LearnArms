package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.di.component.DaggerMainComponent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;


//import com.gracefulwind.learnarms.module_reader.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.contract.MainContract;
import com.gracefulwind.learnarms.reader.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.reader.R;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: MainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/8/27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/8/27
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.HOME_ACTIVITY)
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.reader_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "Main");

        initListener();
    }

    public void initListener() {

    }

    @Override
    public void showMessage(@NonNull @NotNull String s) {

    }
}