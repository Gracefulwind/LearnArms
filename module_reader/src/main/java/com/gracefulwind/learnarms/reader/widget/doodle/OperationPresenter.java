package com.gracefulwind.learnarms.reader.widget.doodle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.ColorInt;
import android.widget.Toast;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;

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
    public static final String TAG = "OperationPresenter";

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
    private List<Operation> mOperationList = new ArrayList<>();
    private List<Operation> mCancelList = new ArrayList<>();

    /**
     * path
     * */
    private Path mPath;
    private Paint mPaint;
    private @ColorInt int mBackgroundColor = 0xFF00f0f0;
    private @ColorInt int mPaintColor = Color.BLACK;
    /**
     * 粗细
     * */
    private int mPaintWidth = 10;

    private float mPrevX;
    private float mPrevY;

    //缓存层
    //todo:wd 现在过程中只操作path了，而ui的操作只在onDraw中,
    // 所以缓存层失去了意义，可删
    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    //持久层
    private Bitmap holdBitmap;
    private Canvas holdCanvas;

    private boolean isFirstInit = true;

    public OperationPresenter(DoodleView doodleView) {
        this.doodleView = doodleView;
//        //初始化画笔和path
//        mPath = new Path();
//        mPaint = new Paint();
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        //滤镜用不到
////        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
////        mPaint.setMaskFilter(PaintBGBlur);
//        mPaint.setStrokeWidth(10);
        createPathAndPaint();
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

    /**
     * 设置画笔模式，目前就画笔和橡皮擦
     * */
    public void setPaintMode(@EditMode int editMode) {
        mEditMode = editMode;
    }

    public int getEditMode() {
        return mEditMode;
    }

    public boolean isModeDoodle(){
        return mEditMode == MODE_DOODLE;
    }
//--------------------------------------------------------------------------------------------------

    private void createPathAndPaint() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setColor(mPaintColor);
        mPaint.setStrokeWidth(mPaintWidth);
        //滤镜用不到
//        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
//        mPaint.setMaskFilter(PaintBGBlur);
        if(MODE_DOODLE == mEditMode){
            mPaint.setXfermode(null);
            mPaint.setColor(mPaintColor);
        }else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mPaint.setColor(mBackgroundColor);
        }
    }

    public void actionDown(float x, float y){
        mPrevX = x;
        mPrevY = y;
        createPathAndPaint();
        saveOperation();
        //将 Path 起始坐标设为手指按下屏幕的坐标
        mPath.moveTo(x, y);
        doodleView.invalidate();
    }

    public void actionMove(float x, float y){
        //绘制贝塞尔曲线，也就是光滑的曲线，如果此处使用 lineTo 方法滑出的曲线会有折角
        mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
        mPrevX = x;
        mPrevY = y;
        doodleView.invalidate();
    }

    public void actionUp(float x, float y){
        //保存放到down里，不然move时没路径
//        saveOperation();
        clearRedoList();
        doodleView.invalidate();
    }

    public void setPaintColor(@ColorInt int paintColor){
        this.mPaintColor = paintColor;
    }

    public @ColorInt int getPaintColor(){
        return mPaintColor;
    }

    public void setPaintSize(int paintSize){
        this.mPaintWidth = paintSize;
    }

    public int getPaintSize(){
        return mPaintWidth;
    }

    private void saveOperation() {
        Operation operation = new Operation(mPath, mPaint);
        mOperationList.add(operation);
    }

    /**
     * onDraw时调用。
     * 现在path操作都在action中，ui绘制都在drawCanvas中，那么缓存canvas就没意义了啊
     * */
    public void drawCanvas(Canvas canvas) {
//        if(isFirstInit){
//            canvas.drawColor(mBackgroundColor);
//            isFirstInit = !isFirstInit;
//        }
        //缓存层可以不用了
        cacheCanvas.drawColor(mBackgroundColor, PorterDuff.Mode.CLEAR);
//        cacheCanvas.drawColor(mBackgroundColor);
//        cacheCanvas.drawColor(0xFF00f0f0);
        while(mOperationList.size() > maxCancelTime){
            Operation remove = mOperationList.remove(0);
            holdCanvas.drawPath(remove.path, remove.paint);
        }

        cacheCanvas.drawBitmap(holdBitmap, 0f,0f,null);
        for(Operation op : mOperationList){
            cacheCanvas.drawPath(op.path, op.paint);
        }
        canvas.drawBitmap(cacheBitmap,0f,0f,null);

//        //mode.clear方法的颜色无所谓，反正都是clear了的
//        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
//        canvas.drawColor(0xFF00f0f0);
//        //持久层
//        while(mOperationList.size() > maxCancelTime){
//            Operation remove = mOperationList.remove(0);
//            holdCanvas.drawPath(remove.path, remove.paint);
//        }
//        canvas.drawBitmap(holdBitmap, 0f,0f,null);
//        //绘制剩余笔画
//        for(Operation op : mOperationList){
//            canvas.drawPath(op.path, op.paint);
//        }
    }

    /**
     * 撤销最后一笔
     * */
    public void cancelLastDraw(){
        if(mOperationList.size() > 0){
            cancelDrawStep(mOperationList);
        }else {
            LogUtil.d(TAG, "can't cancelLastDraw");
            Toast.makeText(doodleView.getContext(), "无可撤销！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 撤回最后一次的撤销最后一笔
     * */
    public void redoLastDraw(){
        if(mCancelList.size() > 0){
            cancelDrawStep(mCancelList);
        }else {
            LogUtil.d(TAG, "can't redoLastDraw");
            Toast.makeText(doodleView.getContext(), "无可fan撤销！", Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelDrawStep(List<Operation> list){
        if(list.size() > 0){
            Operation paint = list.remove(list.size() - 1);
            if(list == mOperationList){
                mCancelList.add(paint);
            }else{
                mOperationList.add(paint);
            }
//            //清空缓存画板
//            cacheCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            doodleView.invalidate();
        }
    }


    private void clearRedoList(){
        mCancelList.clear();
    }

    private void clearAllL(){
        mOperationList.clear();
        mCancelList.clear();
        doodleView.invalidate();
    }

    public void changeSize(int w, int h, int oldw, int oldh) {
        changeCacheBitmap(w, h, oldw, oldh);
        changeHolderBitmap(w, h, oldw, oldh);
    }

    private void changeCacheBitmap(int w, int h, int oldw, int oldh) {
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//大图高宽
        cacheCanvas = new Canvas(newBitmap);
//        cacheCanvas.setBitmap(newBitmap);
        cacheCanvas.drawBitmap(cacheBitmap, 0, 0, null);
        cacheBitmap = newBitmap;
    }

    private void changeHolderBitmap(int w, int h, int oldw, int oldh) {
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);//大图高宽
        holdCanvas = new Canvas(newBitmap);
//        holdCanvas.setBitmap(newBitmap);
        holdCanvas.drawBitmap(holdBitmap, 0, 0, null);
        holdBitmap = newBitmap;
    }


}
