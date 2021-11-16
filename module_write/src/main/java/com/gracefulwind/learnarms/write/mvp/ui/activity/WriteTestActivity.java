package com.gracefulwind.learnarms.write.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.UiUtil;
import com.gracefulwind.learnarms.write.R;
import com.gracefulwind.learnarms.write.R2;
import com.gracefulwind.learnarms.write.mvp.contract.SmartHandNoteContract;
import com.gracefulwind.learnarms.write.mvp.di.component.DaggerSmartHandNoteComponent;
import com.gracefulwind.learnarms.write.mvp.presenter.SmartHandNotePresenter;
import com.gracefulwind.learnarms.write.widget.SmartHandNoteView;
import com.gracefulwind.learnarms.write.widget.doodle.OperationPresenter;
import com.gracefulwind.learnarms.write.widget.doodle.SurfaceDoodleView;
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
@Route(path = RouterHub.SmartWrite.WRITE_TEST_ACTIVITY)
public class WriteTestActivity extends MyBaseActivity{

    @BindView(R2.id.wawt_tv_result)
    TextView wawtTvResult;
    @BindView(R2.id.wawt_sdv_doodle)
    SurfaceDoodleView wawtSdvDoodle;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
//        DaggerSmartHandNoteComponent //如找不到该类,请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.write_activity_write_test;
    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @OnClick({R2.id.wawt_btn_click1, R2.id.wawt_btn_doodle, R2.id.wawt_btn_eraser})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.wawt_btn_click1 == id){
            System.out.println("====================");
        }else if(R.id.wawt_btn_doodle == id){
            //涂鸦
        }else if(R.id.wawt_btn_eraser == id){
            //橡皮擦
        }else if(R.id.wawt_btn_click1 == id){
            //预留
        }
    }

}
