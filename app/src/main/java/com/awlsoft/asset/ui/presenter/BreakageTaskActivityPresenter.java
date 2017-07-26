package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.contract.BreakageTaskContract;
import com.awlsoft.asset.contract.ScrapTaskContract;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.BreakageTaskResponseDao;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by user on 2017/6/14.
 */

public class BreakageTaskActivityPresenter extends BaseTaskActivityPresenter implements BreakageTaskContract.Presenter {
    private BreakageTaskContract.View mView;
    private String taskId;
    private int mCurScanSize = 0;

    public BreakageTaskActivityPresenter(BreakageTaskContract.View mBaseView, DBManager mManager, RfidManager mRfidManager, String taskId) {
        super(mBaseView, mManager, mRfidManager);
        this.mView = mBaseView;
        this.taskId = taskId;
        this.mManager = mManager;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Observable
                .just(taskId).map(new Function<String, BreakageTaskResponse>() {
            @Override
            public BreakageTaskResponse apply(@NonNull String s) throws Exception {
                BreakageTaskResponse receive = mManager.getDaoSession().getBreakageTaskResponseDao().
                        queryBuilder().where(BreakageTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
                return receive;
            }
        })
                .compose(ObservableTransformerUtils.<BreakageTaskResponse>io_main())
                .subscribe(new Observer<BreakageTaskResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BreakageTaskResponse receiveTaskResponse) {
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
        System.out.println("yjx receive task 挂起:" + list.size());
        BreakageTaskResponse receive = mManager.getDaoSession().getBreakageTaskResponseDao()
                .queryBuilder().where(BreakageTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        if (mView.getPrice() != null) {
            receive.setPrice(mView.getPrice());
        }
        if (mView.getSaveSite() != null) {
            receive.setWorkareaId(mView.getSaveSite().getId());
        }
        receive.setStatus(BreakageTaskResponse.RUNNING);
        mManager.getDaoSession().getBreakageTaskResponseDao().insertOrReplace(receive);

        saveFoundAsset(list, taskId, false);
    }

    @Override
    public void onCommitTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 完成:" + list.size());
        BreakageTaskResponse receive = mManager.getDaoSession().getBreakageTaskResponseDao()
                .queryBuilder().where(BreakageTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        if (mView.getPrice() == null) {
            mView.showCompleteTaskError("请输入剩余价值！");
            return;
        }
        receive.setPrice(mView.getPrice());
        if (mView.getSaveSite() == null) {
            mView.showCompleteTaskError("请选择保存地点！");
            return;
        }
        receive.setWorkareaId(mView.getSaveSite().getId());
        receive.setStatus(BreakageTaskResponse.FINISHED);
        mManager.getDaoSession().getBreakageTaskResponseDao().insertOrReplace(receive);
        saveFoundAsset(list, taskId, true);
    }

    @Override
    public void loadSaveSiteData() {
        List<WorkareaResponse> areas = mManager.getDaoSession().getWorkareaResponseDao().queryBuilder().list();
        mView.onShowSaveSiteDialog(areas);
    }
}
