package com.gracefulwind.learnarms.copy.mvp.di.module;

import dagger.Binds;
import dagger.Module;

import com.gracefulwind.learnarms.copy.mvp.contract.MainContract;
import com.gracefulwind.learnarms.copy.mvp.model.MainModel;

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
@Module
//构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}