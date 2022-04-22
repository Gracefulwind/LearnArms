package com.gracefulwind.learnarms.reader.widget.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @ClassName: SmartScrollView1
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/15
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/15
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartScrollView1 extends ScrollView {
    public static final String TAG = "SmartScrollView1";

    public static final int ACTION_MOVE = 0b00000001;
    public static final int ACTION_UP = 0b00000010;
    private int mTouchAction = 0b00000000;
    private float downX;
    private float downY;
    private float moveX;
    private float moveY;
    private float upX;
    private float upY;
    private boolean isFirstInit;

    public SmartScrollView1(Context context) {
        super(context);
    }

    public SmartScrollView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartScrollView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

//    public SmartScrollView1(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }

    private void init() {
        //my init
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
//        int eventAction = ev.getAction();
//        switch (eventAction){
//            case MotionEvent.ACTION_DOWN:
//                downX = ev.getX();
//                downY = ev.getY();
//                LogUtil.d(TAG, "ACTION_DOWN, x = " + downX + "  , y = " + downY );
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                moveX = ev.getX();
//                moveY = ev.getY();
////                mTouchAction = ACTION_MOVE;
////                invalidate();
//                LogUtil.d(TAG, "ACTION_MOVE, x = " + moveX + "  , y = " + moveY );
//                return true;
//            case MotionEvent.ACTION_UP:
//                mTouchAction = ACTION_UP;
//                moveX = ev.getX();
//                moveY = ev.getY();
////                invalidate();
//                LogUtil.d(TAG, "ACTION_UP, x = " + moveX + "  , y = " + moveY );
//                return true;
//        }
//        return false;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
////        return super.dispatchTouchEvent(ev);
//        return false;
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if(isFirstInit){
//            LogUtil.d(TAG, "onDraw,firstInit, canvas = " + canvas);
//            canvas.save();
//            isFirstInit = false;
//        }
//        LogUtil.d(TAG, "onDraw, x = " + moveX + "  , y = " + moveY  + ",  action = " + mTouchAction + " , canvas = " + canvas);
//        if(NumberUtils.isAnd(mTouchAction, ACTION_MOVE | ACTION_UP)){
//            mTouchAction = 0;
////            canvas.restore();
////            canvas.save();
//            canvas.translate(moveX - downX, moveY - downY);
//            canvas.save();
//        }
    }
}

