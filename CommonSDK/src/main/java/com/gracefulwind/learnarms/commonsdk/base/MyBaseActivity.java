package com.gracefulwind.learnarms.commonsdk.base;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gracefulwind.learnarms.commonsdk.R;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.widget.StatusBarView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

import static com.gracefulwind.learnarms.commonsdk.utils.UiUtil.getRealStatusBarHeight;
import static com.gracefulwind.learnarms.commonsdk.utils.UiUtil.getStatusBarHeight;
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

public abstract class MyBaseActivity<P extends IPresenter> extends BaseActivity<P> {
    public static final String TAG = "MyBaseActivity";
    
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setStatusBar();
//        setStatusBar1();
        super.onCreate(savedInstanceState);

    }

    /**
     * 测试代码，9.0+系统下正常的沉浸式透明状态栏
     * */
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
//        //actionBar is null
//        ActionBar actionBar = getActionBar();
//        LogUtil.e(TAG, "actionBar is : " + actionBar);
//        //todo:wd 为什么这个方法得不到？ 此方法会走exception 在android7.0上正常
        int statusBarHeight = getStatusBarHeight(this);
        int realStatusBarHeight = getRealStatusBarHeight(this);
        ViewGroup decorView = (ViewGroup)getWindow().getDecorView();
        int systemOption = decorView.getSystemUiVisibility();
        //SYSTEM_UI_FLAG_FULLSCREEN 会隐藏状态栏，下拉出现且透明
        //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 不会隐藏状态栏，
        //查得资料:配合SYSTEM_UI_FLAG_FULLSCREEN一起使用，效果使得状态栏出现的时候不会挤压activity高度，状态栏会覆盖在activity之上
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | systemOption
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        //5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtil.e(TAG, "SDK == 5.0+");
//            //该方法无法设置导航栏高度
//            decorView.setFitsSystemWindows(true);
////            //设置状态栏, 在7.0以上，这两个标签只要有任意一个，导航栏就会算成灰色。 9.0以上需要再处理半透明状态栏 enforceStatusBarContrast
////            //5.0以上设置沉浸式就 "不能" 设置这个属性！！！
////            //4.4才用这个属性。 设了就会有半透明底色，6/7版本可用反射去除，8版本未测试，9.0以上反射不到该参数
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /**
             * Android5.0系统以上支持，如果设置了该属性，系统栏（状态栏和导航栏）将以透明背景绘制，
             * 并且该窗口中的相应区域将填充setStatusBar（）和setNavigationBarColor（）中设置的颜色
             * */
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.translation));

            //在取消了FLAG_TRANSLUCENT_STATUS后就不需要这个了
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
               //todo:wd find a way to solve it
               //in api29 or higher level, getDeclaredField get error
                LogUtil.d(TAG, "status only half-trans in api 29 or higher");
            //only support android API 24+ ， 确定android12不行，其他版本暂无ROM
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                    && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    field.setInt(getWindow().getDecorView(), Color.TRANSPARENT); //改为透明
                    Log.e(TAG, "set Bar trans");
                } catch (Exception e) {
//                    Log.e(TAG, "set Bar error");
                    Log.e(TAG, e.getMessage() + "\r\n" + e.toString());
                    Log.e(TAG, Log.getStackTraceString(e));
//                    e.printStackTrace();
                }
            }
        //4.4-5.0
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            int       count     = decorView.getChildCount();
//            //判断是否已经添加了statusBarView
//            if (count > 0 && decorView.getChildAt(count - 1) instanceof StatusBarView) {
//                decorView.getChildAt(count - 1).setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
//            } else {
//                //新建一个和状态栏高宽的view
//                StatusBarView statusView = createStatusBarView(this, color, statusBarAlpha);
//                decorView.addView(statusView);
//            }
//            ViewGroup rootView = (ViewGroup) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
////            setRootView(activity);
            //=======
//            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.app_common_title_color));
        }

        hasNaviBar();

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
        display.getRealMetrics(dm);
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
