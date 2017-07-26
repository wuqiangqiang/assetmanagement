package com.awlsoft.asset.model.api.sync;

import android.util.Log;

import com.awlsoft.asset.contract.ReceiveTaskContract;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.api.exception.ExceptionHandle;
import com.awlsoft.asset.model.entry.BaseEntry;
import com.awlsoft.asset.model.entry.BaseListEntry;
import com.awlsoft.asset.model.entry.response.AdminResponse;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetBatchListResponse;
import com.awlsoft.asset.model.entry.response.AssetListResponse;
import com.awlsoft.asset.model.entry.response.AssetNatureResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.DepartmentResponse;
import com.awlsoft.asset.model.entry.response.InventoryListResponse;
import com.awlsoft.asset.model.entry.response.KeeperListResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by yejingxian on 2017/5/18.
 */

public class SyncManager {
    private static final String TAG = SyncManager.class.getSimpleName();
    private static final boolean DATA_DEBUG = false;

    private SyncService mService;
    private static SyncManager INSTANCE;

    private SyncManager(SyncService mService) {
        this.mService = mService;
    }

    public static SyncManager getInstance(SyncService service) {
        if (INSTANCE == null) {
            INSTANCE = new SyncManager(service);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * 获取管理员
     *
     * @param officeId
     * @return
     */
    public Observable<List<AdminResponse>> providerAdmin(int officeId) {
        return mService.providerAdmin(officeId)
                .compose(ObservableTransformerUtils.<List<AdminResponse>>io_main())
                .compose(ObservableTransformerUtils.<List<AdminResponse>>parseException());
    }

    /**
     * 获取保管人
     *
     * @param officeId
     * @param pageno
     * @return
     */
    public Observable<KeeperListResponse> providerKeeper(int officeId, int pageno) {
        return mService.providerKeeper(officeId, pageno)
                .compose(ObservableTransformerUtils.<KeeperListResponse>io_main())
                .compose(ObservableTransformerUtils.<KeeperListResponse>parseException());
    }

    /**
     * 获取pageno开始到结束的保管人
     *
     * @param officeId
     * @param pageno
     * @return
     */
    private Observable<KeeperListResponse> providerKeeperRecursive(final int officeId, int pageno) {
        final int page = pageno;
        return mService.providerKeeper(officeId, page)
                .concatMap(new Function<KeeperListResponse, ObservableSource<KeeperListResponse>>() {
                    int next = page;

                    @Override
                    public ObservableSource<KeeperListResponse> apply(@NonNull KeeperListResponse keeperListResponse) throws Exception {
                        Log.d(TAG, "providerKeeperRecursive currentPage: " + keeperListResponse.getPageNum());
                        if (keeperListResponse.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(keeperListResponse).concatWith(
                                    providerKeeperRecursive(officeId, ++next));
                        } else {
                            return Observable.just(keeperListResponse);
                        }
                    }
                });
    }

    /**
     * 获取所有保管人
     *
     * @param officeId
     * @return
     */
    public Observable<KeeperListResponse> providerAllKeeper(final int officeId) {
        return providerKeeperRecursive(officeId, 1)
                .compose(ObservableTransformerUtils.<KeeperListResponse>io_main())
                .compose(ObservableTransformerUtils.<KeeperListResponse>parseException());
    }

    /**
     * 获取部门
     *
     * @param officeId
     * @return
     */
    public Observable<List<DepartmentResponse>> providerDepartment(int officeId) {
        return mService.providerDepartment(officeId)
                .compose(ObservableTransformerUtils.<List<DepartmentResponse>>io_main())
                .compose(ObservableTransformerUtils.<List<DepartmentResponse>>parseException());
    }

    /**
     * 获取区域
     *
     * @param officeId
     * @return
     */
    public Observable<List<WorkareaResponse>> providerWorkarea(int officeId) {
        return mService.providerWorkarea(officeId)
                .compose(ObservableTransformerUtils.<List<WorkareaResponse>>io_main())
                .compose(ObservableTransformerUtils.<List<WorkareaResponse>>parseException());
    }

    /**
     * 获取资产属性
     *
     * @return
     */
    public Observable<AssetNatureResponse> providerAssetnature() {
        return mService.providerAssetnature()
                .compose(ObservableTransformerUtils.<AssetNatureResponse>io_main())
                .compose(ObservableTransformerUtils.<AssetNatureResponse>parseException());
    }

    /**
     * 获取资产批次
     *
     * @param officeId
     * @param pageno
     * @return
     */
    public Observable<AssetBatchListResponse> providerAssetBatch(int officeId, int pageno) {
        return mService.providerAssetBatch(officeId, pageno)
                .compose(ObservableTransformerUtils.<AssetBatchListResponse>io_main())
                .compose(ObservableTransformerUtils.<AssetBatchListResponse>parseException());
    }


    /**
     * 获取pageno开始到结束的资产批次
     *
     * @param officeId
     * @param pageno
     * @return
     */
    private Observable<AssetBatchListResponse> providerAssetBatchRecursive(final int officeId, int pageno) {
        final int page = pageno;
        return mService.providerAssetBatch(officeId, page)
                .concatMap(new Function<AssetBatchListResponse, ObservableSource<AssetBatchListResponse>>() {
                    int next = page;

                    @Override
                    public ObservableSource<AssetBatchListResponse> apply(@NonNull AssetBatchListResponse assetBatchListResponse) throws Exception {
                        Log.d(TAG, "providerAssetBatchRecursive currentPage: " + assetBatchListResponse.getPageNum());
                        if (assetBatchListResponse.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(assetBatchListResponse).concatWith(
                                    providerAssetBatchRecursive(officeId, ++next));
                        } else {
                            return Observable.just(assetBatchListResponse);
                        }
                    }
                });
    }

    /**
     * 获取所有资产批次
     *
     * @param officeId
     * @return
     */
    public Observable<AssetBatchListResponse> providerAllAssetBatch(final int officeId) {
        return providerAssetBatchRecursive(officeId, 1)
                .compose(ObservableTransformerUtils.<AssetBatchListResponse>io_io())
                .compose(ObservableTransformerUtils.<AssetBatchListResponse>parseException());
    }

    /**
     * 获取资产
     *
     * @param officeId
     * @param pageno
     * @return
     */
    public Observable<AssetListResponse> providerAsset(int officeId, int pageno) {
        return mService.providerAsset(officeId, pageno)
                .compose(ObservableTransformerUtils.<AssetListResponse>io_main())
                .compose(ObservableTransformerUtils.<AssetListResponse>parseException());
    }

    /**
     * 获取pageno开始到结束的资产
     *
     * @param officeId
     * @param pageno
     * @return
     */
    private Observable<AssetListResponse> providerAssetRecursive(final int officeId, int pageno) {
        final int page = pageno;
        return mService.providerAsset(officeId, page)
                .concatMap(new Function<AssetListResponse, ObservableSource<AssetListResponse>>() {
                    int next = page;

                    @Override
                    public ObservableSource<AssetListResponse> apply(@NonNull AssetListResponse assetListResponse) throws Exception {
                        Log.d(TAG, "providerAssetRecursive currentPage: " + assetListResponse.getPageNum());
                        if (assetListResponse.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(assetListResponse).concatWith(
                                    providerAssetRecursive(officeId, ++next));
                        } else {
                            return Observable.just(assetListResponse);
                        }
                    }
                });
    }

    /**
     * 返回所有资产
     *
     * @param officeId
     * @return
     */
    public Observable<AssetListResponse> providerAllAsset(final int officeId) {
        final int page = 1;
        return providerAssetRecursive(officeId, 1)
                .compose(ObservableTransformerUtils.<AssetListResponse>io_main())
                .compose(ObservableTransformerUtils.<AssetListResponse>parseException());
    }

    /**
     * 获取pageno盘点任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<InventoryListResponse> providerInventoryTask(int accountId, int pageno){
        return mService.providerInventoryTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<InventoryListResponse>io_main())
                .compose(ObservableTransformerUtils.<InventoryListResponse>parseException());
    }

    /**
     * 获取pageno开始的盘点任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<InventoryListResponse> providerInventoryTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerInventoryTask(accountId, page)
                .concatMap(new Function<InventoryListResponse, ObservableSource<InventoryListResponse>>() {
                    int next = page;
                    @Override
                    public ObservableSource<InventoryListResponse> apply(@NonNull InventoryListResponse inventoryListResponse) throws Exception {
                        Log.d(TAG, "providerInventoryTaskRecursive currentPage: " + inventoryListResponse.getPageNum());
                        if (inventoryListResponse.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(inventoryListResponse).concatWith(
                                    providerInventoryTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(inventoryListResponse);
                        }
                    }
                });
    }

    /**
     * 获取所有盘点任务
     * @param accountId
     * @return
     */
    public Observable<InventoryListResponse> providerAllInventoryTask(int accountId){
        final int page = 1;
        return providerInventoryTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<InventoryListResponse>io_main())
                .compose(ObservableTransformerUtils.<InventoryListResponse>parseException());
    }


    /**
     * 获取pageno领用任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<ReceiveTaskResponse>> providerReceiveTask(int accountId, int pageno){
        return mService.providerReceiveTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<ReceiveTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ReceiveTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的领用任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<ReceiveTaskResponse>> providerReceiveTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerReceiveTask(accountId, page)
                .concatMap(new Function<BaseListEntry<ReceiveTaskResponse>, ObservableSource<BaseListEntry<ReceiveTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<ReceiveTaskResponse>> apply(@NonNull BaseListEntry<ReceiveTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerReceiveTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerReceiveTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有领用任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<ReceiveTaskResponse>> providerAllReceiveTask(int accountId){
        final int page = 1;
        return providerReceiveTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<ReceiveTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ReceiveTaskResponse>>parseException());
    }


    /**
     * 获取pageno借用任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<BorrowTaskResponse>> providerBorrowTask(int accountId, int pageno){
        return mService.providerBorrowTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<BorrowTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<BorrowTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的借用任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<BorrowTaskResponse>> providerBorrowTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerBorrowTask(accountId, page)
                .concatMap(new Function<BaseListEntry<BorrowTaskResponse>, ObservableSource<BaseListEntry<BorrowTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<BorrowTaskResponse>> apply(@NonNull BaseListEntry<BorrowTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerBorrowTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有借用任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<BorrowTaskResponse>> providerAllBorrowTask(int accountId){
        final int page = 1;
        return providerBorrowTask(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<BorrowTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<BorrowTaskResponse>>parseException());
    }


    /**
     * 获取pageno报废任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<ScrapTaskResponse>> providerScrapTask(int accountId, int pageno){
        return mService.providerScrapTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<ScrapTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ScrapTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的报废任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<ScrapTaskResponse>> providerScrapTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerScrapTask(accountId, page)
                .concatMap(new Function<BaseListEntry<ScrapTaskResponse>, ObservableSource<BaseListEntry<ScrapTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<ScrapTaskResponse>> apply(@NonNull BaseListEntry<ScrapTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerScrapTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有报废任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<ScrapTaskResponse>> providerAllScrapTask(int accountId){
        final int page = 1;
        return providerScrapTask(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<ScrapTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ScrapTaskResponse>>parseException());
    }


    /**
     * 获取pageno报损任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<BreakageTaskResponse>> providerBreakageTask(int accountId, int pageno){
        return mService.providerBreakageTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<BreakageTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<BreakageTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的报损任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<BreakageTaskResponse>> providerBreakageTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerBreakageTask(accountId, page)
                .concatMap(new Function<BaseListEntry<BreakageTaskResponse>, ObservableSource<BaseListEntry<BreakageTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<BreakageTaskResponse>> apply(@NonNull BaseListEntry<BreakageTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerBreakageTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有报损任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<BreakageTaskResponse>> providerAllBreakageTask(int accountId){
        final int page = 1;
        return providerBreakageTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<BreakageTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<BreakageTaskResponse>>parseException());
    }


    /**
     * 获取pageno归还任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<ReturnTaskResponse>> providerReturnTask(int accountId, int pageno){
        return mService.providerReturnTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<ReturnTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ReturnTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的归还任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<ReturnTaskResponse>> providerReturnTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerReturnTask(accountId, page)
                .concatMap(new Function<BaseListEntry<ReturnTaskResponse>, ObservableSource<BaseListEntry<ReturnTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<ReturnTaskResponse>> apply(@NonNull BaseListEntry<ReturnTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerReturnTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有归还任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<ReturnTaskResponse>> providerAllReturnTask(int accountId){
        final int page = 1;
        return providerReturnTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<ReturnTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<ReturnTaskResponse>>parseException());
    }


    /**
     * 获取pageno内部调拨任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationinternalTask(int accountId, int pageno){
        return mService.providerAllocationinternalTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的内部调拨任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationinternalTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerAllocationinternalTask(accountId, page)
                .concatMap(new Function<BaseListEntry<AllocationTaskResponse>, ObservableSource<BaseListEntry<AllocationTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<AllocationTaskResponse>> apply(@NonNull BaseListEntry<AllocationTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerAllocationinternalTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有内部调拨任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<AllocationTaskResponse>> providerAllAllocationinternalTask(int accountId){
        final int page = 1;
        return providerAllocationinternalTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>parseException());
    }


    /**
     * 获取pageno内部调拨任务
     * @param accountId
     * @param pageno
     * @return
     */
    public Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationexternalTask(int accountId, int pageno){
        return mService.providerAllocationexternalTask(accountId, pageno)
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>parseException());
    }

