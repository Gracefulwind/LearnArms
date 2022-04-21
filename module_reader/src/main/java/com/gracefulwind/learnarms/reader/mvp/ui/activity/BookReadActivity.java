package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.mvp.contract.BookReadContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerBookReadComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.BookReadPresenter;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: BookDetailActivity
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/4/13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.BOOK_READ_ACTIVITY)
public class BookReadActivity extends MyBaseActivity<BookReadPresenter> implements BookReadContract.View {
    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerBookReadComponent //如找不到该类,请编译一下项目
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.reader_activity_book_read;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }


}
