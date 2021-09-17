package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.mvp.contract.HandWriteContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerHandWriteComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.HandWritePresenter;
import com.gracefulwind.learnarms.reader.widget.MyHandWriteView;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.OperationPresenter;
import com.gracefulwind.learnarms.reader.widget.edit.SmartTextView;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: HandWriteActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/14
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/14
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.HAND_WRITE_ACTIVITY)
public class HandWriteActivity extends MyBaseActivity<HandWritePresenter> implements HandWriteContract.View {

    @BindView(R2.id.rahw_stv_text)
    SmartTextView rahwStvText;
    @BindView(R2.id.rahw_dv_doodle)
    DoodleView rahwDvDoodle;
    @BindView(R2.id.rahw_mhwv_hand_view)
    MyHandWriteView rahwMhwvHandView;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHandWriteComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.reader_activity_hand_write; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "Main");
//        initListener();
    }

//    @Override
//    public void showMessage(@NonNull @NotNull String message) {
//        checkNotNull(message);
//        ArmsUtils.snackbarText(message);
//    }

    @OnClick({R2.id.rahw_btn_0, R2.id.rahw_btn_1, R2.id.rahw_btn_2, R2.id.rahw_btn_3
            , R2.id.rahw_btn_write, R2.id.rahw_btn_doodle, R2.id.rahw_btn_scale})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.rahw_btn_0 == id){
//            if(rahwStvText.isEnabled()){
//                rahwStvText.setEnabled(false);
////                rahwDvDoodle.requestFocus();
//                rahwDvDoodle.setEnabled(true);
//            }else {
//                rahwStvText.setEnabled(true);
//                rahwStvText.requestFocus();
//                rahwDvDoodle.setEnabled(false);
//            }
            if(MyHandWriteView.MODE_TEXT == rahwMhwvHandView.getViewMode()){
                rahwMhwvHandView.setViewMode(MyHandWriteView.MODE_DOODLE);
            }else {
                rahwMhwvHandView.setViewMode(MyHandWriteView.MODE_TEXT);
            }
        }else if(R.id.rahw_btn_1 == id){
            if(rahwMhwvHandView.isModeDoodle()){
                rahwMhwvHandView.setDoodleEditMode(OperationPresenter.MODE_ERASER);
            }else {
                rahwMhwvHandView.setDoodleEditMode(OperationPresenter.MODE_DOODLE);
            }
        }else if(R.id.rahw_btn_2 == id){
            rahwMhwvHandView.cancelLastDraw();
        }else if(R.id.rahw_btn_3 == id){
            rahwMhwvHandView.redoLastDraw();
        }else if(R.id.rahw_btn_write == id){
            rahwMhwvHandView.setViewMode(MyHandWriteView.MODE_TEXT);
        }else if(R.id.rahw_btn_doodle == id){
            rahwMhwvHandView.setViewMode(MyHandWriteView.MODE_DOODLE);
        }else if(R.id.rahw_btn_scale == id){
            rahwMhwvHandView.setViewMode(MyHandWriteView.MODE_SCALE);
        }
    }

}
