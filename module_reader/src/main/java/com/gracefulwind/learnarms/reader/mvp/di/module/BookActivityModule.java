package com.gracefulwind.learnarms.reader.mvp.di.module;

import com.gracefulwind.learnarms.reader.mvp.contract.BookActivityContract;
import com.gracefulwind.learnarms.reader.mvp.contract.BookMallContract;
import com.gracefulwind.learnarms.reader.mvp.model.BookActivityModel;
import com.gracefulwind.learnarms.reader.mvp.model.BookMallModel;

import dagger.Binds;
import dagger.Module;

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
@Module
//构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class BookActivityModule {

    @Binds
    abstract BookActivityContract.Model bindMainModel(BookActivityModel model);
}