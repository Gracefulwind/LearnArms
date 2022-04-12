package com.gracefulwind.learnarms.reader.mvp.di.component;

import com.gracefulwind.learnarms.reader.mvp.contract.MainContract;
import com.gracefulwind.learnarms.reader.mvp.contract.ReaderMainContract;
import com.gracefulwind.learnarms.reader.mvp.di.module.MainModule;
import com.gracefulwind.learnarms.reader.mvp.di.module.ReaderMainModule;
import com.gracefulwind.learnarms.reader.mvp.ui.activity.MainActivity;
import com.gracefulwind.learnarms.reader.mvp.ui.activity.ReaderMainActivity;
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
@Component(modules = ReaderMainModule.class, dependencies = AppComponent.class)
public interface ReaderMainComponent {

    void inject(ReaderMainActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        ReaderMainComponent.Builder view(ReaderMainContract.View view);
        ReaderMainComponent.Builder appComponent(AppComponent appComponent);
        ReaderMainComponent build();
    }
}