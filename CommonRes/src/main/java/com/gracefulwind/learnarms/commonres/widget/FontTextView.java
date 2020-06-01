package com.gracefulwind.learnarms.commonres.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.gracefulwind.learnarms.commonsdk.utils.TypefaceUtil;

/**
 * @ClassName: FontView
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 10:38
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 10:38
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class FontTextView extends android.support.v7.widget.AppCompatTextView {

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(isInEditMode()){
            return ;
        }
        Typeface mxx_font2 = TypefaceUtil.getTypeface("mxx_font2");
        if(null != mxx_font2){
            setTypeface(mxx_font2);
        }
    }

}
