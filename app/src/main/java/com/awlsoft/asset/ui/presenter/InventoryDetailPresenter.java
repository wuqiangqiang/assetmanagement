package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.InventoryDetailContract;
import com.awlsoft.asset.model.entry.AssetFound;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AssetFoundDao;
import com.awlsoft.asset.orm.greendao.AssetResponseDao;
import com.awlsoft.asset.orm.greendao.InventoryResponseDao;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by yejingxian on 2017/6/2.
 */

public class InventoryDetailPresenter implements InventoryDetailContract.Presenter {
    private InventoryDetailContract.View mView;
    private Long inventoryId;
    private DBManager mManager;
    private RfidManager mRfidManager;
    private Disposable mDisposable;
    private ArrayList<String> mLastFounded;
    private ArrayList<String> mFounded;

    private CompositeDisposable mDisposables;

    public InventoryDetailPresenter(@NonNull InventoryDetailContract.View mView, @NonNull DBManager mManager,
                                    @NonNull RfidManager mRfidManager, @NonNull Long inventoryId) {
        this.mView = mView;
        this.inventoryId = inventoryId;
        this.mManager = mManager;
        this.mRfidManager = mRfidManager;

        mDisposables = new CompositeDisposable();
        this.mView.setPresenter(this);
    }

    @Override
    public void stop() {
        mDisposables.clear();
    }

    @Override
    public void loadAssets() {
        loadAssets(true, true);
    }

    @Override
    public void openDriver() {
        try {
            mRfidManager.openDriver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeDriver() {
        mRfidManager.closeDriver();
    }

    @Override
    public void startScanRfid() {
        if (!mRfidManager.isScanning()) {
            mRfidManager.startScan();
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            mDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
                    .compose(ObservableTransformerUtils.<Long>io_main())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                            mView.showRfids(mRfidManager.getInventory());
                        }
                    });
        }
    }

    @Override
    public void stopScanRfid() {
        mRfidManager.stopScan();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public boolean isScanning() {
        return mRfidManager.isScanning();
    }

    @Override
    public void hangInventory(List<AssetResponse> assets) {
        InventoryResponse inventoryResponse = mManager.getDaoSession().getInventoryResponseDao().queryBuilder().where(InventoryResponseDao.Properties.Id.eq(inventoryId)).unique();
        inventoryResponse.setStatus(InventoryResponse.RUNNING);
        mManager.getDaoSession().getInventoryResponseDao().update(inventoryResponse);
        ArrayList<AssetFound> list = new ArrayList<>();
        for (AssetResponse asset : assets) {
            if (asset.justFound) {
                AssetFound found = new AssetFound(null, inventoryId, asset.getId(), inventoryResponse.getTaskID());
                list.add(found);
            }
        }
        if (!list.isEmpty()) {
            mManager.getDaoSession().getAssetFoundDao().insertOrReplaceInTx(list);
        }

    }

    @Override
    public void completeInventory(List<AssetResponse> assets) {
        InventoryResponse inventoryResponse = mManager.getDaoSession().getInventoryResponseDao().queryBuilder().where(InventoryResponseDao.Properties.Id.eq(inventoryId)).unique();
        inventoryResponse.setStatus(InventoryResponse.FINISHED);
        mManager.getDaoSession().getInventoryResponseDao().update(inventoryResponse);
        ArrayList<AssetFound> list = new ArrayList<>();
        for (AssetResponse asset : assets) {
            if (asset.justFound) {
                AssetFound found = new AssetFound(null, inventoryId, asset.getId(), inventoryResponse.getTaskID());
                list.add(found);
            }
        }
        if (!list.isEmpty()) {
            mManager.getDaoSession().getAssetFoundDao().insertOrReplaceInTx(list);
        }
    }

    private void loadAssets(final boolean forceUpdate, final boolean showLoadingUI) {
        if (inventoryId < 0) {
            mView.showLoadingAssetsError();
            return;
        }

        if (showLoadingUI) {
            mView.setLoadingIndicator(true);
        }
        mDisposables.clear();
        Observable
                .just(inventoryId)
                .map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return mManager.getDaoSession().getInventoryResponseDao().queryBuilder().where(InventoryResponseDao.Properties.Id.eq(aLong)).unique().getCategory_id();
                    }
                })
                .map(new Function<Integer, List<AssetResponse>>() {
                    @Override
                    public List<AssetResponse> apply(@io.reactivex.annotations.NonNull Integer aInt) throws Exception {
                        mManager.getDaoSession().clear();
                        return mManager.getDaoSession().getAssetResponseDao().queryBuilder().where(AssetResponseDao.Properties.Category_id.eq(aInt)).list();
                    }
                })
                .compose(ObservableTransformerUtils.<List<AssetResponse>>io_main())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.setLoadingIndicator(false);
                    }
                })
                .subscribe(new Observer<List<AssetResponse>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        mDisposables.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<AssetResponse> assetResponses) {
                        for (AssetResponse asset : assetResponses) {
                            asset.hasFound = mManager.getDaoSession().getAssetFoundDao().queryBuilder().where(AssetFoundDao.Properties.InventoryId.eq(inventoryId),
                                    AssetFoundDao.Properties.AssetId.eq(asset.getId())).count() != 0;
                        }
                        processAssets(assetResponses);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        mView.showLoadingAssetsError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void processAssets(List<AssetResponse> assetResponses) {
        if (assetResponses.isEmpty()) {
            mView.showNoAssets();
        } else {
            mView.showAssets(assetResponses);
        }

    }

    @Override
    public void start() {
        loadAssets();
    }
}
