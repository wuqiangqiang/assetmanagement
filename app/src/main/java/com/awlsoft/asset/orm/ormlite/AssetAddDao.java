package com.awlsoft.asset.orm.ormlite;

import android.content.Context;

import com.awlsoft.asset.model.entry.AssetAddBean;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by yejingxian on 2017/5/19.
 */

public class AssetAddDao {
    private Context context;
    private DatabaseHelper helper;
    private Dao assetAddOpe;

    public AssetAddDao(Context context) {

        this.context = context;
        try {
            helper = DatabaseHelper.getHelper(context);
            assetAddOpe = helper.getDao(AssetAddBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addAssets2(final List<AssetAddBean> assets){
        if(assets == null || assets.size() == 0){
            return;
        }
        try {
            assetAddOpe.callBatchTasks(new Callable() {
                @Override
                public Object call() throws Exception {
                    for (AssetAddBean asset : assets){
                        assetAddOpe.create(asset);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int addAsset(final AssetAddBean asset){
        try {
            return assetAddOpe.create(asset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int addAssets(final List<AssetAddBean> assets){
        if(assets == null || assets.size() == 0){
            return 0;
        }
        try {
            return assetAddOpe.create(assets);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int clear(){
        try {
            List<AssetAddBean> list = assetAddOpe.queryForAll();
            return assetAddOpe.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteById(String rfid){
        try {
            return assetAddOpe.deleteById(rfid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<AssetAddBean> getAll(){
         try {
            return assetAddOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  new ArrayList<>();
    }

}
