package com.gracefulwind.learnarms.commonsdk.base;

import androidx.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ClassName: BaseObservice
 * @Author: Gracefulwind
 * @CreateDate: 2020/5/28 11:00
 * @Description: ---------------------------
 * @UpdateUser:
 * @UpdateDate: 2020/5/28 11:00
 * @UpdateRemark:
 * @Version: 1.0
 * @Email: 429344332@qq.com
 */

public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable e);

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }
}