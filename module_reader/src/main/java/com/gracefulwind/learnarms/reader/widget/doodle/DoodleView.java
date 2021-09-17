package com.gracefulwind.learnarms.reader.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

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
public class DoodleView extends View {
    public static final String TAG = "DoodleView";

    private OperationPresenter mPresenter;
    private ViewGroup mControlParent;

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
        if(!isEnabled()){
//            mControlParent.requestDisallowInterceptTouchEvent(false);
//            super.onTouchEvent(event);
            return false;
        }
        int actionIndex = event.getActionIndex();
        int tooltype = event.getToolType(actionIndex);
//        LogUtil.e(TAG, "actionIndex = " + actionIndex);
//        LogUtil.e(TAG, "tooltype = " + tooltype);
//        ViewParent parent = getParent();
        if(null != mControlParent){
            mControlParent.requestDisallowInterceptTouchEvent(true);
        }
        int toolType = event.getToolType(actionIndex);
        //非电容笔则不处理
        if(toolType != MotionEvent.TOOL_TYPE_STYLUS){
            return true;
        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPresenter.actionDown(x, y);
                LogUtil.e("Doodle TouchEvent", "down x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                int height = getHeight();
                int width = getWidth();
                if ((x >= 0 && x <= width) && (y >= 0 && y <= height)) {
                    mPresenter.actionMove(x, y);
                }else {
                    mPresenter.actionJump(x, y);
                }
                LogUtil.e("Doodle TouchEvent", "move x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
            case MotionEvent.ACTION_UP:
                mPresenter.actionUp(x, y);
                LogUtil.e("Doodle TouchEvent", "up x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
        }
        return true;
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
    public void cancelLastDraw(){
        mPresenter.cancelLastDraw();
    }

    /**
     * 撤回最后一次的撤销最后一笔
     * */
    public void redoLastDraw(){
        mPresenter.redoLastDraw();
    }

    public void setPaintColor(@ColorInt int paintColor){
        mPresenter.setPaintColor(paintColor);
    }

    public @ColorInt int getPaintColor(){
        return mPresenter.getPaintColor();
    }

    public void setPaintSize(int paintSize){
        mPresenter.setPaintSize(paintSize);
    }

    public int getPaintSize(){
        return mPresenter.getPaintSize();
    }

    /**
     * 将presenter暴露出去的话直接操作presenter就可以了，免去了中间操作View的过渡
     * */
    public OperationPresenter getViewPresenter(){
        return mPresenter;
    }

    public Bitmap saveAsBitmap(){
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.WHITE);
        draw(canvas);
        return bmp;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("Doodle TouchEvent", "dispatch down x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d("Doodle TouchEvent", "dispatch move x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("Doodle TouchEvent", "dispatch up x = " + x + ",  y = " + y + "w = " + getWidth() + " , h = " + getHeight());
                break;
        }
//        if(isEnabled()){
//            return true;
//        }else {
//            return super.dispatchTouchEvent(event);
//        }
        return super.dispatchTouchEvent(event);
    }

    public void setControlParent(ViewGroup viewGroup) {
        mControlParent = viewGroup;
    }

//    @Override
//    public void setEnabled(boolean enabled) {
//        if(enabled){
//            requestDisall
//        }
//        super.setEnabled(enabled);
//    }
}
