package com.gracefulwind.learnarms.newwrite.widget.textbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.newwrite.widget.Smartable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @ClassName: TextBoxContainer
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/26
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/26
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TextBoxContainer extends FrameLayout implements Smartable {

    private Context mContext;
//    private SmartHandNoteView mParent;
    private TextBoxSupportView textBoxView;
    //回头删了吧。。。
    private int width;
    private int height;

    private TextBoxManager mTextBoxManager;

    public TextBoxContainer(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        mContext = context;
//        mParent = (SmartHandNoteView) getRealParent();
        textBoxView = new TextBoxSupportView(mContext, this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textBoxView.setLayoutParams(layoutParams);
        addView(textBoxView);
        mTextBoxManager = new TextBoxManager(mContext, this, textBoxView);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup parent = (ViewGroup) getParent();
        if(parent instanceof SmartHandNoteView){
            SmartHandNoteView parentView = (SmartHandNoteView) parent;
            int parentHeight = parentView.getHeight();
            int textViewHeight = parentView.getTextViewHeight();
            int baseHeight = getHeight();
            //高
            int myHeight = Math.max(Math.max(textViewHeight, parentHeight), baseHeight);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = myHeight;
            setLayoutParams(layoutParams);
        }
    }

    //本身似乎不用touch事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //编辑模式不再向上传递事件
//        if (isEnabled()){
//            return true;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //非编辑模式拦截掉子类点击事件
        if (!isEnabled()){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mTextBoxManager.setEnable(enabled);
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
        super.scrollTo(x, y);
    }

    @Override
    public ViewParent getRealParent() {
        return getParent().getParent();
    }

    public void addTextBox(float prevX, float prevY, float movedX, float movedY) {
        mTextBoxManager.addTextBox(prevX, prevY, movedX, movedY);
    }

    public void test() {
        mTextBoxManager.test();
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Constants.bitmapQuality);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        return bitmap;
    }

    public List<TextBoxBean> getTextBoxContain() {
        return mTextBoxManager.getTextBoxContain();
    }

    @Override
    public void clearFocus(){
        super.clearFocus();
        mTextBoxManager.clearFocus();
    }
}
