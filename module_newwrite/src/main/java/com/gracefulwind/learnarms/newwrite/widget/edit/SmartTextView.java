package com.gracefulwind.learnarms.newwrite.widget.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
//import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.Smartable;

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
public class SmartTextView extends TextView implements Smartable {
    public static final String TAG = SmartTextView.class.getName();

    private Context mContext;
    private  boolean mNeedLines = false;
    private TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private int width;
    private int height;
    private OnSizeChangeListener mOnSizeChangeListener;

    public SmartTextView(Context context) {
        super(context);
        initView(context);

    }

    public SmartTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SmartTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setFocusableInTouchMode(true);
        //android9和10的行高问题的解决暂时解决方案
        //会造成开销，最好还是想办法把中英文的行高固定下来(降低中文行高)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            soluteLineHeightMethod1();
//            soluteLineHeightMethod2();
        }
        //限制下高度
        post(new Runnable() {
            @Override
            public void run() {
                int width = getWidth();
                int maxLines = (int) ((width * Constants.a4Ratio) / getLineHeight());
                setMaxLines(maxLines);
//                int height = getHeight();
                LogUtil.e(TAG, "width = " + width + " , height = " + height);
            }
        });
    }

//    @Override
//    public void scrollTo(int x, int y) {
//        super.scrollTo(x, y);
//    }

    @Override
    public void scrollTo(int x, int y) {
        ViewParent realParent = getRealParent();
        if(realParent instanceof SmartHandNote){
            SmartHandNote smartView = (SmartHandNote) realParent;
            smartView.smartScrollTo(x, y, this);
        }else {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void smartScrollTo(int x, int y){
        super.scrollTo(x, y);
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
//        return TAG;
        return EditText.class.getName();
    }

//    /** @hide */
//    protected boolean supportsAutoSizeText() {
//        return false;
//    }
//
//    /** @hide */
//    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
////        super.onInitializeAccessibilityNodeInfoInternal(info);
//        if (isEnabled()) {
//            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
//        }
//    }
//    @Override
//    protected  boolean setAutoSizeTextTypeWithDefaults(){
//        super.setAutoSizeTextTypeWithDefaults();
//        return false;
//    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtil.e(TAG, "onSizeChanged, { old w,h = " + oldw + "," + oldh
                    + " }        { new w,h = " +  w + "," + h);
        this.width = w;
        this.height = h;
        if(null != mOnSizeChangeListener){
            mOnSizeChangeListener.onSizeChange(w, h, oldw, oldh);
        }
        int lineHeight = getLineHeight();
        float fontHeight = getFontHeight(getTextSize());
//        ViewParent parent = getRealParent();
//        if(parent instanceof TestFrameLayout){
//            TestFrameLayout transParent = (TestFrameLayout) parent;
//            int parentHeight= transParent.getHeight();
//            if(h > parentHeight){
//                scrollBy(0, lineHeight);
//            }
//        }
//        LogUtil.e(TAG, "lineHeight = " + lineHeight + ",  fontHeight = " + fontHeight);
//        if(h - oldh == lineHeight){
//            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            layoutParams.height += 2 * lineHeight;
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        LogUtil.e(TAG, "onDraw");
        super.onDraw(canvas);
        if(!mNeedLines){
            return;
        }
        //do my works
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#FF0000"));
        //todo:wd 这里是凑巧等于-1还是系统设置的-1?
//        float textSize = getTextSize();
//        float textHeight = getFontHeight(textSize) - 1;
        ViewParent parent = getRealParent();
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

        if(parent instanceof SmartHandNote){
            SmartHandNote tempParent = (SmartHandNote) parent;
            tempParent.refreshLineView();
        }
    }
    
