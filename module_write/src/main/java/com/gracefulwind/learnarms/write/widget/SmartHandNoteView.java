package com.gracefulwind.learnarms.write.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.KeyboardUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.write.widget.doodle.Doodle;
import com.gracefulwind.learnarms.write.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.write.widget.doodle.EditMode;
import com.gracefulwind.learnarms.write.widget.doodle.SurfaceDoodleView;
import com.gracefulwind.learnarms.write.widget.edit.SmartTextView;
import com.gracefulwind.learnarms.write.widget.textbox.TextBoxContainer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
public class SmartHandNoteView extends FrameLayout implements SmartHandNote{
    public static final String TAG = "SmartHandNoteView";
    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;
    public static final int MODE_TEXT_BOX = 0x00000003;


    public static final int TYPE_TOUCH = 0x00010001;
    public static final int TYPE_INTERCEPT = 0x00010002;


    static final int ANIMATED_SCROLL_GAP = 250;

    private Context mContext;
    private int mViewMode;
    private List<Smartable> smartViewList = new ArrayList<>();
    private LinesView mLinesView;
    private SmartTextView mSmartTextView;
    private DoodleView mDoodleView;
//    private TextBoxView mTextBoxView;
    private TextBoxContainer mTextBoxContainer;

    private TouchGestureDetector mGestureDetector;
    private TouchGestureDetector mInterceptDetector;
    //最大缩放比例,最好别动
//    float maxScaleRate = 2f;
    float maxScaleRate = 1f;
    //最小响应缩放比例,最好别动
    float minScaleResponse = 0.1f;
    //最小响应滑动距离(的平方)
    float minDisResponse = 3 * 3;
    float mLastScale = 1f;
    float mDistanceX = 0f, mDistanceY = 0f;
    private float focusX;
    private float focusY;
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
//        setFocusable(true);
//        setFocusableInTouchMode(true);
        createChildren();
        mScroller = new OverScroller(getContext());
        initGesture();
        //init viewMode
        setViewMode(MODE_TEXT);
        post(new Runnable() {
            @Override
            public void run() {
                for (Smartable smart : smartViewList) {
                    if(smart instanceof View){
                        View v = (View) smart;
                        KeyboardUtil.hideSoftKeyboard(mContext, v);
                    }
                }
            }
        });
    }

    private void createChildren() {
        //add smartTextView
        mSmartTextView = new SmartTextView(mContext);
        LayoutParams stvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mSmartTextView.setLayoutParams(stvLayoutParams);
        //add linesView
        mLinesView = new LinesView(mContext);
        LayoutParams lvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mLinesView.setLayoutParams(lvLayoutParams);
        //add doodleView
        mDoodleView = new DoodleView(mContext);
//        mDoodleView = new SurfaceDoodleView(mContext);
        LayoutParams dvLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mDoodleView.setLayoutParams(dvLayoutParams);
        //add textBoxView
//        mTextBoxView = new TextBoxView(mContext, this);
        mTextBoxContainer = new TextBoxContainer(mContext, this);
//        mSmartTextView.getLineHeight()
        mSmartTextView.setOnSizeChangedListener(new SmartTextView.OnSizeChangeListener() {
            @Override
            public void onSizeChange(int w, int h, int oldw, int oldh) {
                int thisHeight = getHeight();
                if(h > thisHeight){
                    changeBackgroundHeight(h);
                }else {
                    changeBackgroundHeight((int) (thisHeight));
                }
//                mLinesView.setViewHeightWithTextView(h);
//                mDoodleView.setViewHeightWithTextView(h);
            }
        });
        smartViewList.add(mLinesView);
        addView(mLinesView);
        smartViewList.add(mSmartTextView);
        addView(mSmartTextView);
        smartViewList.add(mDoodleView);
        addView(mDoodleView);
//        smartViewList.add(mTextBoxView);
//        addView(mTextBoxView);
        smartViewList.add(mTextBoxContainer);
        addView(mTextBoxContainer);
//        mLinesView.setVisibility(GONE);
        //=====test======
        mSmartTextView.setBackgroundColor(Color.parseColor("#A003aF30"));
//        mLinesView.setBackgroundColor(Color.parseColor("#A0a03030"));
////        mDoodleView.setBackgroundColor(Color.parseColor("#083030F0"));
////        mDoodleView.setBackgroundColor(Color.parseColor("#083030F0"));
    }

    /**
     * 初始化手势器
     * */
    private void initGesture() {
        mGestureDetector = new TouchGestureDetector(getContext(), new MyTouchControl(TYPE_TOUCH));
        mInterceptDetector = new TouchGestureDetector(getContext(), new MyTouchControl(TYPE_INTERCEPT));
    }

    public void refreshLineView() {
        mLinesView.invalidate();
    }

