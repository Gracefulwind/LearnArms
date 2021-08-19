package com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.module_test_dagger.R;
import com.gracefulwind.learnarms.module_test_dagger.R2;
import com.gracefulwind.learnarms.module_test_dagger.di.component.DaggerDaggerPage1Component;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.DaggerPage1Model;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.entity.DaggerPage1Item1;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.DaggerPage1Presenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @ClassName: DaggerPage1Activity
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/14 16:58
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/14 16:58
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@Route(path = RouterHub.TestDagger.DAGGER_PAGE_1_ACTIVITY)
public class DaggerPage1Activity extends AppCompatActivity/*<DaggerPage1Presenter>*/ implements DaggerPage1Contract.View {

    @Inject
    DaggerPage1Item1 item1Test;

    @Inject
    DaggerPage1Presenter testPresenter;
    @BindView(R2.id.adp1_tv_title)
    TextView adp1TvTitle;
    @BindView(R2.id.adp1_tv_click1)
    TextView adp1TvClick1;
    @BindView(R2.id.adp1_tv_click2)
    TextView adp1TvClick2;
    @BindView(R2.id.adp1_tv_click3)
    TextView adp1TvClick3;

   /* @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //注入dagger2的量
//        DaggerDaggerPage1Component //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
        //完成ARouter注解标记的实例
        ARouter.getInstance().inject(this);

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dagger_page1;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LogUtils.debugInfo("item1Test == " + item1Test);
        LogUtils.debugInfo("do something");
    }*/


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_page1);
        ButterKnife.bind(this);
        DaggerDaggerPage1Component.builder()
//                .daggerPage1Module(new DaggerPage1Module(this, new DaggerPage1Model("from dagger method")))
                .setView(this)
                .setModel(new DaggerPage1Model("from dagger"))
                .build()
                .inject(this);
        ARouter.getInstance().inject(this);

    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @OnClick({R2.id.adp1_tv_click1, R2.id.adp1_tv_click2, R2.id.adp1_tv_click3})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.adp1_tv_click1 == id){
            System.out.println("==adp1_tv_click1===");
        }
        if(R.id.adp1_tv_click2 == id){
            System.out.println("==adp1_tv_click2===");
        }
        if(R.id.adp1_tv_click3 == id){
            System.out.println("==adp1_tv_click3===");
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("==onBackPressed===");
        super.onBackPressed();
    }
}
