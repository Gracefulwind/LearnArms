package com.gracefulwind.learnarms.reader.widget.doodle;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

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

//    public static
    /**
     * path
     * */
    private Path mPath;
    private Paint mPaint;

    private float mPrevX;
    private float mPrevY;

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
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        BlurMaskFilter PaintBGBlur = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
        mPaint.setMaskFilter(PaintBGBlur);
        mPaint.setStrokeWidth(10);
    }

//==================================================================================================
    int eventIndex = 0;
    int drawIndex = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        LogUtil.d(TAG, "touchEvent, index = " + ++eventIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = x;
                mPrevY = y;
//                mPath = new Path();
                mPath.moveTo(x, y);//将 Path 起始坐标设为手指按下屏幕的坐标
                break;
            case MotionEvent.ACTION_MOVE:
//                Canvas canvas = mSurfaceHolder.lockCanvas();
//                restorePreAction(canvas);//首先恢复之前绘制的内容
                mPath.quadTo(mPrevX, mPrevY, (x + mPrevX) / 2, (y + mPrevY) / 2);
                //绘制贝塞尔曲线，也就是光滑的曲线，如果此处使用 lineTo 方法滑出的曲线会有折角
                mPrevX = x;
                mPrevY = y;
//                canvas.drawPath(mPath, mPaint);
//                mSurfaceHolder.unlockCanvasAndPost(canvas);
                //todo:wd 保存手势到缓存
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
//        Canvas canvas = getHolder().lockCanvas();
//        canvas.drawPath(mPath, new Paint());
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.d(TAG, "drawEvent, index = " + drawIndex++);
        LogUtil.d(TAG, "canvascanvas, canvas = " + canvas.toString());
        canvas.save();
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }
}
