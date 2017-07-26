package com.awlsoft.asset.model;

import com.awlsoft.asset.model.api.BaseObserver;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.api.exception.ExceptionHandle;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.AssetFound;
import com.awlsoft.asset.model.entry.BaseEntry;
import com.awlsoft.asset.model.entry.TaskAssetFound;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AllocationTaskResponseDao;
import com.awlsoft.asset.orm.greendao.AssetFoundDao;
import com.awlsoft.asset.orm.greendao.BorrowTaskResponseDao;
import com.awlsoft.asset.orm.greendao.BreakageTaskResponseDao;
import com.awlsoft.asset.orm.greendao.InventoryResponseDao;
import com.awlsoft.asset.orm.greendao.ReceiveTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ReturnTaskResponseDao;
import com.awlsoft.asset.orm.greendao.ScrapTaskResponseDao;
import com.awlsoft.asset.orm.greendao.TaskAssetFoundDao;
import com.awlsoft.asset.util.ObservableTransformerUtils;
import com.awlsoft.asset.util.TasksUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

/**
 * Created by yejingxian on 2017/6/6.
 */

public class AssetAssociationUpload {
    private DBManager mManager;
    private UserResponse mUser;
    private UploadCallback mCallback;

    private int upload_count = 8;

    public static enum UploadTypes {
        ASSET_ADD, INVENTORY_TASK,
        RECEIVE_TASK,BORROW_TASK,SCRAP_TASK,BREAKAGE_TASK,RETURN_TASK,ALLOCATION_TASK,
    }

    private Set<UploadTypes> successType = new HashSet<>();
    private Set<UploadTypes> falureType = new HashSet<>();

    public AssetAssociationUpload(UserResponse user, DBManager manager) {
        this.mUser = user;
        this.mManager = manager;
    }

    public void uploadAllAssociation(UploadCallback cb) {
        this.mCallback = cb;
        resetStatus();
        if (hasDataUpload()) {
            mCallback.onUploadStart();
            uploadAllAddAssetsNew();
            uploadAllInventoryAsset();
            uploadAllReceiveTask();
            uploadAllBorrowTask();
            uploadAllScrapTask();
            uploadAllBreakageTask();
            uploadAllReturnTask();
            uploadAllAllocationTask();
        } else {
            mCallback.onNoUpload();
        }
    }

    public void resetStatus() {
        successType.clear();
        falureType.clear();
    }

    //统计新增资产
    private AtomicInteger mUploadAssetAll = new AtomicInteger();
    private AtomicInteger mUploadAssetFailure = new AtomicInteger();
    private AtomicInteger mUploadAssetSuccess = new AtomicInteger();

    //统计盘点任务
    private AtomicInteger mUploadInventoryAll = new AtomicInteger();
    private AtomicInteger mUploadInventoryFailure = new AtomicInteger();
    private AtomicInteger mUploadInventorySuccess = new AtomicInteger();

    //统计领取任务
    private AtomicInteger mUploadReceiveAll = new AtomicInteger();
    private AtomicInteger mUploadReceiveFailure = new AtomicInteger();
    private AtomicInteger mUploadReceiveSuccess = new AtomicInteger();

    //统计借用任务
    private AtomicInteger mUploadBorrowAll = new AtomicInteger();
    private AtomicInteger mUploadBorrowFailure = new AtomicInteger();
    private AtomicInteger mUploadBorrowSuccess = new AtomicInteger();

    //统计报废任务
    private AtomicInteger mUploadScrapAll = new AtomicInteger();
    private AtomicInteger mUploadScrapFailure = new AtomicInteger();
    private AtomicInteger mUploadScrapSuccess = new AtomicInteger();

    //统计报损任务
    private AtomicInteger mUploadBreakageAll = new AtomicInteger();
    private AtomicInteger mUploadBreakageFailure = new AtomicInteger();
    private AtomicInteger mUploadBreakageSuccess = new AtomicInteger();

    //统计归还任务
    private AtomicInteger mUploadReturnAll = new AtomicInteger();
    private AtomicInteger mUploadReturnFailure = new AtomicInteger();
    private AtomicInteger mUploadReturnSuccess = new AtomicInteger();

