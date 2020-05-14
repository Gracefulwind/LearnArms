package com.gracefulwind.learnarms.module_test_dagger.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.gracefulwind.learnarms.module_test_dagger.di.module.TestDaggerMainModule;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.TestDaggerMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity.TestDaggerMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2020 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TestDaggerMainModule.class, dependencies = AppComponent.class)
public interface TestDaggerMainComponent {
    void inject(TestDaggerMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TestDaggerMainComponent.Builder view(TestDaggerMainContract.View view);

        TestDaggerMainComponent.Builder appComponent(AppComponent appComponent);

        TestDaggerMainComponent build();
    }
}