    /**
     * 获取pageno开始的内部调拨任务
     * @param accountId
     * @param pageno
     * @return
     */
    private Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationexternalTaskRecursive(final int accountId, int pageno){
        final int page = pageno;
        return mService.providerAllocationexternalTask(accountId, page)
                .concatMap(new Function<BaseListEntry<AllocationTaskResponse>, ObservableSource<BaseListEntry<AllocationTaskResponse>>>() {
                    int next = page;
                    @Override
                    public ObservableSource<BaseListEntry<AllocationTaskResponse>> apply(@NonNull BaseListEntry<AllocationTaskResponse> response) throws Exception {
                        Log.d(TAG, "providerBorrowTaskRecursive currentPage: " + response.getPageNum());
                        if (response.isHasNextPage() || (next < 10 && DATA_DEBUG)) {
                            return Observable.just(response).concatWith(
                                    providerAllocationexternalTaskRecursive(accountId, ++next));
                        } else {
                            return Observable.just(response);
                        }
                    }
                });
    }

    /**
     * 获取所有内部调拨任务
     * @param accountId
     * @return
     */
    public Observable<BaseListEntry<AllocationTaskResponse>> providerAllAllocationexternalTask(int accountId){
        final int page = 1;
        return providerAllocationexternalTaskRecursive(accountId, page)
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>io_main())
                .compose(ObservableTransformerUtils.<BaseListEntry<AllocationTaskResponse>>parseException());
    }








    private <T> Function<BaseEntry<T>, T> filterFromBaseEntry() {
        return new Function<BaseEntry<T>, T>() {
            @Override
            public T apply(@NonNull BaseEntry<T> tBaseEntry) throws Exception {
                if (tBaseEntry.getResult()) {
                    return tBaseEntry.getData();
                }
                throw new ExceptionHandle.ServerException(ExceptionHandle.ERROR.SERVICE_ERROR, tBaseEntry.getMsg());
            }
        };
    }
}
