package com.gracefulwind.learnarms.reader.widget.textbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.SmartHandNoteView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: TextBoxView
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/26
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/26
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class TextBoxView extends FrameLayout {
    public static final String TAG = "TextBoxView";

    public static float minWidth = UiUtil.dip2px(32);
    public static float minHeight = UiUtil.dip2px(32);
    private Context mContext;
    private SmartHandNoteView mParent;
    private TextBoxManager mManager;
    private View mRootView;

    @BindView(R2.id.vtb_iv_button_move)
    View vtbIvButtonMove;
    @BindView(R2.id.vtb_iv_button_delete)
    View vtbIvButtonDelete;
    @BindView(R2.id.vtb_iv_button_resize)
    View vtbIvButtonResize;
    //edit
    @BindView(R2.id.vtb_et_edit)
    EditText vtbEtEdit;
    boolean editable = true;

    public TextBoxView(Context context, SmartHandNoteView parent, TextBoxManager manager) {
        this(context, parent, manager, null);
    }

    public TextBoxView(Context context, SmartHandNoteView parent, TextBoxManager manager, AttributeSet attrs) {
        this(context, parent, manager, attrs, 0);
    }

    public TextBoxView(Context context, SmartHandNoteView parent, TextBoxManager manager, AttributeSet attrs, int defStyleAttr) {
        this(context, parent, manager, attrs, defStyleAttr, 0);
    }

    public TextBoxView(Context context, SmartHandNoteView parent, TextBoxManager manager, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, parent, manager);
    }

    //init
    private void initView(Context context, SmartHandNoteView parent, TextBoxManager manager) {
        mContext = context;
        mParent = parent;
        mManager = manager;
        mRootView = UiUtil.inflate(R.layout.view_text_box);
        addView(mRootView);
        ButterKnife.bind(this, mRootView);
        //似乎没用欸
        setMinimumWidth((int)minWidth);
        vtbEtEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editable = hasFocus;
                setButtonVisibility(hasFocus);
//                if(!hasFocus && StringUtil.isEmpty(vtbEtEdit.getText().toString().replace(" ", ""))){
//                    mManager.deleteTextBox(TextBoxView.this);
//                }
            }
        });
        setViewTouchEvent();
        setDefaultEditable();
//        LogUtil.e(TAG, "initView: 1111111");
//        post(new Runnable() {
//            @Override
//            public void run() {
//                LogUtil.e(TAG, "initView: 3333333");
//            }
//        });
//        LogUtil.e(TAG, "initView: 22222222");
    }

    private void setDefaultEditable() {
        if(editable){
            vtbEtEdit.requestFocus();
        }
        setButtonVisibility(editable);
    }

    private void setButtonVisibility(boolean hasFocus) {
        if(hasFocus){
            vtbIvButtonMove.setVisibility(VISIBLE);
            vtbIvButtonDelete.setVisibility(VISIBLE);
            vtbIvButtonResize.setVisibility(VISIBLE);
        }else {
            vtbIvButtonMove.setVisibility(INVISIBLE);
            vtbIvButtonDelete.setVisibility(INVISIBLE);
            vtbIvButtonResize.setVisibility(INVISIBLE);
        }
    }

    private void setViewTouchEvent() {
        vtbIvButtonMove.setOnTouchListener(new OnTouchListener() {
            private float mPrevY;
            private float mPrevX;
            private int baseMarginLeft;
            private int baseMarginTop;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //似乎不需要了，不可见的时候touch事件无效
//                if(!editable){
//                    return false;
//                }
//                return mTouchGestureDetector.onTouchEvent(event);
                int action = event.getAction();
                float x = event.getRawX();
                float y = event.getRawY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mPrevX = x;
                        mPrevY = y;
                        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
                        baseMarginLeft = layoutParams.leftMargin;
                        baseMarginTop = layoutParams.topMargin;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (doActionMove(x, y)) return true;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }

            private boolean doActionMove(float x, float y) {
//                LogUtil.e(TAG, "ACTION_MOVE == mPrevX = " + mPrevX + " , newX = " + x);
                float scaleRate = mParent.getScaleRate();
                float distanceX = (x - mPrevX) / scaleRate;
                float distanceY = (y - mPrevY) / scaleRate;
                float disSquare = distanceX * distanceX + distanceY * distanceY;
                if(disSquare <= 9){
                    return true;
                }
                //设置边界
                ViewGroup parent = (ViewGroup) getParent();
                int width = getWidth();
                int height = getHeight();
                int parentWidth = parent.getWidth();
                int parentHeight = parent.getHeight();
                LayoutParams layoutParams = (LayoutParams) getLayoutParams();
                int targetLeftMargin = (int) (baseMarginLeft + distanceX);
                int targetTopMargin = (int) (baseMarginTop + distanceY);
                if(targetLeftMargin + width > parentWidth){
                    targetLeftMargin = parentWidth - width;
                }
                if(targetLeftMargin < 0){
                    targetLeftMargin = 0;
                }
                if(targetTopMargin + height > parentHeight){
                    targetTopMargin = parentHeight - height;
                }
                if(targetTopMargin < 0){
                    targetTopMargin = 0;
                }
                layoutParams.leftMargin = targetLeftMargin;
                layoutParams.topMargin = targetTopMargin;
                setLayoutParams(layoutParams);
                return false;
            }
        });
        vtbIvButtonDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.deleteTextBox(TextBoxView.this);
            }
        });
        vtbIvButtonResize.setOnTouchListener(new OnTouchListener() {
            private float mPrevY;
            private float mPrevX;
            private int baseWidth;
            private int baseHeight;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                float x = event.getRawX();
                float y = event.getRawY();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mPrevX = x;
                        mPrevY = y;
                        baseWidth = getWidth();
                        baseHeight = getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if (doActionMove(x, y)) return true;
                        //只用处理高度
                        float scaleRate = mParent.getScaleRate();
                        float distanceX = (x - mPrevX) / scaleRate;
                        float distanceY = (y - mPrevY) / scaleRate;
                        //设置边界
                        int targetWidth = (int) (baseWidth + distanceX);
                        setViewWidth(targetWidth);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 设置宽度的方法。
     * */
    public void setViewWidth(int targetWidth) {
        ViewGroup parent = (ViewGroup) getParent();
        int parentWidth = parent.getWidth();
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        int marginLeft = layoutParams.leftMargin;
        if(marginLeft + targetWidth > parentWidth){
            targetWidth = parentWidth - marginLeft;
        }
        if(targetWidth < minWidth){
            targetWidth = (int) minWidth;
        }
        layoutParams.width = targetWidth;
        setLayoutParams(layoutParams);
    }

    public void setBounds(boolean enabled) {
        if(enabled){
            vtbEtEdit.setBackgroundResource(R.drawable.shape_frame_light);
        }else {
            vtbEtEdit.setBackground(null);
        }
    }

//    @OnClick({R2.id.vtb_iv_left_top_button, R2.id.vtb_iv_right_top_button, R2.id.vtb_iv_right_bottom_button})
//    public void onViewClicked(View view) {
//
//    }

}
