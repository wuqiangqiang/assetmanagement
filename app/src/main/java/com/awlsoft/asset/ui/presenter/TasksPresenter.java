package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;

import com.awlsoft.asset.contract.TasksContract;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * Created by yejingxian on 2017/6/12.
 */

public class TasksPresenter implements TasksContract.Presenter{
    private TasksContract.View mView;
    private DBManager mManager;
    private CompositeDisposable mDisposables;

    public TasksPresenter(TasksContract.View mView, DBManager mManager) {
        this.mView = mView;
        this.mManager = mManager;
        mDisposables = new CompositeDisposable();
        this.mView.setPresenter(this);
    }

    @Override
    public void onStop() {
        mDisposables.clear();
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
                .create(new ObservableOnSubscribe<List<BaseTaskResponse>>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<List<BaseTaskResponse>> e) throws Exception {
                        List<BaseTaskResponse> tasks = new ArrayList<>();
                        List<ReceiveTaskResponse> receives = mManager.getDaoSession().getReceiveTaskResponseDao().queryBuilder().list();
                        List<BorrowTaskResponse> borrows = mManager.getDaoSession().getBorrowTaskResponseDao().queryBuilder().list();
                        List<ScrapTaskResponse> scraps = mManager.getDaoSession().getScrapTaskResponseDao().queryBuilder().list();
                        List<BreakageTaskResponse> breakages = mManager.getDaoSession().getBreakageTaskResponseDao().queryBuilder().list();
                        List<ReturnTaskResponse> returns = mManager.getDaoSession().getReturnTaskResponseDao().queryBuilder().list();
                        List<AllocationTaskResponse> allocations = mManager.getDaoSession().getAllocationTaskResponseDao().queryBuilder().list();
                        if(receives != null && !receives.isEmpty()){
                            tasks.addAll(receives);
                        }
                        if(borrows != null && !borrows.isEmpty()){
                            tasks.addAll(borrows);
                        }
                        if(scraps != null && !scraps.isEmpty()){
                            tasks.addAll(scraps);
                        }
                        if(breakages != null && !breakages.isEmpty()){
                            tasks.addAll(breakages);
                        }
                        if(returns != null && !returns.isEmpty()){
                            tasks.addAll(returns);
                        }
                        if(allocations != null && !allocations.isEmpty()){
                            tasks.addAll(allocations);
                        }
                        e.onNext(tasks);
                        e.onComplete();
                    }
                })
                .compose(ObservableTransformerUtils.<List<BaseTaskResponse>>io_main())//组成
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.setLoadingIndicator(false);
                    }
                })
                .subscribe(new Observer<List<BaseTaskResponse>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<BaseTaskResponse> tasks) {
                        processTasks(tasks);
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

    private void processTasks(List<BaseTaskResponse> tasks) {
        if (tasks.isEmpty()) {
            mView.showNoTasks();
        } else {
            mView.showTasks(tasks);
        }
    }

    @Override
    public void openTaskDetails(@NonNull BaseTaskResponse task) {
        mView.showTaskDetailsUi(task);
    }

    @Override
    public void startTask(@NonNull BaseTaskResponse task) {
        mView.showStartTaskUi(task);
    }

    @Override
    public void start() {
        loadTasks(true);
    }
}
