package com.gracefulwind.learnarms.commonsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.gracefulwind.learnarms.commonsdk.R;

import java.lang.reflect.Field;

/**
 * @ClassName: StatusBarUtil
 * @Author: Gracefulwind
 * @CreateDate: 2022/5/6
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/5/6
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class StatusBarUtil {
    public static final String TAG = "StatusBarUtil";

    public static void setStatusBarImmersive(Activity activity){
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        int systemOption = decorView.getSystemUiVisibility();
        //SYSTEM_UI_FLAG_FULLSCREEN 会隐藏状态栏，下拉出现且透明
        //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 不会隐藏状态栏，
        //查得资料:配合SYSTEM_UI_FLAG_FULLSCREEN一起使用，效果使得状态栏出现的时候不会挤压activity高度，状态栏会覆盖在activity之上
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | systemOption
//                //隐藏虚拟按键 的高度，注意，该flag不会真正隐藏虚拟按键
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        );

        //5.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            LogUtil.e(TAG, "SDK == 5.0+");
//            //该方法无法设置导航栏高度
//            decorView.setFitsSystemWindows(true);
////            //设置状态栏, 在7.0以上，这两个标签只要有任意一个，导航栏就会算成灰色。 9.0以上需要再处理半透明状态栏 enforceStatusBarContrast
////            //5.0以上设置沉浸式就 "不能" 设置这个属性！！！
////            //4.4才用这个属性。 设了就会有半透明底色，6-8版本可用反射去除，9.0以上反射被禁止
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            /**
             * Android5.0系统以上支持，如果设置了该属性，系统栏（状态栏和导航栏）将以透明背景绘制，
             * 并且该窗口中的相应区域将填充setStatusBar（）和setNavigationBarColor（）中设置的颜色
             * */
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.translation));

//            //在取消了FLAG_TRANSLUCENT_STATUS后就不需要这个了
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//                //in api29 or higher level, getDeclaredField get error
//                LogUtil.d(TAG, "status only half-trans in api 29 or higher");
//                //only support android API 24+ ， 确定android12不行，其他版本暂无ROM
//            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
//                    && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
//                try {
//                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
//                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
//                    field.setAccessible(true);
//                    field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT); //改为透明
//                    Log.e(TAG, "set Bar trans");
//                } catch (Exception e) {
////                    Log.e(TAG, "set Bar error");
//                    Log.e(TAG, e.getMessage() + "\r\n" + e.toString());
//                    Log.e(TAG, Log.getStackTraceString(e));
////                    e.printStackTrace();
//                }
//            }
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

    }

    /**
     * todo:wd 0
     * 获取statusBar的高度
     * android 9.0后系统hide的反射被禁用，所以此方法不再可用
     * @return px值，无法获取到资源id则返回-1
     * */
    @Deprecated
    public static int getStatusBarHeightReflect(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        boolean var4 = false;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception var6) {
            var6.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取statusBar的高度
     * 相比上面的方法{@link #getStatusBarHeightReflect(Context)}, 这个方法经测试可行
     * @return px值，无法获取到资源id则返回-1
     * */
    public static int getStatusBarHeight(Context context){
        int result = -1;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取navigationBar的高度
     * @return px值，无法获取到资源id则返回-1
     *
     * */
    public static int getNavigationBarHeight(Context context) {
        int result = -1;
        Resources resources = context.getResources();
        int resourceId = context.getResources().getIdentifier("navigation_bar_height","dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;

    }

    /**
     *
     * 仅保留方法，参考用
     *
     * */
    @Deprecated
    private void getStatusBarHeightAfterInset(Activity activity){
        activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                //这就是状态栏高度
                int systemWindowInsetTop = insets.getSystemWindowInsetTop();
                //获取高度后的处理
                LogUtil.e(TAG, "get height after insets : " + systemWindowInsetTop);
                //此函数必须调用，否则WindowInsets无效
                return v.onApplyWindowInsets(insets);
            }
        });
//        获取虚拟键盘高度=================================================
        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取当前界面可视部分
                activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = activity.getWindow().getDecorView().getRootView().getHeight() - rect.bottom;
//                //动态监听键盘高度，更改最底部的view的高度将其他view挤上去
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) postCommentBinding.bottom.getLayoutParams();
//                layoutParams.height = heightDifference;
//                postCommentBinding.bottom.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * 是否有虚拟按键(横屏&纵屏)
     * @return
     * */
    public static boolean hasNaviBar(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        //get real screen size 连带虚拟按键的尺寸，似乎不算statusBar的
        display.getRealMetrics(dm);
        int rWidthPix = dm.widthPixels;
        int rHeightPix = dm.heightPixels;
        //get content size 去除虚拟按键后的尺寸
        dm = new DisplayMetrics();
        display.getMetrics(dm);
//        display.getRealMetrics(dm);
        int cWidthPix = dm.widthPixels;
        int cHeightPix = dm.heightPixels;
        boolean isSame = (0 == rWidthPix - cWidthPix) && (0 == rHeightPix - cHeightPix);
        LogUtil.e(TAG, "hasNaviBar isSame = " + isSame
                + " , dw = " + (rWidthPix - cWidthPix) + " , dh = " + (rHeightPix - cHeightPix));
        return isSame;

    }
}
