package com.gracefulwind.learnarms.newwrite.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gracefulwind.learnarms.commonsdk.core.Constants;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.gracefulwind.learnarms.newwrite.mvp.contract.MainContract;
import com.gracefulwind.learnarms.newwrite.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.newwrite.widget.doodle.OperationPresenter;
import com.gracefulwind.learnarms.newwrite.widget.edit.LinesView;
import com.gracefulwind.learnarms.newwrite.widget.edit.SmartTextView;
import com.gracefulwind.learnarms.newwrite.widget.textbox.TextBoxBean;
import com.gracefulwind.learnarms.newwrite.widget.textbox.TextBoxContainer;

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


    private Context mContext;

    private int mViewMode;
    private List<Smartable> smartViewList = new ArrayList<>();
    @BindView(R2.id.nvshn_lv_lines_view)
    LinesView mLinesView;
    @BindView(R2.id.nvshn_stv_smart_text_view)
    SmartTextView mSmartTextView;
    @BindView(R2.id.nvshn_dv_doodle_view)
    DoodleView mDoodleView;
    @BindView(R2.id.nvshn_tbc_text_box)
    TextBoxContainer mTextBoxContainer;

 //---------------------------------------
    private boolean isChanged = false;
    private ContentChangedListener mContentChangedListener;

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
//        ConstraintLayout childGroup = (ConstraintLayout) childView;
//        mTextBoxContainer = new TextBoxContainer(mContext, this);
        setFillViewport(true);
        bindView(childView);
        smartViewList.add(mLinesView);
        smartViewList.add(mSmartTextView);
        smartViewList.add(mDoodleView);
        smartViewList.add(mTextBoxContainer);
        mDoodleView.setControlParent(this);
    }

    private void bindView(View childView) {
        ButterKnife.bind(this, childView);
        addView(childView);

    }


