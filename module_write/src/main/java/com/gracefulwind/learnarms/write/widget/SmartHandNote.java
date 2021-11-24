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
    void refreshLineView();
    int getLineHeight();
    int getLineCount();
    int getTextViewHeight();
    int getLineBounds(int line, Rect bounds);
}
