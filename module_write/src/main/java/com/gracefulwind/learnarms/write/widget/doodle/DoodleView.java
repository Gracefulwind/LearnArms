package com.gracefulwind.learnarms.write.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;


import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.write.widget.Smartable;

import java.util.List;

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
public class DoodleView extends View implements Smartable {
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
        mPresenter = new OperationPresenter(this);
    }

//==================================================================================================
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPresenter.createCacheBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
        mPresenter.createHoldBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
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
//                LogUtil.e("Doodle TouchEvent", "down x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
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
                if(parent instanceof SmartHandNoteView){
                    SmartHandNoteView parentView = (SmartHandNoteView) parent;
                    int lineHeight = parentView.getLineHeight();
                    if(y >= height - responseLineNumber * lineHeight){
                        parentView.changeBackgroundHeight(height + expandLineNumber * lineHeight);
                    }
                }

//                LogUtil.e("Doodle TouchEvent", "move x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
            case MotionEvent.ACTION_UP:
                mPresenter.actionUp(x, y);
//                LogUtil.e("Doodle TouchEvent", "up x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent instanceof SmartHandNoteView) {
            SmartHandNoteView parentView = (SmartHandNoteView) parent;
            int parentWidth = parentView.getWidth();
            int parentHeight = parentView.getHeight();
            int textViewHeight = parentView.getTextViewHeight();
            int baseWidth = getWidth();
            int baseHeight = getHeight();
//            LogUtil.e(TAG, "baseHeight = " + baseHeight + " , parentHeight = " + parentHeight);
//            float maxScaleRate = parentView.getMaxScaleRate();
//            //宽
//            if ((parentWidth * maxScaleRate) != baseWidth) {
//                ViewGroup.LayoutParams layoutParams = getLayoutParams();
//                layoutParams.width = (int) (parentWidth * maxScaleRate);
//                setLayoutParams(layoutParams);
//                setTranslationX(((maxScaleRate - 1) / 2) * -parentWidth);
//            }
            //高
            int myHeight = Math.max(Math.max(textViewHeight, parentHeight), baseHeight);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
//            if(textViewHeight >= parentHeight){
//                layoutParams.height = textViewHeight;
//            }else {
//                layoutParams.height = (int) (parentHeight);
//            }
            layoutParams.height = myHeight;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleX = getScaleX();
        float scaleY = getScaleY();
//        LogUtil.e(TAG, "onDraw, scaleX = " + scaleX + ", scaleY = " + scaleY);
        mPresenter.drawCanvas(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPresenter.changeSize(w, h, oldw, oldh);
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

    public boolean isModeScale(){
        return mPresenter.isModeDoodle();
    }

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
     * */
    public OperationPresenter getViewPresenter(){
        return mPresenter;
    }

    public void setOnPathChangedListener(OnPathChangedListener listener){
        mPresenter.setOnPathChangedListener(listener);
    }

    public OnPathChangedListener getOnPathChangedListener(){
        return mPresenter.getOnPathChangedListener();
    }

    public Bitmap saveAsBitmap(){
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
//        canvas.drawColor(Color.WHITE);
        draw(canvas);
        return bmp;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        float x = event.getX();
//        float y = event.getY();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                LogUtil.e("Doodle TouchEvent", "dispatch down x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
//                break;
//            case MotionEvent.ACTION_MOVE:
//                LogUtil.e("Doodle TouchEvent", "dispatch move x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
//                break;
//            case MotionEvent.ACTION_UP:
//                LogUtil.e("Doodle TouchEvent", "dispatch up x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
//                break;
//        }
////        if(isEnabled()){
////            return true;
////        }else {
////            return super.dispatchTouchEvent(event);
////        }
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

    //    @Override
//    public void setEnabled(boolean enabled) {
//        if(enabled){
//            requestDisall
//        }
//        super.setEnabled(enabled);
//    }
    public interface OnPathChangedListener{
        void onCancelListChanged(List<Operation> list);
        void onRedoListChanged(List<Operation> list);
    }
}