//    public int getLineBounds(int line, Rect bounds) {
//        Layout mLayout = getLayout();
//        if (mLayout == null) {
//            if (bounds != null) {
//                bounds.set(0, 0, 0, 0);
//            }
//            return 0;
//        } else {
//            int baseline = mLayout.getLineBounds(line, bounds);
//
//            int voffset = getExtendedPaddingTop();
//            if ((mGravity & Gravity.VERTICAL_GRAVITY_MASK) != Gravity.TOP) {
//                voffset += getVerticalOffset(true);
//            }
//            if (bounds != null) {
//                bounds.offset(getCompoundPaddingLeft(), voffset);
//            }
//            return baseline + voffset;
//        }
//    }
//
//    int getVerticalOffset(boolean forceNormal) {
//        int voffset = 0;
//        final int gravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;
//
//        Layout l = mLayout;
//        if (!forceNormal && mText.length() == 0 && mHintLayout != null) {
//            l = mHintLayout;
//        }
//
//        if (gravity != Gravity.TOP) {
//            int boxht = getBoxHeight(l);
//            int textht = l.getHeight();
//
//            if (textht < boxht) {
//                if (gravity == Gravity.BOTTOM) {
//                    voffset = boxht - textht;
//                } else { // (gravity == Gravity.CENTER_VERTICAL)
//                    voffset = (boxht - textht) >> 1;
//                }
//            }
//        }
//        return voffset;
//    }
    
    /**
     * 这里获取的是文字高度，和行高度有偏差
     * */
    public float getFontHeight(float fontSize)  {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }


    

    /**
     * sdk>=23
     */
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private StaticLayout getStaticLayout23(int width) {
//        StaticLayout.Builder builder = StaticLayout.Builder.obtain(getText(),
//                0, getText().length(), getPaint(), width)
//                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
//                .setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_LTR)
//                .setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier())
//                .setIncludePad(getIncludeFontPadding())
//                .setBreakStrategy(getBreakStrategy())
//                .setHyphenationFrequency(getHyphenationFrequency())
//                .setMaxLines(getMaxLines() == -1 ? Integer.MAX_VALUE : getMaxLines());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            builder.setJustificationMode(getJustificationMode());
//        }
//        if (getEllipsize() != null && getKeyListener() == null) {
//            builder.setEllipsize(getEllipsize())
//                    .setEllipsizedWidth(width);
//        }
//        return builder.build();
//    }
//
//    /**
//     * sdk<23
//     */
//    private StaticLayout getStaticLayout(int width) {
//        return new StaticLayout(getText(),
//                0, getText().length(),
//                getPaint(), width, Layout.Alignment.ALIGN_NORMAL,
//                getLineSpacingMultiplier(),
//                getLineSpacingExtra(), getIncludeFontPadding(), getEllipsize(),
//                width);
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
////        int action = event.getAction();
////        float x = event.getX();
////        float y = event.getY();
////        switch (action) {
////            case MotionEvent.ACTION_DOWN:
////                LogUtil.d("Smart dispatchTouchEvent", "dispatch down x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
////                break;
////            case MotionEvent.ACTION_MOVE:
////                LogUtil.d("Smart dispatchTouchEvent", "dispatch move x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
////                break;
////            case MotionEvent.ACTION_UP:
////                LogUtil.d("Smart dispatchTouchEvent", "dispatch up x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
////                break;
////        }
//        return super.dispatchTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.e(TAG, "onTouchEvent == " + event);
        if(isEnabled()){
            boolean b = super.onTouchEvent(event);
            return b;
        }else {
            return false;
        }
    }

    public void setNeedLInes(boolean needLines){
        mNeedLines = needLines;
    }

    public boolean isNeedLines(){
        return mNeedLines;
    }

    public void setOnSizeChangedListener(OnSizeChangeListener onSizeChangeListener){
        mOnSizeChangeListener = onSizeChangeListener;
    }



//    float translateX,translateY;

//    @Override
//    public void smartTranslateTo(float translateX, float translateY) {
//        setTranslationX(translateX);
//        setTranslationY(translateY);
////        this.translateX = translateX;
////        this.translateY = translateY;
////        invalidate();
//    }
//
//    @Override
//    public void smartTranslateBy(float dX, float dY) {
//        setTranslationX(getTranslationX() + dX);
//        setTranslationY(getTranslationY() + dY);
////        this.translateX += dX;
////        this.translateY += dY;
////        invalidate();
//    }
//
//    @Override
//    public void smartScaleTo(float pivotX, float pivotY, float scaleX, float scaleY) {
//        setPivotX(pivotX);
//        setPivotY(pivotY);
//        setScaleX(scaleX);
//        setScaleY(scaleY);
//    }

    public Bitmap getBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Constants.bitmapQuality);
        Canvas canvas = new Canvas(bitmap);
        this.draw(canvas);
        return bitmap;
    }

    @Override
    public ViewParent getRealParent() {
        return getParent().getParent();
    }

    public interface OnSizeChangeListener{
        void onSizeChange(int w, int h, int oldw, int oldh);
    }
}
