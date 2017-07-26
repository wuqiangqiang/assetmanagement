package com.awlsoft.asset.model.api.normal;

import com.awlsoft.asset.model.api.exception.ExceptionHandle;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.BaseEntry;
import com.awlsoft.asset.model.entry.response.PermissionResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.model.entry.response.WrapUserResponse;
import com.awlsoft.asset.util.ObservableTransformerUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by yejingxian on 2017/5/18.
 */

public class OperateManager {
    private OperateService mService;
    private static OperateManager INSTANCE;

    private OperateManager(OperateService mService) {
        this.mService = mService;
    }

    public static OperateManager getInstance(OperateService service) {
        if (INSTANCE == null) {
            INSTANCE = new OperateManager(service);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * 登录
     *
     * @param name
     * @param password
     * @return observable 看得见的
     */
    public Observable<UserResponse> login(String name, String password) {
        return mService.login(name, password)
                .map(new Function<WrapUserResponse, UserResponse>() {
                    @Override
                    public UserResponse apply(@NonNull WrapUserResponse wrapUserResponse) throws Exception {
                        if (wrapUserResponse.getResult()) {
                            return wrapUserResponse.getAccount();
                        }
                        throw new ExceptionHandle.ServerException(ExceptionHandle.ERROR.SERVICE_ERROR, wrapUserResponse.getMsg());
                    }
                })
                .compose(ObservableTransformerUtils.<UserResponse>io_main())
                .compose(ObservableTransformerUtils.<UserResponse>parseException());
    }




    /**
     * 增加资产
     *
     * @param parasm
     * @return
     */
    public Observable<String> uploadAsset(Map<String, String> parasm) {
        return mService.uploadAssetAdd(parasm).map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    public Observable<BaseEntry<String>> uploadAsset2(Map<String, String> parasm) {
        return mService.uploadAssetAdd(parasm);
    }

    /**
     * 上传盘点到的资产
     *
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadInventoryAsset(String taskId, String assetIds, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetInventory");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传领用任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadReceiveTask(String taskId, String assetIds, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetReceive");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传借用任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadBorrowTask(String taskId, String assetIds, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetBorrow");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }
    /**
     *上传报废任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadScrapTask(String taskId, String assetIds, long workareaId, double price, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetScrap");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetId",assetIds);
        map.put("agree","1");
        map.put("workareaId",String.valueOf(workareaId));
        map.put("price",String.valueOf(price));
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传报损任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadBreakageTask(String taskId, String assetIds, long workareaId, double price, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetBreakage");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetId",assetIds);
        map.put("agree","1");
        map.put("workareaId",String.valueOf(workareaId));
        map.put("price",String.valueOf(price));
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传归还任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadReturnTask(String taskId, String assetIds, long workareaId, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AssetReturn");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("workareaId",String.valueOf(workareaId));
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传内部调拨任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadAllocationInternalTask(String taskId, String assetIds, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AllocationInternal");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }

    /**
     *上传外部调拨任务
     * @param taskId
     * @param assetIds
     * @return
     */
    public Observable<String> uploadAllocationExternalTask(String taskId, String assetIds, long accountId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("processKey","AllocationExternal");
        map.put("taskId",taskId);
        map.put("remarks","");
        map.put("assetIds",assetIds);
        map.put("agree","1");
        map.put("accountId",String.valueOf(accountId));
        return mService.uploadTask(map)
                .map(this.filterFromBaseEntry(""))
                .compose(ObservableTransformerUtils.<String>io_main())
                .compose(ObservableTransformerUtils.<String>parseException());
    }


    private <T> Function<BaseEntry<T>, T> filterFromBaseEntry() {
        return filterFromBaseEntry(null);
    }

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
}
