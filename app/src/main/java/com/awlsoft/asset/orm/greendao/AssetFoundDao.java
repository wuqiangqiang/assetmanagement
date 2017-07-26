package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.AssetFound;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ASSET_FOUND".
*/
public class AssetFoundDao extends AbstractDao<AssetFound, Long> {

    public static final String TABLENAME = "ASSET_FOUND";

    /**
     * Properties of entity AssetFound.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property InventoryId = new Property(1, Long.class, "inventoryId", false, "INVENTORY_ID");
        public final static Property AssetId = new Property(2, Long.class, "assetId", false, "ASSET_ID");
        public final static Property TaskId = new Property(3, String.class, "taskId", false, "TASK_ID");
    }


    public AssetFoundDao(DaoConfig config) {
        super(config);
    }
    
    public AssetFoundDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ASSET_FOUND\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"INVENTORY_ID\" INTEGER," + // 1: inventoryId
                "\"ASSET_ID\" INTEGER," + // 2: assetId
                "\"TASK_ID\" TEXT);"); // 3: taskId
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_ASSET_FOUND_INVENTORY_ID ON ASSET_FOUND" +
                " (\"INVENTORY_ID\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_ASSET_FOUND_ASSET_ID ON ASSET_FOUND" +
                " (\"ASSET_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ASSET_FOUND\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AssetFound entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long inventoryId = entity.getInventoryId();
        if (inventoryId != null) {
            stmt.bindLong(2, inventoryId);
        }
 
        Long assetId = entity.getAssetId();
        if (assetId != null) {
            stmt.bindLong(3, assetId);
        }
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(4, taskId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AssetFound entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long inventoryId = entity.getInventoryId();
        if (inventoryId != null) {
            stmt.bindLong(2, inventoryId);
        }
 
        Long assetId = entity.getAssetId();
        if (assetId != null) {
            stmt.bindLong(3, assetId);
        }
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(4, taskId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AssetFound readEntity(Cursor cursor, int offset) {
        AssetFound entity = new AssetFound( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // inventoryId
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // assetId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // taskId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AssetFound entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setInventoryId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setAssetId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setTaskId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AssetFound entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AssetFound entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AssetFound entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
