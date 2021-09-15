package com.gracefulwind.learnarms.module_weather.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.module_weather.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.MainPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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

@Route(path = RouterHub.Weather.HOME_ACTIVITY)
public class MainActivity extends MyBaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R2.id.wam_btn_click1)
    Button wamBtnClick1;
    @BindView(R2.id.wam_btn_click2)
    Button wamBtnClick2;

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
        return R.layout.weather_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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

//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        checkNotNull(intent);
//        ArmsUtils.startActivity(intent);
//    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R2.id.wam_btn_click1, R2.id.wam_btn_click2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.wam_btn_click1 == id){
            //打开天气详情
            Utils.navigation(MainActivity.this, RouterHub.Weather.WEATHER_ACTIVITY);
        }
        else if(R.id.wam_btn_click2 == id){
            //其他页面，设置啥的，以后再做
        }
    }
}
