package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.response.AdminResponse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADMIN_RESPONSE".
*/
public class AdminResponseDao extends AbstractDao<AdminResponse, Long> {

    public static final String TABLENAME = "ADMIN_RESPONSE";

    /**
     * Properties of entity AdminResponse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Office_id = new Property(0, int.class, "office_id", false, "OFFICE_ID");
        public final static Property Password = new Property(1, String.class, "password", false, "PASSWORD");
        public final static Property Login_name = new Property(2, String.class, "login_name", false, "LOGIN_NAME");
        public final static Property Role_id = new Property(3, int.class, "role_id", false, "ROLE_ID");
        public final static Property Name = new Property(4, String.class, "name", false, "NAME");
        public final static Property Id = new Property(5, Long.class, "id", true, "_id");
        public final static Property Login_flag = new Property(6, int.class, "login_flag", false, "LOGIN_FLAG");
    }


    public AdminResponseDao(DaoConfig config) {
        super(config);
    }
    
    public AdminResponseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADMIN_RESPONSE\" (" + //
                "\"OFFICE_ID\" INTEGER NOT NULL ," + // 0: office_id
                "\"PASSWORD\" TEXT," + // 1: password
                "\"LOGIN_NAME\" TEXT," + // 2: login_name
                "\"ROLE_ID\" INTEGER NOT NULL ," + // 3: role_id
                "\"NAME\" TEXT," + // 4: name
                "\"_id\" INTEGER PRIMARY KEY ," + // 5: id
                "\"LOGIN_FLAG\" INTEGER NOT NULL );"); // 6: login_flag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADMIN_RESPONSE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AdminResponse entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getOffice_id());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(2, password);
        }
 
        String login_name = entity.getLogin_name();
        if (login_name != null) {
            stmt.bindString(3, login_name);
        }
        stmt.bindLong(4, entity.getRole_id());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(6, id);
        }
        stmt.bindLong(7, entity.getLogin_flag());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AdminResponse entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getOffice_id());
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(2, password);
        }
 
        String login_name = entity.getLogin_name();
        if (login_name != null) {
            stmt.bindString(3, login_name);
        }
        stmt.bindLong(4, entity.getRole_id());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(5, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(6, id);
        }
        stmt.bindLong(7, entity.getLogin_flag());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5);
    }    

    @Override
    public AdminResponse readEntity(Cursor cursor, int offset) {
        AdminResponse entity = new AdminResponse( //
            cursor.getInt(offset + 0), // office_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // password
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // login_name
            cursor.getInt(offset + 3), // role_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // name
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // id
            cursor.getInt(offset + 6) // login_flag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AdminResponse entity, int offset) {
        entity.setOffice_id(cursor.getInt(offset + 0));
        entity.setPassword(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLogin_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRole_id(cursor.getInt(offset + 3));
        entity.setName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setId(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setLogin_flag(cursor.getInt(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AdminResponse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AdminResponse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AdminResponse entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
