package com.gracefulwind.learnarms.commonsdk.utils;

import android.content.Context;
import androidx.annotation.LayoutRes;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

}