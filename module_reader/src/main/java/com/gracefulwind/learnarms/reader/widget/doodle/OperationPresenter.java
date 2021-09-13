package com.gracefulwind.learnarms.reader.widget.doodle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: OperationManager
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class OperationPresenter {
    public static final String TAG = "OperationManager";

    public static final int MODE_DOODLE = 0x00000000;
    public static final int MODE_ERASER = 0x00000001;

    /**
     * 暂时还没用到，init进来的
     * */
    private DoodleView doodleView;
    //默认模式为涂鸦
    @EditMode
    private int mEditMode = MODE_DOODLE;

    private int maxCancelTime = 20;
    private List<Operation> mPaintList = new ArrayList<>();
    private List<Operation> mCancelList = new ArrayList<>();

    /**
     * path
     * */
    private Path mPath;
    private Paint mPaint;

    private float mPrevX;
    private float mPrevY;

    //缓存层
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    //持久层
    private Bitmap holdBitmap;
    private Canvas holdCanvas;

    public OperationPresenter(DoodleView doodleView) {
        this.doodleView = doodleView;
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //滤镜用不到
//        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
//        mPaint.setMaskFilter(PaintBGBlur);
        mPaint.setStrokeWidth(10);
    }

    public void setPaintMode(@EditMode int editMode) {
        mEditMode = editMode;
        if(MODE_DOODLE == editMode){
            mPaint.setXfermode(null);
        }else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
    }

    public int getEditMode() {
        return mEditMode;
    }

    public boolean isModeDoodle(){
        return mEditMode == MODE_DOODLE;
    }

    public boolean hasCacheBitmap(){
        return cacheBitmap != null;
    }

    public void createCacheBitmapIfNull(int width, int height){
        if(!hasCacheBitmap()){
            createCacheBitmap(width, height);
        }
    }

    public void createCacheBitmap(int width, int height) {
        cacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);
    }

    public boolean hasHoldBitmap(){
        return holdBitmap != null;
    }

    public void createHoldBitmapIfNull(int width, int height){
        if(!hasHoldBitmap()){
            createHoldBitmap(width, height);
        }
    }

    public void createHoldBitmap(int width, int height) {
        holdBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        holdCanvas = new Canvas(holdBitmap);
    }

//    public void moveTo(float x, float y) {
//        mPath.moveTo(x, y);
//    }

    public void actionDown(float x, float y){
        mPrevX = x;
        mPrevY = y;
        //将 Path 起始坐标设为手指按下屏幕的坐标
        mPath.moveTo(x, y);
        cacheCanvas.drawPath(mPath, mPaint);
    }

    public void actionMove(float x, float y){
        //绘制贝塞尔曲线，也就是光滑的曲线，如果此处使用 lineTo 方法滑出的曲线会有折角
        mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
        mPrevX = x;
        mPrevY = y;
        cacheCanvas.drawPath(mPath, mPaint);
    }

    public void actionUp(float x, float y){
        mPath.reset();
        cacheCanvas.drawPath(mPath, mPaint);
    }

    public void drawCanvas(Canvas canvas) {
        canvas.drawBitmap(cacheBitmap,0f,0f,null);
    }

    /**
     * 撤销最后一笔
     * */
    public void removeLastDraw(){

    }

    /**
     * 撤回最后一次的撤销最后一笔
     * */
    public void cancelLsatRemove(){

    }

}
