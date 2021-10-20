package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.widget.edit.SmartTextView;

/**
 * @ClassName: LinesView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/24
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class LinesView extends View implements Smartable{
    public static final String TAG = "LinesView";

    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;

    private int mLineHeight;
    private SmartTextView mSmartTextview;


//    public LinesView(Context context) {
//        this(context, null);
//    }

    public LinesView(Context context, SmartTextView bindTextview) {
        this(context, bindTextview, null);
    }

    public LinesView(Context context, SmartTextView bindTextview, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, bindTextview, attrs, 0);
    }

    public LinesView(Context context, SmartTextView bindTextview, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, bindTextview, attrs, defStyleAttr);
    }

    private void initView(Context context, SmartTextView bindTextview, AttributeSet attrs, int defStyleAttr) {
        mSmartTextview = bindTextview;
        mLineHeight = bindTextview.getLineHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        ViewGroup parent = (ViewGroup) getParent();
//        if(parent instanceof SmartHandNoteView){
//            SmartHandNoteView view = (SmartHandNoteView) parent;
//            int width = view.getWidth();
//            int height = view.getHeight();
//            int width1 = getWidth();
//            int height1 = getHeight();
//            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            LogUtil.e(TAG, "onMeasure, parentwidth = " + width + " , parentheight = " + height
//                    + " , width = " + width1 + " , height = " + height1 + ", layoutHeight = " + layoutParams.height);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup parent = (ViewGroup) getParent();
        if(parent instanceof SmartHandNoteView){
            SmartHandNoteView parentView = (SmartHandNoteView) parent;
            int parentWidth = parentView.getWidth();
            int parentHeight = parentView.getHeight();
            int textViewHeight = parentView.getTextViewHeight();
            int baseWidth = getWidth();
            int baseHeight = getHeight();
//            LogUtil.e(TAG, "onLayout baseHeight = " + baseHeight + " , parentHeight = " + parentHeight + " , textViewHeight = " + textViewHeight);
//            float maxScaleRate = parentView.getMaxScaleRate();
//            //宽
//            if ((parentWidth * maxScaleRate) != baseWidth) {
//                ViewGroup.LayoutParams layoutParams = getLayoutParams();
//                layoutParams.width = (int) (parentWidth * maxScaleRate);
//                setLayoutParams(layoutParams);
//                setTranslationX(((maxScaleRate - 1) / 2) * -parentWidth);
//            }
            //高
            int myHeight = Math.max(Math.max(textViewHeight, parentHeight), baseHeight);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            if(textViewHeight >= parentHeight){
//                layoutParams.height = textViewHeight;
//            }else {
//                layoutParams.height = (int) (parentHeight);
//            }
            layoutParams.height = myHeight;
            setLayoutParams(layoutParams);
//            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            LogUtil.e(TAG, "onLayout, parentwidth = " + parentWidth + " , parentheight = " + parentHeight
//                    + " , width = " + width1 + " , height = " + height1 + ", layoutHeight = " + layoutParams.height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        if(null == mSmartTextview){
            if(!tryToInit()){
                //获取不到smartView直接退出好么
                return;
            }
        }
        int lineCount = mSmartTextview.getLineCount();
        float textHeight = getLineHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int width = getWidth();
        int height = getHeight();
        int totalHeight = paddingTop;
        //base Line
        canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        Rect rect = new Rect();
        for(int x = 0; x <= lineCount - 1; x++){
            mSmartTextview.getLineBounds(x, rect);
            totalHeight = rect.bottom;
//            totalHeight = mSmartTextview.getLineBounds(x, rect);
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
        while (totalHeight <= height - textHeight){
            totalHeight += textHeight;
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }

////        LogUtil.e(TAG, "====onDraw====  , height = " + height + " , width = " + width + ", textHeight = " + textHeight);
////        LogUtil.d(TAG, "this w = " + this.width + " & h = " + this.height + "\r\n get w = " + width + " & h = " + height);
////        for(int x = 0; (paddingTop + paddingBottom + (x * textHeight)) < height; x++){
//        for(int x = 0; (paddingTop + paddingBottom + (x * textHeight)) < this.height; x++){
////            //如果我绘制的超出原来的大小，视图会变大
////        for(int x = 0; (paddingTop + paddingBottom + ((x-2) * textHeight)) < this.height; x++){
////        for(int x = 0; x < textViewLines; x++){
//            canvas.drawLine(paddingLeft, paddingTop + x * textHeight, width - paddingRight, paddingTop + x * textHeight, paint);
//        }
    }

    private boolean tryToInit() {
        ViewParent parent = getParent();
        if(parent instanceof SmartHandNoteView){
            SmartHandNoteView tempParent = (SmartHandNoteView) parent;
            mSmartTextview = tempParent.getTextView();
            mLineHeight = mSmartTextview.getLineHeight();
            return true;
        }else {
            return false;
        }
    }

    public void setLineHeight(int lineHeight){
        mLineHeight = lineHeight;
    }

    public int getLineHeight(){
        ViewParent parent = getParent();
        if(parent instanceof SmartHandNoteView){
            SmartHandNoteView tempParent = (SmartHandNoteView) parent;
            mLineHeight = tempParent.getLineHeight();
        }
        return mLineHeight;
    }


    /**
     * 满足在handNote中的使用，总大小是textview比例系数的需求
     * */
    @Override
    public void setPivotX(float pivotX){
//        ViewParent parent = getParent();
//        if(parent instanceof SmartHandNoteView){
//            SmartHandNoteView parentView = (SmartHandNoteView) parent;
//            int parentWidth = parentView.getWidth();
//            float maxScaleRate = parentView.getMaxScaleRate();
//            super.setPivotX(((maxScaleRate - 1) / 2) * parentWidth + pivotX);
//        }else {
//            super.setPivotX(pivotX);
//        }
        super.setPivotX(pivotX);
    }

    @Override
    public void smartTranslateTo(float translateX, float translateY) {
//        ViewParent parent = getParent();
//        if(parent instanceof SmartHandNoteView){
//            SmartHandNoteView parentView = (SmartHandNoteView) parent;
//            float maxScaleRate = parentView.getMaxScaleRate();
//            float dWidth = parentView.getWidth() * ((maxScaleRate - 1) / 2);
//            setTranslationX(-dWidth + translateX);
//        }else {
//            setTranslationX(translateX);
//        }
        setTranslationX(translateX);
        setTranslationY(translateY);
    }

    @Override
    public void smartTranslateBy(float dX, float dY) {
        setTranslationX(getTranslationX() + dX);
        setTranslationY(getTranslationY() + dY);
    }

    @Override
    public void smartScaleTo(float pivotX, float pivotY, float scaleX, float scaleY) {
        setPivotX(pivotX);
        setPivotY(pivotY);
        setScaleX(scaleX);
        setScaleY(scaleY);
    }


}
