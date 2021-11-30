package com.gracefulwind.learnarms.newwrite.widget.doodle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.ColorInt;

import java.util.List;

/**
 * @ClassName: Doodle
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/17
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/17
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public interface Doodle {

    //==for presenter=========================
    void refreshUi();
    int getHeight();
    int getWidth();
    void draw(Canvas canvas);
    void initCanvas(Canvas canvas, int backgroundColor);

    //==for views======================
    void setLayoutParams(ViewGroup.LayoutParams params);
    ViewParent getRealParent();

    //==for out control===================
    void setPaintEditMode(@EditMode int editMode);
    @EditMode int getEditMode();
    boolean isModeDoodle();
    boolean canCancel();
    boolean canRedo();
    boolean cancelLastDraw();
    boolean redoLastDraw();
    void setPaintColor(@ColorInt int paintColor);
    @ColorInt int getPaintColor();
    void setPaintSize(float paintSize);
    float getPaintSize();
    Bitmap getBitmap();
    void setBitmap(Bitmap bitmap);

    void setOnPathChangedListener(OnPathChangedListener listener);
    OnPathChangedListener getOnPathChangedListener();

    interface OnPathChangedListener{
        void onCancelListChanged(List<Operation> list);
        void onRedoListChanged(List<Operation> list);
    }
}
