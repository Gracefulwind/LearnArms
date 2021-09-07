package com.gracefulwind.learnarms.reader.widget.smart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

/**
 * @ClassName: SmartTextView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/2
 * @Description: ---------------------------
 * 画上分割线，添加富文本图案。所有的手写字都是富文本的形式
 * @UpdateUser:
 * @UpdateDate: 2021/9/2
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartTextView extends android.support.v7.widget.AppCompatTextView {
    public static final String TAG = SmartTextView.class.getName();

    private final TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;

    public SmartTextView(Context context) {
        this(context, null);
    }

    public SmartTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842884);

    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean getFreezesText() {
        return true;
    }

    @Override
    protected boolean getDefaultEditable() {
        return true;
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return ArrowKeyMovementMethod.getInstance();
    }

    @Override
    public Editable getText() {
        CharSequence text = super.getText();
        // This can only happen during construction.
        if (text == null) {
            return null;
        }
        if (text instanceof Editable) {
            return (Editable) super.getText();
        }
        super.setText(text, BufferType.EDITABLE);
        return (Editable) super.getText();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
//        Spanned spanned = Html.fromHtml(text.toString());
//        super.setText(spanned, BufferType.EDITABLE);
        super.setText(text, BufferType.EDITABLE);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int, int)}.
     */
    public void setSelection(int start, int stop) {
        Selection.setSelection(getText(), start, stop);
    }

    /**
     * Convenience for {@link Selection#setSelection(Spannable, int)}.
     */
    public void setSelection(int index) {
        Selection.setSelection(getText(), index);
    }

    /**
     * Convenience for {@link Selection#selectAll}.
     */
    public void selectAll() {
        Selection.selectAll(getText());
    }

    /**
     * Convenience for {@link Selection#extendSelection}.
     */
    public void extendSelection(int index) {
        Selection.extendSelection(getText(), index);
    }

    /**
     * Causes words in the text that are longer than the view's width to be ellipsized instead of
     * broken in the middle. {@link TextUtils.TruncateAt#MARQUEE
     * TextUtils.TruncateAt#MARQUEE} is not supported.
     *
     * @param ellipsis Type of ellipsis to be applied.
     * @throws IllegalArgumentException When the value of <code>ellipsis</code> parameter is
     *      {@link TextUtils.TruncateAt#MARQUEE}.
     * @see TextView#setEllipsize(TextUtils.TruncateAt)
     *
     * 文字过长的特效，避免跑马灯
     */
    @Override
    public void setEllipsize(TextUtils.TruncateAt ellipsis) {
        if (ellipsis == TextUtils.TruncateAt.MARQUEE) {
            throw new IllegalArgumentException("EditText cannot use the ellipsize mode "
                    + "TextUtils.TruncateAt.MARQUEE");
        }
        super.setEllipsize(ellipsis);
    }

    @Override
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
    }

    /**
     * 继承自View的方法，只是把类名返回去而已
     * */
    @Override
    public CharSequence getAccessibilityClassName() {
//        //define in view
//        super.getAccessibilityClassName();
        return TAG;
    }

//    supportsAutoSizeText

//    @Override
//    protected  boolean setAutoSizeTextTypeWithDefaults(){
//        super.setAutoSizeTextTypeWithDefaults();
//        return false;
//    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.d(TAG, "onSizeChanged, { old w,h = " + oldw + "," + oldh
                    + " }        { new w,h = " +  w + "," + h);
        this.width = w;
        this.height = h;
        int lineHeight = getLineHeight();
        float fontHeight = getFontHeight(getTextSize());
        LogUtil.d(TAG, "lineHeight = " + lineHeight + ",  fontHeight = " + fontHeight);
//        if(h - oldh == lineHeight){
//            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            layoutParams.height += 2 * lineHeight;
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //do my works
        paint.setStyle(Paint.Style.FILL);
        //todo:wd 这里是凑巧等于-1还是系统设置的-1?
//        float textSize = getTextSize();
//        float textHeight = getFontHeight(textSize) - 1;
        float textHeight = getLineHeight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        LogUtil.d(TAG, "====onDraw====");
////        int textViewLines = getTextViewLines(width);
//        int height = getHeight();
//        int measuredHeight = getMeasuredHeight();
//        int lineHeight = getLineHeight();
//        for(int x = 0; (paddingTop + paddingBottom + (x * textHeight)) < this.height; x++){
            //如果我绘制的超出原来的大小，视图会变大
        for(int x = 0; (paddingTop + paddingBottom + ((x-2) * textHeight)) < this.height; x++){
//        for(int x = 0; x < textViewLines; x++){
            canvas.drawLine(paddingLeft, paddingTop + x * textHeight, width - paddingRight, paddingTop + x * textHeight, paint);
        }
        
        LogUtil.d(TAG, "==============");
    }

    /**
     * 这里获取的是文字高度，和行高度有偏差
     * */
    public float getFontHeight(float fontSize)  {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }


    public int getTextViewLines(int textViewWidth) {
        int width = textViewWidth - getCompoundPaddingLeft() - getCompoundPaddingRight();
        StaticLayout staticLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            staticLayout = getStaticLayout23(width);
        } else {
            staticLayout = getStaticLayout(width);
        }
        int lines = staticLayout.getLineCount();
        int maxLines = getMaxLines();
        if (maxLines > lines) {
            return lines;
        }
        return maxLines;
    }

    /**
     * sdk>=23
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private StaticLayout getStaticLayout23(int width) {
        StaticLayout.Builder builder = StaticLayout.Builder.obtain(getText(),
                0, getText().length(), getPaint(), width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
                .setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier())
                .setIncludePad(getIncludeFontPadding())
                .setBreakStrategy(getBreakStrategy())
                .setHyphenationFrequency(getHyphenationFrequency())
                .setMaxLines(getMaxLines() == -1 ? Integer.MAX_VALUE : getMaxLines());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setJustificationMode(getJustificationMode());
        }
        if (getEllipsize() != null && getKeyListener() == null) {
            builder.setEllipsize(getEllipsize())
                    .setEllipsizedWidth(width);
        }
        return builder.build();
    }

    /**
     * sdk<23
     */
    private StaticLayout getStaticLayout(int width) {
        return new StaticLayout(getText(),
                0, getText().length(),
                getPaint(), width, Layout.Alignment.ALIGN_NORMAL,
                getLineSpacingMultiplier(),
                getLineSpacingExtra(), getIncludeFontPadding(), getEllipsize(),
                width);
    }
}