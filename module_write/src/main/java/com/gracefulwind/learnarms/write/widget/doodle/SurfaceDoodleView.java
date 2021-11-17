package com.gracefulwind.learnarms.write.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.write.widget.Smartable;

import java.util.ArrayList;
import java.util.List;

import static com.gracefulwind.learnarms.write.widget.doodle.OperationPresenter.MODE_DOODLE;

/**
 * @ClassName: SurfaceDoodleView
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/16
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/16
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SurfaceDoodleView extends SurfaceView implements SurfaceHolder.Callback, Runnable, Doodle, Smartable {
    public static final String TAG = "SurfaceDoodleView";

    private OperationPresenter mPresenter;
    private ViewGroup mControlParent;
    private int responseLineNumber = 2;
    private int expandLineNumber = 3;

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Bitmap holdBitmap;
    private Canvas holdCanvas;
    private SurfaceHolder mHolder;
    private boolean isSurfaceStarted;
    private Canvas mCanvas;
    //默认模式为涂鸦
    @EditMode
    private int mEditMode = MODE_DOODLE;


    public SurfaceDoodleView(Context context) {
        this(context, null);
    }

    public SurfaceDoodleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceDoodleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SurfaceDoodleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        //使surfaceView透明
        setZOrderOnTop(true);
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSLUCENT);
        mHolder.addCallback(this);
        mPresenter = new OperationPresenter(context, this);
    }

//=================================================================================================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        cacheBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Constants.bitmapQuality);
//        cacheCanvas = new Canvas(cacheBitmap);
//        holdBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Constants.bitmapQuality);
//        holdCanvas = new Canvas(holdBitmap);

        mPresenter.createHoldBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPresenter.changeSize(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
//                actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int height = getHeight();
                int width = getWidth();
                if ((x >= 0 && x <= width) && (y >= 0 && y <= height)) {
                    mPresenter.actionMove(x, y );
                }else {
                    mPresenter.actionJump(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPresenter.actionUp(x, y);
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //surfaceView似乎不调用onDraw
        LogUtil.e(TAG, "onDraw");
    }

//=========================================================================
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.e(TAG, "surfaceCreated");
        isSurfaceStarted = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtil.e(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.e(TAG, "surfaceDestroyed");
        isSurfaceStarted = false;
    }

    @Override
    public void run() {
        while(isSurfaceStarted){
            //do someThing
            draw();
            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void draw(){
        mCanvas = mHolder.lockCanvas();
        if (mCanvas != null) {
            try {
                //使用获得的Canvas做具体的绘制
//                mCanvas.drawColor(mBackgroundColor, PorterDuff.Mode.DST);
                mPresenter.drawCanvas(mCanvas);
//                for(Operation op : mOperationList){
//                    mCanvas.drawPath(op.path, op.paint);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

//==================================================================================================
    @Override
    public void setPaintEditMode(@EditMode int editMode){
        mPresenter.setPaintMode(editMode);
    }

    @Override
    public int getEditMode(){
        return mPresenter.getEditMode();
    }

    public boolean isModeDoodle(){
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
     * 没必要暴露出去了的感觉
     * */
    protected OperationPresenter getViewPresenter(){
        return mPresenter;
    }

    public void setOnPathChangedListener(OnPathChangedListener listener){
        mPresenter.setOnPathChangedListener(listener);
    }

    public Doodle.OnPathChangedListener getOnPathChangedListener(){
        return mPresenter.getOnPathChangedListener();
    }

//====================================
    @Override
    public void refreshUi(){
        //do nothing
    }

    @Override
    public void initCanvas(Canvas canvas, int backgroundColor){
        canvas.drawColor(backgroundColor, PorterDuff.Mode.CLEAR);
    }


//==Smartable==========================================
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

}
