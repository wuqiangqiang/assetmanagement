package com.awlsoft.asset.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.awlsoft.asset.orm.greendao.DBHelper;
import com.awlsoft.asset.orm.greendao.DaoMaster;
import com.awlsoft.asset.orm.greendao.DaoSession;

/**
 * Created by yejingxian on 2017/5/25.
 */

public class DBManager {
    private static DBManager mInstance;
    private Context context;
    private DBHelper mDbHelper;

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private SQLiteDatabase db;

    private DBManager(Context context) {
        this.context = context;
        setupGreenDao();
    }

    private void setupGreenDao() {
        mDbHelper = new DBHelper(context);
        db = mDbHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void closeDataBase() {
        if (null != mDaoSession) {
            mDaoSession.clear();
            mDaoSession = null;
        }
        if (mDbHelper != null) {
            mDbHelper.close();
            mDbHelper = null;
        }
    }

    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }

    //清空同步下载的数据
    public void clearAllSyncData(){
        mDaoSession.getAdminResponseDao().deleteAll();
        mDaoSession.getWorkareaResponseDao().deleteAll();
        mDaoSession.getModelResponseDao().deleteAll();
        mDaoSession.getKeeperResponseDao().deleteAll();
        mDaoSession.getDepartmentResponseDao().deleteAll();
        mDaoSession.getCategoryResponseDao().deleteAll();
        mDaoSession.getAssetResponseDao().deleteAll();
        mDaoSession.getCategoryGbResponseDao().deleteAll();
        mDaoSession.getAdminResponseDao().deleteAll();
        mDaoSession.getBrandResponseDao().deleteAll();
        mDaoSession.getAssetBatchResponseDao().deleteAll();

        mDaoSession.getReceiveTaskResponseDao().deleteAll();
        mDaoSession.getBorrowTaskResponseDao().deleteAll();
        mDaoSession.getScrapTaskResponseDao().deleteAll();
        mDaoSession.getBreakageTaskResponseDao().deleteAll();
        mDaoSession.getReturnTaskResponseDao().deleteAll();
        mDaoSession.getAllocationTaskResponseDao().deleteAll();
        mDaoSession.getInventoryResponseDao().deleteAll();

        //清除盘点
        mDaoSession.getAssetFoundDao().deleteAll();
        mDaoSession.getAssetAddBeanDao().deleteAll();
        mDaoSession.getTaskAssetFoundDao().deleteAll();

        //mDaoSession.getUserResponseDao().deleteAll();
    }

    //清空上传的数据
    public void clearAllUploadData(){

    }

}
