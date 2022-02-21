package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.mvp.contract.DoodleContract;
import com.gracefulwind.learnarms.reader.mvp.contract.ReaderMainContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerReaderMainComponent;
import com.gracefulwind.learnarms.reader.mvp.di.component.ReaderMainComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.DoodlePresenter;
import com.gracefulwind.learnarms.reader.mvp.presenter.ReaderMainPresenter;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: ReaderMainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/21
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/21
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.READER_MAIN_ACTIVITY)
public class ReaderMainActivity extends MyBaseActivity<ReaderMainPresenter> implements ReaderMainContract.View {

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerReaderMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.reader_activity_reader_main;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }
}
