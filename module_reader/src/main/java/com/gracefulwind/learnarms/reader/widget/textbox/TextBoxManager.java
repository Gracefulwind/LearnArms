package com.gracefulwind.learnarms.reader.widget.textbox;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gracefulwind.learnarms.commonsdk.utils.KeyboardUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.widget.SmartHandNoteView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TextBoxManager
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/26
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/26
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TextBoxManager {

    private Context mContext;
    private TextBoxContainer mContainer;
    private SmartHandNoteView mParent;
    private TextBoxSupportView mSupportView;
    //可编辑文本框集合
    private List<TextBoxView> mEditViewList = new ArrayList<>();

    public TextBoxManager(Context context, TextBoxContainer container, SmartHandNoteView parent, TextBoxSupportView supportView){
        mContext = context;
        mContainer = container;
        mParent = parent;
        mSupportView = supportView;
    }

    public void addTextBox(float prevX, float prevY, float movedX, float movedY) {
//        EditText editText = new EditText(mContext);
        TextBoxView editText = new TextBoxView(mContext, mParent, this);
        float startX = Math.min(prevX, movedX);
        float startY = Math.min(prevY, movedY);
        if((movedX - prevX) * (movedX - prevX) + (movedY - prevY) * (movedY - prevY) < 400){
            return;
        }
        float width = Math.abs(prevX - movedX);
        int parentWidth = mParent.getWidth();
        float minWidth = TextBoxView.minWidth;
        if(width < minWidth){
            width = minWidth;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) width, FrameLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        if(startX + width > parentWidth){
            startX = parentWidth - width;
        }
        layoutParams.leftMargin = (int) startX;
        layoutParams.topMargin = (int) startY;
        mEditViewList.add(editText);
        mContainer.addView(editText);
    }

    /**
     *
     * 当非文本框模式时去除焦点，选择状态
     * */
    public void setEnable(boolean enabled) {
        if(!enabled){
            for (TextBoxView view : mEditViewList) {
                view.clearFocus();
                view.setBounds(enabled);
            }
        }else {
            for (TextBoxView view : mEditViewList) {
                view.setBounds(enabled);
            }
        }
        //todo:wd
        //1.当可用时子控件显示按钮和边框
        //2.当不可用时子控件隐藏按钮和边框
    }

    public void test() {
        System.out.println("==========");
        System.out.println("==========");
        System.out.println("==========");
    }

    public void deleteTextBox(TextBoxView textBox) {
//        for (int x = 0; x < mEditViewList.size(); x++){
//            View view = mEditViewList.get(x);
//
//        }
        mEditViewList.remove(textBox);
        mContainer.removeView(textBox);
    }

    public void clearFocus() {
        KeyboardUtil.hideSoftKeyboard(mContext, mEditViewList);
    }
}
