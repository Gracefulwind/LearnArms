package com.gracefulwind.learnarms.newwrite.widget.edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.Smartable;


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
public class LinesView extends View implements Smartable {
    public static final String TAG = "LinesView";

    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);


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
//        mSmartTextview = bindTextview;
//        mLineHeight = bindTextview.getLineHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //layout时已完成measure
        ViewGroup parent = (ViewGroup) getRealParent();
        if(parent instanceof SmartHandNote){
            SmartHandNote parentView = (SmartHandNote) parent;
            int parentHeight = parent.getHeight();
            int textViewHeight = parentView.getTextViewHeight();
            int baseHeight = getHeight();
            //高
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            int comparedHeight = Math.max(Math.max(textViewHeight, parentHeight), baseHeight);
            layoutParams.height = comparedHeight;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        ViewGroup parentView = (ViewGroup) getRealParent();
        //父类未实现smart则不需要划线了
        if(!(parentView instanceof SmartHandNote)){
            return;
        }
        SmartHandNote smartView = (SmartHandNote) parentView;
        int lineCount = smartView.getLineCount();
        float textHeight = getLineHeight();
        //获取不到行高就别画了
        if(textHeight <= 0){
            return;
        }
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


    public int getLineHeight(){
        ViewParent parent = getRealParent();
        int lineHeight = 0;
        if(parent instanceof SmartHandNote){
            SmartHandNote tempParent = (SmartHandNote) parent;
            lineHeight = tempParent.getLineHeight();
        }
        return lineHeight;
    }

    @Override
    public ViewParent getRealParent() {
        return getParent().getParent();
    }

    @Override
    public void scrollTo(int x, int y) {
//        ViewParent realParent = getRealParent();
//        if(realParent instanceof SmartHandNote){
//            SmartHandNote smartView = (SmartHandNote) realParent;
//            smartView.smartScrollTo(x, y, this);
//        }else {
//            super.scrollTo(x, y);
//        }
    }

    @Override
    public void smartScrollTo(int x, int y){
//        super.scrollTo(x, y);
    }


//    /**
//     * 满足在handNote中的使用，总大小是textview比例系数的需求
//     * */
//    @Override
//    public void setPivotX(float pivotX){
////        ViewParent parent = getRealParent();
////        if(parent instanceof SmartHandNoteView){
////            SmartHandNoteView parentView = (SmartHandNoteView) parent;
////            int parentWidth = parentView.getWidth();
////            float maxScaleRate = parentView.getMaxScaleRate();
////            super.setPivotX(((maxScaleRate - 1) / 2) * parentWidth + pivotX);
////        }else {
////            super.setPivotX(pivotX);
////        }
//        super.setPivotX(pivotX);
//    }
//
//    @Override
//    public void smartTranslateTo(float translateX, float translateY) {
////        ViewParent parent = getRealParent();
////        if(parent instanceof SmartHandNoteView){
////            SmartHandNoteView parentView = (SmartHandNoteView) parent;
////            float maxScaleRate = parentView.getMaxScaleRate();
////            float dWidth = parentView.getWidth() * ((maxScaleRate - 1) / 2);
////            setTranslationX(-dWidth + translateX);
////        }else {
////            setTranslationX(translateX);
////        }
//        setTranslationX(translateX);
//        setTranslationY(translateY);
//    }
//
//    @Override
//    public void smartTranslateBy(float dX, float dY) {
//        setTranslationX(getTranslationX() + dX);
//        setTranslationY(getTranslationY() + dY);
//    }
//
//    @Override
//    public void smartScaleTo(float pivotX, float pivotY, float scaleX, float scaleY) {
//        setPivotX(pivotX);
//        setPivotY(pivotY);
//        setScaleX(scaleX);
//        setScaleY(scaleY);
//    }


}
