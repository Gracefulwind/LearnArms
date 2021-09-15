package com.gracefulwind.learnarms.commonsdk.base;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gracefulwind.learnarms.commonsdk.interfaces.IBaseFragment;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;

import java.util.ArrayList;
import java.util.List;


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

public abstract class BaseLazyLoadFragment<P extends IPresenter> extends BaseFragment<P> implements IView, IBaseFragment {
    //rootView
    protected View mRootView;
    /**
     * 界面是否已创建完成
     */
    private boolean isViewCreated;
    /**
     * 是否对用户可见
     */
    private boolean isVisibleToUser;
    /**
     * 数据是否已请求
     */
    protected boolean isDataLoaded;
    /**
     * 布局文件ID
     */
    protected abstract @LayoutRes int getLayoutId();


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        return mRootView;
    }

    /**
     * 保证在initData后触发
     */
    @Override
    public void onResume() {
        super.onResume();
        isViewCreated = true;
        tryLoadData();
    }

    @Override
    public void onDestroy() {
        isViewCreated = false;
        super.onDestroy();
    }

    @Override
    public void setData(@Nullable Object data) {
        //空实现，如果实现类需要，则覆写
    }

    @Override
    public void showMessage(@NonNull String message) {
        //空实现，如果实现类需要，则覆写
    }

    /**
     * 此处实现具体的数c据请求逻辑
     */
    public abstract void lazyLoadData();


    /**
     * 找到view
     *
     * @param id
     * @param <T>
     * @return
     * 用butterKnife加载，不用这方法
     */
    @Deprecated
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) mRootView.findViewById(id);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        tryLoadData();
        if (isViewCreated)
            setAllChildFragment(isVisibleToUser);//父fragment变化带动子fragment的visibleToUser变化
    }

    List<Fragment> cacheVisibleToUserChildFragment; //保存可见子fragment,在父fragment下次切回来的时候重新给予赋值
    private void setAllChildFragment(boolean isParentVisibleToUser) {
        if (!this.isAdded()) return;
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> childFragments = childFragmentManager.getFragments();
        if (childFragments == null || childFragments.isEmpty())
            return;
        if (isParentVisibleToUser && cacheVisibleToUserChildFragment != null){
            for (Fragment fragment : cacheVisibleToUserChildFragment) {
                fragment.setUserVisibleHint(isParentVisibleToUser);
            }
            cacheVisibleToUserChildFragment.clear();
        }else{
            for (Fragment child : childFragments) {
                if (child instanceof BaseLazyLoadFragment && ((BaseLazyLoadFragment) child).getUserVisibleHint()) {
                    if (cacheVisibleToUserChildFragment == null) cacheVisibleToUserChildFragment = new ArrayList<>();
                    cacheVisibleToUserChildFragment.add(child);
                    ((BaseLazyLoadFragment) child).setUserVisibleHint(isParentVisibleToUser);
                }
            }
        }

    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        return fragment == null || fragment.getUserVisibleHint();
    }

    /**
     * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
     */
    private void dispatchParentVisibleState() {
        FragmentManager fragmentManager = getChildFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return;
        }
        for (Fragment child : fragments) {
            if (child instanceof BaseLazyLoadFragment && ((BaseLazyLoadFragment) child).isVisibleToUser) {
                ((BaseLazyLoadFragment) child).tryLoadData();
            }
        }
    }

    public void tryLoadData() {
        if (isViewCreated && getUserVisibleHint() && isParentVisible() && !isDataLoaded) {
            lazyLoadData();
            isDataLoaded = true;
            //通知子Fragment请求数据
            dispatchParentVisibleState();
        }
    }

}
