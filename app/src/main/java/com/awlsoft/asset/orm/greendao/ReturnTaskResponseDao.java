package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RETURN_TASK_RESPONSE".
*/
public class ReturnTaskResponseDao extends AbstractDao<ReturnTaskResponse, Long> {

    public static final String TABLENAME = "RETURN_TASK_RESPONSE";

    /**
     * Properties of entity ReturnTaskResponse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TaskID = new Property(0, String.class, "taskID", false, "TASK_ID");
        public final static Property TASK_DEF_KEY_ = new Property(1, String.class, "TASK_DEF_KEY_", false, "TASK__DEF__KEY_");
        public final static Property BUSINESS_KEY_ = new Property(2, String.class, "BUSINESS_KEY_", false, "BUSINESS__KEY_");
        public final static Property Id = new Property(3, Long.class, "id", true, "_id");
        public final static Property Category_id = new Property(4, int.class, "category_id", false, "CATEGORY_ID");
        public final static Property EctID = new Property(5, String.class, "ectID", false, "ECT_ID");
        public final static Property Return_no = new Property(6, String.class, "return_no", false, "RETURN_NO");
        public final static Property Keeper_id = new Property(7, long.class, "keeper_id", false, "KEEPER_ID");
        public final static Property Return_date = new Property(8, String.class, "return_date", false, "RETURN_DATE");
        public final static Property Count = new Property(9, int.class, "count", false, "COUNT");
        public final static Property Status = new Property(10, int.class, "status", false, "STATUS");
        public final static Property WorkareaId = new Property(11, Long.class, "workareaId", false, "WORKAREA_ID");
    }


    public ReturnTaskResponseDao(DaoConfig config) {
        super(config);
    }
    
    public ReturnTaskResponseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RETURN_TASK_RESPONSE\" (" + //
                "\"TASK_ID\" TEXT," + // 0: taskID
                "\"TASK__DEF__KEY_\" TEXT," + // 1: TASK_DEF_KEY_
                "\"BUSINESS__KEY_\" TEXT," + // 2: BUSINESS_KEY_
                "\"_id\" INTEGER PRIMARY KEY ," + // 3: id
                "\"CATEGORY_ID\" INTEGER NOT NULL ," + // 4: category_id
                "\"ECT_ID\" TEXT," + // 5: ectID
                "\"RETURN_NO\" TEXT," + // 6: return_no
                "\"KEEPER_ID\" INTEGER NOT NULL ," + // 7: keeper_id
                "\"RETURN_DATE\" TEXT," + // 8: return_date
                "\"COUNT\" INTEGER NOT NULL ," + // 9: count
                "\"STATUS\" INTEGER NOT NULL ," + // 10: status
                "\"WORKAREA_ID\" INTEGER);"); // 11: workareaId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RETURN_TASK_RESPONSE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ReturnTaskResponse entity) {
        stmt.clearBindings();
 
        String taskID = entity.getTaskID();
        if (taskID != null) {
            stmt.bindString(1, taskID);
        }
 
        String TASK_DEF_KEY_ = entity.getTASK_DEF_KEY_();
        if (TASK_DEF_KEY_ != null) {
            stmt.bindString(2, TASK_DEF_KEY_);
        }
 
        String BUSINESS_KEY_ = entity.getBUSINESS_KEY_();
        if (BUSINESS_KEY_ != null) {
            stmt.bindString(3, BUSINESS_KEY_);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(4, id);
        }
        stmt.bindLong(5, entity.getCategory_id());
 
        String ectID = entity.getEctID();
        if (ectID != null) {
            stmt.bindString(6, ectID);
        }
 
        String return_no = entity.getReturn_no();
        if (return_no != null) {
            stmt.bindString(7, return_no);
        }
        stmt.bindLong(8, entity.getKeeper_id());
 
        String return_date = entity.getReturn_date();
        if (return_date != null) {
            stmt.bindString(9, return_date);
        }
        stmt.bindLong(10, entity.getCount());
        stmt.bindLong(11, entity.getStatus());
 
        Long workareaId = entity.getWorkareaId();
        if (workareaId != null) {
            stmt.bindLong(12, workareaId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ReturnTaskResponse entity) {
        stmt.clearBindings();
 
        String taskID = entity.getTaskID();
        if (taskID != null) {
            stmt.bindString(1, taskID);
        }
 
        String TASK_DEF_KEY_ = entity.getTASK_DEF_KEY_();
        if (TASK_DEF_KEY_ != null) {
            stmt.bindString(2, TASK_DEF_KEY_);
        }
 
        String BUSINESS_KEY_ = entity.getBUSINESS_KEY_();
        if (BUSINESS_KEY_ != null) {
            stmt.bindString(3, BUSINESS_KEY_);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(4, id);
        }
        stmt.bindLong(5, entity.getCategory_id());
 
        String ectID = entity.getEctID();
        if (ectID != null) {
            stmt.bindString(6, ectID);
        }
 
        String return_no = entity.getReturn_no();
        if (return_no != null) {
            stmt.bindString(7, return_no);
        }
        stmt.bindLong(8, entity.getKeeper_id());
 
        String return_date = entity.getReturn_date();
        if (return_date != null) {
            stmt.bindString(9, return_date);
        }
        stmt.bindLong(10, entity.getCount());
        stmt.bindLong(11, entity.getStatus());
 
        Long workareaId = entity.getWorkareaId();
        if (workareaId != null) {
            stmt.bindLong(12, workareaId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3);
    }    

    @Override
    public ReturnTaskResponse readEntity(Cursor cursor, int offset) {
        ReturnTaskResponse entity = new ReturnTaskResponse( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // taskID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // TASK_DEF_KEY_
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // BUSINESS_KEY_
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3), // id
            cursor.getInt(offset + 4), // category_id
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // ectID
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // return_no
            cursor.getLong(offset + 7), // keeper_id
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // return_date
            cursor.getInt(offset + 9), // count
            cursor.getInt(offset + 10), // status
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11) // workareaId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ReturnTaskResponse entity, int offset) {
        entity.setTaskID(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setTASK_DEF_KEY_(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBUSINESS_KEY_(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
        entity.setCategory_id(cursor.getInt(offset + 4));
        entity.setEctID(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setReturn_no(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setKeeper_id(cursor.getLong(offset + 7));
        entity.setReturn_date(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCount(cursor.getInt(offset + 9));
        entity.setStatus(cursor.getInt(offset + 10));
        entity.setWorkareaId(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ReturnTaskResponse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ReturnTaskResponse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ReturnTaskResponse entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
