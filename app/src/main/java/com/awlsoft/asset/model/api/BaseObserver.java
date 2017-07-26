package com.awlsoft.asset.model.api;


import android.support.annotation.NonNull;

import com.awlsoft.asset.model.api.exception.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by yejingxian on 2017/5/20.
 */

public abstract class BaseObserver<T> implements Observer<T> {


    public BaseObserver() {

    }

    @Override
    public void onError(Throwable e) {
        // todo error somthing
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
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


    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
