package com.gracefulwind.learnarms.reader.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.gracefulwind.learnarms.reader.BuildConfig;
import com.gracefulwind.learnarms.reader.greendao.DaoMaster;
import com.gracefulwind.learnarms.reader.greendao.DaoSession;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by ArmsComponentTemplate on 05/13/2020 17:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/ArmsComponent">Star me</a>
 * <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/ArmsComponent-Template">模版请保持更新</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    private static DaoSession mDaoSession;

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //当所有模块集成到宿主 App 时, 在 App 中已经执行了以下代码
        if (BuildConfig.IS_BUILD_MODULE) {
            //leakCanary内存泄露检查
            ArmsUtils.obtainAppComponentFromContext(application).extras()
                    .put(IntelligentCache.getKeyOfKeep(RefWatcher.class.getName())
                            , BuildConfig.USE_CANARY ? LeakCanary.install(application) : RefWatcher.DISABLED);
        }
//        RetrofitUrlManager.getInstance().putDomain(WEATHER_DOMAIN_NAME, WEATHER_DOMAIN);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(application.getApplicationContext(), "novel.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    public static DaoSession getDaoSession(){
        return mDaoSession;
    }
}
