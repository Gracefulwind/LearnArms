/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gracefulwind.learnarms.app.mvp.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gracefulwind.learnarms.app.R;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;
import com.gracefulwind.learnarms.commonsdk.utils.Utils;
import com.gracefulwind.learnarms.commonservice.gank.service.GankInfoService;
import com.gracefulwind.learnarms.commonservice.gold.service.GoldInfoService;
import com.gracefulwind.learnarms.commonservice.zhihu.service.ZhihuInfoService;

/**
 * ================================================
 * 宿主 App 的主页
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki">ArmsComponent wiki 官方文档</a>
 * Created by JessYan on 19/04/2018 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@Route(path = RouterHub.APP_MAINACTIVITY)
public class MainActivity extends BaseActivity {
    @BindView(R.id.bt_zhihu)
    Button mZhihuButton;
    @BindView(R.id.bt_gank)
    Button mGankButton;
    @BindView(R.id.bt_gold)
    Button mGoldButton;

    @Autowired(name = RouterHub.ZhiHu.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    ZhihuInfoService mZhihuInfoService;
    @Autowired(name = RouterHub.Gank.GANK_SERVICE_GANKINFOSERVICE)
    GankInfoService mGankInfoService;
    @Autowired(name = RouterHub.Gold.GOLD_SERVICE_GOLDINFOSERVICE)
    GoldInfoService mGoldInfoService;

    private long mPressedTime;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        //这里想展示组件向外提供服务的功能, 模拟下组件向宿主提供一些必要的信息, 这里为了简单就直接返回本地数据不请求网络了
        loadZhihuInfo();
        loadGankInfo();
        loadGoldInfo();
    }

    private void loadZhihuInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mZhihuInfoService == null) {
            mZhihuButton.setEnabled(false);
            return;
        }
        mZhihuButton.setText(mZhihuInfoService.getInfo().getName());
    }

    private void loadGankInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mGankInfoService == null) {
            mGankButton.setEnabled(false);
            return;
        }
        mGankButton.setText(mGankInfoService.getInfo().getName());
    }

    private void loadGoldInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        if (mGoldInfoService == null) {
            mGoldButton.setEnabled(false);
            return;
        }
        mGoldButton.setText(mGoldInfoService.getInfo().getName());
    }

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long mNowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((mNowTime - mPressedTime) > 2000) {
            ArmsUtils.makeText(getApplicationContext(),
                    "再按一次退出" + ArmsUtils.getString(getApplicationContext(), R.string.public_app_name));
            mPressedTime = mNowTime;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 这里注意下在组件的页面中(使用了 R2 的页面)使用 {@link butterknife.OnClick} 会有概率出现 id 不正确的问题, 使用以下方式解决
     * <pre>
     * @OnClick({R2.id.button1, R2.id.button2})
     * public void Onclick(View view){
     *      if (view.getId() == R.id.button1){
     *          ...
     *      } else if(view.getId() == R.id.button2){
     *          ...
     *      }
     * }
     * </pre>
     * <p>
     * 在注解上使用 R2, 下面使用 R, 并且使用 {@code if else}, 替代 {@code switch}
     *
     * @param view
     */
    @OnClick({R.id.bt_zhihu, R.id.bt_gank, R.id.bt_gold, R.id.am_tv_click1, R.id.am_tv_weather_report
            , R.id.am_tv_see_novels, R.id.am_tv_smart_write, R.id.am_tv_new_smart_write, R.id.am_tv_ocr, R.id.am_tv_copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_zhihu:
                Utils.navigation(MainActivity.this, RouterHub.ZhiHu.ZHIHU_HOMEACTIVITY);
                break;
            case R.id.bt_gank:
                Utils.navigation(MainActivity.this, RouterHub.Gank.GANK_HOMEACTIVITY);
                break;
            case R.id.bt_gold:
                Utils.navigation(MainActivity.this, RouterHub.Gold.GOLD_HOMEACTIVITY);
                break;
            case R.id.am_tv_click1:
                //转跳模块1
                Utils.navigation(MainActivity.this, RouterHub.TestDagger.HOME_ACTIVITY);
                break;
            case R.id.am_tv_weather_report:
                //转跳模块1
                Utils.navigation(MainActivity.this, RouterHub.Weather.HOME_ACTIVITY);
                break;
            case R.id.am_tv_see_novels:
                //看小说
                Utils.navigation(MainActivity.this, RouterHub.Reader.HOME_ACTIVITY);
                break;
            case R.id.am_tv_smart_write:
                //手写板
                Utils.navigation(MainActivity.this, RouterHub.SmartWrite.HOME_ACTIVITY);
                break;
            case R.id.am_tv_new_smart_write:
                //新手写板
                Utils.navigation(MainActivity.this, RouterHub.NewWrite.HOME_ACTIVITY);
                break;
            case R.id.am_tv_ocr:
                //拷贝组件
                Utils.navigation(MainActivity.this, RouterHub.Ocr.HOME_ACTIVITY);
                break;
            case R.id.am_tv_copy:
                //拷贝组件
                Utils.navigation(MainActivity.this, RouterHub.Copy.HOME_ACTIVITY);
                break;
            default:
                break;
        }
    }
}
