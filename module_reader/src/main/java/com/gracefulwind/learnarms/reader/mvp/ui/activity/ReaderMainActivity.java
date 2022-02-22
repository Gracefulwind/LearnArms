package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.entity.SimpleTabEntity;
import com.gracefulwind.learnarms.reader.mvp.contract.DoodleContract;
import com.gracefulwind.learnarms.reader.mvp.contract.ReaderMainContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerMainComponent;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerReaderMainComponent;
import com.gracefulwind.learnarms.reader.mvp.di.component.ReaderMainComponent;
import com.gracefulwind.learnarms.reader.mvp.presenter.DoodlePresenter;
import com.gracefulwind.learnarms.reader.mvp.presenter.ReaderMainPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: ReaderMainActivity
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/21
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2022/2/21
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Reader.READER_MAIN_ACTIVITY)
public class ReaderMainActivity extends MyBaseActivity<ReaderMainPresenter> implements ReaderMainContract.View {

    @BindView(R2.id.rarm_ll_title)
    LinearLayout rarmLlTitle;
    @BindView(R2.id.rarm_vp_content)
    ViewPager rarmVpContent;
    @BindView(R2.id.rarm_ctl_tab)
    CommonTabLayout rarmCtlTab;

    //------------------------------------------
    String[] mTitles = {"书架", "书城", "找书", "活动", "我的"};
    int[] mSelectedIcons = {R.drawable.icon_1_check, R.drawable.icon_2_check, R.drawable.icon_3_check
            , R.drawable.icon_4_check, R.drawable.icon_5_check};
    int[] mUnselectedIcons = {R.drawable.icon_1_uncheck, R.drawable.icon_2_uncheck, R.drawable.icon_3_uncheck
            , R.drawable.icon_4_uncheck, R.drawable.icon_5_uncheck};
    ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerReaderMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        return R.layout.reader_activity_reader_main;
    }


    @Override
    public void initData(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {
        mPresenter.initData();
        initTabBar();
        //getAppVersion();
    }

    private void initTabBar() {
        tabEntities.clear();
        for(int x = 0; x < mTitles.length; x++){
            tabEntities.add(new SimpleTabEntity(mTitles[x], mSelectedIcons[x], mUnselectedIcons[x]));
        }
        rarmCtlTab.setTabData(tabEntities);
        //代码中设置selectColor似乎有bug
//        rarmCtlTab.setTextUnselectColor(R.color.public_color_53A8F4);
//        rarmCtlTab.setTextSelectColor(R.color.public_text_color);
    }

    @Override
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
//==================================================================================================

    public void getAppVersion(){
        //检测app版本
    }


}
