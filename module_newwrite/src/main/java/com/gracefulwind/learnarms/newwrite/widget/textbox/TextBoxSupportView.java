package com.gracefulwind.learnarms.newwrite.widget.textbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.newwrite.widget.Smartable;


/**
 * @ClassName: TextBoxView
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/25
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/25
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TextBoxSupportView extends View implements Smartable {
    public static final String TAG = "TextBoxView";
    private TextBoxContainer mParent;
    private Context mContext;

    public TextBoxSupportView(Context context, TextBoxContainer parent) {
        this(context, parent, null);
    }

    public TextBoxSupportView(Context context, TextBoxContainer parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, parent, attrs, 0);
    }

    public TextBoxSupportView(Context context, TextBoxContainer parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, parent, attrs, defStyleAttr, 0);
    }

    public TextBoxSupportView(Context context, TextBoxContainer parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, parent);
    }

    private void initView(Context context, TextBoxContainer parent) {
        mContext = context;
        mParent = parent;
        createPathAndPaint();
    }

//==================================================================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return doEditTouchEvent(event);
    }

    float mPrevX, mPrevY, mMovedX, mMovedY;
    boolean drawTextLine = false;
    Paint mPaint;
    private boolean doEditTouchEvent(MotionEvent event) {
        if(!isEnabled()){
            return false;
        }
        mParent.clearFocus();
        //非电容笔则不处理
        int actionIndex = event.getActionIndex();
        int toolType = event.getToolType(actionIndex);
        if(toolType != MotionEvent.TOOL_TYPE_STYLUS){
            return false;
        }
        ViewParent realParent = getRealParent();
        if(realParent instanceof SmartHandNote){
            realParent.requestDisallowInterceptTouchEvent(true);
        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = x;
                mPrevY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                drawTextLine = true;
                //todo:wd 限制下在view边界内
                int width = getWidth();
                int height = getHeight();
                if(x > width){
                    x = width;
                }
                if(x < 0){
                    x = 0;
                }
                if(y > height){
                    y = height;
                }
                if(y < 0){
                    y = 0;
                }
//                LogUtil.e(TAG, "ACTION_MOVE  x = " + x + " , y = " + y);
                mMovedX = x;
                mMovedY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //todo:wd 1.clear图形; 2.添加editText
                mMovedX = x;
                mMovedY = y;
                drawTextLine = false;
                if(null != mParent){
                    mParent.addTextBox(mPrevX, mPrevY, mMovedX, mMovedY);
                }
//                LogUtil.e(TAG, "ACTION_UP  x = " + x + " , y = " + y);
                invalidate();
                break;
        }
        return true;
    }

    private void createPathAndPaint() {
//        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(2f);
        mPaint.setColor(Color.parseColor("#FFFF0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.e(TAG, "onDraw");
        if(drawTextLine){
//            LogUtil.e(TAG, "onDraw : " + mPrevX + "," + mPrevY + " ==> " + mMovedX + "," + mMovedY);
            canvas.drawLine(mPrevX, mPrevY, mMovedX, mPrevY, mPaint);
            canvas.drawLine(mMovedX, mPrevY, mMovedX, mMovedY, mPaint);
            canvas.drawLine(mMovedX, mMovedY, mPrevX, mMovedY, mPaint);
            canvas.drawLine(mPrevX, mMovedY, mPrevX, mPrevY, mPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup parent = (ViewGroup) getRealParent();
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

//===================================================================================================
//    @Override
//    public void smartTranslateTo(float translateX, float translateY) {
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

    @Override
    public ViewParent getRealParent() {
        ViewParent parent = getParent();
        if(parent instanceof TextBoxContainer){
            TextBoxContainer boxPrent = (TextBoxContainer) parent;
            return boxPrent.getRealParent();
        }else {
            return null;
        }
//        return parent.getParent();
    }

//============================================================================================


}
