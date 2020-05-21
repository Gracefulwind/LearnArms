package com.gracefulwind.learnarms.module_test_dagger.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.DaggerPage2Contract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.contract.TestDaggerMainContract;
import com.gracefulwind.learnarms.module_test_dagger.mvp.ui.activity.DaggerPage2Activity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;


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
public class DaggerPage2Model extends BaseModel implements DaggerPage2Contract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    String fromData = "null";

    public DaggerPage2Model(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Inject
    public DaggerPage2Model(IRepositoryManager repositoryManager, DaggerPage2Activity activity) {
        super(repositoryManager);
        fromData = activity.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public String getPage2Data() {
//        mRepositoryManager.obtainRetrofitService()
        return fromData;
    }
}