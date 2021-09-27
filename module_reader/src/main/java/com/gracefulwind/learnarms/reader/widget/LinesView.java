package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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



    public LinesView(Context context) {
        this(context, null);
    }

    public LinesView(Context context, int lineHeight) {
        this(context);
        mLineHeight = lineHeight;
    }

    public LinesView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinesView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

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
            int textViewWidth = parentView.getTextViewWidth();
            int textViewHeight = parentView.getTextViewHeight();
            int baseWidth = getWidth();
            int baseHeight = getHeight();
            float maxScaleRate = parentView.getMaxScaleRate();
            //宽
            if ((parentWidth * maxScaleRate) != baseWidth) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = (int) (parentWidth * maxScaleRate);
                setLayoutParams(layoutParams);
                setTranslationX(-parentWidth);
            }
            //高
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if(textViewHeight > parentHeight && baseHeight != textViewHeight){
                layoutParams.height = textViewHeight;
            }else {
                layoutParams.height = parentHeight;
            }
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
        float textHeight = getLineHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int width = getWidth();
        int height = getHeight();
//        LogUtil.e(TAG, "====onDraw====  , height = " + height + " , width = " + width + ", textHeight = " + textHeight);
//        LogUtil.d(TAG, "this w = " + this.width + " & h = " + this.height + "\r\n get w = " + width + " & h = " + height);
//        for(int x = 0; (paddingTop + paddingBottom + (x * textHeight)) < height; x++){
        for(int x = 0; (paddingTop + paddingBottom + (x * textHeight)) < this.height; x++){
//            //如果我绘制的超出原来的大小，视图会变大
//        for(int x = 0; (paddingTop + paddingBottom + ((x-2) * textHeight)) < this.height; x++){
//        for(int x = 0; x < textViewLines; x++){
            canvas.drawLine(paddingLeft, paddingTop + x * textHeight, width - paddingRight, paddingTop + x * textHeight, paint);
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

    @Override
    public void setViewHeightWithTextView(int textviewHeight) {
        int height = getHeight();
        if(textviewHeight > height){
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = textviewHeight;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    public void smartTranslateTo(float translateX, float translateY) {
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
        ViewParent parent = getParent();
        if(parent instanceof SmartHandNoteView){
            SmartHandNoteView parentView = (SmartHandNoteView) parent;
            int parentWidth = parentView.getWidth();
            setPivotX(parentWidth + pivotX);
        }else {
            setPivotX(pivotX);
        }
        setPivotY(pivotY);
        setScaleX(scaleX);
        setScaleY(scaleY);
    }
}
