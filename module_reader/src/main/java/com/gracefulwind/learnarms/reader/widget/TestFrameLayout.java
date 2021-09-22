package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: TestFrameLayout
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TestFrameLayout extends FrameLayout {
    public static final String TAG = "TestFrameLayout";

    public TestFrameLayout(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public TestFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }

    public TestFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LogUtil.e(TAG, "onMeasure");
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        LogUtil.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.e(TAG, "onDraw");
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
//        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        final ViewGroup.LayoutParams lp = child.getLayoutParams();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                paddingLeft + paddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(MeasureSpec.UNSPECIFIED,
                paddingTop + paddingBottom, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

//    public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
//        int specMode = MeasureSpec.getMode(spec);
//        int specSize = MeasureSpec.getSize(spec);
//
//        int size = Math.max(0, specSize - padding);
//
//        int resultSize = 0;
//        int resultMode = 0;
//
//        switch (specMode) {
//            // Parent has imposed an exact size on us
//            case MeasureSpec.EXACTLY:
//                if (childDimension >= 0) {
//                    resultSize = childDimension;
//                    resultMode = MeasureSpec.EXACTLY;
//                } else if (childDimension == ViewGroup.LayoutParams.MATCH_PARENT) {
//                    // Child wants to be our size. So be it.
//                    resultSize = size;
//                    resultMode = MeasureSpec.EXACTLY;
//                } else if (childDimension == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                    // Child wants to determine its own size. It can't be
//                    // bigger than us.
//                    resultSize = size;
//                    resultMode = MeasureSpec.AT_MOST;
//                }
//                break;
//
//            // Parent has imposed a maximum size on us
//            case MeasureSpec.AT_MOST:
//                if (childDimension >= 0) {
//                    // Child wants a specific size... so be it
//                    resultSize = childDimension;
//                    resultMode = MeasureSpec.EXACTLY;
//                } else if (childDimension == ViewGroup.LayoutParams.MATCH_PARENT) {
//                    // Child wants to be our size, but our size is not fixed.
//                    // Constrain child to not be bigger than us.
//                    resultSize = size;
//                    resultMode = MeasureSpec.AT_MOST;
//                } else if (childDimension == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                    // Child wants to determine its own size. It can't be
//                    // bigger than us.
//                    resultSize = size;
//                    resultMode = MeasureSpec.AT_MOST;
//                }
//                break;
//
//            // Parent asked to see how big we want to be
//            case MeasureSpec.UNSPECIFIED:
//                if (childDimension >= 0) {
//                    // Child wants a specific size... let him have it
//                    resultSize = childDimension;
//                    resultMode = MeasureSpec.EXACTLY;
//                } else if (childDimension == ViewGroup.LayoutParams.MATCH_PARENT) {
//                    // Child wants to be our size... find out how big it should
//                    // be
//                    resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
//                    resultMode = MeasureSpec.UNSPECIFIED;
//                } else if (childDimension == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                    // Child wants to determine its own size.... find out how
//                    // big it should be
//                    resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
//                    resultMode = MeasureSpec.UNSPECIFIED;
//                }
//                break;
//        }
//        //noinspection ResourceType
//        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
//    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    public void requestLayout() {
//        super.requestLayout();
//    }
}
