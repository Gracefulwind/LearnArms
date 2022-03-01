package com.gracefulwind.learnarms.write.mvp.ui.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.RxTimerUtil;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.write.R2;
import com.gracefulwind.learnarms.write.mvp.contract.MainContract;
import com.gracefulwind.learnarms.write.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.write.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.widget.dialog.UpdateLoadingDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: MainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/8/27
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/1
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.SmartWrite.HOME_ACTIVITY)
public class MainActivity extends MyBaseActivity<MainPresenter> implements MainContract.View {

    private UpdateLoadingDialog updateDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.write_activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "Main");
        initListener();
    }

    public void initListener() {

    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }



    @OnClick({R2.id.wam_tv_write, R2.id.wam_tv_dialog, R2.id.wam_tv_test1, R2.id.wam_tv_test2})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id == R.id.wam_tv_write){
            Utils.navigation(MainActivity.this, RouterHub.SmartWrite.SMART_HAND_NOTE_ACTIVITY);
        }else if(id == R.id.wam_tv_dialog){
            setUpdateDialog();
        }else if(id == R.id.wam_tv_test1){
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;// 屏幕宽度（像素）
            int height= dm.heightPixels; // 屏幕高度（像素）
            float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
            int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
            //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            int screenWidth = (int) (width/density);//屏幕宽度(dp)
            int screenHeight = (int)(height/density);//屏幕高度(dp)
            LogUtil.e("123", screenWidth + "======" + screenHeight);
        }else if(id == R.id.wam_tv_test2){
            Utils.navigation(MainActivity.this, RouterHub.SmartWrite.WRITE_TEST_ACTIVITY);
        }else if(id == R.id.wam_tv_dialog){

        }
    }

    private boolean testThread = false;
    public void setUpdateDialog() {
        updateDialog = new UpdateLoadingDialog.Builder(this)
            .setIndicator(UpdateLoadingDialog.Builder.TYPE_FOX)
            .setStep1Callback(new UpdateLoadingDialog.OnStepSuccessCallback() {
                @Override
                public void onSuccess() {
                    test1DialogStep2();
                }
            }, new UpdateLoadingDialog.OnStepErrorCallback() {
                @Override
                public void onCancelClicked() {
                    updateDialog.cancel();
                }

                @Override
                public void onRetryClicked() {
                    test1DialogStep1();
                }
            }).setStep2Callback(new UpdateLoadingDialog.OnStepSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        test1DialogStep3();
                    }
                }, new UpdateLoadingDialog.OnStepErrorCallback() {
                    @Override
                    public void onCancelClicked() {
                        Toast.makeText(MainActivity.this, "step2 clicked", Toast.LENGTH_SHORT).show();
                        updateDialog.cancel();
                    }

                    @Override
                    public void onRetryClicked() {
                        test1DialogStep2();
                    }
                }).setStep3Callback(new UpdateLoadingDialog.OnStepSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        updateDialog.cancel();
                    }
                }, new UpdateLoadingDialog.OnStepErrorCallback() {
                    @Override
                    public void onCancelClicked() {
                        Toast.makeText(MainActivity.this, "step3 clicked", Toast.LENGTH_SHORT).show();
                        updateDialog.cancel();
                    }

                    @Override
                    public void onRetryClicked() {
                        test1DialogStep3();
                    }
                })
            .build();
        updateDialog.show();
        test1DialogStep3();
    }

    public void test1DialogStep1() {
        updateDialog.startStep1();
        if(testThread){
            RxTimerUtil.cancel();
            RxTimerUtil.timer(1500, new RxTimerUtil.IRxNext() {
                @Override
                public void doNext(long number) {
                    int i = new Random().nextInt(10);
                    LogUtil.e(TAG, "test1DialogStep1  == " + (i > 2));
                    if(i > 2){
                        updateDialog.setStep1Result(true);
                    }else {
                        updateDialog.setStep1Result(false);
                    }
                }
            });
        }else {
            updateDialog.setStep1Result(true);
        }
    }

    public void test1DialogStep2() {
        updateDialog.startStep2();
        if(testThread){
            RxTimerUtil.cancel();
            RxTimerUtil.timer(2500, new RxTimerUtil.IRxNext() {
                @Override
                public void doNext(long number) {
                    int i = new Random().nextInt(10);
                    LogUtil.e(TAG, "test1DialogStep2  == " + (i > 8));
                    if(i > 4){
                        updateDialog.setStep2Result(true);
                    }else {
                        updateDialog.setStep2Result(false);
                    }
                }
            });
        }else {
            updateDialog.setStep2Result(true);
        }
    }

    public void test1DialogStep3() {
        updateDialog.startStep3();
        RxTimerUtil.cancel();
        RxTimerUtil.timer(4000, new RxTimerUtil.IRxNext() {
            @Override
            public void doNext(long number) {
                int i = new Random().nextInt(10);
                LogUtil.e(TAG, "test1DialogStep3  == " + (i > 8));
                if(i > 8){
                    updateDialog.setStep3Result(true);
                }else {
                    updateDialog.setStep3Result(false);
                }
            }
        });
    }

}