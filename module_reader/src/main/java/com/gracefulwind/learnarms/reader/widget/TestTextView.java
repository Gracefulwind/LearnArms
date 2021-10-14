package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
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
    }

    public TestTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#FF0000"));
        //todo:wd 这里是凑巧等于-1还是系统设置的-1?
//        float textSize = getTextSize();
//        float textHeight = getFontHeight(textSize) - 1;
        ViewParent parent = getParent();
        int lineCount = getLineCount();
        float textHeight = getLineHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
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
//            totalHeight = mSmartTextview.getLineBounds(x, rect);
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
        while (totalHeight <= height - textHeight){
            totalHeight += textHeight;
            canvas.drawLine(paddingLeft, totalHeight, width - paddingRight, totalHeight, paint);
        }
    }
}
