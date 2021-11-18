package com.gracefulwind.learnarms.write.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;
import com.gracefulwind.learnarms.write.mvp.contract.SmartHandNoteContract;
import com.gracefulwind.learnarms.write.mvp.di.component.DaggerSmartHandNoteComponent;
import com.gracefulwind.learnarms.write.mvp.presenter.SmartHandNotePresenter;
import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.write.widget.doodle.OperationPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: SmartHandNoteActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/11/10
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/11/10
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.SmartWrite.SMART_HAND_NOTE_ACTIVITY)
public class SmartHandNoteActivity extends MyBaseActivity<SmartHandNotePresenter> implements SmartHandNoteContract.View{

    @BindView(R2.id.washn_shnv_hand_note)
    SmartHandNoteView washnShnHandNote;
    @BindView(R2.id.washn_ll_Top)
    LinearLayout washnLlTop;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerSmartHandNoteComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.write_activity_smart_hand_note;
    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
//        wawtLlTop.setZOrderOnTop();
    }

    @OnClick({R2.id.washn_btn_keyboard, R2.id.washn_btn_doodle, R2.id.washn_btn_eraser, R2.id.washn_btn_text_box
            , R2.id.washn_btn_test1, R2.id.washn_btn_test2, R2.id.washn_btn_cancel, R2.id.washn_btn_uncancel})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.washn_btn_keyboard == id){
            washnShnHandNote.setViewMode(SmartHandNoteView.MODE_TEXT);
        }else if(R.id.washn_btn_doodle == id){
            washnShnHandNote.setViewMode(SmartHandNoteView.MODE_DOODLE);
            washnShnHandNote.setDoodleMode(OperationPresenter.MODE_DOODLE);
        }else if(R.id.washn_btn_eraser == id){
            washnShnHandNote.setViewMode(SmartHandNoteView.MODE_DOODLE);
            washnShnHandNote.setDoodleMode(OperationPresenter.MODE_ERASER);
        }else if(R.id.washn_btn_text_box == id){
            washnShnHandNote.setViewMode(SmartHandNoteView.MODE_TEXT_BOX);
        }else if(R.id.washn_btn_test1 == id){
            washnShnHandNote.test();
//            washnShnHandNote.getDoodleBitmap();
        }else if(R.id.washn_btn_test2 == id){
            System.out.println("=======================");
            if(View.GONE == washnShnHandNote.getVisibility()){
                washnShnHandNote.setVisibility(View.VISIBLE);
            }else{
                washnShnHandNote.setVisibility(View.GONE);
            }
        }else if(R.id.washn_btn_cancel == id){
            washnShnHandNote.cancelLastDraw();
        }else if(R.id.washn_btn_uncancel == id){
            washnShnHandNote.redoLastDraw();
        }else if(R.id.washn_btn_uncancel == id){
            //预留

        }else if(R.id.washn_btn_uncancel == id){
            //预留
        }
    }

}
