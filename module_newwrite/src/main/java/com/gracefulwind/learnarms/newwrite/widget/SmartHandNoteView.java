package com.gracefulwind.learnarms.newwrite.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.Toast;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.gracefulwind.learnarms.newwrite.mvp.contract.MainContract;
import com.gracefulwind.learnarms.newwrite.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.newwrite.widget.doodle.OperationPresenter;
import com.gracefulwind.learnarms.newwrite.widget.edit.LinesView;
import com.gracefulwind.learnarms.newwrite.widget.edit.SmartTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gracefulwind.learnarms.newwrite.widget.doodle.OperationPresenter.MODE_DOODLE;

/**
 * @ClassName: SmartHandWriteView
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class SmartHandNoteView extends ScrollView implements SmartHandNote {
    public static final String TAG = "SmartHandNoteView";

//    public static final int MODE_SCALE = 0x00000000;
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;
    public static final int MODE_ERASER = 0x00000003;
    public static final int MODE_TEXT_BOX = 0x00000004;

    private Context mContext;

    private int mViewMode;
    private List<Smartable> smartViewList = new ArrayList<>();
    @BindView(R2.id.nvshn_lv_lines_view)
    LinesView mLinesView;
    @BindView(R2.id.nvshn_stv_smart_text_view)
    SmartTextView mSmartTextView;
    @BindView(R2.id.nvshn_dv_doodle_view)
    DoodleView mDoodleView;
//    TextBoxContainer mTextBoxContainer;

    public SmartHandNoteView(Context context) {
        this(context, null);
    }

    public SmartHandNoteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartHandNoteView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SmartHandNoteView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View childView = UiUtil.inflate(R.layout.newwrite_view_smart_hand_note);
        setFillViewport(true);
        bindView(childView);
        smartViewList.add(mLinesView);
        smartViewList.add(mSmartTextView);
    }

    private void bindView(View childView) {
        ButterKnife.bind(this, childView);
        addView(childView);

    }


//===================================================================================================
    /**
     * 操作模式切换
     * */
    public void setViewMode(int viewMode) {
        mViewMode = viewMode;
        switch (viewMode) {
//            case MODE_SCALE:
//                mSmartTextView.setEnabled(false);
//                mDoodleView.setEnabled(false);
//                mTextBoxContainer.setEnabled(false);
////                setTextBoxEnable(false);
//                break;
            case MODE_TEXT:
                mSmartTextView.setEnabled(true);
                mSmartTextView.requestFocus();
                mDoodleView.setEnabled(false);
//                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
//                doTranslateTo(0,0);
//                doScale(1);
                showSoftKeyboard();
                break;
            case MODE_DOODLE:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                mDoodleView.setPaintEditMode(OperationPresenter.MODE_DOODLE);
//                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
            case MODE_ERASER:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                mDoodleView.setPaintEditMode(OperationPresenter.MODE_ERASER);
//                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
            case MODE_TEXT_BOX:
                //设置文本框可编辑模式
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
//                mTextBoxContainer.setEnabled(true);
//                setTextBoxEnable(true);
                break;
            default:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
//                mTextBoxContainer.setEnabled(false);
//                setTextBoxEnable(false);
                break;
        }
        for (Smartable smartView : smartViewList) {
            View view = (View) smartView;
            view.invalidate();
        }
    }

    private void showSoftKeyboard() {
        InputMethodManager manager = ((InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
        if (manager != null) manager.showSoftInput(mSmartTextView, 0);
    }

//===================================================================================================
    @Override
    public void refreshLineView() {
        mSmartTextView.invalidate();
    }

    @Override
    public int getLineHeight() {
        return mSmartTextView.getLineHeight();
    }

    @Override
    public int getLineCount() {
        return mSmartTextView.getLineCount();
    }

    @Override
    public int getTextViewHeight() {
        return mSmartTextView.getHeight();
    }

    @Override
    public int getLineBounds(int line, Rect bounds) {
        return mSmartTextView.getLineBounds(line, bounds);
    }

    @Override
    public void smartScrollTo(int x, int y, View startView) {
        for(Smartable smart : smartViewList){
//            if(startView == smart){
//                continue;
//            }
            smart.smartScrollTo(x, y);
        }
    }

    @Override
    public void changeBackgroundHeight(int height) {
        int doodleHeight = mDoodleView.getHeight();
        if(height > doodleHeight){
            for(Smartable smartView : smartViewList){
                if(smartView instanceof SmartTextView){
                    continue;
                }else {
                    View view = (View) smartView;
                    setChildHeight(view, height);
                }
            }
        }
    }

    boolean firstToastFlag = false;
    private void setChildHeight(View targetView, int height) {
        int width = targetView.getWidth();
        int maxHeight = (int) (width * Constants.a4Ratio);
        ViewGroup.LayoutParams childLayoutParams = targetView.getLayoutParams();
        if (0 == width || maxHeight > height) {
            if(childLayoutParams.height < height){
                childLayoutParams.height = height;
            }
        } else {
            childLayoutParams.height = maxHeight;
            if (!firstToastFlag) {
                firstToastFlag = true;
                Toast.makeText(mContext, "已达到最大长度！", Toast.LENGTH_SHORT).show();
            }
        }
        targetView.setLayoutParams(childLayoutParams);
    }

    public void test() {
        if(mSmartTextView.getVisibility() == View.VISIBLE){
            mSmartTextView.setVisibility(GONE);
        }else {
            mSmartTextView.setVisibility(VISIBLE);
        }
    }
}
