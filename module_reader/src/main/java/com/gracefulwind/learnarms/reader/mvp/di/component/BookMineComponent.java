package com.gracefulwind.learnarms.reader.mvp.di.component;

import com.gracefulwind.learnarms.reader.mvp.contract.BookActivityContract;
import com.gracefulwind.learnarms.reader.mvp.contract.BookMineContract;
import com.gracefulwind.learnarms.reader.mvp.di.module.BookActivityModule;
import com.gracefulwind.learnarms.reader.mvp.di.module.BookMineModule;
import com.gracefulwind.learnarms.reader.mvp.ui.fragment.BookActivityFragment;
import com.gracefulwind.learnarms.reader.mvp.ui.fragment.BookMineFragment;
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
@Component(modules = BookMineModule.class, dependencies = AppComponent.class)
public interface BookMineComponent {

    void inject(BookMineFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        BookMineComponent.Builder view(BookMineContract.View view);
        BookMineComponent.Builder appComponent(AppComponent appComponent);
        BookMineComponent build();
    }
}