//===================================================================================================
    /**
     * 操作模式切换
     * */
    public void setViewMode(@ViewMode int viewMode) {
        mViewMode = viewMode;
        switch (viewMode) {
//            case MODE_SCALE:
//                mSmartTextView.setEnabled(false);
//                mDoodleView.setEnabled(false);
//                mTextBoxContainer.setEnabled(false);
//                break;
            case MODE_TEXT:
                mSmartTextView.setEnabled(true);
                mSmartTextView.requestFocus();
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(false);
//                doTranslateTo(0,0);
//                doScale(1);
                showSoftKeyboard();
                break;
            case MODE_DOODLE:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                mDoodleView.setPaintEditMode(OperationPresenter.MODE_DOODLE);
                mTextBoxContainer.setEnabled(false);
                break;
            case MODE_ERASER:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(true);
                mDoodleView.setPaintEditMode(OperationPresenter.MODE_ERASER);
                mTextBoxContainer.setEnabled(false);
                break;
            case MODE_TEXT_BOX:
                //设置文本框可编辑模式
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(true);
                break;
            default:
                mSmartTextView.setEnabled(false);
                mDoodleView.setEnabled(false);
                mTextBoxContainer.setEnabled(false);
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
//        for(Smartable smart : smartViewList){
////            if(startView == smart){
////                continue;
////            }
//            smart.smartScrollTo(x, y);
//        }
        scrollTo(x, y);
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

//==对外接口==================================================================================================
    /**
     * needCallback:是否要调用内容变化的回调
     */
    public void setText(CharSequence text, boolean needCallback) {
        mSmartTextView.setText(text, needCallback);
    }

    /**
     * 设置textview字号
     */
    public void setTextViewSize(float size) {
        setTextViewSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    public void setTextViewSize(int unit, float size) {
        mSmartTextView.setTextSize(unit, size);
    }

    /**
     * 设置textview文字颜色
     */
    public void setTextViewColor(int color) {
        mSmartTextView.setTextColor(color);
    }

    /**
     * 设置画笔粗细
     */
    public void setDoodlePaintSize(float paintSize) {
        mDoodleView.setPaintSize(paintSize);
    }

    /**
     * 设置画笔颜色
     */
    public void setDoodlePaintColor(int color) {
        mDoodleView.setPaintColor(color);
    }

    public void setDoodlePaint(float paintSize, int color) {
        setDoodlePaintSize(paintSize);
        setDoodlePaintColor(color);
    }

    /**
     * 撤销最后一笔
     */
    public boolean cancelLastDraw() {
        return mDoodleView.cancelLastDraw();
    }

    /**
     * 反撤销最后一笔
     */
    public boolean redoLastDraw() {
        return mDoodleView.redoLastDraw();
    }

    /**
     * 是否可撤销
     */
    public boolean canCancel() {
        return mDoodleView.canCancel();
    }

    /**
     * 是否可 反 撤销
     */
    public boolean canRedo() {
        return mDoodleView.canRedo();
    }

    public void setOnPathChangedListener(DoodleView.OnPathChangedListener listener) {
        mDoodleView.setOnPathChangedListener(listener);
    }

    public DoodleView.OnPathChangedListener getOnPathChangedListener() {
        return mDoodleView.getOnPathChangedListener();
    }

    /**
     * 获取涂鸦画板的文字
     */
    public Editable getText() {
        return mSmartTextView.getText();
    }

    /**
     * 获取涂鸦的bitmap图
     */
    public Bitmap getDoodleBitmap() {
        return mDoodleView.getBitmap();
    }

    /**
     * 获取textBox的内容
     */
    public List<TextBoxBean> getTextBoxContain() {
        return mTextBoxContainer.getTextBoxContain();
    }

    /**
     * 获取带文字和涂鸦的图
     */
    public Bitmap getBitmap() {
        int doodleWidth = mDoodleView.getWidth();
        int doodleHeight = mDoodleView.getHeight();
        if (doodleWidth == 0) {
            return null;
        }
        int textWidth = mSmartTextView.getWidth();
        int leftOutWidth = (doodleWidth - textWidth) / 2;
        //create bitmap
        Bitmap bitmap = Bitmap.createBitmap(doodleWidth, doodleHeight, Constants.bitmapQuality);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        //draw textView
        boolean textEnabled = mSmartTextView.isEnabled();
        //close cursor
        mSmartTextView.setCursorVisible(false);
        if (!textEnabled) {
            mSmartTextView.setEnabled(true);
        }
        Bitmap textBitmap = mSmartTextView.getBitmap();
        //half leftWidth
        canvas.drawBitmap(textBitmap, leftOutWidth, 0f, null);
        mSmartTextView.setEnabled(textEnabled);
        mSmartTextView.setCursorVisible(true);
        //======================
        //draw doodleView
        boolean doodleEnable = mDoodleView.isEnabled();
        if (!doodleEnable) {
            mDoodleView.setEnabled(true);
        }
        Bitmap doodleBitmap = mDoodleView.getBitmap();
        canvas.drawBitmap(doodleBitmap, 0f, 0f, null);
        mDoodleView.setEnabled(doodleEnable);
        //draw textBox
        boolean textBoxEnabled = mTextBoxContainer.isEnabled();
        if (textBoxEnabled) {
            mTextBoxContainer.setEnabled(false);
        }
        Bitmap textBoxBitmap = mTextBoxContainer.getBitmap();
        canvas.drawBitmap(textBoxBitmap, 0f, 0f, null);
        mTextBoxContainer.setEnabled(textEnabled);
        return bitmap;
    }

    public int getDoodleViewHeight() {
        return mDoodleView.getHeight();
    }

    public void setBitmap(Bitmap bitmap) {
        int bitmapHeight = bitmap.getHeight();
        changeBackgroundHeight(bitmapHeight);
        mDoodleView.setBitmap(bitmap);
    }

//===一些其他的接口=================================================================================================
    /**
     * 当前的变动情况
     */
    @Override
    public boolean isChanged() {
        return isChanged;
    }

    /**
     * 每次暂存后手动调用开关
     */
    @Override
    public void setChanged(boolean changed) {
        isChanged = changed;
        LogUtil.e(TAG, "===== content changed, result = " + changed);
        if (null != mContentChangedListener) {
            mContentChangedListener.onContentChanged(changed);
        }
    }

    /**
     * 设置内容变动监听器
     */
    public void setContentChangedListener(ContentChangedListener listener) {
        mContentChangedListener = listener;
    }

    public interface ContentChangedListener {
        void onContentChanged(boolean changed);
    }
//====================================================================================================

    public void test() {
        if(mSmartTextView.getVisibility() == View.VISIBLE){
            mSmartTextView.setVisibility(GONE);
        }else {
            mSmartTextView.setVisibility(VISIBLE);
        }
    }

    public void test2() {
        System.out.println("===========");
        System.out.println("===========");
        System.out.println("===========");
    }
}
