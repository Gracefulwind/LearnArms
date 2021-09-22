package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.doodle.EditMode;
import com.gracefulwind.learnarms.reader.widget.integrate.IntegrateFrameLayout;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: MyHandWriteView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/14
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/14
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MyHandWriteView extends FrameLayout {
    public static final String TAG = "MyHandWriteView";
    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;

//    @BindView(R2.id.vmhw_stv_text)
//    SmartTextView vmhwStvText;
//    @BindView(R2.id.vmhw_dv_doodle)
//    DoodleView vmhwDvDoodle;
    @BindView(R2.id.vmhw_ifl_control)
    IntegrateFrameLayout vmhwIflControl;

//    private int mViewMode;
    private Context mContext;

    public MyHandWriteView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mContext = context;
        View inflate = UiUtil.inflate(R.layout.view_my_hand_write, this);
        this.addView(inflate);
        Unbinder bind = ButterKnife.bind(this, inflate);
//        set attrs

        //init mode
        setViewMode(MODE_TEXT);
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "dispatchTouchEvent");
//////        if(super.dispatchTouchEvent(event)){
//////            return true;
//////        }
////        int eventAction = event.getAction();
////        //非缩放模式直接return
////        if(!isModeScale()){
////            return false;
////        }
////        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
////            @Override
////            public boolean onScale(ScaleGestureDetector detector) {
////                Matrix mMatrix = new Matrix();
////                float scale = detector.getScaleFactor() / 3;
////                mMatrix.setScale(scale, scale);
////                vmhwDvDoodle.setScaleX(scale);
////                vmhwDvDoodle.setScaleY(scale);
////                vmhwDvDoodle.invalidate();
////                return false;
////            }
////
////            @Override
////            public boolean onScaleBegin(ScaleGestureDetector detector) {
////                return true;
////            }
////
////            @Override
////            public void onScaleEnd(ScaleGestureDetector detector) {
////
////            }
////        });
////        return scaleGestureDetector.onTouchEvent(event);
//        return true;
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        int actionMasked = event.getActionMasked();
//        int action = event.getAction();
//        LogUtil.e(TAG, "dispatchTouchEvent");
//        if(isModeScale()){
//            ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
//                @Override
//                public boolean onScale(ScaleGestureDetector detector) {
//                    Matrix mMatrix = new Matrix();
////                    Float.isInfinite();
//                    float scale = detector.getScaleFactor();
//                    LogUtil.e(TAG, "scale = " + scale);
//                    mMatrix.setScale(scale, scale);
////                    vmhwDvDoodle.setScaleX(scale);
////                    vmhwDvDoodle.setScaleY(scale);
////                    vmhwDvDoodle.invalidate();
//                    if(scale < 2 && scale > 0){
//                        LogUtil.e(TAG, "scale return false ");
//                        return false;
//                    }else {
//                        LogUtil.e(TAG, "scale return true ");
//                        return true;
//                    }
//                }
//
//                @Override
//                public boolean onScaleBegin(ScaleGestureDetector detector) {
//                    return true;
//                }
//
//                @Override
//                public void onScaleEnd(ScaleGestureDetector detector) {
//
//                }
//            });
//            scaleGestureDetector.onTouchEvent(event);
//        }
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.e(TAG, "onTouchEvent");
////        if(super.onTouchEvent(event)){
////            return true;
////        }
//        //my actions:
//        int eventAction = event.getAction();
//        //非缩放模式直接return
//        if(!isModeScale()){
//            return false;
//        }
//        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
//            @Override
//            public boolean onScale(ScaleGestureDetector detector) {
//                Matrix mMatrix = new Matrix();
//                float scale = detector.getScaleFactor() / 3;
//                mMatrix.setScale(scale, scale);
//                vmhwDvDoodle.setScaleX(scale);
//                vmhwDvDoodle.setScaleY(scale);
//                vmhwDvDoodle.invalidate();
//                return false;
//            }
//
//            @Override
//            public boolean onScaleBegin(ScaleGestureDetector detector) {
//                return true;
//            }
//
//            @Override
//            public void onScaleEnd(ScaleGestureDetector detector) {
//
//            }
//        });
//        return scaleGestureDetector.onTouchEvent(event);
//    }

    public void setViewMode(int mode){
        vmhwIflControl.setViewMode(mode);
//        mViewMode = mode;
//        switch (mode){
//            case MODE_SCALE:
//                vmhwStvText.setEnabled(false);
//                vmhwDvDoodle.setEnabled(false);
//                break;
//            case MODE_TEXT:
//                vmhwStvText.setEnabled(true);
//                vmhwStvText.requestFocus();
//                vmhwDvDoodle.setEnabled(false);
//                break;
//            case MODE_DOODLE:
//                vmhwStvText.setEnabled(false);
//                vmhwDvDoodle.setEnabled(true);
//                break;
//        }
    }

    public int getViewMode(){
//        return mViewMode;
        return vmhwIflControl.getViewMode();
    }

    public void cancelLastDraw(){
//        vmhwDvDoodle.cancelLastDraw();
        vmhwIflControl.cancelLastDraw();
    }

    public void redoLastDraw(){
//        vmhwDvDoodle.redoLastDraw();
        vmhwIflControl.redoLastDraw();
    }

    public boolean isModeScale(){
//        return mViewMode == MODE_SCALE;
        return vmhwIflControl.isModeScale();
    }

    //涂鸦模块的mode
    public boolean isModeDoodle(){
//        return vmhwDvDoodle.isModeDoodle();
        return vmhwIflControl.isModeDoodle();
    }

    public void setDoodleEditMode(@EditMode int doodleEditMode){
//        vmhwDvDoodle.setPaintEditMode(doodleEditMode);
        vmhwIflControl.setDoodleEditMode(doodleEditMode);
    }

    public void test() {
        vmhwIflControl.test();
    }
}
