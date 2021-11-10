package com.gracefulwind.learnarms.write.mvp.di.component;

import com.gracefulwind.learnarms.write.mvp.contract.MainContract;
import com.gracefulwind.learnarms.write.mvp.contract.SmartHandNoteContract;
import com.gracefulwind.learnarms.write.mvp.di.module.MainModule;
import com.gracefulwind.learnarms.write.mvp.di.module.SmartHandNoteModule;
import com.gracefulwind.learnarms.write.mvp.ui.activity.MainActivity;
import com.gracefulwind.learnarms.write.mvp.ui.activity.SmartHandNoteActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

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
@Component(modules = SmartHandNoteModule.class, dependencies = AppComponent.class)
public interface SmartHandNoteComponent {

    void inject(SmartHandNoteActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        SmartHandNoteComponent.Builder view(SmartHandNoteContract.View view);
        SmartHandNoteComponent.Builder appComponent(AppComponent appComponent);
        SmartHandNoteComponent build();
    }
}