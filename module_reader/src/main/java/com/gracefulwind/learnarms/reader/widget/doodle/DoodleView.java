package com.gracefulwind.learnarms.reader.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    /*public static final int MODE_DOODLE = 0x00000000;
    public static final int MODE_ERASER = 0x00000001;

    //默认模式为涂鸦
    @EditMode
    private int mEditMode = MODE_DOODLE;

    *//**
     * path
     * *//*
    private Path mPath;
    private Paint mPaint;

    private float mPrevX;
    private float mPrevY;
    //缓存层
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    //持久层
    private Bitmap holdBitmap;
    private Canvas holdCanvas;*/
    private OperationPresenter mPresenter;


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
        //context is defined in base view
        //manager my attrs
        //manager defStyle
        //my init
//        mPath = new Path();
//        mPaint = new Paint();
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        //滤镜用不到
////        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
////        mPaint.setMaskFilter(PaintBGBlur);
//        mPaint.setStrokeWidth(10);
        mPresenter = new OperationPresenter(this);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setColor(Color.parseColor("#FF0000"));
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeWidth(10);
    }

//==================================================================================================
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if(cacheCanvas == null){
//            cacheBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//            //canvas绘制的内容，将会在这个cacheBitmap内
//            cacheCanvas = new Canvas(cacheBitmap);
//            LogUtil.d(TAG, "w & h" + getMeasuredWidth() + " == " + getMeasuredHeight());
//        }
//        if(holdCanvas == null){
//            holdBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//            holdCanvas = new Canvas(holdBitmap);
//        }
        mPresenter.createCacheBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
        mPresenter.createHoldBitmapIfNull(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
//                mPrevX = x;
//                mPrevY = y;
//                mPath.moveTo(x, y);//将 Path 起始坐标设为手指按下屏幕的坐标
                mPresenter.actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
//                //绘制贝塞尔曲线，也就是光滑的曲线，如果此处使用 lineTo 方法滑出的曲线会有折角
//                mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
//                mPrevX = x;
//                mPrevY = y;
//                //todo:wd 保存手势到缓存
                mPresenter.actionMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
//                mPath.reset();
                mPresenter.actionUp(x, y);
                break;
        }
//        cacheCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.save();
//        canvas.drawPath(mPath, mPaint);
//        canvas.restore();
//        canvas.drawBitmap(cacheBitmap,0f,0f,null);
        mPresenter.drawCanvas(canvas);
    }
//====================================================================================================
    public void setEditMode(@EditMode int editMode){
//        mEditMode = editMode;
//        if(MODE_DOODLE == editMode){
//            mPaint.setXfermode(null);
//        }else {
//            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        }
        mPresenter.setPaintMode(editMode);
    }

    public int getEditMode(){
//        return mEditMode;
        return mPresenter.getEditMode();
    }

    public boolean isModeDoodle(){
        return mPresenter.isModeDoodle();
    }
}
