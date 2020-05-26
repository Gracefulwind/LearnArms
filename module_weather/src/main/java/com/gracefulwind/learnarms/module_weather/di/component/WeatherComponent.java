package com.gracefulwind.learnarms.module_weather.di.component;

import com.gracefulwind.learnarms.module_weather.di.module.MainModule;
import com.gracefulwind.learnarms.module_weather.di.module.WeatherModule;
import com.gracefulwind.learnarms.module_weather.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.ui.activity.MainActivity;
import com.gracefulwind.learnarms.module_weather.mvp.ui.activity.WeatherActivity;
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
@Component(modules = WeatherModule.class, dependencies = AppComponent.class)
public interface WeatherComponent {
    void inject(WeatherActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        WeatherComponent.Builder view(WeatherContract.View view);
        WeatherComponent.Builder appComponent(AppComponent appComponent);
        WeatherComponent build();
    }
}