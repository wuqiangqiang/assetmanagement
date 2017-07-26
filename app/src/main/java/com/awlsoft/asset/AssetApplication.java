package com.awlsoft.asset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.cache.AssetAssociationCache;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.DBHelper;
import com.awlsoft.asset.orm.greendao.DaoMaster;
import com.awlsoft.asset.orm.greendao.DaoSession;
import com.awlsoft.asset.supernfc.UHFApplication;

import okhttp3.HttpUrl;

/**
 * Created by yejingxian on 2017/5/20.
 */

public class AssetApplication extends UHFApplication {
    private UserResponse mUser;
    private AssetAssociationCache mAssociation;

    public static AssetApplication get(Context context) {
        return (AssetApplication) context.getApplicationContext();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //服务器地址设置
        if(!TextUtils.isEmpty(AppSettings.getServerAddress(this))){
            setupBaseUrl(AppSettings.getServerAddress(this));
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public UserResponse getUser() {
        return mUser;
    }

    public void releaseUser() {
        mUser = null;
        mAssociation = null;
    }

    public void setupBaseUrl(String server) {
        try {
            String[] urls = server.split(":");
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host(urls[0])
                    .port(urls.length == 1 ? HttpUrl.defaultPort("http") : Integer.valueOf(urls[1]))
                    .build();
            RetrofitManager.getInstance().setBaseUrl(url.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void setUser(UserResponse user) {
        this.mUser = user;
        mAssociation = new AssetAssociationCache(user, DBManager.getInstance(this));
    }

    public AssetAssociationCache getAssociation() {
        return mAssociation;
    }

}
