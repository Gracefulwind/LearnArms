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
     * todo:wd 0
     * 此方法在jessYan的工具包里也有，但是走了Exception。网上看是可以的，有空查下问题原因
     * 经测试，在android 7.1上正常，估计和mSemiTransparentStatusBarColor一样。高版本api的反射有被禁止的
     * 查询9.0/10.0反射问题
     * */
    public static int getStatusBarHeight(Context context){
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
            return 0;
        }
    }

    /**
     * 相比上面的方法{@link #getStatusBarHeight(Context)}, 这个方法经测试可行
     * */
    public static int getRealStatusBarHeight(Context context){
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }else {
            //获取不到statusBarHeight则为0
            result = 0;
        }
        return result;
    }

}