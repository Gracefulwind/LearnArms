package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.BitmapUtil;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.Operation;
import com.gracefulwind.learnarms.reader.widget.doodle.OperationPresenter;
import com.jess.arms.di.component.AppComponent;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: HandNoteActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/24
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/24
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.HAND_NOTE_ACTIVITY)
public class HandNoteActivity extends MyBaseActivity {

    @BindView(R2.id.rahn_shn_hand_note)
    SmartHandNoteView rahnShnHandNote;
    private Bitmap viewDoodle;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.reader_activity_hand_note;
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        DoodleView.OnPathChangedListener listener = new DoodleView.OnPathChangedListener() {
            @Override
            public void onCancelListChanged(List<Operation> list) {
                LogUtil.e(TAG, "cancel change : " + list.toString());
            }

            @Override
            public void onRedoListChanged(List<Operation> list) {
                LogUtil.e(TAG, "redo change : " + list.toString());
            }
        };
        rahnShnHandNote.setOnPathChangedListener(listener);
    }

    @OnClick({R2.id.rahn_btn_keyboard, R2.id.rahn_btn_doodle, R2.id.rahn_btn_scale, R2.id.rahn_btn_text_size
            , R2.id.rahn_btn_text_color, R2.id.rahn_btn_cancel, R2.id.rahn_btn_uncancel, R2.id.rahn_btn_test
            , R2.id.rahn_btn_save_text, R2.id.rahn_btn_save_doodle, R2.id.rahn_btn_save_all, R2.id.rahn_btn_show_all})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.rahn_btn_keyboard == id){
            rahnShnHandNote.setViewMode(SmartHandNoteView.MODE_TEXT);
        }else if(R.id.rahn_btn_doodle == id){
            rahnShnHandNote.setViewMode(SmartHandNoteView.MODE_DOODLE);
        }else if(R.id.rahn_btn_scale == id){
            rahnShnHandNote.setDoodleMode(OperationPresenter.MODE_ERASER);
        }else if(R.id.rahn_btn_text_size == id){
            rahnShnHandNote.setTextViewSize(12);
        }else if(R.id.rahn_btn_text_color == id){

        }else if(R.id.rahn_btn_cancel == id){
            rahnShnHandNote.cancelLastDraw();
        }else if(R.id.rahn_btn_uncancel == id){
            rahnShnHandNote.redoLastDraw();
        }else if(R.id.rahn_btn_test == id){
            rahnShnHandNote.test();
        }else if(R.id.rahn_btn_save_text == id){
            Editable viewText = rahnShnHandNote.getViewText();
        }else if(R.id.rahn_btn_save_doodle == id){
            viewDoodle = rahnShnHandNote.getDoodleBitmap();
            if(null != viewDoodle)
                BitmapUtil.saveBitmap(this, viewDoodle);
//            viewDoodle.recycle();
        }else if(R.id.rahn_btn_save_all == id){
            Bitmap viewBitmap = rahnShnHandNote.getBitmap(false);
            if(null != viewBitmap)
                BitmapUtil.saveBitmap(this, viewBitmap);
//            viewBitmap.recycle();
        }else if(R.id.rahn_btn_show_all == id){
//            rahnShnHandNote.test(viewDoodle);
            rahnShnHandNote.setViewMode(SmartHandNoteView.MODE_TEXT_BOX);
        }/*else if(R.id.rahn_btn_doodle == id){

        }*/
    }
}
