package com.gracefulwind.learnarms.module_weather.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.module_weather.api.service.WeatherService;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherEntity;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.app.entity.DoubanMovieBean;
import com.gracefulwind.learnarms.module_weather.app.entity.weather.WeatherData;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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


//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    public void getDataOnCreate(){
//        //打开 App 时自动加载
//        //dagger的绑定晚于onCreate
//        //在fragment中用lazyLoad加载，保证加载时间正确性
//        getWeather(mRootView.getCityName());
//    }

    public void getWeather(String city){
//        mModel.
//        https://free-api.heweather.net/s6/weather/now?location=hangzhou&key=94c2ffc7db1949389f228612266fc7f8
//        String cityStr = "hangzhou";
//        String keyStr = "94c2ffc7db1949389f228612266fc7f8";
        simpleRetrofit(mModel.getWeather(city), new ErrorHandleSubscriber<WeatherData>(mErrorHandler) {
            @Override
            public void onNext(WeatherData datas) {
                //todo:获取到data后的处理应该在view里写
                if(null == datas.getWeatherList() || 0 == datas.getWeatherList().size()){
                    mRootView.showWeather("通讯错误");
                    return;
                }
                System.out.println("result!!");
                System.out.println("json == " + datas);
                System.out.println("result!!");
                WeatherEntity weatherBean = datas.getWeatherList().get(0);
                if(!TextUtils.equals(WeatherData.STATUS_OK, weatherBean.getStatus())){
                    mRootView.showWeather("获取数据异常 : " + weatherBean.getStatus());
                }
                StringBuilder sbWeatherInfo = new StringBuilder();
                sbWeatherInfo.append(weatherBean.getBasic().getLocation()).append("天气：").append(weatherBean.getNow().getCond_txt());
                mRootView.showWeather(sbWeatherInfo.toString());
            }
        });
    }

    public void getWeatherByType(String weatherType, String city){
        //走通了-getWeatherByType
        simpleRetrofit(mModel.getWeatherByType(weatherType, city), new ErrorHandleSubscriber<WeatherData>(mErrorHandler) {
            @Override
            public void onNext(WeatherData datas) {
                //todo:获取到data后的处理应该在view里写
                if(null == datas.getWeatherList() || 0 == datas.getWeatherList().size()){
                    mRootView.showWeather("通讯错误");
                    return;
                }
                System.out.println("result!!");
                System.out.println("json == " + datas);
                System.out.println("result!!");
                WeatherEntity weatherBean = datas.getWeatherList().get(0);
                if(!TextUtils.equals(WeatherData.STATUS_OK, weatherBean.getStatus())){
                    mRootView.showWeather("获取数据异常 : " + weatherBean.getStatus());
                }
                StringBuilder sbWeatherInfo = new StringBuilder();
                sbWeatherInfo.append(weatherBean.getBasic().getLocation()).append("天气：");//.append(weatherBean.getNow().getCond_txt());
                mRootView.showWeather(sbWeatherInfo.toString());
            }
        });
    }



    public void click1(){
        String cityStr = "hangzhou";
        String keyStr = "94c2ffc7db1949389f228612266fc7f8";
        simpleRetrofit(mModel.getWeather(cityStr), new ErrorHandleSubscriber<WeatherData>(mErrorHandler) {
            @Override
            public void onNext(WeatherData datas) {
                System.out.println("result!!");
                System.out.println("json == " + datas);
                System.out.println("result!!");
            }
        });
    }

    public void click2(){
        int start = 0;
        int count = 1;
        mModel.getDouban(start, count)
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
                .subscribe(new ErrorHandleSubscriber<DoubanMovieBean>(mErrorHandler) {
                    @Override
                    public void onNext(DoubanMovieBean datas) {
                        System.out.println("result!!");
                        System.out.println("json == " + datas);
                        System.out.println("result!!");
                    }
                });
    }

//------------------------------------------------------------------------------------------------------

    public<T> void simpleRetrofit(Observable<T> observable, ErrorHandleSubscriber<T> subcriber){
        simpleRetrofit(observable, subcriber, false);
    }

    //网络回调统一处理
    public<T> void simpleRetrofit(Observable<T> observable, ErrorHandleSubscriber<T> subcriber, boolean showLoading){
        observable.subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if(showLoading){
                        //显示下拉刷新的进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    //隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                //使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(subcriber);
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

//-------------------------------------------------------------------------------------------------------

    @Deprecated
    public void connectWeatherByNewRetrofit(){
        //走通了
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://free-api.heweather.net/s6/")
                //设置 Json 转换器
                .addConverterFactory(GsonConverterFactory.create())
                //RxJava 适配器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        service.getWeather("hangzhou", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<WeatherData>(){
                    @Override
                    public void onNext(WeatherData responseBean) {
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

    @Deprecated
    public void connectDoubanByNewRetrofit(){
        //走通了
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
                });
    }

}
