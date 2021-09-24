package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
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

    private Context mContext;

    private TouchGestureDetector mGestureDetector;
    private OnScaleListener mScaleListener;
    private View mView;

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
        mContext = context;
        initView();
    }

    private void initView() {
        initGesture();
    }


    public void setChild(View view){
        mView = view;
    }

    public void setScaleListener(OnScaleListener listener){
        mScaleListener = listener;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "Touch event" + event.getAction());
        boolean result = mGestureDetector.onTouchEvent(event);
        return result ? true : super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "dispatch event" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    /**
     * 滑动在onIntercept中做
     * requestDisallowInterceptTouchEvent(true)会拦截此事件，但是始终无法拦截dispatchTouchEvent
     * 很奇怪的是，onInterceptTouchEvent的调用始终在child的onTouch前，为什么child的onTouch可以反而控制onInterceptTouchEvent的？
     * 没问题，在touch事件中拦截后，单次事件的后续事件不在响应
     * */
    private boolean mIsBeingDragged = false;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        LogUtil.e(TAG, "onIntercept event" + event.getAction());
//        return true;
        return super.onInterceptTouchEvent(event);
//        return result ? true : super.onInterceptTouchEvent(event);
    }
    Matrix mMatrix = new Matrix();
    private void initGesture() {
        mGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {
            float mLastScale = 1f;
            float mDistanceX = 0f, mDistanceY = 0f;
            @Override
            public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
                LogUtil.e(TAG, "onScaleBegin: " + detector.getScaleFactor());
                //准心，回头再调了
                float focusX = detector.getFocusX();
                float focusY = detector.getFocusY();
                mView.setPivotX(focusX);
                mView.setPivotY(focusY);
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
                LogUtil.e(TAG, "onScaleEnd: " + detector.getScaleFactor());
//                float scaleFactor = detector.getScaleFactor();
//                float tempScale = lastScale * scaleFactor;
//                if(tempScale >= 4){
//                    tempScale = 4;
//                }else if (tempScale <= 0.25){
//                    tempScale = 0.25f;
//                }
//                lastScale = tempScale;
//                mView.setPivotX(0);
//                mView.setPivotY(0);
            }


            @Override
            public boolean onScale(ScaleGestureDetectorApi27 detector) { // 双指缩放中
                LogUtil.e(TAG, "onScale: " + detector.getScaleFactor());
                float scaleFactor = detector.getScaleFactor();
//                float currentSpan = detector.getCurrentSpan();
//                float currentSpanX = detector.getCurrentSpanX();
//                float currentSpanY = detector.getCurrentSpanY();
//                float focusX = detector.getFocusX();
//                float focusY = detector.getFocusY();
//                LogUtil.e(TAG, "scaleFactor = " + scaleFactor + " , currentSpan = " + currentSpan
//                    + " , currentSpanX = " + currentSpanX + " , currentSpanY = " + currentSpanY);
                if(scaleFactor < 1.1 && scaleFactor > 0.9){
                    return false;
                }else {
                    float tempScale = mLastScale * scaleFactor;
                    if(tempScale >= 4){
                        tempScale = 4;
                    }else if (tempScale <= 0.25){
                        tempScale = 0.25f;
                    }
                    //默认缩放的中心是view的中心，且这个坐标是缩放前的真实坐标
                    //此方法不走onDraw
                    //scrollview似乎也不会走onDraw，所以应该用view的而非canvan的
                    mView.setScaleX(tempScale);
                    mView.setScaleY(tempScale);

                    mLastScale = tempScale;
                    mMatrix.setScale(mLastScale, mLastScale);
                    return true;
                }
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                LogUtil.e(TAG, "onScroll, e1 = " + e1.getAction() + " , e2 = " + e2.getAction()
                        + " , distanceX " + distanceX + " , distanceY " + distanceY);
                double squareDis =  distanceX * distanceX + distanceY * distanceY;
                if(squareDis < 8){
                    return false;
                }else {
                    mDistanceX += distanceX;
                    mDistanceY += distanceY;
                    mMatrix.setTranslate(mDistanceX, mDistanceY);
                    mView.setTranslationX(-mDistanceX);
                    mView.setTranslationY(-mDistanceY);
                    return true;
                }
            }

//            @Override
//            public void onScrollBegin(MotionEvent e) {
//                LogUtil.e(TAG, "onScrollBegin, e = " + e.getAction());
//                super.onScrollBegin(e);
//            }
//
            @Override
            public void onScrollEnd(MotionEvent e) {
                LogUtil.e(TAG, "onScrollEnd, e = " + e.getAction());
                float[] mf = new float[9];
                mMatrix.getValues(mf);
                System.out.println("===============");
                super.onScrollEnd(e);
            }
        });
    }

    public interface OnScaleListener{
        boolean onScaled(ScaleGestureDetectorApi27 detector);
    }
}
