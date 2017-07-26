package com.awlsoft.asset.model.api.normal;

import com.awlsoft.asset.model.entry.BaseEntry;
import com.awlsoft.asset.model.entry.response.PermissionResponse;
import com.awlsoft.asset.model.entry.response.WrapUserResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by yejingxian on 2017/5/18.
 */

public interface OperateService {

    //登录验证
    @POST("service/handset/login")
    @FormUrlEncoded
    Observable<WrapUserResponse> login(@Field("loginname") String name, @Field("password") String password);

    //上传资产-单条
    @GET("service/handset/upload/asset")
    Observable<BaseEntry<String>> uploadAssetAdd(@QueryMap Map<String, String> parasm);

    //上传任务
    @POST("/service/handset/upload/task")
    @FormUrlEncoded
    Observable<BaseEntry<String>> uploadTask(@FieldMap Map<String,String> map);


}
