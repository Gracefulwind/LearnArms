package com.gracefulwind.learnarms.newwrite.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.gracefulwind.learnarms.newwrite.mvp.contract.MainContract;
import com.gracefulwind.learnarms.newwrite.widget.edit.LinesView;
import com.gracefulwind.learnarms.newwrite.widget.edit.SmartTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public static final int MODE_TEXT_BOX = 0x00000003;

    private Context mContext;

    private int mViewMode;
    private List<Smartable> smartViewList = new ArrayList<>();
    @BindView(R2.id.nvshn_lv_lines_view)
    LinesView mLinesView;
    @BindView(R2.id.nvshn_stv_smart_text_view)
    SmartTextView mSmartTextView;
    //todo:wd 等2模块搞定了放出
//    private DoodleView mDoodleView;
//    private TextBoxContainer mTextBoxContainer;

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
}
