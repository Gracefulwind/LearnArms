package com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.module_test_dagger.R;
import com.gracefulwind.learnarms.module_test_dagger.R2;
import com.gracefulwind.learnarms.module_test_dagger.di.component.DaggerTestDaggerMainComponent;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.TestDaggerMainContract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.TestDaggerMainPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2020 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.TEST_DAGGER.HOME_ACTIVITY)
public class TestDaggerMainActivity extends BaseActivity<TestDaggerMainPresenter> implements TestDaggerMainContract.View {

    @BindView(R2.id.atdm_tv_title)
    TextView atdmTvTitle;
    @BindView(R2.id.atdm_tv_click1)
    TextView atdmTvClick1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTestDaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
//        EventBus.getDefault().post(new Object(), "11222");
    }

//    public void testEventBus(){
//
//    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_test_dagger_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R2.id.atdm_tv_title, R2.id.atdm_tv_click1, R2.id.atdm_tv_click2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.atdm_tv_title == id){
            //do nothing
        }
        else if(R.id.atdm_tv_click1 == id){
            Utils.navigation(this, RouterHub.TEST_DAGGER.DAGGER_PAGE_1_ACTIVITY);
//            Intent intent = new Intent(this, DaggerPage1Activity.class);
//            startActivity(intent);
        }
        else if(R.id.atdm_tv_click2 == id){
            Utils.navigation(this, RouterHub.TEST_DAGGER.DAGGER_PAGE_2_ACTIVITY);
        }
    }
}
