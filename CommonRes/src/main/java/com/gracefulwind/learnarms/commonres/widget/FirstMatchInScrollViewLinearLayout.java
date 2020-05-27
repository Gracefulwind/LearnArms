package com.gracefulwind.learnarms.commonres.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * @ClassName: FirstMatchInScrollViewLinearLayout
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/27 11:31
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/27 11:31
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public class FirstMatchInScrollViewLinearLayout extends LinearLayout {

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FirstMatchInScrollViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstMatchInScrollViewLinearLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(getChildCount() > 0){
            final ViewParent parent = getParent();
            if(parent!=null){
                int getHeight = 0;
                if(parent instanceof NestedScrollView){
                    getHeight = ((NestedScrollView)parent).getMeasuredHeight();

                }
                if(parent instanceof ScrollView){
                    getHeight = ((ScrollView)parent).getMeasuredHeight();
                }
                final int height = getHeight;
                if(height > 0 ){
                    final View firstChild = getChildAt(0);
                    LayoutParams layoutParams = (LayoutParams) firstChild.getLayoutParams();
                    layoutParams.height = height;
                    firstChild.setLayoutParams(layoutParams);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
