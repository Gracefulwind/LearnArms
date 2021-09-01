package com.gracefulwind.learnarms.reader.mvp.ui.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;

//import cn.skytech.iglobalwin.app.base.SimpleBaseFragment;

import com.gracefulwind.learnarms.commonres.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
//import com.gracefulwind.learnarms.module_reader.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.contract.MainContract;
import com.gracefulwind.learnarms.reader.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.reader.R;

/**
 * Created on 2021/08/20 11:09
 *
 * @author
 * module name is MainActivity
 */

public class MainFragment extends BaseLazyLoadFragment<MainPresenter> implements MainContract.View {

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
//        DaggerMainComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.reader_fragment_main;
    }

    @Override
    public void lazyLoadData() {

    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBarNoBack(toolbar, "Main");

        initListener();
    }

    private void initListener() {

    }

}