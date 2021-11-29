package com.gracefulwind.learnarms.newwrite.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.newwrite.widget.Smartable;

/**
 * @ClassName: DoodleView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/9
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/9
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class DoodleView extends View implements Smartable,Doodle {
    public static final String TAG = "DoodleView";

    private OperationPresenter mPresenter;
    private ViewGroup mControlParent;
    private int responseLineNumber = 2;
    private int expandLineNumber = 3;

    public DoodleView(Context context) {
        this(context, null);
    }

    public DoodleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DoodleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }
//---------------------

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        setBackgroundColor(getResources().getColor(R.color.public_color_transparent));
        mPresenter = new OperationPresenter(context, this);
    }

//==================================================================================================
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.e(TAG, String.format("onMeasure :  widthMeasureSpec = %s, heightMeasureSpec = %s", widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtil.e(TAG, String.format("onLayout :  changed = %s, left = %s, top = %s, right = %s, bottom = %s"
                , changed, left, top, right, bottom));
        ViewGroup parent = (ViewGroup) getParent();
        if (parent instanceof SmartHandNote) {
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
        mPresenter.createCacheBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
        mPresenter.createHoldBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.e(TAG, "onDraw !!!");
        super.onDraw(canvas);
        mPresenter.drawCanvas(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当doodleView处于编辑状态时，屏蔽父类事件
        int logE = event.getActionIndex();
//        LogUtil.e(TAG, "onTouchEvent event = " + event.getActionMasked() + " , toolType = " + event.getToolType(logE));
        if(!isEnabled()){
            return false;
        }
        if(null != mControlParent){
            mControlParent.requestDisallowInterceptTouchEvent(true);
        }
        int actionIndex = event.getActionIndex();
        int toolType = event.getToolType(actionIndex);
        //非电容笔则不处理
        if(toolType != MotionEvent.TOOL_TYPE_STYLUS){
            return false;
        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPresenter.actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int height = getHeight();
                int width = getWidth();
                if ((x >= 0 && x <= width) && (y >= 0 && y <= height)) {
                    mPresenter.actionMove(x, y);
                }else {
                    mPresenter.actionJump(x, y);
                }
                ViewParent parent = getParent();
                if(parent instanceof SmartHandNote){
                    SmartHandNote parentView = (SmartHandNote) parent;
                    int lineHeight = parentView.getLineHeight();
                    if(y >= height - responseLineNumber * lineHeight){
                        parentView.changeBackgroundHeight(height + expandLineNumber * lineHeight);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mPresenter.actionUp(x, y);
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtil.e(TAG, String.format("onSizeChanged :  w = %s, h = %s, oldw = %s, oldh = %s", w, h, oldw, oldh));
        super.onSizeChanged(w, h, oldw, oldh);
        if(oldw == 0 || oldh == 0){
            //
        }else {
            mPresenter.changeSize(w, h, oldw, oldh);
        }

    }

//    /**
//     * 满足在handNote中的使用，总大小是textview比例系数的需求
//     * */
//    @Override
//    public void setPivotX(float pivotX){
//        super.setPivotX(pivotX);
//    }

//    @Override
//    public void setViewHeightWithTextView(int textViewHeight) {
//        int height = getHeight();
//        ViewParent parent = getParent();
//        if (parent instanceof SmartHandNoteView) {
//            SmartHandNoteView parentView = (SmartHandNoteView) parent;
//            int parentHeight = parentView.getHeight();
//            int baseHeight = getHeight();
//            float maxScaleRate = parentView.getMaxScaleRate();
//            //高
//            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            if(textViewHeight > parentHeight * maxScaleRate){
//                layoutParams.height = textViewHeight;
//            }else {
//                layoutParams.height = (int) (parentHeight * maxScaleRate);
//            }
//            setLayoutParams(layoutParams);
//        }
//    }

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

    //====================================================================================================
    public void setPaintEditMode(@EditMode int editMode){
        mPresenter.setPaintMode(editMode);
    }

    public int getEditMode(){
        return mPresenter.getEditMode();
    }

    public boolean isModeDoodle(){
        return mPresenter.isModeDoodle();
    }

//    public boolean isModeScale(){
//        return mPresenter.isModeDoodle();
//    }

    /**
     * 撤销最后一笔
     * */
    public boolean cancelLastDraw(){
        return mPresenter.cancelLastDraw();
    }

    /**
     * 撤回最后一次的撤销最后一笔
     * */
    public boolean redoLastDraw(){
        return mPresenter.redoLastDraw();
    }

    public void setPaintColor(@ColorInt int paintColor){
        mPresenter.setPaintColor(paintColor);
    }

    public @ColorInt int getPaintColor(){
        return mPresenter.getPaintColor();
    }

    public void setPaintSize(float paintSize){
        mPresenter.setPaintSize(paintSize);
    }

    public float getPaintSize(){
        return mPresenter.getPaintSize();
    }

    /**
     * 将presenter暴露出去的话直接操作presenter就可以了，免去了中间操作View的过渡
     * 不再暴露吧，安全起见
     * */
    protected OperationPresenter getViewPresenter(){
        return mPresenter;
    }

    public void setOnPathChangedListener(OnPathChangedListener listener){
        mPresenter.setOnPathChangedListener(listener);
    }

    public OnPathChangedListener getOnPathChangedListener(){
        return mPresenter.getOnPathChangedListener();
    }
//-----===-----
    public Bitmap saveAsBitmap(){
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
//        canvas.drawColor(Color.WHITE);
        draw(canvas);
        return bmp;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    public void setControlParent(ViewGroup viewGroup) {
        mControlParent = viewGroup;
    }

    public Bitmap getBitmap() {
        return mPresenter.getBitmap();
    }

    public void setBitmap(Bitmap bitmap) {
        mPresenter.setBitmap(bitmap);
    }

    @Override
    public void refreshUi(){
        this.invalidate();
    }

    @Override
    public void initCanvas(Canvas canvas, int backgroundColor){
        canvas.drawColor(backgroundColor, PorterDuff.Mode.DST);
    }

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

    public void smartScrollTo(int x, int y){
        super.scrollTo(x, y);
    }

    @Override
    public ViewParent getRealParent() {
        return getParent().getParent();
    }
}
