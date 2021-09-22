package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: TestGestureActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.TEST_GESTURE_ACTIVITY)
public class TestGestureActivity extends BaseActivity {
    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.view_integrate_frame_layout;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }
}
