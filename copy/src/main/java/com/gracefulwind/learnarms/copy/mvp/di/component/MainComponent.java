package com.gracefulwind.learnarms.copy.mvp.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.gracefulwind.learnarms.copy.mvp.contract.MainContract;
import com.gracefulwind.learnarms.copy.mvp.di.module.MainModule;
import com.gracefulwind.learnarms.copy.mvp.ui.activity.MainActivity;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.di.scope.ActivityScope;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/01 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {

    void inject(MainActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);
        MainComponent.Builder appComponent(AppComponent appComponent);
        MainComponent build();
    }
}