/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gracefulwind.learnarms.commonsdk.core;

import android.app.Application;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonsdk.BuildConfig;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.TypefaceUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.ClientModule;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.log.RequestInterceptor;
import com.jess.arms.integration.ConfigModule;
import com.gracefulwind.learnarms.commonsdk.imgaEngine.Strategy.CommonGlideImageLoaderStrategy;

import java.util.List;

import butterknife.ButterKnife;
import com.gracefulwind.learnarms.commonsdk.http.Api;
import com.gracefulwind.learnarms.commonsdk.http.SSLSocketClient;
import com.jess.arms.utils.LogUtils;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import timber.log.Timber;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.3">ConfigModule wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

//todo: 核心config，在application中的初始化在这里完成。
public class GlobalConfiguration implements ConfigModule {
    public static final String TAG = "GlobalConfiguration";

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
//        LogUtil.e(TAG,"core applyOptions");
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                        //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                        RetrofitUrlManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });

    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
//        LogUtil.e(TAG,"core injectAppLifecycle");
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        LogUtils.debugInfo("===context==" + context.toString());
        lifecycles.add(new AppLifecycles() {

            @Override
            public void attachBaseContext(@NonNull Context base) {
                System.out.println("---call attachBaseContext");
            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }
                //init Arouter
                ARouter.init(application); // 尽可能早,推荐在Application中初始化
                //init UiUtil
                UiUtil.init(application.getBaseContext());
                //init typefaceUtil
                TypefaceUtil.init(application.getBaseContext());
                //todo: application的init
                System.out.println("---call onCreate");

            }

            @Override
            public void onTerminate(@NonNull Application application) {
                System.out.println("---call onTerminate");
            }
        });
//        initUiUtil(application.getBaseContext());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
//        LogUtil.e(TAG,"core injectActivityLifecycle");
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
//        LogUtil.e(TAG,"core injectFragmentLifecycle");
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
