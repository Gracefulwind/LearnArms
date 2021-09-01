package com.gracefulwind.learnarms.module_demo.mvp.ui.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;

import com.gracefulwind.learnarms.module_demo.R;
import com.gracefulwind.learnarms.module_demo.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_demo.mvp.presenter.MainPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;


//import com.gracefulwind.learnarms.module_reader.di.component.DaggerMainComponent;

import org.jetbrains.annotations.NotNull;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2020 17:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        DaggerMainComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.demo_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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