    //统计调拨任务
    private AtomicInteger mUploadAllocationAll = new AtomicInteger();
    private AtomicInteger mUploadAllocationFailure = new AtomicInteger();
    private AtomicInteger mUploadAllocationSuccess = new AtomicInteger();

    /**
     * 上传所有新增资产
     */
    public void uploadAllAddAssets() {
        List<AssetAddBean> assets = mManager.getDaoSession().getAssetAddBeanDao().queryBuilder().list();
        if (assets != null && assets.size() != 0) {
            for (AssetAddBean asset : assets) {
                uploadAddAsset(asset);
            }
        } else {

        }
    }

    public void uploadAllAddAssetsNew() {
        mUploadAssetAll.set(0);
        mUploadAssetFailure.set(0);
        mUploadAssetSuccess.set(0);
        List<AssetAddBean> assets = mManager.getDaoSession().getAssetAddBeanDao().queryBuilder().list();
        if (assets != null && assets.size() != 0) {
            mUploadAssetAll.set(assets.size());
            for (AssetAddBean asset : assets) {
                uploadAddAssetNew(asset);
            }
        } else {
            //没有数据需要上传
            successType.add(UploadTypes.ASSET_ADD);
            analysisResult();
        }
    }

    public void uploadAddAssetNew(final AssetAddBean asset) {
        Map<String,String> map = asset.generatorParams();
        RetrofitManager.getInstance().getOperateManager().uploadAsset(map).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("yjx uploadAsset-----------" + mUploadAssetSuccess.intValue() + "->" + mUploadAssetFailure.intValue() + "->" + mUploadAssetAll.intValue());
                boolean success = (mUploadAssetSuccess.intValue() + mUploadAssetFailure.intValue()) == mUploadAssetAll.intValue();
                if (success) {
                    if (mUploadAssetFailure.intValue() == 0) {
                        successType.add(UploadTypes.ASSET_ADD);
                    } else {
                        falureType.add(UploadTypes.ASSET_ADD);
                    }
                    analysisResult();
                }
            }
        }).subscribe(new BaseObserver<String>() {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                    //提交失败，RFID已经使用过 从本地中删除
                    mManager.getDaoSession().getAssetAddBeanDao().delete(asset);
                    mUploadAssetSuccess.incrementAndGet();
                } else {
                    mUploadAssetFailure.incrementAndGet();
                    //其他诸如网络原因失败，下次继续提交
                }
            }

            @Override
            public void onNext(@NonNull String s) {
                //提交成功 从本地中删除
                mManager.getDaoSession().getAssetAddBeanDao().delete(asset);
                mUploadAssetSuccess.incrementAndGet();
            }
        });
    }

    //TODO TEST
    public void uploadAllAddAssetsTest() {
        Observable
                .just(mUser.getId())
                .map(new Function<Long, List<AssetAddBean>>() {
                    @Override
                    public List<AssetAddBean> apply(@NonNull Long aLong) throws Exception {
                        return mManager.getDaoSession().getAssetAddBeanDao().queryBuilder().list();
                    }
                })
                .flatMap(new Function<List<AssetAddBean>, ObservableSource<AssetAddBean>>() {
                    @Override
                    public ObservableSource<AssetAddBean> apply(@NonNull List<AssetAddBean> assetAddBeen) throws Exception {
                        return Observable.fromIterable(assetAddBeen);
                    }
                })
                .flatMap(new Function<AssetAddBean, ObservableSource<BaseEntry<String>>>() {
                    @Override
                    public ObservableSource<BaseEntry<String>> apply(@NonNull final AssetAddBean assetAddBean) throws Exception {
                        return RetrofitManager.getInstance().getOperateManager().uploadAsset2(assetAddBean.generatorParams());
                    }
                })
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException())
                .onErrorReturnItem("failure")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        System.out.println("yjx upload asset onNext:" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("yjx upload asset fail 2 !");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("yjx upload asset success 2 !");
                    }
                });
    }

    //TODO TEST
    private <T> Function<BaseEntry<T>, T> filterFromBaseEntry(final T def) {
        return new Function<BaseEntry<T>, T>() {
            @Override
            public T apply(@NonNull BaseEntry<T> tBaseEntry) throws Exception {
                if (tBaseEntry.getResult()) {
                    return tBaseEntry.getData() == null ? def : tBaseEntry.getData();
                }
                throw new ExceptionHandle.ServerException(ExceptionHandle.ERROR.SERVICE_ERROR, tBaseEntry.getMsg());
            }
        };
    }

    private <T> Function<Throwable, ObservableSource<T>> parseException() {
        return new Function<Throwable, ObservableSource<T>>() {
            @Override
            public ObservableSource<T> apply(@NonNull Throwable throwable) throws Exception {
                return Observable.error(ExceptionHandle.handleException(throwable));
            }
        };
    }

    public void uploadAddAsset(final AssetAddBean asset) {
        RetrofitManager.getInstance().getOperateManager().uploadAsset(asset.generatorParams()).subscribe(new BaseObserver<String>() {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                    //提交失败，RFID已经使用过 从本地中删除
                    mManager.getDaoSession().getAssetAddBeanDao().delete(asset);
                } else {
                    //其他诸如网络原因失败，下次继续提交
                }
            }

            @Override
            public void onNext(@NonNull String s) {
                //提交成功 从本地中删除
                mManager.getDaoSession().getAssetAddBeanDao().delete(asset);
            }
        });
    }

    /**
     * 上传所有盘点任务
     */
    public void uploadAllInventoryAsset() {
        mUploadInventoryAll.set(0);
        mUploadInventoryFailure.set(0);
        mUploadInventorySuccess.set(0);
        List<InventoryResponse> inventorys = mManager.getDaoSession().getInventoryResponseDao().queryBuilder().where(InventoryResponseDao.Properties.Status.eq(InventoryResponse.FINISHED)).list();
        if (inventorys == null || inventorys.isEmpty()) {
            //无完成状态的盘点任务，不需要上传
            successType.add(UploadTypes.INVENTORY_TASK);
            analysisResult();
        } else {
            mUploadInventoryAll.set(inventorys.size());
            for (final InventoryResponse inventory : inventorys) {
                final List<AssetFound> assets = mManager.getDaoSession().getAssetFoundDao().queryBuilder().where(AssetFoundDao.Properties.InventoryId.eq(inventory.getId())).list();
                StringBuilder builder = new StringBuilder();
                if (assets != null && assets.size() != 0) {
                    for (int i = 0; i < assets.size(); i++) {
                        if (i != 0) {
                            builder.append(",");
                        }
                        builder.append(assets.get(i).getAssetId());
                    }
                }
                RetrofitManager.getInstance().getOperateManager().uploadInventoryAsset(inventory.getTaskID(), builder.toString(), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadInventorySuccess.intValue() + mUploadInventoryFailure.intValue()) == mUploadInventoryAll.intValue();
                                System.out.println("yjx uploadAllInventoryAsset" + mUploadInventorySuccess.intValue() + "->" + mUploadInventoryFailure.intValue() + "->" + mUploadInventoryAll.intValue());
                                if (success) {
                                    if (mUploadInventoryFailure.intValue() == 0) {
                                        successType.add(UploadTypes.INVENTORY_TASK);
                                    } else {
                                        falureType.add(UploadTypes.INVENTORY_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllInventoryAsset success------------------");
                                mManager.getDaoSession().getAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getInventoryResponseDao().delete(inventory);

                                mUploadInventorySuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllInventoryAsset error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    mManager.getDaoSession().getAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getInventoryResponseDao().delete(inventory);
                                    mUploadInventorySuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadInventoryFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }


    private void uploadInventoryAsset(final AssetFound asset) {
        RetrofitManager.getInstance().getOperateManager().uploadInventoryAsset(asset.getTaskId(), String.valueOf(asset.getAssetId()), mUser.getId()).subscribe(new BaseObserver<String>() {
            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                    //提交失败,可能改资产已经盘点过 从本地中删除
                    mManager.getDaoSession().getAssetFoundDao().deleteInTx(asset);
                    long count = mManager.getDaoSession().getAssetFoundDao().queryBuilder().where(AssetFoundDao.Properties.InventoryId.eq(asset.getInventoryId().intValue())).count();
                    if (count == 0) {
                        List<InventoryResponse> inventorys = mManager.getDaoSession().getInventoryResponseDao().queryBuilder()
                                .where(InventoryResponseDao.Properties.Id.eq(asset.getInventoryId())).list();
                        if (inventorys != null && !inventorys.isEmpty()) {
                            mManager.getDaoSession().getInventoryResponseDao().deleteInTx(inventorys);
                        }
                    }
                } else {
                    //其他诸如网络原因失败，下次继续提交
                }
            }

            @Override
            public void onNext(@NonNull String s) {
                //提交成功 从本地中删除
            }
        });
    }

    /**
     * 上传领取任务
     */
    public void uploadAllReceiveTask() {
        mUploadReceiveAll.set(0);
        mUploadReceiveFailure.set(0);
        mUploadReceiveSuccess.set(0);
        List<ReceiveTaskResponse> receives = mManager.getDaoSession().getReceiveTaskResponseDao().queryBuilder().
                where(ReceiveTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (receives == null || receives.isEmpty()) {
            //无完成状态的领取任务，不需要上传
            successType.add(UploadTypes.RECEIVE_TASK);
            analysisResult();
        } else {
            mUploadReceiveAll.set(receives.size());
            for (final ReceiveTaskResponse receive : receives) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(receive.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();

                RetrofitManager.getInstance().getOperateManager().uploadReceiveTask(receive.getTaskID(), TasksUtils.concatTaskId(assets), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadReceiveSuccess.intValue() + mUploadReceiveFailure.intValue()) == mUploadReceiveAll.intValue();
                                System.out.println("yjx uploadAllReceiveTask" + mUploadReceiveSuccess.intValue() + "->" + mUploadReceiveFailure.intValue() + "->" + mUploadReceiveAll.intValue());
                                if (success) {
                                    if (mUploadReceiveFailure.intValue() == 0) {
                                        successType.add(UploadTypes.RECEIVE_TASK);
                                    } else {
                                        falureType.add(UploadTypes.RECEIVE_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllReceiveTask success------------------");
                                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(receive.getTaskId()))
                                        .list();
                                mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getReceiveTaskResponseDao().delete(receive);

                                mUploadReceiveSuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllReceiveTask error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(receive.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getReceiveTaskResponseDao().delete(receive);
                                    mUploadReceiveSuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadReceiveFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传借用任务
     */
    public void uploadAllBorrowTask() {
        mUploadBorrowAll.set(0);
        mUploadBorrowFailure.set(0);
        mUploadBorrowSuccess.set(0);
        List<BorrowTaskResponse> borrows = mManager.getDaoSession().getBorrowTaskResponseDao().queryBuilder().
                where(BorrowTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (borrows == null || borrows.isEmpty()) {
            //无完成状态的借用任务，不需要上传
            successType.add(UploadTypes.BORROW_TASK);
            analysisResult();
        } else {
            mUploadBorrowAll.set(borrows.size());
            for (final BorrowTaskResponse task : borrows) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();

                RetrofitManager.getInstance().getOperateManager().uploadBorrowTask(task.getTaskID(), TasksUtils.concatTaskId(assets), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadBorrowSuccess.intValue() + mUploadBorrowFailure.intValue()) == mUploadBorrowAll.intValue();
                                System.out.println("yjx uploadAllBorrowTask" + mUploadBorrowSuccess.intValue() + "->" + mUploadBorrowFailure.intValue() + "->" + mUploadBorrowAll.intValue());
                                if (success) {
                                    if (mUploadBorrowFailure.intValue() == 0) {
                                        successType.add(UploadTypes.BORROW_TASK);
                                    } else {
                                        falureType.add(UploadTypes.BORROW_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllBorrowTask success------------------");
                                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                        .list();
                                mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getBorrowTaskResponseDao().delete(task);

                                mUploadBorrowSuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllBorrowTask error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getBorrowTaskResponseDao().delete(task);
                                    mUploadBorrowSuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadBorrowFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传报废任务
     */
    public void uploadAllScrapTask() {
        mUploadScrapAll.set(0);
        mUploadScrapFailure.set(0);
        mUploadScrapSuccess.set(0);
        List<ScrapTaskResponse> tasks = mManager.getDaoSession().getScrapTaskResponseDao().queryBuilder().
                where(ScrapTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (tasks == null || tasks.isEmpty()) {
            //无完成状态的报废任务，不需要上传
            successType.add(UploadTypes.SCRAP_TASK);
            analysisResult();
        } else {
            mUploadScrapAll.set(tasks.size());
            for (final ScrapTaskResponse task : tasks) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();

                RetrofitManager.getInstance().getOperateManager().uploadScrapTask(task.getTaskID(), TasksUtils.concatTaskId(assets), task.getWorkareaId(), task.getPrice(), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadScrapSuccess.intValue() + mUploadScrapFailure.intValue()) == mUploadScrapAll.intValue();
                                System.out.println("yjx uploadAllScrapTask" + mUploadScrapSuccess.intValue() + "->" + mUploadScrapFailure.intValue() + "->" + mUploadScrapAll.intValue());
                                if (success) {
                                    if (mUploadScrapFailure.intValue() == 0) {
                                        successType.add(UploadTypes.SCRAP_TASK);
                                    } else {
                                        falureType.add(UploadTypes.SCRAP_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllScrapTask success------------------");
                                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                        .list();
                                mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getScrapTaskResponseDao().delete(task);

                                mUploadScrapSuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllScrapTask error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getScrapTaskResponseDao().delete(task);
                                    mUploadScrapSuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadScrapFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传报损任务
     */
    public void uploadAllBreakageTask() {
        mUploadBreakageAll.set(0);
        mUploadBreakageFailure.set(0);
        mUploadBreakageSuccess.set(0);
        List<BreakageTaskResponse> tasks = mManager.getDaoSession().getBreakageTaskResponseDao().queryBuilder().
                where(BreakageTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (tasks == null || tasks.isEmpty()) {
            //无完成状态的报损任务，不需要上传
            successType.add(UploadTypes.BREAKAGE_TASK);
            analysisResult();
        } else {
            mUploadBreakageAll.set(tasks.size());
            for (final BreakageTaskResponse task : tasks) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();

                RetrofitManager.getInstance().getOperateManager().uploadBreakageTask(task.getTaskID(), TasksUtils.concatTaskId(assets), task.getWorkareaId(), task.getPrice(), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadBreakageSuccess.intValue() + mUploadBreakageFailure.intValue()) == mUploadBreakageAll.intValue();
                                System.out.println("yjx uploadAllBreakageTask" + mUploadBreakageSuccess.intValue() + "->" + mUploadBreakageFailure.intValue() + "->" + mUploadBreakageAll.intValue());
                                if (success) {
                                    if (mUploadBreakageFailure.intValue() == 0) {
                                        successType.add(UploadTypes.BREAKAGE_TASK);
                                    } else {
                                        falureType.add(UploadTypes.BREAKAGE_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllBreakageTask success------------------");
                                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                        .list();
                                mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getBreakageTaskResponseDao().delete(task);

                                mUploadBreakageSuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllBreakageTask error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getBreakageTaskResponseDao().delete(task);
                                    mUploadBreakageSuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadBreakageFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传归还任务
     */
    public void uploadAllReturnTask() {
        mUploadReturnAll.set(0);
        mUploadReturnFailure.set(0);
        mUploadReturnSuccess.set(0);
        List<ReturnTaskResponse> tasks = mManager.getDaoSession().getReturnTaskResponseDao().queryBuilder().
                where(ReturnTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (tasks == null || tasks.isEmpty()) {
            //无完成状态的归还任务，不需要上传
            successType.add(UploadTypes.RETURN_TASK);
            analysisResult();
        } else {
            mUploadReturnAll.set(tasks.size());
            for (final ReturnTaskResponse task : tasks) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();

                RetrofitManager.getInstance().getOperateManager().uploadReturnTask(task.getTaskID(), TasksUtils.concatTaskId(assets), task.getWorkareaId(), mUser.getId())
                        .doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                boolean success = (mUploadReturnSuccess.intValue() + mUploadReturnFailure.intValue()) == mUploadReturnAll.intValue();
                                System.out.println("yjx uploadAllReturnTask" + mUploadReturnSuccess.intValue() + "->" + mUploadReturnFailure.intValue() + "->" + mUploadReturnAll.intValue());
                                if (success) {
                                    if (mUploadReturnFailure.intValue() == 0) {
                                        successType.add(UploadTypes.RETURN_TASK);
                                    } else {
                                        falureType.add(UploadTypes.RETURN_TASK);
                                    }
                                    analysisResult();
                                }
                            }
                        })
                        .subscribe(new BaseObserver<String>() {

                            @Override
                            public void onNext(@NonNull String s) {
                                System.out.println("yjx uploadAllReturnTask success------------------");
                                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                        .list();
                                mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                mManager.getDaoSession().getReturnTaskResponseDao().delete(task);

                                mUploadReturnSuccess.incrementAndGet();
                            }

                            @Override
                            public void onError(ExceptionHandle.ResponeThrowable e) {
                                System.out.println("yjx uploadAllReturnTask error-------------------" + e);
                                if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                    //提交失败,可能改资产已经盘点过 从本地中删除
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getReturnTaskResponseDao().delete(task);
                                    mUploadReturnSuccess.incrementAndGet();
                                } else {
                                    //其他诸如网络原因失败，下次继续提交
                                    mUploadReturnFailure.incrementAndGet();
                                }
                            }
                        });
            }
        }
    }

    /**
     * 上传调拨任务
     */
    public void uploadAllAllocationTask() {
        mUploadAllocationAll.set(0);
        mUploadAllocationFailure.set(0);
        mUploadAllocationSuccess.set(0);
        List<AllocationTaskResponse> tasks = mManager.getDaoSession().getAllocationTaskResponseDao().queryBuilder().
                where(AllocationTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).list();
        if (tasks == null || tasks.isEmpty()) {
            //无完成状态的调拨任务，不需要上传
            successType.add(UploadTypes.ALLOCATION_TASK);
            analysisResult();
        } else {
            mUploadAllocationAll.set(tasks.size());
            for (final AllocationTaskResponse task : tasks) {
                final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                        .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()),
                                TaskAssetFoundDao.Properties.Checked.eq(true))
                        .list();
                if(task.BUSINESS_KEY_.startsWith("AllocationExternal")){
                    RetrofitManager.getInstance().getOperateManager().uploadAllocationExternalTask(task.getTaskID(), TasksUtils.concatTaskId(assets), mUser.getId())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {
                                    boolean success = (mUploadAllocationSuccess.intValue() + mUploadAllocationFailure.intValue()) == mUploadAllocationAll.intValue();
                                    System.out.println("yjx uploadAllAllocationTask" + mUploadAllocationSuccess.intValue() + "->" + mUploadAllocationFailure.intValue() + "->" + mUploadAllocationAll.intValue());
                                    if (success) {
                                        if (mUploadAllocationFailure.intValue() == 0) {
                                            successType.add(UploadTypes.ALLOCATION_TASK);
                                        } else {
                                            falureType.add(UploadTypes.ALLOCATION_TASK);
                                        }
                                        analysisResult();
                                    }
                                }
                            })
                            .subscribe(new BaseObserver<String>() {

                                @Override
                                public void onNext(@NonNull String s) {
                                    System.out.println("yjx uploadAllAllocationTask success------------------");
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getAllocationTaskResponseDao().delete(task);

                                    mUploadAllocationSuccess.incrementAndGet();
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable e) {
                                    System.out.println("yjx uploadAllAllocationTask error-------------------" + e);
                                    if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                        //提交失败,可能改资产已经盘点过 从本地中删除
                                        final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                                .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                                .list();
                                        mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                        mManager.getDaoSession().getAllocationTaskResponseDao().delete(task);
                                        mUploadAllocationSuccess.incrementAndGet();
                                    } else {
                                        //其他诸如网络原因失败，下次继续提交
                                        mUploadAllocationFailure.incrementAndGet();
                                    }
                                }
                            });

                }else{
                    RetrofitManager.getInstance().getOperateManager().uploadAllocationInternalTask(task.getTaskID(), TasksUtils.concatTaskId(assets), mUser.getId())
                            .doFinally(new Action() {
                                @Override
                                public void run() throws Exception {
                                    boolean success = (mUploadAllocationSuccess.intValue() + mUploadAllocationFailure.intValue()) == mUploadAllocationAll.intValue();
                                    System.out.println("yjx uploadAllAllocationTask" + mUploadAllocationSuccess.intValue() + "->" + mUploadAllocationFailure.intValue() + "->" + mUploadAllocationAll.intValue());
                                    if (success) {
                                        if (mUploadAllocationFailure.intValue() == 0) {
                                            successType.add(UploadTypes.ALLOCATION_TASK);
                                        } else {
                                            falureType.add(UploadTypes.ALLOCATION_TASK);
                                        }
                                        analysisResult();
                                    }
                                }
                            })
                            .subscribe(new BaseObserver<String>() {

                                @Override
                                public void onNext(@NonNull String s) {
                                    System.out.println("yjx uploadAllAllocationTask success------------------");
                                    final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                            .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                            .list();
                                    mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                    mManager.getDaoSession().getAllocationTaskResponseDao().delete(task);

                                    mUploadAllocationSuccess.incrementAndGet();
                                }

                                @Override
                                public void onError(ExceptionHandle.ResponeThrowable e) {
                                    System.out.println("yjx uploadAllAllocationTask error-------------------" + e);
                                    if (e.getCode() == ExceptionHandle.ERROR.SERVICE_ERROR) {
                                        //提交失败,可能改资产已经盘点过 从本地中删除
                                        final List<TaskAssetFound> assets = mManager.getDaoSession().getTaskAssetFoundDao()
                                                .queryBuilder().where(TaskAssetFoundDao.Properties.TaskId.eq(task.getTaskId()))
                                                .list();
                                        mManager.getDaoSession().getTaskAssetFoundDao().deleteInTx(assets);
                                        mManager.getDaoSession().getAllocationTaskResponseDao().delete(task);
                                        mUploadAllocationSuccess.incrementAndGet();
                                    } else {
                                        //其他诸如网络原因失败，下次继续提交
                                        mUploadAllocationFailure.incrementAndGet();
                                    }
                                }
                            });
                }
            }
        }
    }

    private void analysisResult() {
        if (mCallback != null) {
            mCallback.onUploadProgressUpdate(successType.size(), falureType.size(), upload_count);
            if (successType.size() + falureType.size() == upload_count) {
                mCallback.onUploadComplete(falureType.size() == 0);
            }
        }
    }

    private boolean hasDataUpload() {
        long assetAdds = mManager.getDaoSession().getAssetAddBeanDao().count();
        boolean hasAdd = assetAdds != 0;
        if (hasAdd) {
            return true;
        }
        long inventory = mManager.getDaoSession().getInventoryResponseDao().queryBuilder().where(InventoryResponseDao.Properties.Status.eq(InventoryResponse.FINISHED)).count();
        boolean hasInventory = inventory != 0;
        if (hasInventory) {
            return true;
        }
        long receiveTask = mManager.getDaoSession().getReceiveTaskResponseDao().queryBuilder().where(ReceiveTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasReceive = receiveTask != 0;
        if (hasReceive) {
            return true;
        }
        long borrowTask = mManager.getDaoSession().getBorrowTaskResponseDao().queryBuilder().where(BorrowTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasBorrow = borrowTask != 0;
        if (hasBorrow) {
            return true;
        }
        long scrapTask = mManager.getDaoSession().getScrapTaskResponseDao().queryBuilder().where(ScrapTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasScrap = scrapTask != 0;
        if (hasScrap) {
            return true;
        }
        long breakageTask = mManager.getDaoSession().getBreakageTaskResponseDao().queryBuilder().where(BreakageTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasBreakage = breakageTask != 0;
        if (hasBreakage) {
            return true;
        }
        long returnTask = mManager.getDaoSession().getReturnTaskResponseDao().queryBuilder().where(ReturnTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasReturn = returnTask != 0;
        if (hasReturn) {
            return true;
        }
        long allocationTask = mManager.getDaoSession().getAllocationTaskResponseDao().queryBuilder().where(AllocationTaskResponseDao.Properties.Status.eq(BaseTaskResponse.FINISHED)).count();
        boolean hasAllocation = allocationTask != 0;
        if (hasAllocation) {
            return true;
        }

        return false;
    }

    public interface UploadCallback {
        void onNoUpload();

        void onUploadStart();

        void onUploadComplete(boolean success);

        void onUploadProgressUpdate(int success, int falure, int all);
    }
}
