package com.gracefulwind.learnarms.module_test_dagger.di.module;

import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage1Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.DaggerPage1Model;
import com.gracefulwind.learnarms.module_test_dagger.mvp.model.entity.DaggerPage1Item1;
import com.gracefulwind.learnarms.module_test_dagger.mvp.presenter.DaggerPage1Presenter;
import com.jess.arms.di.scope.ActivityScope;

import java.text.SimpleDateFormat;
import java.util.Date;

import dagger.Module;
import dagger.Provides;

/**
 * @ClassName: DaggerPage1Module
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/15 15:28
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/15 15:28
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

@Module
public class DaggerPage1Module {

    private DaggerPage1Contract.View mView;
    private DaggerPage1Contract.Model mModel;

    public DaggerPage1Module() {
    }

    public DaggerPage1Module(DaggerPage1Contract.View mView, DaggerPage1Contract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    //    public DaggerPage1Module(DaggerPage1Contract.View view, DaggerPage1Contract.Model model) {
//        mView = view;
//        mModel = model;
//    }

    @Provides
    DaggerPage1Presenter providePresenter(DaggerPage1Contract.View view, DaggerPage1Contract.Model model) {
        return new DaggerPage1Presenter(view, model);
    }

    @Provides
    DaggerPage1Item1 provideItem1(DaggerPage1Contract.View view) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(new Date());
        return new DaggerPage1Item1("lalala" + view.toString(), dateString);
    }

//    @Provides
//    DaggerPage1Contract.View provideView() {
//        return mView;
//    }
//
//    @Provides
//    DaggerPage1Contract.Model provideModel() {
//        return mModel;
//    }

    @Provides
    DaggerPage1Model provideModelImp() {
        return new DaggerPage1Model("from DaggerPage1Model");
    }

}
