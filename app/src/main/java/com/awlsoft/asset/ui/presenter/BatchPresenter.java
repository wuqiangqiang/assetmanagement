package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;

import com.awlsoft.asset.contract.BatchContract;
import com.awlsoft.asset.contract.TasksContract;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AssetBatchResponseDao;
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
 * Created by Wuqiangqiang on 2017/7/31.
 */

public class BatchPresenter implements BatchContract.Presenter{
    private BatchContract.View mView;
    private DBManager mDBManager;

    public BatchPresenter(BatchContract.View mView, DBManager mManager) {
        this.mView = mView;
        this.mDBManager = mManager;

        this.mView.setPresenter(this);
    }

    /**
     * 在Activity中被调用
     * mView在该类实例化时，作为构造函数的参数
     */
    @Override
    public void loadBatch() {
        mView.showBatch(mDBManager.getDaoSession().getAssetBatchResponseDao().queryBuilder().orderDesc(AssetBatchResponseDao.Properties.Id).list());
    }

    @Override
    public void openAssetAddActivity(AssetBatchResponse assetBatchResponse) {
        mView.showAssetAddActivity(assetBatchResponse);
    }

    @Override
    public void loadNewBatch() {
        mView.showNewBatch();
    }

    @Override
    public void saveBatch(AssetBatchResponse assetBatchResponse) {
        assetBatchResponse.setBatchNo(String.valueOf(System.currentTimeMillis()));
        mDBManager.getDaoSession().getAssetBatchResponseDao().insertOrReplaceInTx(assetBatchResponse);
    }



    @Override
    public void start() {
        loadBatch();
    }

}
