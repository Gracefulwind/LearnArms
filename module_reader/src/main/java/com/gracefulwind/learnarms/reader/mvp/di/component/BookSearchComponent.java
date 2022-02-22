package com.gracefulwind.learnarms.reader.mvp.di.component;

import com.gracefulwind.learnarms.reader.mvp.contract.BookMallContract;
import com.gracefulwind.learnarms.reader.mvp.contract.BookSearchContract;
import com.gracefulwind.learnarms.reader.mvp.di.module.BookMallModule;
import com.gracefulwind.learnarms.reader.mvp.di.module.BookSearchModule;
import com.gracefulwind.learnarms.reader.mvp.ui.fragment.BookMallFragment;
import com.gracefulwind.learnarms.reader.mvp.ui.fragment.BookSearchFragment;
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
@Component(modules = BookSearchModule.class, dependencies = AppComponent.class)
public interface BookSearchComponent {

    void inject(BookSearchFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        BookSearchComponent.Builder view(BookSearchContract.View view);
        BookSearchComponent.Builder appComponent(AppComponent appComponent);
        BookSearchComponent build();
    }
}