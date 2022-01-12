package com.gracefulwind.learnarms.newwrite.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.BitmapUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.newwrite.R;
import com.gracefulwind.learnarms.newwrite.R2;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNote;
import com.gracefulwind.learnarms.newwrite.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.newwrite.widget.textbox.TextBoxBean;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    private Bitmap viewDoodle;


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

    List<TextBoxBean> textBoxContain = new ArrayList<>();

    @OnClick({R2.id.natnw_btn_click1, R2.id.natnw_btn_click2, R2.id.natnw_btn_click3, R2.id.natnw_btn_click4
            , R2.id.natnw_btn_click2_1, R2.id.natnw_btn_click2_2, R2.id.natnw_btn_click2_3, R2.id.natnw_btn_click2_4
            , R2.id.natnw_btn_click3_1, R2.id.natnw_btn_click3_2, R2.id.natnw_btn_click3_3, R2.id.natnw_btn_click3_4
    })
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.natnw_btn_click1 == id){
            LogUtil.e(TAG, "====");
            LogUtil.e(TAG, "====");
            LogUtil.e(TAG, "====");

        }else if(R.id.natnw_btn_click2 == id){
            smartHandNoteView.test();
        }else if(R.id.natnw_btn_click3 == id){
            smartHandNoteView.test2();
        }else if(R.id.natnw_btn_click4 == id){
//            Utils.navigation(this, RouterHub.Weather.WEATHER_ACTIVITY);
            viewDoodle = smartHandNoteView.getDoodleBitmap();
            if(null != viewDoodle) {
                String s = BitmapUtil.saveBitmap(this, viewDoodle);
                if(null == s){
                    Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(R.id.natnw_btn_click2_1 == id){
            //写字板
            smartHandNoteView.setViewMode(SmartHandNote.MODE_TEXT);
        }else if(R.id.natnw_btn_click2_2 == id){
            //涂鸦
            smartHandNoteView.setViewMode(SmartHandNote.MODE_DOODLE);
        }else if(R.id.natnw_btn_click2_3 == id){
            //橡皮擦
            smartHandNoteView.setViewMode(SmartHandNote.MODE_ERASER);
        }else if(R.id.natnw_btn_click2_4 == id){
            //文本框
            smartHandNoteView.setViewMode(SmartHandNote.MODE_TEXT_BOX);
        }else if(R.id.natnw_btn_click3_1 == id){
            textBoxContain = smartHandNoteView.getTextBoxContain();
            System.out.println("=======");
            System.out.println("=======");
            System.out.println("=======");
        }else if(R.id.natnw_btn_click3_2 == id){
            smartHandNoteView.setTextBoxContain(textBoxContain);
        }else if(R.id.natnw_btn_click3_3 == id){
            smartHandNoteView.clearTextBoxContain();
        }else if(R.id.natnw_btn_click3_4 == id){

        }else if(R.id.natnw_btn_click4 == id){

        }
    }
}
