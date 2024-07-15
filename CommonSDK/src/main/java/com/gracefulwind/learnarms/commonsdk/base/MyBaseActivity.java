package com.gracefulwind.learnarms.commonsdk.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.R;
import com.gracefulwind.learnarms.commonsdk.interfaces.Immersible;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.StatusBarUtil;
import com.gracefulwind.learnarms.commonsdk.widget.StatusBarView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;


import org.jetbrains.annotations.NotNull;

import static com.gracefulwind.learnarms.commonsdk.utils.StatusBarUtil.getStatusBarHeight;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: MyBaseActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/8
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public abstract class MyBaseActivity<P extends IPresenter> extends BaseActivity<P> implements Immersible {
    public static final String TAG = "MyBaseActivity";
    protected View mRootView;
    protected boolean isImmersive = true;
    protected boolean needSuitImmersive = true;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
//        setStatusBar();
//        setStatusBar1();
        super.onCreate(savedInstanceState);
        //根据contentView来找rootView，不用管外面的那么多层级包裹
        mRootView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        if(isImmersive){
            //似乎statusBar的设定时机无所谓啊。不是网上说的必须在setContent前
            setStatusBar();
        }
    }

    public void setImmersiveMode(boolean isImmersive){
        this.isImmersive = isImmersive;
    }

    public View getRootView(){
        return mRootView;
    }

    /**
     * 测试代码，9.0+系统下正常的沉浸式透明状态栏
     * */
    @Deprecated
    private void setStatusBar1() {

        int uiFlag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        //设置全屏
        uiFlag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        //隐藏底部布局
        uiFlag |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        //6.0以上设置状态栏暗色 todo:wd
        uiFlag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//        //去除系统导航栏的透明属性，为什么？会变白色 todo:wd 没有这句也是正常的
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //设置后才能改变状态栏颜色 todo:wd 没有这句也是正常的。。。
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        //=================================
        getWindow().getDecorView().setSystemUiVisibility(uiFlag);
        getWindow().setStatusBarColor(getResources().getColor(R.color.translation));

    }

    private void setStatusBar() {
        int realStatusBarHeight = getStatusBarHeight(this);
        LogUtil.e(TAG, "get height on set : " + realStatusBarHeight);
        StatusBarUtil.setStatusBarImmersive(this);
        StatusBarUtil.suitStatusBarImmersive(this, this, mRootView);
//        boolean hasNaviBar = StatusBarUtil.hasNaviBar(this);
    }

    @Override
    public View getStatusBarPaddingView(){
        return null;
    }

    @Override
    public View getStatusBarMarginView(){
        return null;
    }

    private void hasNaviBar() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        //get real screen size
        display.getRealMetrics(dm);
        int rWidthPix = dm.widthPixels;
        int rHeightPix = dm.heightPixels;
        //get content size
        dm = new DisplayMetrics();
        display.getMetrics(dm);
//        display.getRealMetrics(dm);
        int cWidthPix = dm.widthPixels;
        int cHeightPix = dm.heightPixels;
        boolean isSame = (0 == rWidthPix - cWidthPix) && (0 == rHeightPix - cHeightPix);
        LogUtil.e(TAG, "activity is " + this.getClass().getSimpleName() + " , isSame = " + isSame
            + " , dw = " + (rWidthPix - cWidthPix) + " , dh = " + (rHeightPix - cHeightPix));
    }

    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    private static StatusBarView createStatusBarView(Activity activity, int color, int alpha) {
        // 绘制一个和状态栏一样高的矩形
        StatusBarView statusBarView = new StatusBarView(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        return statusBarView;
    }

    private static int calculateStatusColor(int color, int alpha){
        return 0;
    }


}
