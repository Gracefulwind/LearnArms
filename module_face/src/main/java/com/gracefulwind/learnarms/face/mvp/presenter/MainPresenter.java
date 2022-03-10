package com.gracefulwind.learnarms.face.mvp.presenter;

import android.app.Application;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.utils.Base64Util;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.face.api.service.FaceService;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;

import com.gracefulwind.learnarms.face.mvp.contract.MainContract;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

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

    int writeFlag = 2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void testNet(){
        LogUtil.e(TAG, "testTest");
        AssetManager assets = mApplication.getAssets();
        InputStream open1 = null;
        InputStream open2 = null;
        byte[] bytes1;
        byte[] bytes2;

        String base64_1 = "";
        String base64_2 = "";
        try {
            String add1 = "wd1.jpg";
            String add2 = "";
//            String add2 = "wd2.jpg";
            if(writeFlag > 0){
                add2 = "wd" + writeFlag + ".jpg";
            }else if(writeFlag < 0){
                add2 = "ymh" + (-writeFlag) + ".jpg";
            }else {
                add2 = "wd2.jpg";
            }
            byte[] buffer = new byte[1024 * 4];
            int n = 0;

            open1 = assets.open(add1);
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            while ((n = open1.read(buffer)) != -1) {
                out1.write(buffer, 0, n);
            }
            bytes1 = out1.toByteArray();

            //===============================================
            open2 = assets.open(add2);
            n = 0;
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            while ((n = open2.read(buffer)) != -1) {
                out2.write(buffer, 0, n);
            }
            bytes2 = out2.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        base64_1 = Base64.getEncoder().encodeToString(bytes1);
        base64_2 = Base64.getEncoder().encodeToString(bytes2);

        FormBody body = new FormBody.Builder()
                .add("api_key", API_KEY)
                .add("api_secret", API_SECRET)
//                .add("image_file1", )
//                .add("image_file2", )

                .add("image_base64_1", base64_1)
                .add("image_base64_2", base64_2)
                .build();
        Request request = new Request.Builder()
                .url(FaceService.COMPARE_FACE)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e(TAG, "end call onFailure: " + System.currentTimeMillis());
                LogUtil.e(TAG, "--------onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.e(TAG, "end call onResponse: " + System.currentTimeMillis());
                String responseStr = response.body().string();
                LogUtil.e(TAG, "--------onResponse, response = " + responseStr);
            }
        });
        LogUtil.e(TAG, "start call: " + System.currentTimeMillis());

    }
}