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
package com.gracefulwind.learnarms.app.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.app.R;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * ================================================
 * Created by JessYan on 18/04/2018 17:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.APP_SPLASHACTIVITY)
public class SplashActivity extends MyBaseActivity {
    public static final String TAG = "SplashActivity";

    @BindView(R.id.as_iv_bg)
    ImageView asIvBg;
    @BindView(R.id.as_tv_jump_button)
    TextView asTvJumpButton;
    private Disposable jumpToMainTimer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        //timer只能发送一次数据，这里不用
//        jumpToMainTimer = Observable.timer(3, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        jumpToMain();
//                    }
//                });
        asTvJumpButton.setText(String.format("%d秒后跳转", 3));
        //延迟时间，间隔时间，时间单位
        Observable.interval(0, 1, TimeUnit.SECONDS)
                //重复次数
//                .take(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    int counter = 3;

                    @Override
                    public void onSubscribe(@NotNull Disposable disposable) {
                        jumpToMainTimer = disposable;
                        counter = 3;
                    }

                    @Override
                    public void onNext(@NotNull Long aLong) {
                        LogUtil.e(TAG, "onNext : " + aLong);
                        long now = counter - aLong;
                        if(0 <= now){
                            asTvJumpButton.setText(String.format("%d秒后跳转", now));
                        }else {
                            jumpToMainImmediately();
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable throwable) {
                        closeTimer();
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG, "onComplete : ");
                        jumpToMainImmediately();
                    }
                });
    }

    private void closeTimer() {
        if(null != jumpToMainTimer && !jumpToMainTimer.isDisposed()){
            jumpToMainTimer.dispose();
            jumpToMainTimer = null;
        }
    }


    @OnClick({R.id.as_tv_jump_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.as_tv_jump_button:
                jumpToMainImmediately();
                break;
        }
    }

    private void jumpToMain() {
        Utils.navigation(SplashActivity.this, RouterHub.APP_MAINACTIVITY);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void jumpToMainImmediately() {
        closeTimer();
        jumpToMain();
    }
}
