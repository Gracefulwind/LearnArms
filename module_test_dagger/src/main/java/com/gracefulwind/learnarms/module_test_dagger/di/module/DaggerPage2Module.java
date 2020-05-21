package com.gracefulwind.learnarms.module_test_dagger.di.module;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage2Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.TestDaggerMainContract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.DaggerPage2Model;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.TestDaggerMainModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class DaggerPage2Module {

    @Binds
    abstract DaggerPage2Contract.Model bindDaggerPage2Model(DaggerPage2Model model);
}