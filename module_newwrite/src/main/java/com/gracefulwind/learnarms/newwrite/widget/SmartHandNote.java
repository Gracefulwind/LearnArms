package com.gracefulwind.learnarms.newwrite.widget;

import android.graphics.Rect;
import android.view.View;

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
    void refreshLineView();
    int getLineHeight();
    int getLineCount();
    int getTextViewHeight();
    int getLineBounds(int line, Rect bounds);
    void smartScrollTo(int x, int y, View startView);
}
