package com.gracefulwind.learnarms.commonres.widget;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @ClassName: MatchMaxCHildViewPager
 * @Author: Gracefulwind
 * @CreateDate: 2020/6/1 10:09
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/6/1 10:09
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */


/**
 *
 * 有问题，暂时不用
 *
 * */
public class MatchMaxChildViewPager extends ViewPager {
    private int current;
    private int height = 0;
    /**
     * 保存position与对于的View
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

    private boolean scrollble = true;

    public MatchMaxChildViewPager(Context context) {
        super(context);
    }

    public MatchMaxChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {
            FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) getLayoutParams();
//       LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }
    /**
     * 保存position与对于的View
     */
    public void setObjectForPosition(View view, int position)
    {
        mChildrenViews.put(position, view);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return true;
        }
        return super.onTouchEvent(ev);
    }


    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }


}
