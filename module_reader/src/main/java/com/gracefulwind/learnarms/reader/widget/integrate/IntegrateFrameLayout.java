package com.gracefulwind.learnarms.reader.widget.integrate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.ScaleGestureDetectorApi27;
import com.gracefulwind.learnarms.reader.widget.TouchGestureDetector;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.EditMode;
import com.gracefulwind.learnarms.reader.widget.edit.SmartTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: IntegrateFrameLayout
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/17
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/17
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class IntegrateFrameLayout extends FrameLayout {

    public static final String TAG = "IntegrateFrameLayout";
    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;

    @BindView(R2.id.vmhw_stv_text)
    SmartTextView vmhwStvText;
    @BindView(R2.id.vmhw_dv_doodle)
    DoodleView vmhwDvDoodle;
    private int mViewMode;
    private Context mContext;

    private TouchGestureDetector touchGestureDetector;
    //缩放的基准
    public double scaleBaseRatio = 0.8;
    //最后一次操作完成的缩放比例
    private double lastScaleRatio = 1;
    private double tempScaleRatio = 1;
    //记录下的最后的单指坐标
    private Point mSingleFingerPoint = new Point();
    //x,y轴的偏移量
    private float lastMovedX = 0;
    private float lastMovedY = 0;


    public IntegrateFrameLayout(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public IntegrateFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IntegrateFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public IntegrateFrameLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(@NotNull Context context, @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        View inflate = UiUtil.inflate(R.layout.view_integrate_frame_layout, this);
        this.addView(inflate);
        Unbinder bind = ButterKnife.bind(this, inflate);
        vmhwDvDoodle.setControlParent(this);
        initDetector();
        initChild();
        setViewMode(MODE_TEXT);
    }

    private void initDetector() {
//        touchGestureDetector = new TouchGestureDetector(mContext, new TouchGestureDetector.OnScaleGestureListener() {
//            float lastScale = 1;
//            float tempScale = 1;
//            @Override
//            public boolean onScale(ScaleGestureDetector detector) {
//                Matrix mMatrix = new Matrix();
////                    Float.isInfinite();
//                float scale = detector.getScaleFactor();
////                    LogUtil.e(TAG, "scale = " + scale);
//                tempScale = scale;
//                LogUtil.e(TAG, "onScale: " + scale);
//                float targetScale = lastScale * tempScale;
//                LogUtil.e(TAG, "lastScale: " + lastScale);
//                LogUtil.e(TAG, "targetScale: " + targetScale);
////                IntegrateFrameLayout.this.setScaleX(targetScale);
////                IntegrateFrameLayout.this.setScaleY(targetScale);
//                scaleChild(targetScale);
//                mMatrix.setScale(scale, scale);
////                    vmhwDvDoodle.setScaleX(scale);
////                    vmhwDvDoodle.setScaleY(scale);
////                    vmhwDvDoodle.invalidate();
////                if (scale < 2 && scale > 0) {
//////                        LogUtil.e(TAG, "scale return false ");
////                    return false;
////                } else {
//////                        LogUtil.e(TAG, "scale return true ");
////                    return true;
////                }
//                return true;
//            }
//
//            @Override
//            public boolean onScaleBegin(ScaleGestureDetector detector) {
//                return true;
//            }
//
//            @Override
//            public void onScaleEnd(ScaleGestureDetector detector) {
//                //这里的scale返回的一直是1.0
//                float scale = detector.getScaleFactor();
//                lastScale *= tempScale;
////                LogUtil.e(TAG, "onScaleEnd: " + scale);
//            }
//        });
        touchGestureDetector = new TouchGestureDetector(getContext(), new TouchGestureDetector.OnTouchGestureListener() {
            float lastScale = 1;
            float tempScale = 1;
            @Override
            public boolean onScaleBegin(ScaleGestureDetectorApi27 detector) {
                LogUtil.e(TAG, "onScaleBegin: ");
                return true;
            }

            @Override
            public boolean onScale(ScaleGestureDetectorApi27 detector) { // 双指缩放中
                Matrix mMatrix = new Matrix();
//                    Float.isInfinite();
                float scale = detector.getScaleFactor();
//                    LogUtil.e(TAG, "scale = " + scale);
                tempScale = scale;
                LogUtil.e(TAG, "onScale: " + scale);
                float targetScale = lastScale * tempScale;
                LogUtil.e(TAG, "lastScale: " + lastScale);
                LogUtil.e(TAG, "targetScale: " + targetScale);
//                IntegrateFrameLayout.this.setScaleX(targetScale);
//                IntegrateFrameLayout.this.setScaleY(targetScale);
                scaleChild(targetScale);
                mMatrix.setScale(scale, scale);
//                    vmhwDvDoodle.setScaleX(scale);
//                    vmhwDvDoodle.setScaleY(scale);
//                    vmhwDvDoodle.invalidate();
//                if (scale < 2 && scale > 0) {
////                        LogUtil.e(TAG, "scale return false ");
//                    return false;
//                } else {
////                        LogUtil.e(TAG, "scale return true ");
//                    return true;
//                }
                return true;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetectorApi27 detector) {
                //这里的scale返回的一直是1.0
                float scale = detector.getScaleFactor();
                lastScale *= tempScale;
                LogUtil.e(TAG, "onScaleEnd: " + scale);
                LogUtil.e(TAG, "onScaleEnd: tempScale = " + tempScale);
                LogUtil.e(TAG, "onScaleEnd: lastScale = " + lastScale);
            }
        });
    }

    private void initChild() {

    }

    public void setViewMode(int mode) {
        mViewMode = mode;
        switch (mode) {
            case MODE_SCALE:
                vmhwStvText.setEnabled(false);
                vmhwDvDoodle.setEnabled(false);
                break;
            case MODE_TEXT:
                vmhwStvText.setEnabled(true);
                vmhwStvText.requestFocus();
                vmhwDvDoodle.setEnabled(false);
                break;
            case MODE_DOODLE:
                vmhwStvText.setEnabled(false);
                vmhwDvDoodle.setEnabled(true);
                break;
        }
    }

    public int getViewMode() {
        return mViewMode;
    }

    public void cancelLastDraw() {
        vmhwDvDoodle.cancelLastDraw();
    }

    public void redoLastDraw() {
        vmhwDvDoodle.redoLastDraw();
    }

    public boolean isModeScale() {
        return mViewMode == MODE_SCALE;
    }

    //涂鸦模块的mode
    public boolean isModeDoodle() {
        return vmhwDvDoodle.isModeDoodle();
    }

    public void setDoodleEditMode(@EditMode int doodleEditMode) {
        vmhwDvDoodle.setPaintEditMode(doodleEditMode);
    }

    private float firstPointX, firstPointY, secondPointX, secondPointY;
    private int firstPointId, secondPointId, pointCount;
    private float baseDisSquare, baseDis;
    private List<Point> mBasePointList = new ArrayList<>();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return oldTest(event);
        doGesture(event);
//        touchGestureDetector.onTouchEvent(event);
        //事实上，当两个子控件不消费，释放touchEvent的时候也已经是modeScale了
        if (isModeScale()) {
            //
            return true;
        }else {
            return false;
        }
    }

    private void doGesture(MotionEvent event) {
        int actionId = event.getActionIndex();
        int actionMasked = event.getActionMasked();
        int pointerId = event.getPointerId(actionId);
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                addPoint(event);
                break;
            case MotionEvent.ACTION_MOVE:
                movePoint(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                deletePoint(event);
                break;
            default:
                break;
        }
    }

    private void addPoint(MotionEvent event) {
        LogUtil.e(TAG, "addPoint");
        if(mBasePointList.size() >= 2){
            return;
        }
        int actionId = event.getActionIndex();
        int pointerId = event.getPointerId(actionId);
        Point point = new Point();
        point.id = pointerId;
        point.x = event.getX(pointerId);
        point.y = event.getY(pointerId);
        mBasePointList.add(point);
        if(mBasePointList.size() == 1){
            saveSingleFingerPoint(event);
        }
        LogUtil.e(TAG, "addPointOk List = " + mBasePointList.toString());
    }

    private void movePoint(MotionEvent event){
        LogUtil.e(TAG, "movePoint");
        int actionId = event.getActionIndex();
        int pointerId = event.getPointerId(actionId);
        //两点缩放
        if(mBasePointList.size() == 2){
            onScale(event, pointerId);
        //一点平移
        }else if(mBasePointList.size() == 1){
            onTranslate(event, pointerId);
        }

    }

    /**
     * 当有只有一个手指时，保存下当前的缩放比例
     * */
    private void deletePoint(MotionEvent event) {
        LogUtil.e(TAG, "deletePoint");
        int actionId = event.getActionIndex();
        int pointerId = event.getPointerId(actionId);
        Point removedPoint = null;
        out : for(int x = 0; x < mBasePointList.size(); x++){
            Point point = mBasePointList.get(x);
            if(point.id == pointerId){
                removedPoint = mBasePointList.remove(x);
                break out;
            }
        }
        if(null != removedPoint && 1 == mBasePointList.size()){
            //当一个手指放开后，不会走到movePoint方法里了，所以tempScale不会再改变
            //只有两指变成一指时才记录
            saveLastScaleRatio();
            saveSingleFingerPoint(event);
        }
//        if(mBasePointList.size() == 0){
//            saveSingleFingerPoint(null);
//        }
    }

    /**
     * 记录下最后的缩放比例
     * */
    private void saveLastScaleRatio() {
        lastScaleRatio *= tempScaleRatio;
    }

    /**
     * 记录下只剩单点时的当前坐标
     * */
    private void saveSingleFingerPoint(MotionEvent event) {
//        if(null == event){
//            mSingleFingerPoint = null;
//        }
        //记录下当前的一指坐标
        Point singleFingerPoint = mBasePointList.get(0);
//        mSingleFingerPoint = new Point();
        mSingleFingerPoint.id = singleFingerPoint.id;
        mSingleFingerPoint.x = event.getX(singleFingerPoint.id);
        mSingleFingerPoint.y = event.getY(singleFingerPoint.id);
    }

    /**
     * 缩放事件
     * */
    private void onScale(MotionEvent event, int pointerId) {
        //0.确认是否是已存点
        Point movedPoint = null;
        Point otherPoint = null;
        for(int x = 0; x < mBasePointList.size(); x++){
            Point point = mBasePointList.get(x);
            if(point.id == pointerId){
                movedPoint = mBasePointList.get(x);
            }else {
                otherPoint = mBasePointList.get(x);
            }
        }
        //移动事件的点不在记录中则不处理事件
        if(null == movedPoint){
            return;
        }

        if(null == otherPoint){
            //理论上不可能存在的情况，必然有 两个 不同 的点，那就必然有一个
            return;
        }
        //1.算出原始距离差
        Point point0 = mBasePointList.get(0);
        Point point1 = mBasePointList.get(1);
        float movedX = event.getX(pointerId);
        float movedY = event.getY(pointerId);
        double disSquareBase = (point0.x - point1.x) * (point0.x - point1.x)
                + (point0.y - point1.y) * (point0.y - point1.y);
        double disSquareMoved = (otherPoint.x - movedX) * (otherPoint.x - movedX)
                + (otherPoint.y - movedY) * (otherPoint.y - movedY);
        //2.算出当前距离差
        double scale = 1;
        if(0 == disSquareMoved || disSquareBase == disSquareMoved){
            scale = 1;
        }else {
            scale = Math.sqrt(disSquareMoved) / Math.sqrt(disSquareBase);
        }
        //3.计算比例scale
        //缩放比例为移动比例的一半
        if(scale > 1){
            scale = ((scale - 1) / scaleBaseRatio) + 1;
        }else if(scale < 1){
            scale = 1 - ((1- scale) / scaleBaseRatio);
        }
//        LogUtil.e(TAG, "movePoint, scale = " + scale + ", List = " + mBasePointList.toString());
        //4.缩放图形
        scaleChild(scale);
    }

    private void scaleChild(double scaleRatio) {
        tempScaleRatio = scaleRatio;
        LogUtil.e(TAG, "scaleChild : tempScaleRatio = " + tempScaleRatio);
        double targetScaleRatio = lastScaleRatio * scaleRatio;
        vmhwDvDoodle.setScaleX((float) targetScaleRatio);
        vmhwDvDoodle.setScaleY((float) targetScaleRatio);
//        int width = vmhwDvDoodle.getWidth();
//        int height = vmhwDvDoodle.getHeight();
//        LogUtil.e(TAG, "vmhwDvDoodle : w = " + width + ",   h = " + height);
        vmhwStvText.setScaleX((float) targetScaleRatio);
        vmhwStvText.setScaleY((float) targetScaleRatio);
    }

    private void onTranslate(MotionEvent event, int pointerId) {
        Point movedPoint = null;
        //其实只剩一个点了，不需要遍历的写法
        for(int x = 0; x < mBasePointList.size(); x++){
            Point point = mBasePointList.get(x);
            if(point.id == pointerId){
                movedPoint = mBasePointList.get(x);
                break;
            }
        }
        if(null == movedPoint){
            //理论上不存在。只有单指了肯定在
            return;
        }
        float movedX = event.getX(pointerId);
        float movedY = event.getY(pointerId);
        float disX = movedX - mSingleFingerPoint.x;
        float disY = movedY - mSingleFingerPoint.y;
        mSingleFingerPoint.x = movedX;
        mSingleFingerPoint.y = movedY;
        lastMovedX += disX;
        lastMovedY += disY;
        float translationX = vmhwDvDoodle.getTranslationX();
        float translationY = vmhwDvDoodle.getTranslationY();
        LogUtil.e(TAG, "disX = " + disX + ", disY = " + disY);
        LogUtil.e(TAG, "translationX = " + translationX + ", translationY = " + translationY);
        vmhwDvDoodle.setTranslationX(lastMovedX);
        vmhwDvDoodle.setTranslationY(lastMovedY);
        vmhwStvText.setTranslationX(lastMovedX);
        vmhwStvText.setTranslationY(lastMovedY);


    }

    private boolean oldTest(MotionEvent event) {
        int actionId = event.getActionIndex();
        int actionMasked = event.getActionMasked();
        int pointerId = event.getPointerId(actionId);
        switch (actionMasked){
            case MotionEvent.ACTION_DOWN:
                pointCount++;
                firstPointId = pointerId;
                firstPointX = event.getX(firstPointId);
                firstPointY = event.getY(firstPointId);
//                LogUtil.e(TAG, "down , baseX = " + firstPointX + ", baseY = " + firstPointY);
                LogUtil.e(TAG, "ACTION_DOWN id = " + pointerId);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //已经存在两点后不再处理
                if(pointCount >= 2){
                    break;
                }
                pointCount++;
                secondPointId = pointerId;
                secondPointX = event.getX(secondPointId);
                secondPointY = event.getY(secondPointId);
                baseDisSquare = (firstPointX - secondPointX) * (firstPointX - secondPointX)
                        + (firstPointY - secondPointY) * (firstPointY - secondPointY);
                baseDis = (float) Math.sqrt(baseDisSquare);
//                LogUtil.e(TAG, "pointer_down , baseX = " + secondPointX + ", baseY = " + secondPointY);
                LogUtil.e(TAG, "ACTION_POINTER_DOWN id = " + pointerId);
                break;
            case MotionEvent.ACTION_MOVE:
//                if(pointCount < 2){
//                    break;
//                }
//                float disX = event.getX(secondPointId) - event.getX(firstPointId);
//                float disY = event.getY(secondPointId) - event.getY(firstPointId);
//                float movedDisSquare = disX * disX + disY * disY;
//                float movedDis = (float) Math.sqrt(movedDisSquare);
//                float scale = 1;
//                if(0 == baseDisSquare){
//                    scale = 1;
//                }else if(movedDisSquare > baseDisSquare){
//                    //放大
//                    scale = movedDis / baseDisSquare;
//                }else if (movedDisSquare < baseDisSquare){
//                    //缩小
//                    scale = movedDis / baseDisSquare;
//                }else {
//                    scale = 1;
//                }
//                LogUtil.e(TAG, "move, 1X = " + event.getX(firstPointId) + ", 1Y = " + event.getY(firstPointId));
//                LogUtil.e(TAG, "move, 2X = " + event.getX(secondPointId) + ", 2Y = " + event.getY(secondPointId));
////                LogUtil.e(TAG, "scale == " + scale);
                break;
            case MotionEvent.ACTION_UP:
                pointCount--;
                LogUtil.e(TAG, "ACTION_UP id = " + pointerId);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                LogUtil.e(TAG, "ACTION_POINTER_UP id = " + pointerId);
                if(pointerId == firstPointId){
                    pointCount--;
                    firstPointId = 0;
                }
                if(pointerId == secondPointId){
                    pointCount--;
                    secondPointId = 0;
                }
                break;
            default:
                break;
        }
        int action = event.getAction();
        if (isModeScale()) {
            //scrollview可滑动
//            requestDisallowInterceptTouchEvent(true);
            ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
                @Override
                public boolean onScale(ScaleGestureDetector detector) {
                    Matrix mMatrix = new Matrix();
//                    Float.isInfinite();
                    float scale = detector.getScaleFactor();
//                    LogUtil.e(TAG, "scale = " + scale);
                    mMatrix.setScale(scale, scale);
//                    vmhwDvDoodle.setScaleX(scale);
//                    vmhwDvDoodle.setScaleY(scale);
//                    vmhwDvDoodle.invalidate();
                    if (scale < 2 && scale > 0) {
//                        LogUtil.e(TAG, "scale return false ");
                        return false;
                    } else {
//                        LogUtil.e(TAG, "scale return true ");
                        return true;
                    }
                }

                @Override
                public boolean onScaleBegin(ScaleGestureDetector detector) {
                    return true;
                }

                @Override
                public void onScaleEnd(ScaleGestureDetector detector) {

                }
            });
            scaleGestureDetector.onTouchEvent(event);
            return true;
        }else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleX = getScaleX();
        float scaleY = getScaleY();
        LogUtil.e(TAG, "onDraw, scaleX = " + scaleX + ", scaleY = " + scaleY);
    }
}
