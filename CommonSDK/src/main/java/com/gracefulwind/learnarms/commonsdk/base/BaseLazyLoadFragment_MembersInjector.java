package com.gracefulwind.learnarms.commonsdk.base;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.BaseFragment_MembersInjector;
import com.jess.arms.mvp.IPresenter;

import javax.inject.Provider;

import dagger.MembersInjector;

/**
 * @ClassName: BaseLazyLoadFragment_MemberInjector
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/8
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class BaseLazyLoadFragment_MembersInjector<P extends IPresenter> implements MembersInjector<BaseLazyLoadFragment<P>> {
    private final Provider<P> mPresenterProvider;

    public BaseLazyLoadFragment_MembersInjector(Provider<P> mPresenterProvider) {
        this.mPresenterProvider = mPresenterProvider;
    }

    public static <P extends IPresenter> MembersInjector<BaseLazyLoadFragment<P>> create(
            Provider<P> mPresenterProvider) {
        return new BaseLazyLoadFragment_MembersInjector<P>(mPresenterProvider);}

    @Override
    public void injectMembers(BaseLazyLoadFragment<P> instance) {
        BaseFragment_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    }
}

