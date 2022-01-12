package com.gracefulwind.learnarms.newwrite.widget.textbox;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.gracefulwind.learnarms.commonsdk.utils.KeyboardUtil;
import com.gracefulwind.learnarms.commonsdk.utils.StringUtil;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;

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
//    private SmartHandNoteView mParent;
    private TextBoxSupportView mSupportView;
    //可编辑文本框集合
    private List<TextBoxView> mEditViewList = new ArrayList<>();
    private static float minDisSquare = 40 * 40;

    public TextBoxManager(Context context, TextBoxContainer container, TextBoxSupportView supportView){
        mContext = context;
        mContainer = container;
        mSupportView = supportView;
    }

    public void addTextBox(float prevX, float prevY, float movedX, float movedY) {
        if((movedX - prevX) * (movedX - prevX) + (movedY - prevY) * (movedY - prevY) < minDisSquare){
            return;
        }
        //todo:wd 健壮性
        ViewParent realParent = getRealParent();
        SmartHandNoteView parent = (SmartHandNoteView) realParent;

        float startX = Math.min(prevX, movedX);
        float startY = Math.min(prevY, movedY);
        float width = Math.abs(prevX - movedX);
        int parentWidth = parent.getWidth();
        float minWidth = TextBoxView.minWidth;
        if(width < minWidth){
            width = minWidth;
        }
        if(startX + width > parentWidth){
            startX = parentWidth - width;
        }
        addNewBox((int) startX, (int) startY, (int) width, null);
    }

    private void addNewBox(int startX, int startY, int width, String text) {
        ViewParent realParent = getRealParent();
        SmartHandNoteView parent = (SmartHandNoteView) realParent;
        TextBoxView editText = new TextBoxView(mContext, parent, this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, FrameLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(layoutParams);
        layoutParams.leftMargin = startX;
        layoutParams.topMargin = startY;
        if(!StringUtil.isEmpty(text)){
            editText.setText(text);
        }
        mEditViewList.add(editText);
        mContainer.addView(editText);
        callSmartHandViewChanged();
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
        callSmartHandViewChanged();
    }

    public void clearFocus() {
        KeyboardUtil.hideSoftKeyboard(mContext, mEditViewList);
    }

    public ViewParent getRealParent(){
        return mContainer.getRealParent();
    }

    /**
     * 增删全在manager里统一响应，拖拽和改变宽度不涉及到manager，就在内部处理
     * */
    private void callSmartHandViewChanged() {
        ViewParent realParent = mContainer.getRealParent();
        //能 instanceof 就代表肯定不为null了
        if(realParent instanceof SmartHandNote){
            SmartHandNote parent = (SmartHandNote) realParent;
            parent.setChanged(true);
        }
    }

    public List<TextBoxBean> getTextBoxContain() {
        List<TextBoxBean> containList = new ArrayList<>();
        for (TextBoxView view: mEditViewList) {
            TextBoxBean bean = new TextBoxBean();
            bean.str = view.getText();
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
            bean.disX = lp.leftMargin;
            bean.disY = lp.topMargin;
            bean.width = view.getWidth();
            containList.add(bean);
        }
        return containList;
    }

    /**
     * 反显textBox的内容
     */
    public void setTextBoxContain(List<TextBoxBean> textBoxContain) {
        for (TextBoxBean boxBean: textBoxContain) {
            addNewBox(boxBean.disX, boxBean.disY, boxBean.width, boxBean.str);
        }
    }

    /**
     * 清空textBox的内容
     */
    public void clearTextBoxContain() {
//        mEditViewList.add(editText);
//        mContainer.addView(editText);
        while(mEditViewList.size() > 0){
            TextBoxView textBoxView = mEditViewList.remove(0);
            mContainer.removeView(textBoxView);
        }
        callSmartHandViewChanged();
    }
}
