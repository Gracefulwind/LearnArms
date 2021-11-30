package com.gracefulwind.learnarms.newwrite.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.ViewParent;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;

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

//    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_DOODLE = 0x00000002;
    public static final int MODE_ERASER = 0x00000003;


    /**
     * 暂时还没用到，init进来的
     * */
    private Doodle doodleView;
    private Context mContext;
    //默认模式为涂鸦
    @EditMode
    private int mEditMode = MODE_DOODLE;

    private int maxCancelTime = 20;
    private List<Operation> mOperationList = new ArrayList<>();
    private List<Operation> mRedoList = new ArrayList<>();
    private Doodle.OnPathChangedListener mOnPathChangedListener;
    /**
     * path
     * */
    private Path mPath;
    private Paint mPaint;
    private Operation mOperation;

//    private @ColorInt int mBackgroundColor = 0x00FFFFFF;
    private @ColorInt int mBackgroundColor = 0;
    private @ColorInt int mPaintColor = Color.RED;
    private @ColorInt int mEraserColor = Color.WHITE;
    /**
     * 粗细
     * */
    private float mPaintWidth = 10f;

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


    public OperationPresenter(Context context, Doodle doodleView) {
        mContext = context;
        this.doodleView = doodleView;
        mPaintWidth = mContext.getResources().getDimensionPixelSize(R.dimen.public_dimen_2dp);
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
        if(width == 0 || height == 0){
            LogUtil.e(TAG, "w or h = 0 when create bitmap");
            return;
        }
        cacheBitmap = Bitmap.createBitmap(width, height, Constants.bitmapQuality);
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
        if(width == 0 || height == 0){
            LogUtil.e(TAG, "w or h = 0 when create bitmap");
            return;
        }
        holdBitmap = Bitmap.createBitmap(width, height, Constants.bitmapQuality);
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
            mPaint.setXfermode(null);
            mPaint.setColor(mEraserColor);
        }
        saveOperation();
    }

    public void actionDown(float x, float y){
        mPrevX = x;
        mPrevY = y;
        createPathAndPaint();
        //将 Path 起始坐标设为手指按下屏幕的坐标
        mPath.moveTo(x, y);
        doodleView.refreshUi();
    }

    public void actionMove(float x, float y){
        //绘制贝塞尔曲线，也就是光滑的曲线，如果此处使用 lineTo 方法滑出的曲线会有折角
//        int height = doodleView.getHeight();
//        int width = doodleView.getWidth();
//        if(mPrevX > width || mPrevY > height){
//            if(mPrevX > width){
//                mPrevX = width;
//            }
//            if(mPrevY > height){
//                mPrevY = height;
//            }
//            mPath.moveTo(mPrevX, mPrevY);
//            mPath.lineTo(x, y);
//        }else {
//            mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
//        }
////        float mpveToX = (x + mPrevX) / 2;
////        float mpveToY = (y + mPrevY) / 2;
        mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
//        mPath.lineTo(x, y);
        mPrevX = x;
        mPrevY = y;
        doodleView.refreshUi();
    }

    public void actionJump(float x, float y){
        int height = doodleView.getHeight();
        int width = doodleView.getWidth();
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
        mOperation.isFinished = true;
        if(mOperation.operationType == Operation.ERASER){
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mPaint.setColor(mBackgroundColor);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
        }
        clearRedoList();
        doodleView.refreshUi();
    }

    public void setPaintColor(@ColorInt int paintColor){
        this.mPaintColor = paintColor;
    }

    public @ColorInt int getPaintColor(){
        return mPaintColor;
    }

    public void setPaintSize(float paintSize){
        this.mPaintWidth = paintSize;
    }

    public float getPaintSize(){
        return mPaintWidth;
    }

    private void saveOperation() {
        mOperation = new Operation(mPath, mPaint);
        if(mEditMode == MODE_DOODLE){
            mOperation.operationType = Operation.DOODLE;
        }else {
            mOperation.operationType = Operation.ERASER;
        }
        mOperationList.add(mOperation);
        if(null != mOnPathChangedListener){
            mOnPathChangedListener.onCancelListChanged(mOperationList);
        }
        callSmartHandViewChanged();
    }

    /**
     * onDraw时调用。
     * 现在path操作都在action中，ui绘制都在drawCanvas中，那么缓存canvas就没意义了啊
     * */
    public void drawCanvas(Canvas canvas) {
//        if(doodleView instanceof SurfaceDoodleView){
//            drawWithSurfaceView(canvas);
//        }else if(doodleView instanceof DoodleView){
//            drawWithCommon(canvas);
//        }
        drawWithCommon(canvas);
    }

    public void drawWithCommon(Canvas canvas) {
        cacheCanvas.drawColor(mBackgroundColor, PorterDuff.Mode.CLEAR);
//            doodleView.initCanvas(canvas, mBackgroundColor);
        boolean operationListChanged = false;
        //固化了的
        while(mOperationList.size() > maxCancelTime){
            operationListChanged = true;
            Operation remove = mOperationList.remove(0);
            holdCanvas.drawPath(remove.path, remove.paint);
        }
        if(operationListChanged && null != mOnPathChangedListener){
            mOnPathChangedListener.onCancelListChanged(mOperationList);
        }
        cacheCanvas.drawBitmap(holdBitmap, 0f,0f,null);
        for(Operation op : mOperationList){
            cacheCanvas.drawPath(op.path, op.paint);
        }
        canvas.drawBitmap(cacheBitmap,0f,0f,null);
    }

    public void drawWithSurfaceView(Canvas canvas) {
        doodleView.initCanvas(canvas, mBackgroundColor);
        boolean operationListChanged = false;
        //固化了的
        while(mOperationList.size() > maxCancelTime){
            operationListChanged = true;
            Operation remove = mOperationList.remove(0);
            holdCanvas.drawPath(remove.path, remove.paint);
        }
        if(operationListChanged && null != mOnPathChangedListener){
            mOnPathChangedListener.onCancelListChanged(mOperationList);
        }
        canvas.drawBitmap(holdBitmap, 0f,0f,null);
        for(Operation op : mOperationList){
            canvas.drawPath(op.path, op.paint);
        }
    }

    /**
     * 撤销最后一笔
     * */
    public boolean cancelLastDraw(){
        if(mOperationList.size() > 0){
            cancelDrawStep(mOperationList);
            return true;
        }else {
            LogUtil.d(TAG, "can't cancelLastDraw");
            Toast.makeText(mContext, "无可撤销！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 撤回最后一次的撤销最后一笔
     * */
    public boolean redoLastDraw(){
        if(mRedoList.size() > 0){
            cancelDrawStep(mRedoList);
            return true;
        }else {
            LogUtil.d(TAG, "can't redoLastDraw");
            Toast.makeText(mContext, "无可fan撤销！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void cancelDrawStep(List<Operation> list){
        if(list.size() > 0){
            Operation paint = list.remove(list.size() - 1);
            if(list == mOperationList){
                mRedoList.add(paint);
            }else{
                mOperationList.add(paint);
            }
            if(null != mOnPathChangedListener){
                mOnPathChangedListener.onCancelListChanged(mOperationList);
                mOnPathChangedListener.onRedoListChanged(mRedoList);
            }
//            //清空缓存画板
//            cacheCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
            callSmartHandViewChanged();
            doodleView.refreshUi();
        }
    }


    private void clearRedoList(){
        mRedoList.clear();
        if(null != mOnPathChangedListener){
            mOnPathChangedListener.onRedoListChanged(mRedoList);
        }
    }

    private void clearOperationList(){
        mOperationList.clear();
        if(null != mOnPathChangedListener){
            mOnPathChangedListener.onCancelListChanged(mOperationList);
        }

    }

    private void clearAllL(){
        clearRedoList();
        clearOperationList();
        callSmartHandViewChanged();
        doodleView.refreshUi();
    }

    public void changeSize(int w, int h, int oldw, int oldh) {
        changeCacheBitmap(w, h, oldw, oldh);
        changeHolderBitmap(w, h, oldw, oldh);
    }

    private void changeCacheBitmap(int w, int h, int oldw, int oldh) {
        if(w == 0 || h == 0){
            LogUtil.e(TAG, "w or h = 0 when create bitmap");
            return;
        }
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Constants.bitmapQuality);//大图高宽
        Bitmap tempBitmap = cacheBitmap;
        cacheCanvas = new Canvas(newBitmap);
        cacheCanvas.drawBitmap(cacheBitmap, 0, 0, null);
        cacheBitmap = newBitmap;
        tempBitmap.recycle();
    }

    private void changeHolderBitmap(int w, int h, int oldw, int oldh) {
        if(w == 0 || h == 0){
            LogUtil.e(TAG, "w or h = 0 when create bitmap");
            return;
        }
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Constants.bitmapQuality);//大图高宽
        Bitmap tempBitmap = holdBitmap;
        holdCanvas = new Canvas(newBitmap);
//        holdCanvas.setBitmap(newBitmap);
        holdCanvas.drawBitmap(holdBitmap, 0, 0, null);
        holdBitmap = newBitmap;
        tempBitmap.recycle();
    }

    public void setOnPathChangedListener(Doodle.OnPathChangedListener listener){
        mOnPathChangedListener = listener;
    }

    public Doodle.OnPathChangedListener getOnPathChangedListener(){
        return mOnPathChangedListener;
    }

    public Bitmap getBitmap() {
        int width = doodleView.getWidth();
        int height = doodleView.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Constants.bitmapQuality);
        Canvas canvas = new Canvas(bitmap);
        doodleView.draw(canvas);
//        canvas.drawBitmap(cacheBitmap,0f,0f,null);
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        clearAllL();
        holdBitmap = Bitmap.createBitmap(width, height, Constants.bitmapQuality);
        holdCanvas = new Canvas(holdBitmap);
        holdCanvas.drawBitmap(bitmap, 0f, 0f, null);
    }

    private void callSmartHandViewChanged() {
        ViewParent parentView = doodleView.getRealParent();
        if (parentView instanceof SmartHandNote) {
            SmartHandNote smartHandView = (SmartHandNote) parentView;
            smartHandView.setChanged(true);
        }
    }
}
