package com.gracefulwind.learnarms.reader.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.EditMode;
import com.gracefulwind.learnarms.reader.widget.smart.SmartTextView;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @ClassName: MyHandWriteView
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/14
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/14
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MyHandWriteView extends FrameLayout {
    public static final int MODE_TEXT = 0x00000001;
    public static final int MODE_DOODLE = 0x00000002;

    @BindView(R2.id.vmhw_stv_text)
    SmartTextView vmhwStvText;
    @BindView(R2.id.vmhw_dv_doodle)
    DoodleView vmhwDvDoodle;
    private int mViewMode;

    public MyHandWriteView(@NonNull @NotNull Context context) {
        this(context, null);
    }

    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyHandWriteView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        View inflate = UiUtil.inflate(R.layout.view_my_hand_write, this);
        this.addView(inflate);
        Unbinder bind = ButterKnife.bind(this, inflate);
//        set attrs

        //init mode
        setViewMode(MODE_TEXT);
    }


    public void setViewMode(int mode){
        mViewMode = mode;
        switch (mode){
            case MODE_TEXT:
                vmhwStvText.setEnabled(true);
                vmhwStvText.requestFocus();
                vmhwDvDoodle.setEnabled(false);
                break;
            case MODE_DOODLE:
                vmhwStvText.setEnabled(false);
                vmhwDvDoodle.setEnabled(true);
                break;
        }
    }

    public int getViewMode(){
        return mViewMode;
    }

    public void cancelLastDraw(){
        vmhwDvDoodle.cancelLastDraw();
    }

    public void redoLastDraw(){
        vmhwDvDoodle.redoLastDraw();
    }

    public boolean isModeDoodle(){
        return vmhwDvDoodle.isModeDoodle();
    }

    public void setDoodleEditMode(@EditMode int doodleEditMode){
        vmhwDvDoodle.setEditMode(doodleEditMode);
    }
}
