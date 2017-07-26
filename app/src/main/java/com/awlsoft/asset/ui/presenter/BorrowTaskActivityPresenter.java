package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.contract.BorrowTaskContract;
import com.awlsoft.asset.contract.ReceiveTaskContract;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.BorrowTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ReceiveTaskResponseDao;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
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

public class BorrowTaskActivityPresenter extends BaseTaskActivityPresenter implements BorrowTaskContract.Presenter {
    private BorrowTaskContract.View mView;
    private String taskId;
    private int mCurScanSize = 0;
    public BorrowTaskActivityPresenter(BorrowTaskContract.View mBaseView, DBManager mManager, RfidManager mRfidManager, String taskId) {
        super(mBaseView, mManager, mRfidManager);
        this.mView = mBaseView;
        this.taskId = taskId;
        this.mManager = mManager;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Observable
                .just(taskId).map(new Function<String, BorrowTaskResponse>() {
            @Override
            public BorrowTaskResponse apply(@NonNull String s) throws Exception {
                BorrowTaskResponse receive = mManager.getDaoSession().getBorrowTaskResponseDao().
                        queryBuilder().where(BorrowTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
                return receive;
            }
        })
                .compose(ObservableTransformerUtils.<BorrowTaskResponse>io_main())
                .subscribe(new Observer<BorrowTaskResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BorrowTaskResponse receiveTaskResponse) {
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
        BorrowTaskResponse receive = mManager.getDaoSession().getBorrowTaskResponseDao()
                .queryBuilder().where(BorrowTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(BorrowTaskResponse.RUNNING);
        mManager.getDaoSession().getBorrowTaskResponseDao().insertOrReplace(receive);

        saveFoundAsset(list,taskId,false);
    }

    @Override
    public void onCommitTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 完成:"+list.size());
        BorrowTaskResponse receive = mManager.getDaoSession().getBorrowTaskResponseDao()
                .queryBuilder().where(BorrowTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(BorrowTaskResponse.FINISHED);
        mManager.getDaoSession().getBorrowTaskResponseDao().insertOrReplace(receive);
        saveFoundAsset(list,taskId,true);

    }
}
