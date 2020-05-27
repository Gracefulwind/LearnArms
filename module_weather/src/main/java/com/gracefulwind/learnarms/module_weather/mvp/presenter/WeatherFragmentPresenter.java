package com.gracefulwind.learnarms.module_weather.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.gracefulwind.learnarms.module_weather.app.api.service.WeatherService;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherContract;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.mvp.model.entity.WeatherEntity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


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
public class WeatherFragmentPresenter extends BasePresenter<WeatherFragmentContract.Model, WeatherFragmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WeatherFragmentPresenter(WeatherFragmentContract.Model model, WeatherFragmentContract.View rootView) {
        super(model, rootView);
    }


    /**
     *
     * {
     *   "HeWeather6": [
     *     {
     *       "basic": {
     *         "cid": "CN101210101",
     *         "location": "杭州",
     *         "parent_city": "杭州",
     *         "admin_area": "浙江",
     *         "cnty": "中国",
     *         "lat": "30.28745842",
     *         "lon": "120.15357971",
     *         "tz": "+8.00"
     *       },
     *       "update": {
     *         "loc": "2020-05-27 15:03",
     *         "utc": "2020-05-27 07:03"
     *       },
     *       "status": "ok",
     *       "now": {
     *         "cloud": "91",
     *         "cond_code": "101",
     *         "cond_txt": "多云",
     *         "fl": "28",
     *         "hum": "51",
     *         "pcpn": "0.0",
     *         "pres": "1005",
     *         "tmp": "27",
     *         "vis": "16",
     *         "wind_deg": "175",
     *         "wind_dir": "南风",
     *         "wind_sc": "1",
     *         "wind_spd": "3"
     *       }
     *     }
     *   ]
     * }
     *
     * */

//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    void onCreate() {
//        getWeather("hangzhou");//打开 App 时自动加载列表
//    }

    public void click1(){
        String cityStr = "hangzhou";
        String keyStr = "94c2ffc7db1949389f228612266fc7f8";
        mModel.getWeather(cityStr, keyStr)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    //显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<WeatherEntity>(mErrorHandler) {
                    @Override
                    public void onNext(WeatherEntity datas) {
                        System.out.println("result!!");
                        System.out.println("json == " + datas);
                        System.out.println("result!!");
                    }
                });
    }

    public void click2(){
        //走通了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.net/s6/")
                //设置 Json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        service.getWeather("hangzhou", "94c2ffc7db1949389f228612266fc7f8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WeatherEntity>(){
                    @Override
                    public void onNext(WeatherEntity responseBean) {
                        System.out.println("success=============");
                        System.out.println(responseBean);
                        System.out.println("===end===");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println("error");
                        System.out.println("error==");
                        System.out.println("error====");
                    }

                    //onNext后会执行
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                        System.out.println("onComplete==");
                        System.out.println("onComplete====");
                    }
                });
    }
    public void getWeather(String city){
//        mModel.
//        https://free-api.heweather.net/s6/weather/now?location=hangzhou&key=94c2ffc7db1949389f228612266fc7f8
        String cityStr = "hangzhou";
        String keyStr = "94c2ffc7db1949389f228612266fc7f8";
        mModel.getWeather(cityStr, keyStr)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    //显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<WeatherEntity>(mErrorHandler) {
                    @Override
                    public void onNext(WeatherEntity datas) {
                        System.out.println("result!!");
                        System.out.println("json == " + datas);
                        System.out.println("result!!");
                    }
                });

/*        //走通了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.net/s6/")
                //设置 Json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        service.getWeather("hangzhou", "94c2ffc7db1949389f228612266fc7f8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WeatherEntity>(){
                    @Override
                    public void onNext(WeatherEntity responseBean) {
                        System.out.println("success=============");
                        System.out.println(responseBean);
                        System.out.println("===end===");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        System.out.println("error");
                        System.out.println("error==");
                        System.out.println("error====");
                    }

                    //onNext后会执行
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                        System.out.println("onComplete==");
                        System.out.println("onComplete====");
                    }
                });*/

    //=========================================================
/*        //走通了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                //设置 Json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WeatherService doubanServices = retrofit.create(WeatherService.class);

        int count = 1;
        System.out.println("count ==" + count);
        doubanServices.getMovieSubjectRx("0df993c66c0c636e29ecbb5344252a4a", 0, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<DoubanMovieBean>(){
                    @Override
                    public void onNext(DoubanMovieBean responseBean) {
                        System.out.println("success=============");
                        System.out.println(responseBean);
                        System.out.println("===end===");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("error");
                        System.out.println("error==");
                        System.out.println("error====");
                    }

                    //onNext后会执行
                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                        System.out.println("onComplete==");
                        System.out.println("onComplete====");
                    }
                });*/

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
