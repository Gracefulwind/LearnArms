package com.gracefulwind.learnarms.module_test_dagger.di.component;

import com.gracefulwind.learnarms.module_test_dagger.di.module.DaggerPage1Module;
import com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity.DaggerPage1Activity;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Scope;

import dagger.Component;

/**
 * @ClassName: DaggerPage1Component
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/15 15:27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/15 15:27
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

//@ActivityScope
//@Component(modules = DaggerPage1Module.class)
public interface DaggerPage1Component {
    void inject(DaggerPage1Activity activity);
}
