package com.gracefulwind.learnarms.copy.mvp.ui.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.copy.R;
import com.gracefulwind.learnarms.copy.mvp.contract.MainContract;
import com.gracefulwind.learnarms.copy.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.copy.mvp.presenter.MainPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: MainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/8/27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/1
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Copy.HOME_ACTIVITY)
public class MainActivity extends MyBaseActivity<MainPresenter> implements MainContract.View {
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
        return R.layout.copy_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//        return 0;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "Main");

        initListener();
    }

    public void initListener() {

    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

}