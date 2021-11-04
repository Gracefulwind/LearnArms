package com.gracefulwind.learnarms.write.mvp.ui.activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.write.R2;
import com.gracefulwind.learnarms.write.mvp.contract.MainContract;
import com.gracefulwind.learnarms.write.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.write.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.widget.dialog.UpdateLoadingDialog;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

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
            System.out.println("=========");
            System.out.println("=========");
            System.out.println("=========");
        }else if(id == R.id.wam_tv_dialog){

        }
    }

    public void setUpdateDialog() {
        UpdateLoadingDialog updateDialog = new UpdateLoadingDialog.Builder(this)
            .setIndicator(R.drawable.gif_uld_hxb_default, R.drawable.gif_uld_fox)
            .setStep1Callback(new UpdateLoadingDialog.OnStepSuccessCallback() {
                @Override
                public void onSuccess() {

                }
            }, new UpdateLoadingDialog.OnStepErrorCallback() {
                @Override
                public void onCancelClicked() {

                }

                @Override
                public void onRetryClicked() {

                }
            })
            .build();
        updateDialog.show();

    }

}