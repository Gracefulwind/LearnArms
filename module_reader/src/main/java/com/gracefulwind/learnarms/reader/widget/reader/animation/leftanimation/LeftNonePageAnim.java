package com.gracefulwind.learnarms.reader.widget.reader.animation.leftanimation;

import android.graphics.Canvas;
import android.view.View;

/**
 * @ClassName: LeftNonePageAnim
 * @Author: Gracefulwind
 * @CreateDate: 2022/4/21
 * @Description: ---------------------------
 * todo: novel中没有这个动画，回头研究下
 * @UpdateUser:
 * @UpdateDate: 2022/4/21
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class LeftNonePageAnim extends LeftHorizonPageAnim {

    public LeftNonePageAnim(int w, int h, View view, OnPageChangeListener listener) {
        super(w, h, view, listener);
    }

    @Override
    public void drawStatic(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, 0, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, 0, null);
        }
    }

    @Override
    public void drawMove(Canvas canvas) {
        if (isCancel){
            canvas.drawBitmap(mCurBitmap, 0, 0, null);
        }else {
            canvas.drawBitmap(mNextBitmap, 0, 0, null);
        }
    }

    @Override
    public void startAnim() {
    }
}
