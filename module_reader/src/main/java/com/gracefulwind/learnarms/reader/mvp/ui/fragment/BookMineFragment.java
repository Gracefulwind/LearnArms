package com.gracefulwind.learnarms.reader.mvp.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.BaseLazyLoadFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.mvp.contract.BookActivityContract;
import com.gracefulwind.learnarms.reader.mvp.contract.BookMineContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerBookMineComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.BookActivityPresenter;
import com.gracefulwind.learnarms.reader.mvp.presenter.BookMinePresenter;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: BookActivityFragment
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.BOOK_MINE_FRAGMENT)
public class BookMineFragment extends BaseLazyLoadFragment<BookMinePresenter> implements BookMineContract.View{

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerBookMineComponent //如找不到该类,请编译一下项目
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.reader_fragment_book_mine;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @Override
    public void lazyLoadData() {
        //loadData
        TextView view = mRootView.findViewById(R.id.test);
        view.setText(view.getText() + "\r\n" + this.toString());
    }
}
