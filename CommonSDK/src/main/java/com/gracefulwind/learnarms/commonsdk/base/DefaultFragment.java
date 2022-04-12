package com.gracefulwind.learnarms.commonsdk.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gracefulwind.learnarms.commonsdk.R;
import com.gracefulwind.learnarms.commonsdk.core.RouterHub;

/**
 * @ClassName: DefaultFragment
 * @Author: Gracefulwind
 * @CreateDate: 2022/2/22
 * @Description: ---------------------------
 * defaultFragment 默认空白页面
 * @UpdateUser:
 * @UpdateDate: 2022/2/22
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
@Route(path = RouterHub.Default.DEFAULT_FRAGMENT)
//@Route(path = "/degrade/fragment")
public class DefaultFragment extends Fragment {
    View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_default, container, false);
        return mRootView;
    }
}
