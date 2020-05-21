package com.gracefulwind.learnarms.module_test_dagger.di.component;

import com.gracefulwind.learnarms.module_test_dagger.di.module.DaggerPage2Module;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage2Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.DaggerPage2Model;
import com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity.DaggerPage2Activity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @ClassName: DaggerPage2Component
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/20 16:33
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/20 16:33
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@ActivityScope
@Component(modules = DaggerPage2Module.class, dependencies = AppComponent.class)
public interface DaggerPage2Component {

    void inject(DaggerPage2Activity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DaggerPage2Component.Builder view(DaggerPage2Contract.View view);

        DaggerPage2Component.Builder appComponent(AppComponent appComponent);
        @BindsInstance
        DaggerPage2Component.Builder activity(DaggerPage2Activity appComponent);

        DaggerPage2Component build();
    }
}