//    public void addTextBox(float mPrevX, float mPrevY, float mMovedX, float mMovedY) {
//        EditText editText = new EditText(mContext);
//        float startX = Math.min(mPrevX, mMovedX);
//        float startY = Math.min(mPrevY, mMovedY);
//        float width = Math.abs(mPrevX - mMovedX);
//        LayoutParams layoutParams = new LayoutParams((int) width, LayoutParams.WRAP_CONTENT);
//        editText.setLayoutParams(layoutParams);
//        layoutParams.leftMargin = (int) startX;
//        layoutParams.topMargin = (int) startY;
//        mEditViewList.add(editText);
//        addView(editText);
//    }

    class MyTouchControl extends TouchGestureDetector.OnTouchGestureListener {

        int mType;

        public MyTouchControl(int type) {
            super();
            mType = type;
        }

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
            smartScrollBy(0, 0);
        }

        @Override
        public boolean onScale(ScaleGestureDetectorApi27 detector) { // 双指缩放中
            if(mViewMode == MODE_TEXT_BOX && mType == TYPE_INTERCEPT){
                return false;
            }
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
            if(mViewMode == MODE_TEXT_BOX && mType == TYPE_INTERCEPT){
                return false;
            }
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
                smartScrollBy(realX, realY);
//                    doTranslateBy(realX, realY);
                return true;
            }
        }

        @Override
        public void onScrollEnd(MotionEvent e) {
            super.onScrollEnd(e);
        }
    }

    private void smartScrollBy(float realX, float realY) {
        mDistanceX += realX;
        mDistanceY += realY;
        float textWidth = mSmartTextView.getWidth();
        //基于缩放比例的左/右偏移量
//        float realOutWidth = ((1 - 1) / 2) * textWidth;
//                    float textHeight = mSmartTextView.getHeight();
        float lineHeight = mDoodleView.getHeight();
        float noteHeight = getHeight();
        float textPx = mSmartTextView.getPivotX();
//                    float textPy = mSmartTextView.getPivotY();
//                    float linePx = mLinesView.getPivotX();
        float linePy = mDoodleView.getPivotY();
        //控制右滑
        float minDistanceX = (textWidth - textPx) - (textWidth - textPx) * mLastScale;
        if(mDistanceX < minDistanceX){
            mDistanceX = minDistanceX;
        }
        //控制左滑
        float maxDistanceX = (textPx) * (mLastScale - 1);
        if(mDistanceX > maxDistanceX){
            mDistanceX = maxDistanceX;
        }
        //先下划
        float minDistanceY = noteHeight - linePy - (lineHeight - linePy) * mLastScale;
        if(mDistanceY < minDistanceY){
            mDistanceY = minDistanceY;
        }
        //上划
        float maxDistanceY = linePy * mLastScale - linePy;
        if(mDistanceY > maxDistanceY){
            mDistanceY = maxDistanceY;
        }

//                    LogUtil.e(TAG, "maxDistanceY = " + maxDistanceY + " , minDistanceY = " + minDistanceY
//                        + " , tempY = " + tempY + " , targetY = " + mDistanceY);
//                    if(mDistanceX)
        //两个均可，用To方法方便控制最大距离
        doTranslateTo(mDistanceX, mDistanceY);
    }

    /**
     * 写字板，涂鸦层和背景线条的缩放
     * */
    private void doScale(float focusX, float focusY, float tempScale) {
//        LogUtil.e(TAG, "focusX = " + focusX + ", focusY = " + focusY);
        mLastScale = tempScale;
        for (Smartable smartView : smartViewList) {
            smartView.smartScaleTo(focusX, focusY, tempScale, tempScale);
        }
//        mSmartTextView.smartScaleTo(focusX, focusY, tempScale, tempScale);
//        mLinesView.smartScaleTo(focusX, focusY, tempScale, tempScale);
//        mDoodleView.smartScaleTo(focusX, focusY, tempScale, tempScale);
//        mTextBoxView.smartScaleTo(focusX, focusY, tempScale, tempScale);
//        textBoxContainer.smartScaleTo(focusX, focusY, tempScale, tempScale);
    }

    private void doScale(float tempScale) {
//        LogUtil.e(TAG, "focusX = " + focusX + ", focusY = " + focusY);
        mLastScale = tempScale;
        float pivotX = mSmartTextView.getPivotX();
        float pivotY = mSmartTextView.getPivotY();
        for (Smartable smartView : smartViewList) {
            smartView.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
        }
//        mSmartTextView.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
//        mLinesView.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
//        mDoodleView.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
//        mTextBoxView.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
//        textBoxContainer.smartScaleTo(pivotX, pivotY, tempScale, tempScale);
    }

    public float getScaleRate(){
        return mLastScale;
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * */
    private void doTranslateTo(float translateX, float translateY) {
        //todo:wd 添加doodleView的translate，与LineView同步
        //todo:wd 感觉LineView可以只添加Y轴的偏移，保证X轴的位置不变(和doodleView同步也可以，保证了统一性)
        mDistanceX = translateX;
        mDistanceY = translateY;
        for (Smartable smartView : smartViewList) {
            smartView.smartTranslateTo(translateX, translateY);
        }
//        mSmartTextView.smartTranslateTo(translateX, translateY);
//        mLinesView.smartTranslateTo(translateX, translateY);
//        mDoodleView.smartTranslateTo(translateX, translateY);
//        mTextBoxView.smartTranslateTo(translateX, translateY);
//        textBoxContainer.smartTranslateTo(translateX, translateY);
    }

    /**
     * 写字板，涂鸦层和背景线条的移动
     * todo:wd 未完成，需要同时修改disX与Y
     * */
    @Deprecated
    private void doTranslateBy(float distanceX, float distanceY) {
        for (Smartable smartView : smartViewList) {
            smartView.smartTranslateBy(distanceX, distanceY);
        }
//        mSmartTextView.smartTranslateBy(distanceX, distanceY);
//        mLinesView.smartTranslateBy(distanceX, distanceY);
//        mDoodleView.smartTranslateBy(distanceX, distanceY);
//        mTextBoxView.smartTranslateBy(distanceX, distanceY);
//        textBoxContainer.smartTranslateBy(distanceX, distanceY);
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
        //处于EDIT MODE时，已屏蔽tetview，所以理论上事件都是走TouchEvent
//        if(mViewMode == MODE_TEXT_BOX){
//            return doTextBoxTouchEvent(event);
//        }
        boolean result = mGestureDetector.onTouchEvent(event);
        return result ? true : super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "onInterceptTouchEvent == " + event.toString());
//        return super.onInterceptTouchEvent(event);
        //处理但不拦截
        if(mViewMode == MODE_TEXT){
            boolean result = mInterceptDetector.onTouchEvent(event);
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
            case MODE_TEXT:
                mSmartTextView.setEnabled(true);
                mSmartTextView.requestFocus();
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                doTranslateTo(0,0);
                doScale(1);
                showSoftKeyboard();
                break;
            case MODE_DOODLE:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
            case MODE_TEXT_BOX:
                //设置文本框可编辑模式
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(true);
//                setTextBoxEnable(true);
                break;
            default:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
        }
        for (Smartable smartView : smartViewList) {
            View view = (View) smartView;
            view.invalidate();
        }
//        mSmartTextView.invalidate();
//        mLinesView.invalidate();
//        mDoodleView.invalidate();
//        mTextBoxView.invalidate();
//        mTextBoxContainer.invalidate();
    }

