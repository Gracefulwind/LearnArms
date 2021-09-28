package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Color;
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
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.edit.SmartTextView;

import org.jetbrains.annotations.NotNull;

/**
 * @ClassName: SmartHandNoteView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/24
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartHandNoteView extends FrameLayout {
    public static final String TAG = "SmartHandNoteView";
    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;

    static final int ANIMATED_SCROLL_GAP = 250;

    private Context mContext;
    private int mViewMode;
    private LinesView mLinesView;
    private SmartTextView mSmartTextView;
    private DoodleView mDoodleView;

    private TouchGestureDetector mGestureDetector;
    //最大缩放比例,最好别动
    float maxScaleRate = 3f;
    //最小响应缩放比例,最好别动
    float minScaleResponse = 0.05f;
    //最小响应滑动距离(的平方)
    float minDisResponse = 3 * 3;
    float mLastScale = 1f;
    float mDistanceX = 0f, mDistanceY = 0f;
//    private Matrix mMatrix = new Matrix();
//    private float[] matrixValues = new float[9];
//    boolean flag = true;
    //scroll相关
    private OverScroller mScroller;
    private long mLastScroll;


    public SmartHandNoteView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public SmartHandNoteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartHandNoteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(@NotNull Context context, @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        mContext = context;

        createChildren();
        mScroller = new OverScroller(getContext());
        initGesture();
        //init viewMode
        setViewMode(MODE_TEXT);
    }

    private void createChildren() {
        //add smartTextView
        mSmartTextView = new SmartTextView(mContext);
        LayoutParams stvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mSmartTextView.setLayoutParams(stvLayoutParams);
        //add linesView
        mLinesView = new LinesView(mContext, mSmartTextView.getLineHeight());
        LayoutParams lvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLinesView.setLayoutParams(lvLayoutParams);
        //add doodleView
        mDoodleView = new DoodleView(mContext);
        LayoutParams dvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mDoodleView.setLayoutParams(dvLayoutParams);
        mSmartTextView.setOnSizeChangedListener(new SmartTextView.OnSizeChangeListener() {
            @Override
            public void onSizeChange(int w, int h, int oldw, int oldh) {
                mLinesView.setViewHeightWithTextView(h);
                mDoodleView.setViewHeightWithTextView(h);
            }
        });
        addView(mLinesView);
        addView(mSmartTextView);
        addView(mDoodleView);
        //=====test======
        mSmartTextView.setBackgroundColor(Color.parseColor("#A003aF30"));
        mLinesView.setBackgroundColor(Color.parseColor("#A0a03030"));
        mDoodleView.setBackgroundColor(Color.parseColor("#083030F0"));
    }

    /**
     * 初始化手势器
     * */
    private void initGesture() {
        mGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {

            private float focusX;
            private float focusY;

            @Override
            public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
//                LogUtil.e(TAG, "onScaleBegin: " + detector.getScaleFactor());
                //准心，回头再调了
                focusX = detector.getFocusX();
                focusY = detector.getFocusY();
//                LogUtil.e(TAG, "onScaleBegin: focusX = " + focusX + " , focusY " + focusY);
                float realX = (focusX - mDistanceX) / mLastScale;
                float realY = (focusY - mDistanceY) / mLastScale;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
//                LogUtil.e(TAG, "onScaleEnd: " + detector.getScaleFactor());
            }

            @Override
            public boolean onScale(ScaleGestureDetectorApi27 detector) { // 双指缩放中
                float scaleFactor = detector.getScaleFactor();
                if(scaleFactor < (1 + minScaleResponse) && scaleFactor > (1 - minScaleResponse)){
                    return false;
                }else {
                    float tempScale = mLastScale * scaleFactor;
                    if(tempScale >= maxScaleRate){
                        tempScale = maxScaleRate;
                    }else if (tempScale <= 1 / maxScaleRate){
                        tempScale = 1 / maxScaleRate;
                    }
//                    doScale(0, 0, tempScale);
                    doScale(focusX, focusY, tempScale);
//                    doScale(detector.getFocusX(), detector.getFocusY(), tempScale);
                    mLastScale = tempScale;
                    return true;
                }
            }
            //--------------------------------------------------------------------------------------
            @Override
            public void onScrollBegin(MotionEvent e) {
                super.onScrollBegin(e);
            }
            //
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //这里的distance是local - target的结果，所以右移得到的是负的值，需要取反
                double squareDis =  distanceX * distanceX + distanceY * distanceY;
                float realX = -distanceX;
                float realY = -distanceY;
                double responseDis = 0;
                responseDis = responseDis >= 1 ? minDisResponse / mLastScale : minDisResponse;
                if(squareDis < responseDis){
                    return false;
                }else {
//                    LogUtil.e(TAG, "distanceX = " + realX);
                    mDistanceX += realX;
                    mDistanceY += realY;
                    float textWidth = mSmartTextView.getWidth();
                    float textHeight = mSmartTextView.getHeight();
                    float lineHeight = mLinesView.getHeight();
                    float noteHeight = getHeight();
                    float textPx = mSmartTextView.getPivotX();
                    float textPy = mSmartTextView.getPivotY();
                    float linePx = mLinesView.getPivotX();
                    float linePy = mLinesView.getPivotY();
//                    LogUtil.e(TAG, "textPx + textWidth = " + (textPx + textWidth) + " , linePx = " + linePx);
                    //控制左滑
                    float maxDistanceX = (textPx + textWidth) * mLastScale - textPx;
                    if(mDistanceX > maxDistanceX){
                        mDistanceX = maxDistanceX;
                    }
                    float minDistanceX = textWidth - textPx - (2 * textWidth - textPx) * mLastScale;
                    if(mDistanceX < minDistanceX){
                        mDistanceX = minDistanceX;
                    }
                    float maxDistanceY = linePy * mLastScale - linePy;
                    float tempY = mDistanceY;
                    if(mDistanceY > maxDistanceY){
                        mDistanceY = maxDistanceY;
                    }
//                    if(textHeight * mLastScale > noteHeight){
//                        float minDistanceY = noteHeight - textPy - (textHeight - textPy) * mLastScale;
//                        if(mDistanceY < minDistanceY){
//                            mDistanceY = minDistanceY;
//                        }
////                        LogUtil.e(TAG, "min");
//                    }else {
//                        mDistanceY = maxDistanceY;
//                    }
                    float minDistanceY = noteHeight - linePy - (lineHeight - linePy) * mLastScale;
                    if(mDistanceY < minDistanceY){
                        mDistanceY = minDistanceY;
                    }
                    LogUtil.e(TAG, "maxDistanceY = " + maxDistanceY + " , minDistanceY = " + minDistanceY
                        + " , tempY = " + tempY + " , targetY = " + mDistanceY);
//                    if(mDistanceX)
                    //两个均可，用To方法方便控制最大距离
                    doTranslateTo(mDistanceX, mDistanceY);
//                    doTranslateBy(realX, realY);
                    return true;
                }
            }

            @Override
            public void onScrollEnd(MotionEvent e) {
                super.onScrollEnd(e);
            }
        });
    }

    /**
     * 写字板，涂鸦层和背景线条的缩放
     * */
    private void doScale(float focusX, float focusY, float tempScale) {
//        LogUtil.e(TAG, "focusX = " + focusX + ", focusY = " + focusY);
        mSmartTextView.smartScaleTo(focusX, focusY, tempScale, tempScale);
        mLinesView.smartScaleTo(focusX, focusY, tempScale, tempScale);
        mDoodleView.smartScaleTo(focusX, focusY, tempScale, tempScale);
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * */
    private void doTranslateTo(float translateX, float translateY) {
        //todo:wd 添加doodleView的translate，与LineView同步
        //todo:wd 感觉LineView可以只添加Y轴的偏移，保证X轴的位置不变(和doodleView同步也可以，保证了统一性)
        mSmartTextView.smartTranslateTo(translateX, translateY);
        mLinesView.smartTranslateTo(translateX, translateY);
        mDoodleView.smartTranslateTo(translateX, translateY);
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * */
    private void doTranslateBy(float distanceX, float distanceY) {
        mSmartTextView.smartTranslateBy(distanceX, distanceY);
        mLinesView.smartTranslateBy(distanceX, distanceY);
        mDoodleView.smartTranslateBy(distanceX, distanceY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = getWidth();
        int height = getHeight();
//        ViewGroup.LayoutParams layoutParams = mLinesView.getLayoutParams();
//        layoutParams.width = 3 * width;
//        mLinesView.setLayoutParams(layoutParams);
//        mLinesView.scrollTo(width, 0);
        LogUtil.e(TAG, "onMeasure, width = " + width + " , height = " + height);
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        LogUtil.e(TAG, "onLayout");
//        super.onLayout(changed, left, top, right, bottom);
//    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
//        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        //设置成UNSPECIFIED，让textview能够超出一屏而非内部滚动
        if(child instanceof SmartTextView){
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
        }else {
            super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        }

    }

    /**
     *
     * 控制光标,这里是否可以移动光标，同时不用系统scroll?
     *
     * */
    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rectangle,
                                                 boolean immediate) {
//        // offset into coordinate space of this scroll view
//        rectangle.offset(child.getLeft() - child.getScrollX(),
//                child.getTop() - child.getScrollY());
//
//        return scrollToChildRect(rectangle, immediate);
        //TODO:wd 这里原来是算出Y偏移量并scroll的，现在我们不实用scroll，所以用矩阵算出translateY及scaleY的联动，然后手动translate
//        LogUtil.e(TAG, "requestChildRectangleOnScreen == child.getScrollX() = " + child.getScrollX() + " , child.getScrollY() = " + child.getScrollY());
        return false;
    }

    /**
     * If rect is off screen, scroll just enough to get it (or at least the
     * first screen size chunk of it) on screen.
     *
     * @param rect      The rectangle.
     * @param immediate True to scroll immediately without animation
     * @return true if scrolling was performed
     * todo:wd 回头把这里的scroll替换成smartTranslateBy
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "Touch event" + event.getAction());
        boolean result = mGestureDetector.onTouchEvent(event);
        return result ? true : super.onTouchEvent(event);
    }

//===========================================================================
    /**
     * 操作模式切换
     * */
    public void setViewMode(int viewMode) {
        mViewMode = viewMode;

        switch (viewMode) {
            case MODE_SCALE:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
                break;
            case MODE_TEXT:
                mSmartTextView.setEnabled(true);
                mSmartTextView.requestFocus();
                mDoodleView.setEnabled(false);
                break;
            case MODE_DOODLE:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                break;
        }
        mSmartTextView.invalidate();
        mLinesView.invalidate();
        mDoodleView.invalidate();
    }

    public int getLineHeight() {
        return mSmartTextView.getLineHeight();
    }

    public int getTextViewWidth() {
        return mSmartTextView.getWidth();
    }

    public int getTextViewHeight() {
        return mSmartTextView.getHeight();
    }

    public float getScaledTextViewWidth() {
        return mSmartTextView.getWidth() * maxScaleRate;
    }

    public float getScaledTextViewHeight() {
        return mSmartTextView.getHeight() * maxScaleRate;
    }

    /**
    * 最大缩放比例的入口
    * */
    public void setMaxScaleRate(float maxScaleRate) {
        this.maxScaleRate = maxScaleRate;
        //todo:wd 设置完新的最大缩放比例后，最好重构下，以适应新比例小于老比例时的画面跳跃
    }

    public float getMaxScaleRate() {
        return maxScaleRate;
    }

    public void test() {
        Matrix matrix = mSmartTextView.getMatrix();
        Matrix matrix1 = mLinesView.getMatrix();
//        Matrix matrix1 = new Matrix();
        logView(mLinesView);
        logView(mSmartTextView);
    }

    public void logView(View v){
        float scaleX = v.getScaleX();
        float translationX = v.getTranslationX();
        float translationY = v.getTranslationY();
        float pivotX = v.getPivotX();
        float pivotY = v.getPivotY();
        LogUtil.e(TAG, v.getClass().getSimpleName() + " == scale = " + scaleX + ", translationX = " + translationX + " , translationY = " + translationY);
        LogUtil.e(TAG, v.getClass().getSimpleName() + " pivotX = " + pivotX + ", pivotY = " + pivotY);
    }
}
