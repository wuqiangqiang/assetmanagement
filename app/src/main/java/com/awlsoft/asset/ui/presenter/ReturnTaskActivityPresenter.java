package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.ReturnTaskContract;
import com.awlsoft.asset.contract.ScrapTaskContract;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.ReturnTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ScrapTaskResponseDao;
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

public class ReturnTaskActivityPresenter  extends BaseTaskActivityPresenter implements ReturnTaskContract.Presenter {
    private ReturnTaskContract.View mView;
    private String taskId;
    private int mCurScanSize = 0;
    public ReturnTaskActivityPresenter(ReturnTaskContract.View mBaseView, DBManager mManager, RfidManager mRfidManager, String taskId) {
        super(mBaseView, mManager, mRfidManager);
        this.mView = mBaseView;
        this.taskId = taskId;
        this.mManager = mManager;
        mView.setPresenter(this);
    }


    @Override
    public void start() {
        Observable
                .just(taskId).map(new Function<String, ReturnTaskResponse>() {
            @Override
            public ReturnTaskResponse apply(@NonNull String s) throws Exception {
                ReturnTaskResponse receive = mManager.getDaoSession().getReturnTaskResponseDao().
                        queryBuilder().where(ReturnTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
                return receive;
            }
        })
                .compose(ObservableTransformerUtils.<ReturnTaskResponse>io_main())
                .subscribe(new Observer<ReturnTaskResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ReturnTaskResponse receiveTaskResponse) {
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
        ReturnTaskResponse receive = mManager.getDaoSession().getReturnTaskResponseDao()
                .queryBuilder().where(ReturnTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        if(mView.getSaveSite() != null){
            receive.setWorkareaId(mView.getSaveSite().getId());
        }
        receive.setStatus(ReturnTaskResponse.RUNNING);
        mManager.getDaoSession().getReturnTaskResponseDao().insertOrReplace(receive);

        saveFoundAsset(list,taskId,false);
    }

    @Override
    public void onCommitTask(List<AssetResponse> list) {
        System.out.println("yjx receive task 完成:"+list.size());
        ReturnTaskResponse receive = mManager.getDaoSession().getReturnTaskResponseDao()
                .queryBuilder().where(ReturnTaskResponseDao.Properties.TaskID.eq(taskId)).uniqueOrThrow();
        if(mView.getSaveSite() == null){
            mView.showCompleteTaskError("请选择保存地点！");
            return;
        }
        receive.setWorkareaId(mView.getSaveSite().getId());
        receive.setStatus(ReturnTaskResponse.FINISHED);
        mManager.getDaoSession().getReturnTaskResponseDao().insertOrReplace(receive);
        saveFoundAsset(list,taskId,true);
    }

    @Override
    public void loadSaveSiteData() {
        List<WorkareaResponse> areas = mManager.getDaoSession().getWorkareaResponseDao().queryBuilder().list();
        mView.onShowSaveSiteDialog(areas);
    }
}