//    private void setTextBoxEnable(boolean enable) {
//        mTextBoxView.setEnabled(enable);
//        enableEditText(enable);
//    }
//
//    @Deprecated
//    private void enableEditText(boolean enable) {
//        for (TextView tv : mEditViewList){
//            tv.setEnabled(enable);
//        }
//    }

    private void showSoftKeyboard() {
        InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) manager.showSoftInput(mSmartTextView, 0);
    }

    public SmartTextView getTextView() {
        return mSmartTextView;
    }

    public int getLineHeight() {
        return mSmartTextView.getLineHeight();
    }

    @Override
    public int getLineCount() {
        return mSmartTextView.getLineCount();
    }

    public int getTextViewWidth() {
        return mSmartTextView.getWidth();
    }

    public int getTextViewHeight() {
        return mSmartTextView.getHeight();
    }

    @Override
    public int getLineBounds(int line, Rect bounds) {
        return mSmartTextView.getLineBounds(line, bounds);
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

    public void setOnPathChangedListener(Doodle.OnPathChangedListener listener){
        mDoodleView.setOnPathChangedListener(listener);
    }

    public Doodle.OnPathChangedListener getOnPathChangedListener(){
        return mDoodleView.getOnPathChangedListener();
    }

    //=========================================
    int lineNum = 0;
    public void test() {
//        LogUtil.e(TAG, "rect = " + rect);
//        System.out.println("========");
//        int lineBounds = mSmartTextView.getLineBounds(lineNum, rect);
//        LogUtil.e(TAG, "result rect = " + rect + " , lineBounds = " + lineBounds + " , viewHeight = " + mSmartTextView.getHeight());
//        scrollTo(0, 0);
//        mDoodleView.setZOrderMediaOverlay(true);
    }

    public void setChildPivot(float pivotX, float pivotY){
        for (Smartable smartView : smartViewList) {
            View view = (View) smartView;
            view.setPivotX(pivotX);
            view.setPivotY(pivotY);
        }
//        mLinesView.setPivotX(pivotX);
//        mLinesView.setPivotY(pivotY);
//        mDoodleView.setPivotX(pivotX);
//        mDoodleView.setPivotY(pivotY);
//        mSmartTextView.setPivotX(pivotX);
//        mSmartTextView.setPivotY(pivotY);
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

    /**
     * 涂鸦画板的文字
     * */
    public Editable getViewText() {
        return mSmartTextView.getText();
    }


    public Bitmap getDoodleBitmap() {
        return mDoodleView.getBitmap();
    }

    public Bitmap getBitmap(boolean hasLines) {
//        if(!hasLines){
//            mLinesView.setVisibility(INVISIBLE);
//        }
        //calculate default outLeft
        int doodleWidth = mDoodleView.getWidth();
        int textWidth = mSmartTextView.getWidth();
        int leftOutWidth = (doodleWidth - textWidth) / 2;
        //create bitmap
        Bitmap bitmap = Bitmap.createBitmap(doodleWidth, mDoodleView.getHeight(), Constants.bitmapQuality);
        Canvas canvas = new Canvas(bitmap);
        //draw textView
        boolean textEnable = mSmartTextView.isEnabled();
        mSmartTextView.setCursorVisible(false);
        if(!textEnable){
            mSmartTextView.setEnabled(true);
        }
        Bitmap textBitmap = mSmartTextView.getBitmap();
        //half leftWidth
        canvas.drawBitmap(textBitmap, leftOutWidth,0f,null);
        mSmartTextView.setEnabled(textEnable);
        mSmartTextView.setCursorVisible(true);
        //----
        //draw doodleView
        boolean doodleEnable = mDoodleView.isEnabled();
        if(!doodleEnable){
            mDoodleView.setEnabled(true);
        }
        Bitmap doodleBitmap = mDoodleView.getBitmap();
        canvas.drawBitmap(doodleBitmap,0f,0f,null);
        mDoodleView.setEnabled(doodleEnable);
        //draw textBox
        boolean textBoxEnable = mTextBoxContainer.isEnabled();
        if(textBoxEnable){
            mTextBoxContainer.setEnabled(false);
        }
        Bitmap textBoxBitmap = mTextBoxContainer.getBitmap();
        canvas.drawBitmap(textBoxBitmap,0f,0f,null);
        mTextBoxContainer.setEnabled(textBoxEnable);
        //==========
//        mLinesView.setVisibility(VISIBLE);
        return bitmap;
    }


    public void setText(CharSequence text){
        mSmartTextView.setText(text);
    }

    public void setBitmap(Bitmap bitmap){
        int bitmapHeight = bitmap.getHeight();
        changeBackgroundHeight(bitmapHeight);
        mDoodleView.setBitmap(bitmap);
    }

    public void changeBackgroundHeight(int height) {
        int doodleHeight = mDoodleView.getHeight();
        if(height > doodleHeight){
            for(Smartable smartView : smartViewList){
                if(smartView instanceof SmartTextView){
                    continue;
                }else {
                    View view = (View) smartView;
                    setChildHeight(view, height);
                }
            }
        }
    }

    boolean firstToastFlag = false;
    private void setChildHeight(View targetView, int height) {
        int width = targetView.getWidth();
        int maxHeight = (int) (width * Constants.a4Ratio);
        ViewGroup.LayoutParams childLayoutParams = targetView.getLayoutParams();
        if (0 == width || maxHeight > height) {
            if(childLayoutParams.height < height){
                childLayoutParams.height = height;
            }
        } else {
            childLayoutParams.height = maxHeight;
            if (!firstToastFlag) {
                firstToastFlag = true;
                Toast.makeText(mContext, "已达到最大长度！", Toast.LENGTH_SHORT).show();
            }
        }
        targetView.setLayoutParams(childLayoutParams);
    }
}
