package com.gracefulwind.learnarms.commonsdk.base;

import android.support.annotation.NonNull;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * @ClassName: MyBaseActivity
 * @Author: Gracefulwind
 * @CreateDate: 2021/9/8
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2021/9/8
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public abstract class MyBaseActivity<P extends IPresenter> extends BaseActivity<P> {
    public void showMessage(@NonNull @NotNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }
}
