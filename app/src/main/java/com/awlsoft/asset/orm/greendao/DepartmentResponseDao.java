package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.response.DepartmentResponse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEPARTMENT_RESPONSE".
*/
public class DepartmentResponseDao extends AbstractDao<DepartmentResponse, Long> {

    public static final String TABLENAME = "DEPARTMENT_RESPONSE";

    /**
     * Properties of entity DepartmentResponse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property Pid = new Property(1, int.class, "pid", false, "PID");
        public final static Property Id = new Property(2, Long.class, "id", true, "_id");
        public final static Property Pid_all = new Property(3, String.class, "pid_all", false, "PID_ALL");
    }


    public DepartmentResponseDao(DaoConfig config) {
        super(config);
    }
    
    public DepartmentResponseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEPARTMENT_RESPONSE\" (" + //
                "\"NAME\" TEXT," + // 0: name
                "\"PID\" INTEGER NOT NULL ," + // 1: pid
                "\"_id\" INTEGER PRIMARY KEY ," + // 2: id
                "\"PID_ALL\" TEXT);"); // 3: pid_all
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEPARTMENT_RESPONSE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DepartmentResponse entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
        stmt.bindLong(2, entity.getPid());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(3, id);
        }
 
        String pid_all = entity.getPid_all();
        if (pid_all != null) {
            stmt.bindString(4, pid_all);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DepartmentResponse entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
        stmt.bindLong(2, entity.getPid());
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(3, id);
        }
 
        String pid_all = entity.getPid_all();
        if (pid_all != null) {
            stmt.bindString(4, pid_all);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2);
    }    

    @Override
    public DepartmentResponse readEntity(Cursor cursor, int offset) {
        DepartmentResponse entity = new DepartmentResponse( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.getInt(offset + 1), // pid
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // id
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // pid_all
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DepartmentResponse entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPid(cursor.getInt(offset + 1));
        entity.setId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setPid_all(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DepartmentResponse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DepartmentResponse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DepartmentResponse entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
