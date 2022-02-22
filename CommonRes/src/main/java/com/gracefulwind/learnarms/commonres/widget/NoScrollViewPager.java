package com.gracefulwind.learnarms.commonres.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: NoScrollViewPager
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * 禁止滑动的viewPager
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class NoScrollViewPager extends ViewPager {

    private boolean noScroll = true;

    public NoScrollViewPager(@NonNull @NotNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //调用此方法 参数为true 即可禁止滑动
    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else {
            return super.onInterceptTouchEvent(arg0);
        }
    }

}
