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

import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;

import java.util.ArrayList;
import java.util.List;

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
public class SurfaceDoodleView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
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
//        mPresenter = new OperationPresenter(context, this);
    }

//=================================================================================================


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cacheBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Constants.bitmapQuality);
        cacheCanvas = new Canvas(cacheBitmap);
        holdBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Constants.bitmapQuality);
        holdCanvas = new Canvas(holdBitmap);
    }

    private float mPrevX;
    private float mPrevY;

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
//        //非电容笔则不处理
//        if(toolType != MotionEvent.TOOL_TYPE_STYLUS){
//            return false;
//        }
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                actionDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                int height = getHeight();
                int width = getWidth();
                if ((x >= 0 && x <= width) && (y >= 0 && y <= height)) {
                    actionMove(x, y);
                }else {
                    actionJump(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                actionUp(x, y);
                break;
        }
        return true;
    }

    public void actionDown(float x, float y){
        mPrevX = x;
        mPrevY = y;
        createPathAndPaint();
        //将 Path 起始坐标设为手指按下屏幕的坐标
        mPath.moveTo(x, y);
    }


    public void actionMove(float x, float y){
        mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
        mPrevX = x;
        mPrevY = y;
    }

    public void actionJump(float x, float y){
        int height = getHeight();
        int width = getWidth();
        if(mPrevX > width){
            mPrevX = width;
        }
        if(mPrevY > height){
            mPrevY = height;
        }
        mPath.moveTo(mPrevX, mPrevY);
        mPrevX = x;
        mPrevY = y;
    }

    public void actionUp(float x, float y){
        //保存放到down里，不然move时没路径
//        saveOperation();
        mOperation.isFinished = true;
//        if(mOperation.operationType == Operation.ERASER){
//            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
////            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
//            mPaint.setColor(mBackgroundColor);
//            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//            mPaint.setStrokeJoin(Paint.Join.ROUND);
//        }
//        clearRedoList();
//        doodleView.invalidate();
    }

    private Path mPath;
    private Paint mPaint;
    private Operation mOperation;

    private void createPathAndPaint() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(10);
        mPaint.setXfermode(null);
        mPaint.setColor(Color.RED);
        saveOperation();
    }
    private List<Operation> mOperationList = new ArrayList<>();
    private void saveOperation() {
        mOperation = new Operation(mPath, mPaint);
//        if(mEditMode == MODE_DOODLE){
//            mOperation.operationType = Operation.DOODLE;
//        }else {
//            mOperation.operationType = Operation.ERASER;
//        }
        mOperation.operationType = Operation.DOODLE;
        mOperationList.add(mOperation);
//        if(null != mOnPathChangedListener){
//            mOnPathChangedListener.onCancelListChanged(mOperationList);
//        }
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
                for(Operation op : mOperationList){
                    mCanvas.drawPath(op.path, op.paint);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
