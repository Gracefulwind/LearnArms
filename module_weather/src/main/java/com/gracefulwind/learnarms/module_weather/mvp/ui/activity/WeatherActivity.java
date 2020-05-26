package com.gracefulwind.learnarms.module_weather.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.di.component.DaggerWeatherComponent;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.WeatherPresenter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: WeatherActivity
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/25 11:01
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/25 11:01
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@Route(path = RouterHub.WEATHER.WEATHER_ACTIVITY)
public class WeatherActivity extends BaseActivity<WeatherPresenter> implements WeatherContract.View {

    @BindView(R2.id.waw_btn_click1)
    Button wawBtnClick1;
    @BindView(R2.id.waw_btn_click2)
    Button wawBtnClick2;
    @BindView(R2.id.waw_tv_show_result)
    TextView wawTvShowResult;
    @BindView(R2.id.waw_fl_container)
    FrameLayout wawFlContainer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //init things...like dagger
        DaggerWeatherComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.weather_activity_weather;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R2.id.waw_btn_click1, R2.id.waw_btn_click2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.waw_btn_click1 == id){
            mPresenter.doSomething();
        }
        else if(R.id.waw_btn_click2 == id){

        }
    }

    @Override
    public void showSomeThing(String str) {
        wawTvShowResult.setText("result : " + str);
    }
}
