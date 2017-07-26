package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;

import com.awlsoft.asset.contract.InventoryTaskContract;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class InventoryTaskPresenter implements InventoryTaskContract.Presenter {
    private InventoryTaskContract.View mView;
    private DBManager mManager;
    private CompositeDisposable mDisposables;

    public InventoryTaskPresenter(InventoryTaskContract.View mView, DBManager mManager) {
        this.mView = mView;
        this.mManager = mManager;
        mDisposables = new CompositeDisposable();
        this.mView.setPresenter(this);
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate, true);
    }

    private void loadTasks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            mView.setLoadingIndicator(true);
        }
        mDisposables.clear();
        Observable
                .create(new ObservableOnSubscribe<List<InventoryResponse>>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<InventoryResponse>> e) throws Exception {
                        List<InventoryResponse> tasks = mManager.getDaoSession().getInventoryResponseDao().queryBuilder().list();
                        e.onNext(tasks);
                        e.onComplete();
                    }
                })
                .compose(ObservableTransformerUtils.<List<InventoryResponse>>io_main())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.setLoadingIndicator(false);
                    }
                })
                .subscribe(new Observer<List<InventoryResponse>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<InventoryResponse> inventoryResponses) {
                        processTasks(inventoryResponses);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        mView.showLoadingTasksError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void processTasks(List<InventoryResponse> inventorys) {
        if (inventorys.isEmpty()) {
            mView.showNoTasks();
        } else {
            mView.showTasks(inventorys);
        }
    }

    @Override
    public void openTaskDetails(@NonNull InventoryResponse inventoryResponse) {
        mView.showTaskDetailsUi(inventoryResponse);
    }

    @Override
    public void startTask(@NonNull InventoryResponse inventoryResponse) {
        mView.showStartTaskUi(inventoryResponse);
    }

    @Override
    public void start() {
        loadTasks(true);
    }

    @Override
    public void onStop() {
        mDisposables.clear();
    }
}
