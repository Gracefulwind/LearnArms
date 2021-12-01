package com.gracefulwind.learnarms.write.widget;

import android.graphics.Rect;

/**
 * @ClassName: SmartHandWrite
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public interface SmartHandNote {
    //舍弃缩放了
//    int MODE_SCALE = 0x00000000;
    int MODE_TEXT = 0x00000001;
    int MODE_DOODLE = 0x00000002;
    int MODE_ERASER = 0x00000003;
    int MODE_TEXT_BOX = 0x00000004;

//==对外的接口==================================================================================================
    void setViewMode(@ViewMode int viewMode);

//==========================================================
    void refreshLineView();
    int getLineHeight();
    int getLineCount();
    int getTextViewHeight();
    int getLineBounds(int line, Rect bounds);
    void changeBackgroundHeight(int height);
    void smartScrollTo(float X, float Y);
    /**
     * smartHandNoteView变动监听控制接口
     * */
    void setChanged(boolean changed);
    boolean isChanged();
}
