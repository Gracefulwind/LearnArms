package com.gracefulwind.learnarms.commonsdk.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName: TypeFaceUtil
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 10:30
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 10:30
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class TypefaceUtil {

    public static Map<String, Typeface> typefaceList = new TreeMap<>();
    public static Typeface typeface;

    public static void init(Context context){
        if(typeface == null){
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/mxx_font2.ttf");
            typefaceList.put("mxx_font2", typeface);
        }
    }

    public static Typeface getDefaultTypeface(){
        return getTypeface("mxx_font2");
    }

    public static Typeface getTypeface(String typefaceName) {
        return typefaceList.get(typefaceName);
    }
}
