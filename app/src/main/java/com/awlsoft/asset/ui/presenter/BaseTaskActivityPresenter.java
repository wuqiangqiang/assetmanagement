package com.awlsoft.asset.ui.presenter;

import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.TaskAssetFound;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AssetResponseDao;
import com.awlsoft.asset.orm.greendao.TaskAssetFoundDao;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.util.ObservableTransformerUtils;
import com.awlsoft.asset.util.RfidUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by yejingxian on 2017/6/9.
 */

public abstract class BaseTaskActivityPresenter implements BaseTaskPresenter {
    private BaseTaskView mBaseView;
    private Disposable mDisposable;
    private RfidManager mRfidManager;

    protected DBManager mManager;
    protected List<AssetResponse> assets = new ArrayList<>();
    //ymx add
    protected List<AssetResponse> mListHandleTask = new ArrayList<>();
    private int mCurScanSize = 0;

    public BaseTaskActivityPresenter(BaseTaskView mBaseView, DBManager mManager, RfidManager mRfidManager) {
        this.mBaseView = mBaseView;
        this.mRfidManager = mRfidManager;
    }

    protected void loadAllCategortAssets(long id) {
        try {
            mManager.getDaoSession().clear();
            List<AssetResponse> list = mManager.getDaoSession().getAssetResponseDao().queryBuilder().where(AssetResponseDao.Properties.Category_id.eq(id)).list();
            assets.addAll(list);
            System.out.println("yjx loadAllCategortAssets size:" + assets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void saveFoundAsset(List<AssetResponse> list, String taskId, boolean clearOld) {
        if (clearOld || true) {
            List<TaskAssetFound> hasFounds = mManager.getDaoSession().getTaskAssetFoundDao().queryBuilder()
                    .where(TaskAssetFoundDao.Properties.TaskId.eq(taskId)).list();
            mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(hasFounds);
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        List<TaskAssetFound> founds = new ArrayList<>();
        for (AssetResponse asset : list) {
            TaskAssetFound found = new TaskAssetFound(null, asset.getId(), taskId, asset.justFound);
            founds.add(found);
        }
        mManager.getDaoSession().getTaskAssetFoundDao().insertOrReplaceInTx(founds);

    }

    protected void loadAllCachedAssets(String taskId) {
        mManager.getDaoSession().clear();
        try {
            List<TaskAssetFound> list = mManager.getDaoSession().getTaskAssetFoundDao().queryBuilder()
                    .where(TaskAssetFoundDao.Properties.TaskId.eq(taskId)).list();
            for (TaskAssetFound found : list) {
                try {
                    AssetResponse asset = mManager.getDaoSession().getAssetResponseDao().queryBuilder()
                            .where(AssetResponseDao.Properties.Id.eq(found.getAssetId())).uniqueOrThrow();
                    asset.justFound = found.getChecked();
                    mListHandleTask.add(asset);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBaseView.showReceiveHandleTask();
        System.out.println("yjx loadAllCachedAssets size:" + mListHandleTask.size());
    }

    @Override
    public void compareList(List<InventoryBuffer.InventoryTagMap> list) {
        if (mCurScanSize != list.size()) {
            for (int i = mCurScanSize; i < list.size(); i++) {
                System.out.println("ymxdebug--11>" + RfidUtils.parseRfidEPC(list.get(i)).trim());
                AssetResponse ar = getAssetResponseByRFID(list.get(i));
                if (ar != null) {
                    mBaseView.onAddAssetResponse(ar);
                }
            }
            mCurScanSize = list.size();
        }
    }

    public AssetResponse getAssetResponseByRFID(InventoryBuffer.InventoryTagMap tag) {
        String rfid = RfidUtils.parseRfidEPC(tag).trim();
        for (AssetResponse ar : assets) {
            if (rfid.equals(ar.getRfid_code())) {
                return ar;
            }
        }
        return null;
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
            mCurScanSize = 0;
            mRfidManager.startScan();
            if (mDisposable != null && !mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
            //成功开始扫描
            if (mRfidManager.isScanning()) {
                mDisposable = Observable.interval(500, TimeUnit.MILLISECONDS)
                        .compose(ObservableTransformerUtils.<Long>io_main())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                                mBaseView.showFindRfids(mRfidManager.getInventory());
                            }
                        });
            }
        }
    }

    @Override
    public List<AssetResponse> getHandleTaskList() {
        return mListHandleTask;
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
}
