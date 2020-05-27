package com.gracefulwind.learnarms.module_weather.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.commonres.base.MyBaseFragment;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.interfaces.IBaseFragment;
import com.gracefulwind.learnarms.module_weather.R;
import com.gracefulwind.learnarms.module_weather.R2;
import com.gracefulwind.learnarms.module_weather.mvp.contract.WeatherFragmentContract;
import com.gracefulwind.learnarms.module_weather.mvp.presenter.WeatherFragmentPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @ClassName: WeatherFragment
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/26 17:47
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/26 17:47
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.WEATHER.WEATHER_FRAGMENT)
public class WeatherFragment extends MyBaseFragment<WeatherFragmentPresenter> implements WeatherFragmentContract.View {



    @BindView(R2.id.wfw_tv_title)
    TextView wfwTvTitle;

//-----------------------------------------------------------------------------------------------------

    public static final String KEY_TITLE = "key_title";
//-----------------------------------------------------------------------------------------------------
//    @Autowired(name = KEY_TITLE, required = false)
    String title = "default";
//-----------------------------------------------------------------------------------------------------


    @Override
    protected int getLayoutId() {
        return R.layout.weather_fragment_weather;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        System.out.println("========");
    }


    @OnClick({R2.id.wfw_tv_title})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(R.id.wfw_tv_title == id){
            System.out.println("======================");
        }
    }


    //============================
    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
