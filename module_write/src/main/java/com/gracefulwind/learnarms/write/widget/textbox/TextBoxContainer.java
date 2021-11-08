package com.gracefulwind.learnarms.write.widget.textbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.write.widget.Smartable;

import org.jetbrains.annotations.NotNull;

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
    private SmartHandNoteView mParent;
    private TextBoxSupportView textBoxView;
    //回头删了吧。。。
    private int width;
    private int height;

    private TextBoxManager mTextBoxManager;

    public TextBoxContainer(@NonNull @NotNull Context context, SmartHandNoteView parent) {
        this(context, parent, null);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, SmartHandNoteView parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, parent, attrs, 0);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, SmartHandNoteView parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, parent, attrs, defStyleAttr, 0);
    }

    public TextBoxContainer(@NonNull @NotNull Context context, SmartHandNoteView parent, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, parent);
    }

    private void initView(@NotNull Context context, SmartHandNoteView parent) {
        mContext = context;
        mParent = parent;
        textBoxView = new TextBoxSupportView(mContext, this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textBoxView.setLayoutParams(layoutParams);
        addView(textBoxView);
//        //todo:wd for test, delete after all
//        EditText textView = new EditText(mContext);
//        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        layoutParams1.leftMargin = 200;
//        layoutParams1.topMargin = 100;
//        textView.setLayoutParams(layoutParams1);
//        textView.setText("testtest");
//        addView(textView);
        mTextBoxManager = new TextBoxManager(mContext, this, mParent, textBoxView);
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
        if (isEnabled()){
            return true;
        }
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
        setPivotX(pivotX);
        setPivotY(pivotY);
        setScaleX(scaleX);
        setScaleY(scaleY);
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

    @Override
    public void clearFocus(){
        super.clearFocus();
        mTextBoxManager.clearFocus();
    }
}
