package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;

/**
 * @ClassName: TestTextView
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/14
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/14
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TestTextView extends EditText {
    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    
    public TestTextView(Context context) {
        super(context);
        initView();
    }

 

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
//        setFallbackLineSpacing(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            soluteLineHeightMethod1();
//            soluteLineHeightMethod2();
        }
    }

    private void soluteLineHeightMethod1() {
        setFallbackLineSpacing(false);
    }

    private void soluteLineHeightMethod2() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //...
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //...
            }

            @Override
            public void afterTextChanged(Editable s) {
                //...
                if(!isFallbackLineSpacing()){
                    return;
                }
                removeTextChangedListener(this);

                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                setText(s);
                setSelection(selectionStart, selectionEnd);

                addTextChangedListener(this);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#FF0000"));
        ViewParent parent = getParent();
        int lineCount = getLineCount();
        float textHeight = getLineHeight();
        int paddingTop = getPaddingTop();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int width = getWidth();
        int height = 0;
        if(parent instanceof ViewGroup){
            ViewGroup tempParent = (ViewGroup) parent;
            height = tempParent.getHeight();
        }

        int totalHeight = paddingTop;
        //base Line
        canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        Rect rect = new Rect();
        Layout layout = getLayout();
        for(int x = 0; x <= lineCount - 1; x++){
            getLineBounds(x, rect);
            totalHeight = rect.bottom;
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
        while (totalHeight <= height - textHeight){
            totalHeight += textHeight;
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
    }
}
