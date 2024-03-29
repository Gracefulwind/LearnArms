package com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.module_test_dagger.R;
import com.gracefulwind.learnarms.module_test_dagger.R2;
import com.gracefulwind.learnarms.module_test_dagger.di.component.DaggerDaggerPage2Component;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage2Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.entity.DaggerPage1Item1;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.DaggerPage2Presenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;

import javax.inject.Inject;

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
@Route(path = RouterHub.TestDagger.DAGGER_PAGE_2_ACTIVITY)
public class DaggerPage2Activity extends BaseActivity<DaggerPage2Presenter> implements DaggerPage2Contract.View {

    @Inject
    DaggerPage1Item1 item1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDaggerPage2Component//如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .activity(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_dagger_page2; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.requestData();
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

    @OnClick({R2.id.adp2_tv_click1, R2.id.adp2_tv_click2, R2.id.adp2_tv_click3})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.adp2_tv_click1 == id){
            //do nothing
        }
        else if(R.id.adp2_tv_click2 == id){
//            Utils.navigation(this, RouterHub.TEST_DAGGER.DAGGER_PAGE_1_ACTIVITY);
//            Intent intent = new Intent(this, DaggerPage1Activity.class);
//            startActivity(intent);
        }
        else if(R.id.adp2_tv_click3 == id){

        }
    }

    @Override
    public void showPage2View(String str) {
        ArmsUtils.makeText(this, str);
        LogUtils.debugInfo("---" + item1 + "");
    }
}
