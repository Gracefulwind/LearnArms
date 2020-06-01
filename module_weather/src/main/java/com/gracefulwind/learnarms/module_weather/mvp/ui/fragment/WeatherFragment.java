package com.gracefulwind.learnarms.module_weather.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonres.base.BaseLazyLoadFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.mvp.di.component.DaggerWeatherFragmentComponent;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.app.entity.CityEntity;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.WeatherFragmentPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: WeatherFragment
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/26 17:47
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/26 17:47
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.WEATHER.WEATHER_FRAGMENT)
public class WeatherFragment extends BaseLazyLoadFragment<WeatherFragmentPresenter> implements WeatherFragmentContract.View {


    @BindView(R2.id.wfw_tv_title)
    TextView wfwTvTitle;
    @BindView(R2.id.wfw_fmisvll_container)
//    FirstMatchInScrollViewLinearLayout wfwFmisvllContainer;
    LinearLayout wfwFmisvllContainer;
    @BindView(R2.id.wfw_nsv_container)
    NestedScrollView wfwNsvContainer;

//-----------------------------------------------------------------------------------------------------

    //    public static final String KEY_TITLE = "key_title";
//-----------------------------------------------------------------------------------------------------
//    @Autowired(name = KEY_TITLE, required = false)
    String cityName = "default";
    String citySearchName = "";
//-----------------------------------------------------------------------------------------------------


    @Override
    protected int getLayoutId() {
        return R.layout.weather_fragment_weather;
    }

    @Override
    public void lazyLoadData() {
        mPresenter.getWeather(citySearchName);
    }

    public WeatherFragment makeInstance(CityEntity cityEntity) {
//        WeatherFragment newInstance = new WeatherFragment();
        WeatherFragment newInstance = (WeatherFragment) ARouter.getInstance()
                .build(RouterHub.WEATHER.WEATHER_FRAGMENT)
                .navigation();
        newInstance.setCityName(cityEntity.getCityName());
        newInstance.setCitySearchName(cityEntity.getCitySearchName());
        return newInstance;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerWeatherFragmentComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        System.out.println("========");

    }


    @OnClick({R2.id.wfw_tv_title})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (R.id.wfw_tv_title == id) {
            System.out.println("======================");
            mPresenter.getWeather("hangzhou");
        }
    }


    //============================
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public void showWeather(String weatherJson) {
        //todo:show weather json。 then translate into bean
        ArmsUtils.makeText(this.getContext(), weatherJson);
        //获取数据后重新测绘高度，让第一个子类能正确占满父控件
        wfwFmisvllContainer.requestLayout();
    }

    @Override
    public String getCitySearchName() {
        return citySearchName;
    }

    @Override
    public void setCitySearchName(String citySearchName) {
        this.citySearchName = citySearchName;
    }

    public void click1() {
//        mPresenter.click1();

        System.out.println("===111===");
    }

    public void click2() {
//        mPresenter.click2();
        System.out.println("====222===");
    }

}
