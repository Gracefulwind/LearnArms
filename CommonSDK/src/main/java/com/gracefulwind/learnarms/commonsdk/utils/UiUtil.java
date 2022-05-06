package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;
import androidx.annotation.LayoutRes;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;

import static android.util.TypedValue.applyDimension;

/**
 * @ClassName: UiUtil
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/13 15:13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/13 15:13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class UiUtil {
    public static final String TAG = "UiUtil";

    private static Context appContext = null;

    public static void init(Context appContext){
        UiUtil.appContext = appContext;
    }

    public static void checkInit(){
        if(null == appContext){
            throw new RuntimeException("context must be init before use!");
        }
    }

    /**
     * 描述：dip转换为px.
     *
     * @param dipValue the dip value
     * @return px值
     */
    public static float dip2px(float dipValue) {
        checkInit();
        DisplayMetrics mDisplayMetrics = appContext.getResources().getDisplayMetrics();
        return applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, mDisplayMetrics);
    }

    /**
     * 描述：px转换为dip.
     *
     * @param pxValue the px value
     * @return dip值
     */
    public static float px2dip(float pxValue) {
        checkInit();
        DisplayMetrics mDisplayMetrics = appContext.getResources().getDisplayMetrics();
        return pxValue / mDisplayMetrics.density;
    }

    /**
     * 描述：sp转换为px.
     *
     * @param spValue the sp value
     * @return sp值
     */
    public static float sp2px(float spValue) {
        checkInit();
        DisplayMetrics mDisplayMetrics = appContext.getResources().getDisplayMetrics();
        return applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, mDisplayMetrics);
    }

    /**
     * 描述：px转换为sp.
     *
     * @param pxValue the sp value
     * @return sp值
     */
    public static float px2sp(float pxValue) {
        checkInit();
        DisplayMetrics mDisplayMetrics = appContext.getResources().getDisplayMetrics();
        return pxValue / mDisplayMetrics.scaledDensity;
    }

    public static View inflate(@LayoutRes int layoutId){
//        checkInit();
        View inflate = inflate(layoutId, null);
        return inflate;
    }

    public static View inflate(@LayoutRes int layoutId, ViewGroup parent){
        checkInit();
        View inflate = LayoutInflater.from(appContext).inflate(layoutId, parent, false);
        return inflate;
    }

    public static int getTextViewHeight(TextView tv){
        Layout layout = tv.getLayout();
        if(null == layout){
            return 0;
        }
        int desired = layout.getLineTop(tv.getLineCount());
        int padding = tv.getCompoundPaddingTop() + tv.getCompoundPaddingBottom();
        return desired + padding;
    }

    /**
     * 获取当前屏幕的尺寸大小，不包含状态栏
     * @param context
     * @return
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取当前屏幕包含状态栏的尺寸大小
     * @param context
     * @return
     * todo:wd 存疑，测试出来似乎不带statusBar的高度
     */
    public static DisplayMetrics getRealMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowMgr.getDefaultDisplay().getRealMetrics(metrics);
        return metrics;

    }


}