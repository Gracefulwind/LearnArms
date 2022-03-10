package com.gracefulwind.learnarms.face.mvp.ui.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.face.R;
import com.gracefulwind.learnarms.face.R2;
import com.gracefulwind.learnarms.face.mvp.contract.MainContract;
import com.gracefulwind.learnarms.face.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.face.mvp.presenter.MainPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.OnClick;

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
@Route(path = RouterHub.Ocr.HOME_ACTIVITY)
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
        return R.layout.face_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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

    @OnClick({R2.id.oam_btn_two_picture, R2.id.oam_btn_find_people})
    public void onClick(View view) {
        int id = view.getId();
        if(R.id.oam_btn_two_picture == id){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mPresenter.testNet();
            }
        }else if(R.id.oam_btn_find_people == id){

        }else if(R.id.oam_btn_find_people == id){
            //to expand
        }
    }


}