package com.awlsoft.asset.model.api.sync;

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
import com.awlsoft.asset.model.entry.response.WorkareaResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yejingxian on 2017/5/18.
 */

public interface SyncService {

    //获取管理员用户
    @GET("service/handset/synchro/account/{officeId}")
    Observable<List<AdminResponse>> providerAdmin(@Path("officeId") int officeId);

    //获取保管人
    @GET("service/handset/synchro/keeper/{officeId}/{pageno}")
    Observable<KeeperListResponse> providerKeeper(@Path("officeId") int officeId, @Path("pageno") int pageno);

    //获取部门
    @GET("service/handset/synchro/department/{officeId}")
    Observable<List<DepartmentResponse>> providerDepartment(@Path("officeId") int officeId);

    //获取区域
    @GET("service/handset/synchro/workarea/{officeId}")
    Observable<List<WorkareaResponse>> providerWorkarea(@Path("officeId") int officeId);

    //获取资产属性
    @GET("service/handset/synchro/assetnature")
    Observable<AssetNatureResponse> providerAssetnature();

    //获取资产批次
    @GET("service/handset/synchro/assetbatch/{officeId}/{pageno}")
    Observable<AssetBatchListResponse> providerAssetBatch(@Path("officeId") int officeId, @Path("pageno") int pageno);

    //获取资产
    @GET("service/handset/synchro/asset/{officeId}/{pageno}")
    Observable<AssetListResponse> providerAsset(@Path("officeId") int officeId, @Path("pageno") int pageno);

    //获取盘点任务
    @GET("/service/handset/synchro/task/inventory/{accountId}/{pageno}")
    Observable<InventoryListResponse> providerInventoryTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取领用任务
    @GET("/service/handset/synchro/task/receive/{accountId}/{pageno}")
    Observable<BaseListEntry<ReceiveTaskResponse>> providerReceiveTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取借用任务
    @GET("/service/handset/synchro/task/borrow/{accountId}/{pageno}")
    Observable<BaseListEntry<BorrowTaskResponse>> providerBorrowTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取报废任务
    @GET("/service/handset/synchro/task/scrap/{accountId}/{pageno}")
    Observable<BaseListEntry<ScrapTaskResponse>> providerScrapTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取报损任务
    @GET("/service/handset/synchro/task/breakage/{accountId}/{pageno}")
    Observable<BaseListEntry<BreakageTaskResponse>> providerBreakageTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取归还任务
    @GET("/service/handset/synchro/task/return/{accountId}/{pageno}")
    Observable<BaseListEntry<ReturnTaskResponse>> providerReturnTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取内部调拨任务
    @GET("/service/handset/synchro/task/allocationinternal/{accountId}/{pageno}")
    Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationinternalTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

    //获取外部调拨任务
    @GET("/service/handset/synchro/task/allocationexternal/{accountId}/{pageno}")
    Observable<BaseListEntry<AllocationTaskResponse>> providerAllocationexternalTask(@Path("accountId") int accountId , @Path("pageno") int pageno);

}
