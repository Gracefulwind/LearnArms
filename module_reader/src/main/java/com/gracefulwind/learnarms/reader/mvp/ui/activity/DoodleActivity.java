package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.LogUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.mvp.contract.DoodleContract;
import com.gracefulwind.learnarms.reader.mvp.contract.MainContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerDoodleComponent;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.DoodlePresenter;
import com.gracefulwind.learnarms.reader.mvp.presenter.MainPresenter;
import com.gracefulwind.learnarms.reader.widget.doodle.DoodleView;
import com.gracefulwind.learnarms.reader.widget.doodle.OperationPresenter;
import com.gracefulwind.learnarms.reader.widget.smart.SmartTextView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: DoodleActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/9
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/9
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.DOODLE_ACTIVITY)
public class DoodleActivity extends MyBaseActivity<DoodlePresenter> implements DoodleContract.View {
    public static final String TAG = "DoodleActivity";
    public static int fontSize = 14;

    @BindView(R2.id.rad_tv_click0)
    Button radTvClick0;
    @BindView(R2.id.rad_dv_container)
    DoodleView radDvContainer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDoodleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.reader_activity_doodle; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //setToolBar(toolbar, "Main");
        initListener();
    }

    public void initListener() {

    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @OnClick({R2.id.rad_tv_click0})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.rad_tv_click0 == id){
            LogUtil.e(TAG, "do some thing");
            LogUtil.e(TAG, "do some thing");
            int editMode = radDvContainer.getEditMode();
            if(radDvContainer.isModeDoodle()){
                radDvContainer.setEditMode(OperationPresenter.MODE_ERASER);
            }else {
                radDvContainer.setEditMode(OperationPresenter.MODE_DOODLE);
            }
        }
    }

}
