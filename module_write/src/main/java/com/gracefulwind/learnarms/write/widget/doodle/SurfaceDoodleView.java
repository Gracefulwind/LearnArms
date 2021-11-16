package com.gracefulwind.learnarms.write.widget.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.gracefulwind.learnarms.commonsdk.core.Constants;

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
public class SurfaceDoodleView extends SurfaceView {
    public static final String TAG = "SurfaceDoodleView";

    private OperationPresenter mPresenter;
    private ViewGroup mControlParent;
    private int responseLineNumber = 2;
    private int expandLineNumber = 3;

    private Bitmap cacheBitmap;
    private Canvas cacheCanvas;
    private Bitmap holdBitmap;
    private Canvas holdCanvas;

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
//        mPresenter = new OperationPresenter(context, this);
    }

//=================================================================================================


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        cacheBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Constants.bitmapQuality);
        cacheCanvas = new Canvas(cacheBitmap);
        holdBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Constants.bitmapQuality);
        holdCanvas = new Canvas(holdBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
