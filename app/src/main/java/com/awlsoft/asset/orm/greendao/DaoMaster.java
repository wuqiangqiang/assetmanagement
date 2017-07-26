package com.awlsoft.asset.orm.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        AssetAddBeanDao.createTable(db, ifNotExists);
        AssetFoundDao.createTable(db, ifNotExists);
        AdminResponseDao.createTable(db, ifNotExists);
        AllocationTaskResponseDao.createTable(db, ifNotExists);
        AssetBatchResponseDao.createTable(db, ifNotExists);
        AssetResponseDao.createTable(db, ifNotExists);
        BorrowTaskResponseDao.createTable(db, ifNotExists);
        BrandResponseDao.createTable(db, ifNotExists);
        BreakageTaskResponseDao.createTable(db, ifNotExists);
        CategoryGbResponseDao.createTable(db, ifNotExists);
        CategoryResponseDao.createTable(db, ifNotExists);
        DepartmentResponseDao.createTable(db, ifNotExists);
        InventoryResponseDao.createTable(db, ifNotExists);
        KeeperResponseDao.createTable(db, ifNotExists);
        ModelResponseDao.createTable(db, ifNotExists);
        ReceiveTaskResponseDao.createTable(db, ifNotExists);
        ReturnTaskResponseDao.createTable(db, ifNotExists);
        ScrapTaskResponseDao.createTable(db, ifNotExists);
        UserResponseDao.createTable(db, ifNotExists);
        WorkareaResponseDao.createTable(db, ifNotExists);
        TaskAssetFoundDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        AssetAddBeanDao.dropTable(db, ifExists);
        AssetFoundDao.dropTable(db, ifExists);
        AdminResponseDao.dropTable(db, ifExists);
        AllocationTaskResponseDao.dropTable(db, ifExists);
        AssetBatchResponseDao.dropTable(db, ifExists);
        AssetResponseDao.dropTable(db, ifExists);
        BorrowTaskResponseDao.dropTable(db, ifExists);
        BrandResponseDao.dropTable(db, ifExists);
        BreakageTaskResponseDao.dropTable(db, ifExists);
        CategoryGbResponseDao.dropTable(db, ifExists);
        CategoryResponseDao.dropTable(db, ifExists);
        DepartmentResponseDao.dropTable(db, ifExists);
        InventoryResponseDao.dropTable(db, ifExists);
        KeeperResponseDao.dropTable(db, ifExists);
        ModelResponseDao.dropTable(db, ifExists);
        ReceiveTaskResponseDao.dropTable(db, ifExists);
        ReturnTaskResponseDao.dropTable(db, ifExists);
        ScrapTaskResponseDao.dropTable(db, ifExists);
        UserResponseDao.dropTable(db, ifExists);
        WorkareaResponseDao.dropTable(db, ifExists);
        TaskAssetFoundDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AssetAddBeanDao.class);
        registerDaoClass(AssetFoundDao.class);
        registerDaoClass(AdminResponseDao.class);
        registerDaoClass(AllocationTaskResponseDao.class);
        registerDaoClass(AssetBatchResponseDao.class);
        registerDaoClass(AssetResponseDao.class);
        registerDaoClass(BorrowTaskResponseDao.class);
        registerDaoClass(BrandResponseDao.class);
        registerDaoClass(BreakageTaskResponseDao.class);
        registerDaoClass(CategoryGbResponseDao.class);
        registerDaoClass(CategoryResponseDao.class);
        registerDaoClass(DepartmentResponseDao.class);
        registerDaoClass(InventoryResponseDao.class);
        registerDaoClass(KeeperResponseDao.class);
        registerDaoClass(ModelResponseDao.class);
        registerDaoClass(ReceiveTaskResponseDao.class);
        registerDaoClass(ReturnTaskResponseDao.class);
        registerDaoClass(ScrapTaskResponseDao.class);
        registerDaoClass(UserResponseDao.class);
        registerDaoClass(WorkareaResponseDao.class);
        registerDaoClass(TaskAssetFoundDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}