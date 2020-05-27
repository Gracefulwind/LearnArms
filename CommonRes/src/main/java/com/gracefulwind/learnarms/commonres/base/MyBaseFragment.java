package com.gracefulwind.learnarms.commonres.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gracefulwind.learnarms.commonsdk.interfaces.IBaseFragment;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;

import javax.annotation.Resource;

/**
 * @ClassName: BaseFragment
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/26 18:08
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/26 18:08
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public abstract class MyBaseFragment<P extends IPresenter> extends BaseFragment<P> implements IView, IBaseFragment {
    //rootView
    protected View mRootView;

    protected abstract @LayoutRes int getLayoutId();

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        return mRootView;
    }

    @Override
    public void setData(@Nullable Object data) {
        //空实现，如果实现类需要，则覆写
    }

    @Override
    public void showMessage(@NonNull String message) {
        //空实现，如果实现类需要，则覆写
    }


}
