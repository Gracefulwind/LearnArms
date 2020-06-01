package com.gracefulwind.learnarms.module_weather.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.mvp.di.component.DaggerWeatherComponent;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.WeatherPresenter;
import com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R2.id.waw_ll_container)
    LinearLayout wawLlContainer;
    @BindView(R2.id.waw_stl_weather_title)
    SlidingTabLayout wawStlWeatherTitle;
    @BindView(R2.id.waw_vp_weather_container)
    ViewPager wawVpWeatherContainer;
    private ArrayList<WeatherFragment> fragments;

    public static String[] cities = {"hangzhou", "shanghai", "beijing", "yichang"};
    public static String[] citiesName = {"杭州", "上海", "北京", "宜昌"};

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
        //todo:后面用newInstance获取,便于入参
        FragmentManager fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        for(int x = 0; x < 4; x++){
            WeatherFragment fragment = (WeatherFragment) ARouter.getInstance()
                    .build(RouterHub.WEATHER.WEATHER_FRAGMENT)
//                    .withString(WeatherFragment.KEY_TITLE, "title_" + x)
                    .navigation();
            //设置city
            fragment.setCityName(citiesName[x]);
            fragment.setCitySearchName(cities[x]);
            fragments.add(fragment);
        }

        WeatherActivityViewPagerAdapter vpAdapter = new WeatherActivityViewPagerAdapter(fm, fragments);
        wawVpWeatherContainer.setAdapter(vpAdapter);

        wawStlWeatherTitle.setViewPager(wawVpWeatherContainer);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R2.id.waw_btn_click1, R2.id.waw_btn_click2})
    public void onViewClicked(View view) {
        int id = view.getId();
        //todo:修改下。不合理方式
        if(R.id.waw_btn_click1 == id){
//            mPresenter.doSomething();
            fragments.get(0).click1();
        }
        else if(R.id.waw_btn_click2 == id){
            fragments.get(0).click2();
        }
    }

    @Override
    public void showSomeThing(String str) {
        wawTvShowResult.setText("result : " + str);
    }

    public static class WeatherActivityViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<WeatherFragment> fragments = new ArrayList<>();

        private WeatherFragment primaryItem;

        public WeatherActivityViewPagerAdapter(FragmentManager fm, List<WeatherFragment> fragmentList) {
            super(fm);
            fragments.clear();
            if(null != fragmentList){
                fragments.addAll(fragmentList);
            }
        }

        @Override
        public WeatherFragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return null == fragments ? 0 : fragments.size();
        }

        @Override
        public void setPrimaryItem(@NonNull View container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
//            primaryItem = object;
        }

        /**
         *
         * 这个方法的执行顺序在fragment的setupFragmentComponent之前
         *
         * */
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getItem(position).getCityName();
        }
    }


}
