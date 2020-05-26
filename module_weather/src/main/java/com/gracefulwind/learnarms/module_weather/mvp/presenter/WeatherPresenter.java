package com.gracefulwind.learnarms.module_weather.mvp.presenter;

import android.app.Application;

import com.gracefulwind.learnarms.module_weather.mvp.contract.MainContract;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2020 17:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WeatherPresenter extends BasePresenter<WeatherContract.Model, WeatherContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WeatherPresenter(WeatherContract.Model model, WeatherContract.View rootView) {
        super(model, rootView);
    }

    public void doSomething(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                for (int x = 0; x < 4; x++) {
                    Thread.sleep(500);
                    emitter.onNext("连载" + x);
                }
                emitter.onComplete();
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtils.debugInfo("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                LogUtils.debugInfo("onNext");
                mRootView.showSomeThing(s);
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.debugInfo("onError");
            }

            @Override
            public void onComplete() {
                LogUtils.debugInfo("onComplete");
            }
        });
    }




//-------------------------------------------------------------------------------------------------------
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


}
