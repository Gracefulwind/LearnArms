package com.gracefulwind.learnarms.reader.widget;

/**
 * @ClassName: Smartable
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/26
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/26
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public interface Smartable {
//    void setViewHeightWithTextView(int textViewHeight);
    void smartTranslateTo(float translateX, float translateY);
    void smartTranslateBy(float dX, float dY);
    void smartScaleTo(float pivotX, float pivotY, float scaleX, float scaleY);
}
