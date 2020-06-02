package com.gracefulwind.learnarms.module_weather.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonres.base.BaseLazyLoadFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.TimeUtil;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.commonservice.gank.service.GankInfoService;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.app.entity.CityEntity;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherEntity;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherNow;
import com.gracefulwind.learnarms.module_weather.app.utils.WeatherUtil;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.mvp.di.component.DaggerWeatherFragmentComponent;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.WeatherFragmentPresenter;
import com.gracefulwind.learnarms.module_weather.widget.DailyForecastView;
import com.gracefulwind.learnarms.module_weather.widget.HourlyForecastView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

    //=======================================================================================================
    public static final String WEATHER_TYPE_NOW = "now";
    public static final String WEATHER_TYPE_FORECAST = "forecast";
    public static final String WEATHER_TYPE_LIFESTYLE = "lifestyle";
    public static final String WEATHER_TYPE_HOURLY = "hourly";
    //=======================================================================================================
    @BindView(R2.id.wfw_tv_title)
    TextView wfwTvTitle;
    @BindView(R2.id.wfw_fmisvll_container)
//    FirstMatchInScrollViewLinearLayout wfwFmisvllContainer;
    LinearLayout wfwFmisvllContainer;
    @BindView(R2.id.wfw_nsv_container)
    NestedScrollView wfwNsvContainer;
    @BindView(R2.id.wfw_tv_now_tmp_minus)
    TextView wfwTvNowTmpMinus;
    @BindView(R2.id.wfw_now_tmp)
    TextView wfwNowTmp;
    @BindView(R2.id.wfw_now_condition_text)
    TextView wfwNowConditionText;
    @BindView(R2.id.wfw_aqi_text)
    TextView wfwAqiText;
    @BindView(R2.id.wfw_basic_update_loc)
    TextView wfwBasicUpdateLoc;
    @BindView(R2.id.wfw_daily_forecast)
    DailyForecastView wfwDailyForecast;
    @BindView(R2.id.wfw_hourly_forecast)
    HourlyForecastView wfwHourlyForecast;
    //----day detail-----------------
    @BindView(R2.id.wfw_day_detail_temp)
    TextView wfwDayDetailTemp;
    @BindView(R2.id.wfw_today_detail_bottomline)
    TextView wfwTodayDetailBottomline;
    @BindView(R2.id.wfw_tv_temp)
    TextView wfwTvTemp;
    @BindView(R2.id.wfw_tv_hum)
    TextView wfwTvHum;
    @BindView(R2.id.wfw_tv_vis)
    TextView wfwTvVis;
    @BindView(R2.id.wfw_tv_pcpn)
    TextView wfwTvPcpn;

//-----------------------------------------------------------------------------------------------------

    //    public static final String KEY_TITLE = "key_title";
//-----------------------------------------------------------------------------------------------------
    //todo: for test, delete latter
    //不用引入相关module，只需要公共接口就能引入相关服务
    @Autowired(name = RouterHub.GANK.GANK_SERVICE_GANKINFOSERVICE)
    GankInfoService mGankInfoService;
    //    @Autowired(name = KEY_TITLE, required = false)
    String cityName = "default";
    String citySearchName = "";


//-----------------------------------------------------------------------------------------------------

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
    protected int getLayoutId() {
//        new OkHttpClient()
        return R.layout.weather_fragment_weather;
    }

    @Override
    public void lazyLoadData() {
        mPresenter.getWeatherByType(WEATHER_TYPE_NOW, citySearchName);
        mPresenter.getWeatherByType(WEATHER_TYPE_FORECAST, citySearchName);
        mPresenter.getWeatherByType(WEATHER_TYPE_HOURLY, citySearchName);
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
    public void showWeatherByType(String weatherType, WeatherEntity weatherEntity) {
        switch (weatherType) {
            case WEATHER_TYPE_NOW:
                WeatherNow now = weatherEntity.getNow();
                if (null != now) {
                    setNowWeather(weatherEntity);
                    setDayDetail(weatherEntity);
                }
                break;
            case WEATHER_TYPE_FORECAST:
                wfwDailyForecast.setData(weatherEntity);
                break;
            case WEATHER_TYPE_LIFESTYLE:
                break;
            case WEATHER_TYPE_HOURLY:
                wfwHourlyForecast.setData(weatherEntity);
                break;
            default:
                break;
        }
    }

    private void setNowWeather(WeatherEntity weatherEntity) {
        WeatherNow weatherNow = weatherEntity.getNow();
        //set Tmp
        try {
            final int tmpInt = Integer.valueOf(weatherNow.getTmp());
            if (tmpInt < 0) {
                wfwTvNowTmpMinus.setVisibility(View.VISIBLE);
                wfwNowTmp.setText(String.valueOf(-tmpInt));
            } else {
                wfwTvNowTmpMinus.setVisibility(View.GONE);
                wfwNowTmp.setText(String.valueOf(tmpInt));
            }
        } catch (Exception e) {
            e.printStackTrace();
            wfwTvNowTmpMinus.setVisibility(View.GONE);
            wfwNowTmp.setText(weatherNow.getTmp());
        }
        //
        wfwNowConditionText.setText(weatherNow.getCond_txt());
        //更新时间
        String updateTime = weatherEntity.getUpdate().getLoc();
        if (TimeUtil.isToday(updateTime)) {
            wfwBasicUpdateLoc.setText(updateTime.substring(11) + " 发布");
        } else {
            wfwBasicUpdateLoc.setText(updateTime.substring(5) + " 发布");
        }

    }

    private void setDayDetail(WeatherEntity weatherEntity) {
        WeatherNow weatherNow = weatherEntity.getNow();
        wfwDayDetailTemp.setText(weatherNow.getTmp() + "°");
        //todo:维护weatherManager，从那边拿数据填充
        wfwTodayDetailBottomline.setText(weatherNow.getCond_txt() + WeatherUtil.getTodayTempDescription(weatherEntity));
        wfwTvTemp.setText(weatherNow.getTmp() + "°");
        wfwTvHum.setText(weatherNow.getHum() + "%");
        wfwTvVis.setText(weatherNow.getVis() + "km");
        wfwTvPcpn.setText(weatherNow.getPcpn() + "mm");
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
//        mPresenter.getWeatherByType("now", "hangzhou");
//        mPresenter.getWeatherByType("forecast", "hangzhou");
//        mPresenter.getWeatherByType("lifestyle", "hangzhou");
//        mPresenter.getWeatherByType("hourly", "hangzhou");
        String weatherType = WEATHER_TYPE_FORECAST;
        mPresenter.getWeatherByType(weatherType, "hangzhou");

        System.out.println("===111===");
    }

    public void click2() {
//        mPresenter.click2();
        //todo:test open without implement  =>it's OK
        //路由的意义在于不用依赖关系，就可以调起相关的组件
        Utils.navigation(this.getContext(), RouterHub.GANK.GANK_HOMEACTIVITY);
        System.out.println("====222===");
    }

}
