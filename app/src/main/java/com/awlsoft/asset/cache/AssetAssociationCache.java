package com.awlsoft.asset.cache;

import com.awlsoft.asset.model.api.BaseObserver;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.api.exception.ExceptionHandle;
import com.awlsoft.asset.model.entry.BaseListEntry;
import com.awlsoft.asset.model.entry.response.AdminResponse;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetBatchListResponse;
import com.awlsoft.asset.model.entry.response.AssetListResponse;
import com.awlsoft.asset.model.entry.response.AssetNatureResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.DepartmentResponse;
import com.awlsoft.asset.model.entry.response.InventoryListResponse;
import com.awlsoft.asset.model.entry.response.KeeperListResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yejingxian on 2017/5/23.
 */

public class AssetAssociationCache {

    private static ArrayList<AdminResponse> adminsList;
    private static ArrayList<KeeperListResponse> keepersList;
    private static ArrayList<DepartmentResponse> departmentsList;
    private static ArrayList<WorkareaResponse> areasList;
    private static ArrayList<AssetNatureResponse> assetNaturesList;
    private static ArrayList<AssetBatchListResponse> assetBatchsList;
    private static ArrayList<AssetListResponse> assetsList;
    private static ArrayList<InventoryListResponse> inventoryList;

    private DBManager mManager;
    private UserResponse mUser;

    private boolean adminLoaded;
    private boolean keeperLoaded;
    private boolean departmentLoaded;
    private boolean areaLoaded;
    private boolean assetNatureLoaded;
    private boolean assetBatchLoaded;
    private boolean assetLoaded;

    private int sync_count = 15;

    private Callback mCallback;

    public static enum SyncTypes {
        ADMIN, KEEPER, DEPARTMENT, AREA, ASSETNATURE, ASSETBATCH, ASSET, INVENTORY,
        RECEIVE,BORROW,SCRAP,BREAKAGE,RETURN,ALLOCATIONINTERNAL,ALLOCATIONEXTERNAL
    }

    private Set<SyncTypes> successType = new HashSet<>();
    private Set<SyncTypes> falureType = new HashSet<>();

    static {
        adminsList = new ArrayList<>();
        keepersList = new ArrayList<>();
        departmentsList = new ArrayList<>();
        areasList = new ArrayList<>();
        assetNaturesList = new ArrayList<>();
        assetBatchsList = new ArrayList<>();
        assetsList = new ArrayList<>();
        inventoryList = new ArrayList<>();
    }

    public static void clear() {
        adminsList = new ArrayList<>();
        keepersList = new ArrayList<>();
        departmentsList = new ArrayList<>();
        areasList = new ArrayList<>();
        assetNaturesList = new ArrayList<>();
        assetBatchsList = new ArrayList<>();
        assetsList = new ArrayList<>();
        inventoryList = new ArrayList<>();
    }

    public void resetStatus() {
        successType.clear();
        falureType.clear();
    }

    public AssetAssociationCache(UserResponse user, DBManager manager) {
        this.mUser = user;
        this.mManager = manager;
    }

    public void syncAssociation(Callback cb) {
        sync_count = 15;
        mCallback = cb;
        clear();
        resetStatus();
        mCallback.onSyncStart();
        syncAdmin(mUser);
        syncKeeper(mUser);
        syncDepartment(mUser);
        syncWorkarea(mUser);
        syncAssetnature(mUser);
        syncAssetbatch(mUser);
        syncAsset(mUser);
        syncInventory(mUser);

        syncRecivieTask(mUser);
        syncBorrowTask(mUser);
        syncScrapTask(mUser);
        syncBreakageTask(mUser);
        syncReturnTask(mUser);
        syncAllocationinternalTask(mUser);
        syncAllocationexternalTask(mUser);

    }

