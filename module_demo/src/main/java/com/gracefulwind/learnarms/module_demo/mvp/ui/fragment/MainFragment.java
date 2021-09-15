package com.gracefulwind.learnarms.module_demo.mvp.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;


import com.gracefulwind.learnarms.commonsdk.base.BaseLazyLoadFragment;
import com.gracefulwind.learnarms.module_demo.R;
import com.gracefulwind.learnarms.module_demo.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_demo.mvp.presenter.MainPresenter;
import com.jess.arms.di.component.AppComponent;
//import com.gracefulwind.learnarms.module_reader.di.component.DaggerMainComponent;

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
        return R.layout.demo_fragment_main;
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