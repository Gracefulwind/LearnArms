package com.gracefulwind.learnarms.reader.widget.integrate;

import android.content.Context;
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
        setViewMode(MODE_TEXT);
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
        //事实上，当两个子控件不消费，释放touchEvent的时候也已经是modeScale了
        if (isModeScale()) {
            //
            return true;
        }else {
            return super.onTouchEvent(event);
        }
    }

    private void addPoint(MotionEvent event) {
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
    }

    private void movePoint(MotionEvent event){
        int actionId = event.getActionIndex();
        int pointerId = event.getPointerId(actionId);
        if(mBasePointList.size() != 2){
            return;
        }
        //1.算出原始距离差
        //2.算出当前距离差
        //3.计算比例scale
        //4.缩放图形
    }

    private void deletePoint(MotionEvent event) {
        int actionId = event.getActionIndex();
        int pointerId = event.getPointerId(actionId);
        out : for(int x = 0; x < mBasePointList.size(); x++){
            Point point = mBasePointList.get(x);
            if(point.id == pointerId){
                mBasePointList.remove(x);
                break out;
            }
        }
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
}
