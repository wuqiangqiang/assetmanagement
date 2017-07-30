package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.AssetAddContract;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yejingxian on 2017/5/26.
 */

public class AssetAddPresenter implements AssetAddContract.Presenter {

    private AssetAddContract.View mView;
    private DBManager mDBManager;
    private RfidManager mRfidManager;
    private Disposable mDisposable;

    public AssetAddPresenter(AssetAddContract.View mView, DBManager mDBManager, RfidManager mRfidManager) {
        this.mView = mView;
        this.mDBManager = mDBManager;
        this.mRfidManager = mRfidManager;
        this.mView.setPresenter(this);
    }

    @Override
    public void saveAssets(List<AssetAddBean> assets) {
        mDBManager.getDaoSession().getAssetAddBeanDao().insertOrReplaceInTx(assets);

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

    /**
     * 点击扫描按钮调用该方法
     */
    @Override
    public void startScanRfid() {
        //当RFID不在扫描时
        if (!mRfidManager.isScanning()) {
            mRfidManager.startScan();
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            //Observable看得见的 interval间隔
            mDisposable = Observable.interval(200, TimeUnit.MILLISECONDS)
                    .compose(ObservableTransformerUtils.<Long>io_main())
                    .subscribe(new Consumer<Long>() {//订阅
                        @Override
                        public void accept(@NonNull Long aLong) throws Exception {
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
    public void loadBatch() {
        mView.showBatch(mDBManager.getDaoSession().getAssetBatchResponseDao().queryBuilder().list());
    }

    @Override
    public void loadBrand() {
        mView.showBrand(mDBManager.getDaoSession().getBrandResponseDao().queryBuilder().list());
    }

    @Override
    public void loadCategory() {
        mView.showCategory(mDBManager.getDaoSession().getCategoryResponseDao().queryBuilder().list());
    }

    @Override
    public void loadCategoryGb() {
        mView.showCategoryGb(mDBManager.getDaoSession().getCategoryGbResponseDao().queryBuilder().list());
    }

    @Override
    public void loadModel() {
        mView.showModel(mDBManager.getDaoSession().getModelResponseDao().queryBuilder().list());
    }


    @Override
    public void start() {

    }
}
