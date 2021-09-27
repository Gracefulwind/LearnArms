package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.OverScroller;

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
    private OverScroller mScroller;

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
        mScroller = new OverScroller(getContext());
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

//        LogUtil.e(TAG, "onIntercept event" + event.getAction());
//        return true;
        return super.onInterceptTouchEvent(event);
//        return result ? true : super.onInterceptTouchEvent(event);
    }

    float mLastScale = 1f;
    float mDistanceX = 0f, mDistanceY = 0f;
    private Matrix mMatrix = new Matrix();
    private float[] matrixValues = new float[9];
    boolean flag = true;
    private void initGesture() {
        mGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            @Override
            public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
                LogUtil.e(TAG, "onScaleBegin: " + detector.getScaleFactor());
                //准心，回头再调了
                float focusX = detector.getFocusX();
                float focusY = detector.getFocusY();
                LogUtil.e(TAG, "onScaleBegin: focusX = " + focusX + " , focusY " + focusY);
                float realX = (focusX - mDistanceX) / mLastScale;
                float realY = (focusY - mDistanceY) / mLastScale;
//                mView.setPivotX(realX);
//                mView.setPivotY(realY);
//                mView.setPivotX(focusX);
//                mView.setPivotY(focusY);
//                if(flag){
//                    mView.setPivotX(0);
//                    mView.setPivotY(0);
//                }else {
//                    int width = mView.getWidth();
//                    int height = mView.getHeight();
//                    mView.setPivotX(width);
//                    mView.setPivotY(height);
//                }

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
//                LogUtil.e(TAG, "onScale: " + detector.getScaleFactor());
                float scaleFactor = detector.getScaleFactor();
//                float currentSpan = detector.getCurrentSpan();
//                float currentSpanX = detector.getCurrentSpanX();
//                float currentSpanY = detector.getCurrentSpanY();
//                float focusX = detector.getFocusX();
//                float focusY = detector.getFocusY();
//                LogUtil.e(TAG, "scaleFactor = " + scaleFactor + " , currentSpan = " + currentSpan
//                    + " , currentSpanX = " + currentSpanX + " , currentSpanY = " + currentSpanY);
                if(scaleFactor < 1.05f && scaleFactor > 0.95f){
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
//                LogUtil.e(TAG, "onScroll, e1 = " + e1.getAction() + " , e2 = " + e2.getAction()
//                        + " , distanceX " + distanceX + " , distanceY " + distanceY);
                LogUtil.e(TAG, "onScroll, e = " + e2.getAction() + " , id = " + e2.getPointerId(e2.getActionIndex()));

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

            @Override
            public void onScrollBegin(MotionEvent e) {
                LogUtil.e(TAG, "onScrollBegin, e = " + e.getAction() + " , id = " + e.getPointerId(e.getActionIndex()));
                int width = mView.getWidth();
                int height = mView.getHeight();
                mView.setPivotX(width);
                mView.setPivotY(height);
                super.onScrollBegin(e);
            }
//
            @Override
            public void onScrollEnd(MotionEvent e) {
                LogUtil.e(TAG, "onScrollEnd, e = " + e.getAction() + " , id = " + e.getPointerId(e.getActionIndex()));
//                matrixValues = new float[9];
                mMatrix.getValues(matrixValues);
                System.out.println("===============");
                super.onScrollEnd(e);
            }
        });
    }

    /**
     *
     * 控制光标
     *
     * */
    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
                                                 boolean immediate) {
        // offset into coordinate space of this scroll view
        rectangle.offset(child.getLeft() - child.getScrollX(),
                child.getTop() - child.getScrollY());

        return scrollToChildRect(rectangle, immediate);
    }

    /**
     * If rect is off screen, scroll just enough to get it (or at least the
     * first screen size chunk of it) on screen.
     *
     * @param rect      The rectangle.
     * @param immediate True to scroll immediately without animation
     * @return true if scrolling was performed
     */
    private boolean scrollToChildRect(Rect rect, boolean immediate) {
        final int delta = computeScrollDeltaToGetChildRectOnScreen(rect);
        final boolean scroll = delta != 0;
        if (scroll) {
            if (immediate) {
                scrollBy(0, delta);
            } else {
                smoothScrollBy(0, delta);
            }
        }
        return scroll;
    }

    /**
     * Compute the amount to scroll in the Y direction in order to get
     * a rectangle completely on the screen (or, if taller than the screen,
     * at least the first screen size chunk of it).
     *
     * @param rect The rect.
     * @return The scroll delta.
     */
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        if (getChildCount() == 0) return 0;

        int height = getHeight();
        int screenTop = getScrollY();
        int screenBottom = screenTop + height;

        int fadingEdge = getVerticalFadingEdgeLength();

        // leave room for top fading edge as long as rect isn't at very top
        if (rect.top > 0) {
            screenTop += fadingEdge;
        }

        // leave room for bottom fading edge as long as rect isn't at very bottom
        if (rect.bottom < getChildAt(0).getHeight()) {
            screenBottom -= fadingEdge;
        }

        int scrollYDelta = 0;

        if (rect.bottom > screenBottom && rect.top > screenTop) {
            // need to move down to get it in view: move down just enough so
            // that the entire rectangle is in view (or at least the first
            // screen size chunk).

            if (rect.height() > height) {
                // just enough to get screen size chunk on
                scrollYDelta += (rect.top - screenTop);
            } else {
                // get entire rect at bottom of screen
                scrollYDelta += (rect.bottom - screenBottom);
            }

            // make sure we aren't scrolling beyond the end of our content
            int bottom = getChildAt(0).getBottom();
            int distanceToBottom = bottom - screenBottom;
            scrollYDelta = Math.min(scrollYDelta, distanceToBottom);

        } else if (rect.top < screenTop && rect.bottom < screenBottom) {
            // need to move up to get it in view: move up just enough so that
            // entire rectangle is in view (or at least the first screen
            // size chunk of it).

            if (rect.height() > height) {
                // screen size chunk
                scrollYDelta -= (screenBottom - rect.bottom);
            } else {
                // entire rect at top
                scrollYDelta -= (screenTop - rect.top);
            }

            // make sure we aren't scrolling any further than the top our content
            scrollYDelta = Math.max(scrollYDelta, -getScrollY());
        }
        return scrollYDelta;
    }

    /**
     * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
     *
     * @param dx the number of pixels to scroll by on the X axis
     * @param dy the number of pixels to scroll by on the Y axis
     */
    private long mLastScroll;
    static final int ANIMATED_SCROLL_GAP = 250;
    public final void smoothScrollBy(int dx, int dy) {
        if (getChildCount() == 0) {
            // Nothing to do.
            return;
        }
        int tempScrollX = getScrollX();
        int tempScrollY = getScrollY();
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();
        long duration = AnimationUtils.currentAnimationTimeMillis() - mLastScroll;
        if (duration > ANIMATED_SCROLL_GAP) {
            final int height = getHeight() - paddingBottom - paddingTop;
            final int bottom = getChildAt(0).getHeight();
            final int maxY = Math.max(0, bottom - height);
            final int scrollY = tempScrollY;
            dy = Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY;

            mScroller.startScroll(tempScrollX, scrollY, 0, dy);
            postInvalidateOnAnimation();
        } else {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
//                if (mFlingStrictSpan != null) {
//                    mFlingStrictSpan.finish();
//                    mFlingStrictSpan = null;
//                }
            }
            scrollBy(dx, dy);
        }
        mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    //    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        LogUtil.e(TAG, "onSizeChanged");
//        super.onSizeChanged(w, h, oldw, oldh);
//    }
//
//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        LogUtil.e(TAG, "onScrollChanged");
//        super.onScrollChanged(l, t, oldl, oldt);
//    }
//
//    @Override
//    public boolean canScrollVertically(int direction) {
//        return super.canScrollVertically(direction);
//    }


    public interface OnScaleListener{
        boolean onScaled(ScaleGestureDetectorApi27 detector);
    }
}
