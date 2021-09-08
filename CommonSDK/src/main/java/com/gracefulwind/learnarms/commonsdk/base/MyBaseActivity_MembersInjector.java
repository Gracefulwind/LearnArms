package com.gracefulwind.learnarms.commonsdk.base;

import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.mvp.IPresenter;

import javax.inject.Provider;

import dagger.MembersInjector;

/**
 * @ClassName: MyBaseActivity_MemberInjector
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/8
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */
public class MyBaseActivity_MembersInjector<P extends IPresenter> implements MembersInjector<MyBaseActivity<P>> {
    private final Provider<P> mPresenterProvider;

    public MyBaseActivity_MembersInjector(Provider<P> mPresenterProvider) {
        this.mPresenterProvider = mPresenterProvider;
    }

    public static <P extends IPresenter> MembersInjector<MyBaseActivity<P>> create(
            Provider<P> mPresenterProvider) {
        return new MyBaseActivity_MembersInjector<P>(mPresenterProvider);}

    @Override
    public void injectMembers(MyBaseActivity<P> instance) {
        BaseActivity_MembersInjector.injectMPresenter(instance, mPresenterProvider.get());
    }
}
