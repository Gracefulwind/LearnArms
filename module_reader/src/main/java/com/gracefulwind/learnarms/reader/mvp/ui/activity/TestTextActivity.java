package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.utils.ChildTest;
import com.gracefulwind.learnarms.reader.widget.TestTextView;
import com.gracefulwind.learnarms.reader.widget.edit.SmartTextView;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: TestTextActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/10/13
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/10/13
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.TEST_TEXT_ACTIVITY)
public class TestTextActivity extends MyBaseActivity {
    
    @BindView(R2.id.ratt_ttv_container)
    TestTextView rattTtvContainer;
    @BindView(R2.id.ratt_stv_container)
    SmartTextView rattStvContainer;
    @BindView(R2.id.ratt_tv_test)
    TextView rattTvTest;
    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.reader_activity_test_text;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
//        rattTtvContainer.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                LogUtil.e(TAG, "beforeTextChanged , text = " + s);
////                rattTtvContainer.setText(s + "1");
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                LogUtil.e(TAG, "onTextChanged , text = " + s);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                LogUtil.e(TAG, "afterTextChanged , text = " + s);
//                LogUtil.e(TAG, "============");
//                rattTtvContainer.removeTextChangedListener(this);
//
//                int selectionStart = rattTtvContainer.getSelectionStart();
//                int selectionEnd = rattTtvContainer.getSelectionEnd();
//                rattTtvContainer.setText(s);
//                rattTtvContainer.setSelection(selectionStart, selectionEnd);
//
//                rattTtvContainer.addTextChangedListener(this);
////                rattTtvContainer.invalidate();
////                rattTtvContainer.forceLayout();
////                rattTtvContainer.requestLayout();
//            }
//        });
    }

    boolean flag = true;
    int line = 0;
    @OnClick({R2.id.ratt_btn0, R2.id.ratt_btn1, R2.id.ratt_btn2, R2.id.ratt_btn3})
    public void onCLick(View view){
        int id = view.getId();
//        TextView targetView = rattTvTest;
//        TextView targetView = rattStvContainer;
        TextView targetView = rattTtvContainer;
        if(R.id.ratt_btn0 == id){
            int height = targetView.getHeight();
            Rect rect = new Rect();
            if(flag){
                line = targetView.getLineCount() - 1;
            }
            targetView.getLineBounds(line, rect);
            LogUtil.e(TAG, "targetView height = " + height + " , rect = " + rect);
        }else if(R.id.ratt_btn1 == id){
            //mLayout.getLineBounds与textview的getLineBounds值相同
            Layout layout = targetView.getLayout();
            Rect rect = new Rect();
            if(flag){
                line = targetView.getLineCount() - 1;
            }
            layout.getLineBounds(line, rect);
            LogUtil.e(TAG, "layout height = " + layout.getHeight() + " , rect = " + rect);
        }else if(R.id.ratt_btn2 == id){
//            ChildTest childTest = new ChildTest();
//            childTest.log();
        }else if(R.id.ratt_btn3 == id){
//            targetView.setText();
            targetView.forceLayout();
            targetView.requestLayout();
            targetView.invalidate();
        }
    }
}
