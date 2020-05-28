package com.gracefulwind.learnarms.module_weather.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.app.api.service.WeatherService;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.WeatherEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;


import javax.inject.Inject;

import io.reactivex.Observable;


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
@ActivityScope
public class WeatherFragmentModel extends BaseModel implements WeatherFragmentContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WeatherFragmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<WeatherEntity> getWeather(String city, String key) {
        return mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getWeather(city, key);
    }
    @Override
    public Observable<DoubanMovieBean> getDouban(int start, int count) {
        return mRepositoryManager
                .obtainRetrofitService(WeatherService.class)
                .getMovieSubjectRx(Constants.KEY.DOUBAN_KEY, start, count);
    }
}