package com.gracefulwind.learnarms.face.mvp.presenter;

import android.app.Application;

import com.gracefulwind.learnarms.commonsdk.utils.Base64Util;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.face.api.service.FaceService;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.FormBody;
import okhttp3.Request;

import javax.inject.Inject;

import com.gracefulwind.learnarms.face.mvp.contract.MainContract;

import static com.gracefulwind.learnarms.face.api.service.FaceService.API_KEY;
import static com.gracefulwind.learnarms.face.api.service.FaceService.API_SECRET;

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
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
    
    public void testNet(){
        LogUtil.e(TAG, "testTest");
//        RequestBody.create(MediaType.parse("multipart/form-data"), slice);
//        Base64Util.imageToBase64()
        FormBody body = new FormBody.Builder()
                .add("api_key", API_KEY)
                .add("api_secret", API_SECRET)
//                .add("image_file1", )
//                .add("image_file2", )

//                .add("image_base64_1", )
//                .add("image_base64_2", )
                .build();
        Request request = new Request.Builder()
                .url(FaceService.COMPARE_FACE)
                .post(body)
                .build();
    }
}