    /**
     * 同步管理员
     *
     * @param user
     */
    private void syncAdmin(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAdmin(user.getOfficeId())
                .compose(this.<List<AdminResponse>>postExecute(SyncTypes.ADMIN))
                .compose(ObservableTransformerUtils.<List<AdminResponse>>io_io())
                .subscribe(new BaseObserver<List<AdminResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull List<AdminResponse> adminResponses) {
                        if (adminResponses != null) {
                            adminsList.addAll(adminResponses);
                            mManager.getDaoSession().getAdminResponseDao().insertOrReplaceInTx(adminResponses);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAdmin 成功！");
                    }
                });
    }

    /**
     * 同步保管员
     *
     * @param user
     */
    private void syncKeeper(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllKeeper(user.getOfficeId())
                .compose(this.<KeeperListResponse>postExecute(SyncTypes.KEEPER))
                .compose(ObservableTransformerUtils.<KeeperListResponse>io_io())
                .subscribe(new BaseObserver<KeeperListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull KeeperListResponse keeperListResponse) {
                        if (keeperListResponse != null) {
                            keepersList.add(keeperListResponse);
                            if (keeperListResponse.getList() != null) {
                                mManager.getDaoSession().getKeeperResponseDao().insertOrReplaceInTx(keeperListResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncKeeper 成功！");
                    }
                });

    }

    /**
     * 同步部门
     *
     * @param user
     */
    private void syncDepartment(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerDepartment(user.getOfficeId())
                .compose(this.<List<DepartmentResponse>>postExecute(SyncTypes.DEPARTMENT))
                .compose(ObservableTransformerUtils.<List<DepartmentResponse>>io_io())
                .subscribe(new BaseObserver<List<DepartmentResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull List<DepartmentResponse> departmentResponses) {
                        if (departmentResponses != null) {
                            departmentsList.addAll(departmentResponses);
                            mManager.getDaoSession().getDepartmentResponseDao().insertOrReplaceInTx(departmentResponses);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncDepartment 成功！");
                    }
                });
    }

    /**
     * 同步工作区域
     *
     * @param user
     */
    private void syncWorkarea(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerWorkarea(user.getOfficeId())
                .compose(this.<List<WorkareaResponse>>postExecute(SyncTypes.AREA))
                .compose(ObservableTransformerUtils.<List<WorkareaResponse>>io_io())
                .subscribe(new BaseObserver<List<WorkareaResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull List<WorkareaResponse> workareaResponses) {
                        if (workareaResponses != null) {
                            areasList.addAll(workareaResponses);
                            mManager.getDaoSession().getWorkareaResponseDao().insertOrReplaceInTx(workareaResponses);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncWorkarea 成功！");
                    }
                });
    }

    /**
     * 同步资产属性
     *
     * @param user
     */
    private void syncAssetnature(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAssetnature()
                .compose(this.<AssetNatureResponse>postExecute(SyncTypes.ASSETNATURE))
                .compose(ObservableTransformerUtils.<AssetNatureResponse>io_io())
                .subscribe(new BaseObserver<AssetNatureResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull AssetNatureResponse assetNatureResponse) {
                        if (assetNatureResponse != null) {
                            assetNaturesList.add(assetNatureResponse);
                            if (assetNatureResponse.getBrandList() != null) {
                                mManager.getDaoSession().getBrandResponseDao().insertOrReplaceInTx(assetNatureResponse.getBrandList());
                            }
                            if (assetNatureResponse.getCategoryGbList() != null) {
                                mManager.getDaoSession().getCategoryGbResponseDao().insertOrReplaceInTx(assetNatureResponse.getCategoryGbList());
                            }
                            if (assetNatureResponse.getCategoryList() != null) {
                                mManager.getDaoSession().getCategoryResponseDao().insertOrReplaceInTx(assetNatureResponse.getCategoryList());
                            }
                            if (assetNatureResponse.getModelList() != null) {
                                mManager.getDaoSession().getModelResponseDao().insertOrReplaceInTx(assetNatureResponse.getModelList());
                            }

                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAssetnature 成功！");
                    }
                });
    }

    /**
     * 同步批次
     *
     * @param user
     */
    private void syncAssetbatch(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllAssetBatch(user.getOfficeId())
                .compose(this.<AssetBatchListResponse>postExecute(SyncTypes.ASSETBATCH))
                .compose(ObservableTransformerUtils.<AssetBatchListResponse>io_io())
                .subscribe(new BaseObserver<AssetBatchListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull AssetBatchListResponse assetBatchListResponse) {
                        if (assetBatchListResponse != null) {
                            assetBatchsList.add(assetBatchListResponse);
                            if (assetBatchListResponse.getList() != null) {
                                mManager.getDaoSession().getAssetBatchResponseDao().insertOrReplaceInTx(assetBatchListResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAssetbatch 成功！");
                    }
                });
    }

    /**
     * 同步资产
     *
     * @param user
     */
    private void syncAsset(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllAsset(user.getOfficeId())
                .compose(this.<AssetListResponse>postExecute(SyncTypes.ASSET))
                .compose(ObservableTransformerUtils.<AssetListResponse>io_io())
                .subscribe(new BaseObserver<AssetListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull AssetListResponse assetListResponse) {
                        if (assetListResponse != null) {
                            assetsList.add(assetListResponse);
                            if (assetListResponse.getList() != null) {
                                mManager.getDaoSession().getAssetResponseDao().insertOrReplaceInTx(assetListResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAsset 成功！");
                    }
                });
    }

    /**
     * 同步资产盘点
     *
     * @param user
     */
    private void syncInventory(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllInventoryTask(user.getId().intValue())
                .compose(this.<InventoryListResponse>postExecute(SyncTypes.INVENTORY))
                .compose(ObservableTransformerUtils.<InventoryListResponse>io_io())
                .subscribe(new BaseObserver<InventoryListResponse>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull InventoryListResponse inventoryListResponse) {
                        if (inventoryListResponse != null) {
                            inventoryList.add(inventoryListResponse);
                            if (inventoryListResponse.getList() != null) {
                                mManager.getDaoSession().getInventoryResponseDao().insertOrReplaceInTx(inventoryListResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncInventory 成功！");
                    }
                });
    }

    /**
     * 同步领用申请
     *
     * @param user
     */
    private void syncRecivieTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllReceiveTask(user.getId().intValue())
                .compose(this.<BaseListEntry<ReceiveTaskResponse>>postExecute(SyncTypes.RECEIVE))
                .compose(ObservableTransformerUtils.<BaseListEntry<ReceiveTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<ReceiveTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<ReceiveTaskResponse> receiveListResponse) {
                        if (receiveListResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (receiveListResponse.getList() != null) {
                                mManager.getDaoSession().getReceiveTaskResponseDao().insertOrReplaceInTx(receiveListResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncRecivieTask 成功！");
                    }
                });
    }

    /**
     * 同步借用申请
     *
     * @param user
     */
    private void syncBorrowTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllBorrowTask(user.getId().intValue())
                .compose(this.<BaseListEntry<BorrowTaskResponse>>postExecute(SyncTypes.BORROW))
                .compose(ObservableTransformerUtils.<BaseListEntry<BorrowTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<BorrowTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<BorrowTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getBorrowTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncBorrowTask 成功！");
                    }
                });
    }

    /**
     * 同步报废申请
     *
     * @param user
     */
    private void syncScrapTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllScrapTask(user.getId().intValue())
                .compose(this.<BaseListEntry<ScrapTaskResponse>>postExecute(SyncTypes.SCRAP))
                .compose(ObservableTransformerUtils.<BaseListEntry<ScrapTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<ScrapTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<ScrapTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getScrapTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncScrapTask 成功！");
                    }
                });
    }


    /**
     * 同步报损申请
     *
     * @param user
     */
    private void syncBreakageTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllBreakageTask(user.getId().intValue())
                .compose(this.<BaseListEntry<BreakageTaskResponse>>postExecute(SyncTypes.BREAKAGE))
                .compose(ObservableTransformerUtils.<BaseListEntry<BreakageTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<BreakageTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<BreakageTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getBreakageTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncBreakageTask 成功！");
                    }
                });
    }


    /**
     * 同步归还申请
     *
     * @param user
     */
    private void syncReturnTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllReturnTask(user.getId().intValue())
                .compose(this.<BaseListEntry<ReturnTaskResponse>>postExecute(SyncTypes.RETURN))
                .compose(ObservableTransformerUtils.<BaseListEntry<ReturnTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<ReturnTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<ReturnTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getReturnTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncReturnTask 成功！");
                    }
                });
    }


    /**
     * 同步内部调拨申请
     *
     * @param user
     */
    private void syncAllocationinternalTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllAllocationinternalTask(user.getId().intValue())
                .compose(this.<BaseListEntry<AllocationTaskResponse>>postExecute(SyncTypes.ALLOCATIONINTERNAL))
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<AllocationTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<AllocationTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getAllocationTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAllocationinternalTask 成功！");
                    }
                });
    }


    /**
     * 同步外部调拨申请
     *
     * @param user
     */
    private void syncAllocationexternalTask(final UserResponse user) {
        RetrofitManager.getInstance().getSyncManager().providerAllAllocationexternalTask(user.getId().intValue())
                .compose(this.<BaseListEntry<AllocationTaskResponse>>postExecute(SyncTypes.ALLOCATIONEXTERNAL))
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_io())
                .subscribe(new BaseObserver<BaseListEntry<AllocationTaskResponse>>() {
                    @Override
                    public void onError(ExceptionHandle.ResponeThrowable e) {

                    }

                    @Override
                    public void onNext(@NonNull BaseListEntry<AllocationTaskResponse> listResponse) {
                        if (listResponse != null) {
                            //缓存
                            //inventoryList.add(receiveListResponse);
                            if (listResponse.getList() != null) {
                                mManager.getDaoSession().getAllocationTaskResponseDao().insertOrReplaceInTx(listResponse.getList());
                            }
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        System.out.println("yjx  syncAllocationexternalTask 成功！");
                    }
                });
    }



    public <T> ObservableTransformer<T, T> postExecute(final SyncTypes type) {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .compose(ObservableTransformerUtils.<T>io_main())
                        .doOnError(new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                falureType.add(type);
                            }
                        }).doOnComplete(new Action() {
                            @Override
                            public void run() throws Exception {
                                successType.add(type);
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                //TODO 更新进度
                                if (mCallback != null) {
                                    mCallback.onSyncProgressUpdate(successType.size(), falureType.size(), sync_count);
                                    if (successType.size() + falureType.size() == sync_count) {
                                        mCallback.onSyncComplete(falureType.size() == 0);
                                    }
                                }
                            }
                        });
            }
        };
    }

    public interface Callback {
        void onSyncStart();

        void onSyncComplete(boolean success);

        void onSyncProgressUpdate(int success, int falure, int all);
    }

}
