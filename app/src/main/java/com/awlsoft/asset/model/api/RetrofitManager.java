package com.awlsoft.asset.model.api;

import com.awlsoft.asset.BuildConfig;
import com.awlsoft.asset.model.api.normal.OperateManager;
import com.awlsoft.asset.model.api.normal.OperateService;
import com.awlsoft.asset.model.api.sync.SyncManager;
import com.awlsoft.asset.model.api.sync.SyncService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yejingxian on 2017/3/30.
 * <p>改进管理器</p>
 */

public class RetrofitManager {
    private Retrofit mRetrofit;
    private String baseUrl = Constant.BASE_URL;

    private RetrofitManager() {
        mRetrofit = createRestAdapter(createOkHttpClient());
    }

    private static class InnerClass {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return InnerClass.INSTANCE;
    }

    public RetrofitManager setBaseUrl(String baseUrl){
        this.baseUrl = baseUrl;
        mRetrofit = mRetrofit.newBuilder().baseUrl(baseUrl).build();
        SyncManager.destroyInstance();
        OperateManager.destroyInstance();
        return this;
    }

    public OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    public Retrofit createRestAdapter(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    private <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    public SyncManager getSyncManager(){
        return SyncManager.getInstance(create(SyncService.class));
    }

    public OperateManager getOperateManager(){
        return OperateManager.getInstance(create(OperateService.class));
    }

}
