package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.contract.AllocationTaskContract;
import com.awlsoft.asset.contract.BorrowTaskContract;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AllocationTaskResponseDao;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by user on 2017/6/14.
 */

public class AllocationTaskActivityPresenter extends BaseTaskActivityPresenter implements AllocationTaskContract.Presenter{
    private AllocationTaskContract.View mView;
    private String taskId;
    private int mCurScanSize = 0;
    public AllocationTaskActivityPresenter(AllocationTaskContract.View mBaseView, DBManager mManager, RfidManager mRfidManager, String taskId) {
        super(mBaseView, mManager, mRfidManager);
        this.mView = mBaseView;
        this.taskId = taskId;
        this.mManager = mManager;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Observable
                .just(taskId).map(new Function<String, AllocationTaskResponse>() {
            @Override
            public AllocationTaskResponse apply(@NonNull String s) throws Exception {
                AllocationTaskResponse receive = mManager.getDaoSession().getAllocationTaskResponseDao().
                        queryBuilder().where(AllocationTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
                return receive;
            }
        })
                .compose(ObservableTransformerUtils.<AllocationTaskResponse>io_main())
                .subscribe(new Observer<AllocationTaskResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull AllocationTaskResponse receiveTaskResponse) {
                        mView.showReceiveTaskDetail(receiveTaskResponse);
                        loadAllCategortAssets(receiveTaskResponse.getCategory_id());
                        loadAllCachedAssets(taskId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("加载错误，没有该taskid！");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void stop() {

    }

    @Override
    public void onHandTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 挂起:"+list.size());
        AllocationTaskResponse receive = mManager.getDaoSession().getAllocationTaskResponseDao()
                .queryBuilder().where(AllocationTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(AllocationTaskResponse.RUNNING);
        mManager.getDaoSession().getAllocationTaskResponseDao().insertOrReplace(receive);

        saveFoundAsset(list,taskId,false);
    }

    @Override
    public void onCommitTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 完成:"+list.size());
        AllocationTaskResponse receive = mManager.getDaoSession().getAllocationTaskResponseDao()
                .queryBuilder().where(AllocationTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(AllocationTaskResponse.FINISHED);
        mManager.getDaoSession().getAllocationTaskResponseDao().insertOrReplace(receive);
        saveFoundAsset(list,taskId,true);
    }
}
