package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.response.CategoryGbResponse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CATEGORY_GB_RESPONSE".
*/
public class CategoryGbResponseDao extends AbstractDao<CategoryGbResponse, Long> {

    public static final String TABLENAME = "CATEGORY_GB_RESPONSE";

    /**
     * Properties of entity CategoryGbResponse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Name = new Property(0, String.class, "name", false, "NAME");
        public final static Property Id = new Property(1, Long.class, "id", true, "_id");
        public final static Property Type = new Property(2, int.class, "type", false, "TYPE");
    }


    public CategoryGbResponseDao(DaoConfig config) {
        super(config);
    }
    
    public CategoryGbResponseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CATEGORY_GB_RESPONSE\" (" + //
                "\"NAME\" TEXT," + // 0: name
                "\"_id\" INTEGER PRIMARY KEY ," + // 1: id
                "\"TYPE\" INTEGER NOT NULL );"); // 2: type
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CATEGORY_GB_RESPONSE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CategoryGbResponse entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
        stmt.bindLong(3, entity.getType());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CategoryGbResponse entity) {
        stmt.clearBindings();
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(1, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(2, id);
        }
        stmt.bindLong(3, entity.getType());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1);
    }    

    @Override
    public CategoryGbResponse readEntity(Cursor cursor, int offset) {
        CategoryGbResponse entity = new CategoryGbResponse( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // name
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // id
            cursor.getInt(offset + 2) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CategoryGbResponse entity, int offset) {
        entity.setName(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setType(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CategoryGbResponse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CategoryGbResponse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CategoryGbResponse entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
