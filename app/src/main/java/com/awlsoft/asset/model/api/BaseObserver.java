package com.awlsoft.asset.model.api;


import android.support.annotation.NonNull;

import com.awlsoft.asset.model.api.exception.ExceptionHandle;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 实现rxjava 的Observer
 * Created by yejingxian on 2017/5/20.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    public BaseObserver() {

    }

    /**
     * Notifies the Observer that the {@link Observable} has experienced an error condition.
     * <p>
     * If the {@link Observable} calls this method, it will not thereafter 其后 call {@link #onNext} or
     * {@link #onComplete}.
     *
     * @param e
     *          the exception encountered by the Observable
     */
    @Override
    public void onError(Throwable e) {
        // todo error somthing
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onBaseError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onBaseError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        // todo some common as show loadding  and check netWork is NetworkAvailable
    }

    @Override
    public void onComplete() {
        // todo some common as  dismiss loadding
    }


    public abstract void onBaseError(ExceptionHandle.ResponeThrowable e);

}
