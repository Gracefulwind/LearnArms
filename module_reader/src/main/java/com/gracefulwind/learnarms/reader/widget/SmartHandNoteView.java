package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.EditMode;
import com.gracefulwind.learnarms.reader.widget.doodle.OperationPresenter;
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
    float maxScaleRate = 2f;
    //最小响应缩放比例,最好别动
    float minScaleResponse = 0.1f;
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
//        mSmartTextView.setBackgroundColor(Color.parseColor("#A003aF30"));
//        mLinesView.setBackgroundColor(Color.parseColor("#A0a03030"));
////        mDoodleView.setBackgroundColor(Color.parseColor("#083030F0"));
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
                float parentFocusX = detector.getFocusX();
                float parentFocusY = detector.getFocusY();
                float textTx = mSmartTextView.getTranslationX();
                float textTy = mSmartTextView.getTranslationY();
                float beginScale = detector.getScaleFactor();
                focusX = parentFocusX / beginScale - textTx;
                focusY = parentFocusY / beginScale - textTy;

//                LogUtil.e(TAG, "onScaleBegin: focusX = " + focusX + " , focusY " + focusY);
//                float realX = (focusX - mDistanceX) / mLastScale;
//                float realY = (focusY - mDistanceY) / mLastScale;
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
//                LogUtil.e(TAG, "onScaleEnd: " + detector.getScaleFactor());
                //恢复4向多余量
                scrollBy(0, 0);
            }

            @Override
            public boolean onScale(ScaleGestureDetectorApi27 detector) { // 双指缩放中
                float scaleFactor = detector.getScaleFactor();
                float tempScale = mLastScale * scaleFactor;
                if(scaleFactor < (1 + minScaleResponse) && scaleFactor > (1 - minScaleResponse)){
                    return false;
                }else {
                    if(tempScale >= maxScaleRate){
                        tempScale = maxScaleRate;
                    }else if (tempScale <= 1 / maxScaleRate){
                        tempScale = 1 / maxScaleRate;
                    }
                    //-------------
                    float scaleX = mSmartTextView.getScaleX();
                    float textTx = mSmartTextView.getTranslationX();
                    float textTy = mSmartTextView.getTranslationY();
                    int lineWidth = mLinesView.getWidth();
                    float lineTx = mLinesView.getTranslationX();
                    float lineTy = mLinesView.getTranslationY();
                    float linePx = mLinesView.getPivotX();
                    float linePy = mLinesView.getPivotY();
                    float textPx = mSmartTextView.getPivotX();
                    float textPy = mSmartTextView.getPivotY();
                    int textWidth = mSmartTextView.getWidth();
                    float outWidth = ((maxScaleRate - 1) / 2) * textWidth;
                    LogUtil.e(TAG, "textTx = " + textTx + ", textPx = " + textPx +  " , scale = " + tempScale);
//                    //先右边界
//                    scaleManagerRight(tempScale);
//                    //再左边界
//                    scaleManagerLeft(tempScale);
//                    //先上边界
//                    scaleManagerTop(tempScale, textTy, textPy);
                    doScale(focusX, focusY, tempScale);
//                    mLastScale = tempScale;
                    return true;
                }
            }

            private void scaleManagerTop(float tempScale, float textTy, float textPy) {
                if(tempScale == 1){
                    //正好缩成一倍时，如果textTx > outWidth,那么必然越界，否则必然不越界，此时和缩放中心无关了
                    LogUtil.e(TAG, "top tempScale == 1");
                }else {
                    if((textPy * tempScale - textPy - textTy) < 0){
                        double newPy = textTy / (tempScale - 1d);
                        LogUtil.e(TAG, "top 在屏幕外，新py = " + newPy);
//                            focusY = (float) newPy;
                        focusY = 0;
                    }else {
                        LogUtil.e(TAG, "top 在屏幕内");
                    }
                }
            }

            private void scaleManagerRight(float tempScale) {
                float textTx = mSmartTextView.getTranslationX();
                float textPx = mSmartTextView.getPivotX();
                int textWidth = mSmartTextView.getWidth();
                float outWidth = ((maxScaleRate - 1) / 2) * textWidth;
                //右边界
                if(tempScale == 1){
                    //正好缩成一倍时，如果textTx > outWidth,那么必然越界，否则必然不越界，此时和缩放中心无关了
                    LogUtil.e(TAG, "right tempScale == 1");
                }else {
                    float tx = Math.abs(textTx);
                    if((textWidth + outWidth - textPx) * tempScale - (textWidth - textPx + tx) < 0){
                        double newPx = (outWidth * tempScale + textWidth * tempScale - textWidth - tx) / (tempScale - 1d);
                        LogUtil.e(TAG, "right 在屏幕外，新px = " + newPx);
//                        focusX = (float) newPx;
                        focusX = textWidth + outWidth;
                    }else {
                        LogUtil.e(TAG, "right 在屏幕内");
                    }
                }
            }

            private void scaleManagerLeft(float tempScale) {
                float textTx = mSmartTextView.getTranslationX();
                float textTy = mSmartTextView.getTranslationY();
                int lineWidth = mLinesView.getWidth();
                float lineTx = mLinesView.getTranslationX();
                float lineTy = mLinesView.getTranslationY();
                float linePx = mLinesView.getPivotX();
                float linePy = mLinesView.getPivotY();
                float textPx = mSmartTextView.getPivotX();
                float textPy = mSmartTextView.getPivotY();
                int textWidth = mSmartTextView.getWidth();
                float outWidth = ((maxScaleRate - 1) / 2) * textWidth;
                //处理左边界
                if(tempScale == 1){
                    //正好缩成一倍时，如果textTx > outWidth,那么必然越界，否则必然不越界，此时和缩放中心无关了
                    LogUtil.e(TAG, "left tempScale == 1");
                }else {
                    if((outWidth * tempScale + textPx * (tempScale - 1) - textTx) < 0){
                        double newPx = (textTx - outWidth * tempScale) / (tempScale - 1d);
                        LogUtil.e(TAG, "left 在屏幕外，新px = " + newPx);
//                        focusX = (float) newPx;
                        focusX = - outWidth;
                    }else {
                        LogUtil.e(TAG, "left 在屏幕内");
                    }
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
                    scrollBy(realX, realY);
//                    doTranslateBy(realX, realY);
                    return true;
                }
            }

            private void scrollBy(float realX, float realY) {
                mDistanceX += realX;
                mDistanceY += realY;
                float textWidth = mSmartTextView.getWidth();
                //基于缩放比例的左/右偏移量
                float realOutWidth = ((maxScaleRate - 1) / 2) * textWidth;
//                    float textHeight = mSmartTextView.getHeight();
                float lineHeight = mLinesView.getHeight();
                float noteHeight = getHeight();
                float textPx = mSmartTextView.getPivotX();
//                    float textPy = mSmartTextView.getPivotY();
//                    float linePx = mLinesView.getPivotX();
                float linePy = mLinesView.getPivotY();
                //控制左滑
                float maxDistanceX = (textPx + realOutWidth) * mLastScale - textPx;
                if(mDistanceX > maxDistanceX){
                    mDistanceX = maxDistanceX;
                }
                float minDistanceX = (textWidth - textPx) - (textWidth - textPx + realOutWidth) * mLastScale;
                if(mDistanceX < minDistanceX){
                    mDistanceX = minDistanceX;
                }
                //上划
                float maxDistanceY = linePy * mLastScale - linePy;
                if(mDistanceY > maxDistanceY){
                    mDistanceY = maxDistanceY;
                }
                float minDistanceY = noteHeight - linePy - (lineHeight - linePy) * mLastScale;
                if(mDistanceY < minDistanceY){
                    mDistanceY = minDistanceY;
                }
//                    LogUtil.e(TAG, "maxDistanceY = " + maxDistanceY + " , minDistanceY = " + minDistanceY
//                        + " , tempY = " + tempY + " , targetY = " + mDistanceY);
//                    if(mDistanceX)
                //两个均可，用To方法方便控制最大距离
                doTranslateTo(mDistanceX, mDistanceY);
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
        mLastScale = tempScale;
        mSmartTextView.smartScaleTo(focusX, focusY, tempScale, tempScale);
        mLinesView.smartScaleTo(focusX, focusY, tempScale, tempScale);
        mDoodleView.smartScaleTo(focusX, focusY, tempScale, tempScale);
    }

    private void doScale(float tempScale) {
//        LogUtil.e(TAG, "focusX = " + focusX + ", focusY = " + focusY);
        mLastScale = tempScale;
        mSmartTextView.smartScaleTo(mSmartTextView.getPivotX(), mSmartTextView.getPivotY(), tempScale, tempScale);
        mLinesView.smartScaleTo(mSmartTextView.getPivotX(), mSmartTextView.getPivotY(), tempScale, tempScale);
        mDoodleView.smartScaleTo(mSmartTextView.getPivotX(), mSmartTextView.getPivotY(), tempScale, tempScale);
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * */
    private void doTranslateTo(float translateX, float translateY) {
        //todo:wd 添加doodleView的translate，与LineView同步
        //todo:wd 感觉LineView可以只添加Y轴的偏移，保证X轴的位置不变(和doodleView同步也可以，保证了统一性)
        mDistanceX = translateX;
        mDistanceY = translateY;
        mSmartTextView.smartTranslateTo(translateX, translateY);
        mLinesView.smartTranslateTo(translateX, translateY);
        mDoodleView.smartTranslateTo(translateX, translateY);
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * todo:wd 未完成，需要同时修改disX与Y
     * */
    @Deprecated
    private void doTranslateBy(float distanceX, float distanceY) {
        mSmartTextView.smartTranslateBy(distanceX, distanceY);
        mLinesView.smartTranslateBy(distanceX, distanceY);
        mDoodleView.smartTranslateBy(distanceX, distanceY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        int width = getWidth();
//        int height = getHeight();
//        ViewGroup.LayoutParams layoutParams = mLinesView.getLayoutParams();
//        layoutParams.width = 3 * width;
//        mLinesView.setLayoutParams(layoutParams);
//        mLinesView.scrollTo(width, 0);
//        LogUtil.e(TAG, "onMeasure, width = " + width + " , height = " + height);
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
        //写字板模式屏蔽缩放
//        if(mViewMode == MODE_TEXT){
//            return super.onTouchEvent(event);
//        }
        boolean result = mGestureDetector.onTouchEvent(event);
        return result ? true : super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        LogUtil.e(TAG, "onInterceptTouchEvent == " + event.toString());
        return super.onInterceptTouchEvent(event);
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
                doTranslateTo(0,0);
                doScale(1);
                showSoftKeyboard();
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

    private void showSoftKeyboard() {
        InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) manager.showSoftInput(mSmartTextView, 0);
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


    /**
     * 设置textview字号
     * */
    public void setTextViewSize(float size) {
        setTextViewSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTextViewSize(int unit, float size) {
        mSmartTextView.setTextSize(unit, size);
    }

    /**
     * 设置textview文字颜色
     * */
    public void setTextViewColor(int color){
        mSmartTextView.setTextColor(color);
    }

    /**
     * 设置画笔粗细
     * */
    public void setDoodlePaintSize(float paintSize){
        mDoodleView.setPaintSize(paintSize);
    }

    /**
     * 设置画笔颜色
     * */
    public void setDoodlePaintColor(int color){
        mDoodleView.setPaintColor(color);
    }

    /**
     * OperationPresenter.MODE_ERASER  ==>橡皮擦
     * OperationPresenter.MODE_DOODLE  ==>涂鸦
     * */
    public void setDoodleMode(@EditMode int doodleEditMode){
        mDoodleView.setPaintEditMode(doodleEditMode);
    }

    public void setDoodleMode(@EditMode int doodleEditMode, float paintSize){
        mDoodleView.setPaintEditMode(doodleEditMode);
        setDoodlePaintSize(paintSize);
    }

    public void setDoodlePaint(float paintSize, int color){
        setDoodlePaintSize(paintSize);
        setDoodlePaintColor(color);
    }

    /**
     * 撤销最后一笔
     * */
    public boolean cancelLastDraw(){
        return mDoodleView.cancelLastDraw();
    }

    /**
     * 反撤销最后一笔
     * */
    public boolean redoLastDraw(){
        return mDoodleView.redoLastDraw();
    }

    public void setOnPathChangedListener(DoodleView.OnPathChangedListener listener){
        mDoodleView.setOnPathChangedListener(listener);
    }

    public DoodleView.OnPathChangedListener getOnPathChangedListener(){
        return mDoodleView.getOnPathChangedListener();
    }

    //=========================================
    public void test() {
//        Matrix matrix1 = mLinesView.getMatrix();
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
        int width = v.getWidth();
        int height = v.getHeight();
        Matrix matrix = v.getMatrix();
        LogUtil.e(TAG, v.getClass().getSimpleName() + " == scale = " + scaleX + ", translationX = " + translationX + " , translationY = " + translationY);
        LogUtil.e(TAG, v.getClass().getSimpleName() + " pivotX = " + pivotX + ", pivotY = " + pivotY + " , width = " + width + " , height = " + height);
        LogUtil.e(TAG, v.getClass().getSimpleName() + " matrix = " + matrix);
    }
}
