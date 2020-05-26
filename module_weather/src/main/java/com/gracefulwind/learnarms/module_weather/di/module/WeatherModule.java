package com.gracefulwind.learnarms.module_weather.di.module;

import com.gracefulwind.learnarms.module_weather.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.model.MainModel;
import com.gracefulwind.learnarms.module_weather.mvp.model.WeatherModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class WeatherModule {

    @Binds
    abstract WeatherContract.Model bindMainModel(WeatherModel model);
}