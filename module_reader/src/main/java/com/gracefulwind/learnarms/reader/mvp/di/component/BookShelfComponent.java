package com.gracefulwind.learnarms.reader.mvp.di.component;

import com.gracefulwind.learnarms.reader.mvp.contract.BookShelfContract;
import com.gracefulwind.learnarms.reader.mvp.contract.ReaderMainContract;
import com.gracefulwind.learnarms.reader.mvp.di.module.BookShelfModule;
import com.gracefulwind.learnarms.reader.mvp.di.module.ReaderMainModule;
import com.gracefulwind.learnarms.reader.mvp.ui.activity.ReaderMainActivity;
import com.gracefulwind.learnarms.reader.mvp.ui.fragment.BookShelfFragment;
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
@Component(modules = BookShelfModule.class, dependencies = AppComponent.class)
public interface BookShelfComponent {

    void inject(BookShelfFragment fragment);
    @Component.Builder
    interface Builder {
        @BindsInstance
        BookShelfComponent.Builder view(BookShelfContract.View view);
        BookShelfComponent.Builder appComponent(AppComponent appComponent);
        BookShelfComponent build();
    }
}