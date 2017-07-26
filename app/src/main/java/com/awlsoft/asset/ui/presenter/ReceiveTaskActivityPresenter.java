package com.awlsoft.asset.ui.presenter;

import android.widget.Toast;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.ReceiveTaskContract;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.ReceiveTaskResponseDao;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.util.ObservableTransformerUtils;
import com.awlsoft.asset.util.RfidUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by yejingxian on 2017/6/9.
 */

public class ReceiveTaskActivityPresenter extends BaseTaskActivityPresenter implements ReceiveTaskContract.Presenter {
    private ReceiveTaskContract.View mView;
    private String taskId;

    public ReceiveTaskActivityPresenter(@NonNull ReceiveTaskContract.View mView, @NonNull DBManager mManager,
                                        @NonNull RfidManager mRfidManager, @NonNull String taskId) {
        super(mView, mManager, mRfidManager);
        this.mView = mView;
        this.taskId = taskId;
        this.mManager = mManager;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        Observable
                .just(taskId).map(new Function<String, ReceiveTaskResponse>() {
            @Override
            public ReceiveTaskResponse apply(@NonNull String s) throws Exception {
                ReceiveTaskResponse receive = mManager.getDaoSession().getReceiveTaskResponseDao().
                        queryBuilder().where(ReceiveTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
                return receive;
            }
        })
                .compose(ObservableTransformerUtils.<ReceiveTaskResponse>io_main())
                .subscribe(new Observer<ReceiveTaskResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReceiveTaskResponse receiveTaskResponse) {
                        mView.showReceiveTaskDetail(receiveTaskResponse);
                        loadAllCategortAssets(receiveTaskResponse.getCategory_id());
                        loadAllCachedAssets(taskId);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("加载错误，没有该taskid！");
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
        ReceiveTaskResponse receive = mManager.getDaoSession().getReceiveTaskResponseDao()
                .queryBuilder().where(ReceiveTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(ReceiveTaskResponse.RUNNING);
        mManager.getDaoSession().getReceiveTaskResponseDao().insertOrReplace(receive);

        saveFoundAsset(list,taskId,false);
    }

    @Override
    public void onCommitTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 完成:"+list.size());
        ReceiveTaskResponse receive = mManager.getDaoSession().getReceiveTaskResponseDao()
                .queryBuilder().where(ReceiveTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        receive.setStatus(ReceiveTaskResponse.FINISHED);
        mManager.getDaoSession().getReceiveTaskResponseDao().insertOrReplace(receive);
        saveFoundAsset(list,taskId,true);
    }

}
