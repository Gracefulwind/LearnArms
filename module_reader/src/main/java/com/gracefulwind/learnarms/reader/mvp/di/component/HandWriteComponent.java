package com.gracefulwind.learnarms.reader.mvp.di.component;

import com.gracefulwind.learnarms.reader.mvp.contract.DoodleContract;
import com.gracefulwind.learnarms.reader.mvp.contract.HandWriteContract;
import com.gracefulwind.learnarms.reader.mvp.di.module.DoodleModule;
import com.gracefulwind.learnarms.reader.mvp.di.module.HandWriteModule;
import com.gracefulwind.learnarms.reader.mvp.ui.activity.DoodleActivity;
import com.gracefulwind.learnarms.reader.mvp.ui.activity.HandWriteActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/08/20 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HandWriteModule.class, dependencies = AppComponent.class)
public interface HandWriteComponent {

    void inject(HandWriteActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HandWriteComponent.Builder view(HandWriteContract.View view);
        HandWriteComponent.Builder appComponent(AppComponent appComponent);
        HandWriteComponent build();
    }
}