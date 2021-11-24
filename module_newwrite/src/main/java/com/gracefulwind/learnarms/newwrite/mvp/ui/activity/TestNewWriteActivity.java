package com.gracefulwind.learnarms.newwrite.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: TestNewWriteActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/22
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.NewWrite.TEST_NEW_WRITE_ACTIVITY)
public class TestNewWriteActivity extends BaseActivity {
    public static final String TAG = "TestNewWriteActivity";

    @BindView(R2.id.natnw_shv_hand_view)
    SmartHandNoteView smartHandNoteView;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.newwrite_activity_test_new_write;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @OnClick({R2.id.natnw_btn_click1, R2.id.natnw_btn_click2, R2.id.natnw_btn_click3, R2.id.natnw_btn_click4})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.natnw_btn_click1 == id){
            LogUtil.e(TAG, "====");
            LogUtil.e(TAG, "====");
            LogUtil.e(TAG, "====");

        }else if(R.id.natnw_btn_click2 == id){

        }else if(R.id.natnw_btn_click3 == id){

        }else if(R.id.natnw_btn_click4 == id){

        }else if(R.id.natnw_btn_click4 == id){

        }
    }
}
