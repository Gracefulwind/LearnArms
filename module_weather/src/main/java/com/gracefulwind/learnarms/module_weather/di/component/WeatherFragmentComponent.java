package com.gracefulwind.learnarms.module_weather.di.component;

import com.gracefulwind.learnarms.module_weather.di.module.WeatherFragmentModule;
import com.gracefulwind.learnarms.module_weather.di.module.WeatherModule;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.mvp.ui.activity.WeatherActivity;
import com.gracefulwind.learnarms.module_weather.mvp.ui.fragment.WeatherFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;


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
@Component(modules = WeatherFragmentModule.class, dependencies = AppComponent.class)
public interface WeatherFragmentComponent {
    void inject(WeatherFragment view);
    @Component.Builder
    interface Builder {
        @BindsInstance
        WeatherFragmentComponent.Builder view(WeatherFragmentContract.View view);
        WeatherFragmentComponent.Builder appComponent(AppComponent appComponent);
        WeatherFragmentComponent build();
    }
}