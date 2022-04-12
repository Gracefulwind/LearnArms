package com.gracefulwind.learnarms.reader.mvp.ui.activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gracefulwind.learnarms.commonsdk.base.MyBaseActivity;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.ARouterUtil;
import com.gracefulwind.learnarms.reader.R;
import com.gracefulwind.learnarms.reader.R2;
import com.gracefulwind.learnarms.reader.entity.SimpleTabEntity;
import com.gracefulwind.learnarms.reader.mvp.contract.ReaderMainContract;
import com.gracefulwind.learnarms.reader.mvp.di.component.DaggerReaderMainComponent;
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

//    @BindView(R2.id.rarm_ll_title)
//    LinearLayout rarmLlTitle;
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
    String[] mFragments = {RouterHub.Reader.BOOK_SHELF_FRAGMENT, RouterHub.Reader.BOOK_MALL_FRAGMENT
            , RouterHub.Reader.BOOK_SEARCH_FRAGMENT, RouterHub.Reader.BOOK_ACTIVITY_FRAGMENT
            , RouterHub.Reader.BOOK_MINE_FRAGMENT};
   /*String[] mFragments = {RouterHub.Reader.BOOK_SHELF_FRAGMENT, RouterHub.Reader.BOOK_SHELF_FRAGMENT
           , RouterHub.Reader.BOOK_SHELF_FRAGMENT, RouterHub.Reader.BOOK_SHELF_FRAGMENT
           , RouterHub.Reader.BOOK_SHELF_FRAGMENT};*/
    ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    ArrayList<Fragment> tabFragments = new ArrayList<>();
    private ReaderPagerAdapter readerPagerAdapter;


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
//        Object navigation = ARouter.getInstance().build(RouterHub.Reader.BOOK_SHELF_FRAGMENT).navigation();
        Fragment bookShelfFragment = ARouterUtil.getFragment(RouterHub.Reader.BOOK_SHELF_FRAGMENT);

        //getAppVersion();
    }

    private void initTabBar() {
        tabEntities.clear();
        tabFragments.clear();
        for(int x = 0; x < mTitles.length; x++){
            tabEntities.add(new SimpleTabEntity(mTitles[x], mSelectedIcons[x], mUnselectedIcons[x]));
            //使用时再添加
            //但是这样子就缺失了fragment的管理了
//            tabFragments.add(ARouterUtil.getFragment(mFragments[x]));
        }
        rarmCtlTab.setTabData(tabEntities);
        rarmCtlTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                rarmVpContent.setCurrentItem(position, false);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        //代码中设置selectColor似乎有bug
//        rarmCtlTab.setTextUnselectColor(R.color.public_color_53A8F4);
//        rarmCtlTab.setTextSelectColor(R.color.public_text_color);
        //=========
//        new ReaderPagerAdapter(getFragmentManager(), mFragments);
        readerPagerAdapter = new ReaderPagerAdapter(getSupportFragmentManager(), mFragments);
        rarmVpContent.setAdapter(readerPagerAdapter);
        rarmVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                rarmCtlTab.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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

    private class ReaderPagerAdapter extends FragmentPagerAdapter{
//        ArrayList<Fragment> tabFragments = new ArrayList<>();
        String[] mFragmentPath;

        public ReaderPagerAdapter(@NonNull @NotNull FragmentManager fm, String[] mFragments) {
            super(fm);
//            super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);
            mFragmentPath = mFragments;
        }

        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int i) {
//            return null;
            return ARouterUtil.getFragment(mFragmentPath[i]);
//            return tabFragments.get(i);
        }

        @Override
        public int getCount() {
            return null == mFragmentPath ? 0 : mFragmentPath.length;
        }
    }

}
