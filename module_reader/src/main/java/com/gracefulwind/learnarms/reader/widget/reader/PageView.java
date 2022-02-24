package com.gracefulwind.learnarms.reader.widget.reader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ClassName: PageView
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/23
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/23
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class PageView extends View {
    public static final String TAG = "PageView";

    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//==================================================================================================
    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高
    private boolean isPrepare;

    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    // 初始化参数
    private int mBgColor = 0xFFCEC29C;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        isPrepare = true;

//        if (mPageLoader != null) {
//            mPageLoader.prepareDisplay(w, h);
//        }
    }
}
