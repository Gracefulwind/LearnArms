package com.gracefulwind.learnarms.write.widget;

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
import androidx.core.content.ContextCompat;

import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.widget.edit.SmartTextView;


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

//    private int mLineHeight;
//    private SmartTextView mSmartTextview;


//    public LinesView(Context context) {
//        this(context, null);
//    }

    public LinesView(Context context) {
        this(context, null);
    }

    public LinesView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinesView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
//        paint.setColor(ContextCompat.getColor(context, R.color.note_line));
        paint.setStrokeWidth(2f);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
        if(parent instanceof SmartHandNote){
            SmartHandNote parentView = (SmartHandNote) parent;
            int parentHeight = parent.getHeight();
            int textViewHeight = parentView.getTextViewHeight();
            int baseHeight = getHeight();
            //高
            int myHeight = Math.max(Math.max(textViewHeight, parentHeight), baseHeight);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = myHeight;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ViewParent parent = getParent();
        if(!(parent instanceof SmartHandNote)){
            return;
        }
        SmartHandNote smartView = (SmartHandNote) parent;
        int lineCount = smartView.getLineCount();
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
            smartView.getLineBounds(x, rect);
            totalHeight = rect.bottom;
//            totalHeight = mSmartTextview.getLineBounds(x, rect);
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
        while (totalHeight <= height - textHeight){
            totalHeight += textHeight;
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }

    }

//    private boolean tryToInit() {
//        ViewParent parent = getParent();
//        if(parent instanceof SmartHandNote){
//            SmartHandNoteView tempParent = (SmartHandNoteView) parent;
//            mSmartTextview = tempParent.getTextView();
//            mLineHeight = mSmartTextview.getLineHeight();
//            return true;
//        }else {
//            return false;
//        }
//    }

    public int getLineHeight(){
        ViewParent parent = getParent();
        int lineHeight = 0;
        if (parent instanceof SmartHandNote) {
            SmartHandNote tempParent = (SmartHandNote) parent;
            lineHeight = tempParent.getLineHeight();
        }
        return lineHeight;
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
