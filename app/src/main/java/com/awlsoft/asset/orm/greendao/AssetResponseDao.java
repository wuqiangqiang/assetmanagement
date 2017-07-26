package com.awlsoft.asset.orm.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.awlsoft.asset.model.entry.response.AssetResponse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ASSET_RESPONSE".
*/
public class AssetResponseDao extends AbstractDao<AssetResponse, Long> {

    public static final String TABLENAME = "ASSET_RESPONSE";

    /**
     * Properties of entity AssetResponse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Keeper_id = new Property(0, int.class, "keeper_id", false, "KEEPER_ID");
        public final static Property Batch_no = new Property(1, String.class, "batch_no", false, "BATCH_NO");
        public final static Property Model_id = new Property(2, int.class, "model_id", false, "MODEL_ID");
        public final static Property Brand_id = new Property(3, int.class, "brand_id", false, "BRAND_ID");
        public final static Property Rfid_code = new Property(4, String.class, "rfid_code", false, "RFID_CODE");
        public final static Property Category_id = new Property(5, int.class, "category_id", false, "CATEGORY_ID");
        public final static Property Durable_years = new Property(6, int.class, "durable_years", false, "DURABLE_YEARS");
        public final static Property Price = new Property(7, double.class, "price", false, "PRICE");
        public final static Property Buy_date = new Property(8, String.class, "buy_date", false, "BUY_DATE");
        public final static Property Name = new Property(9, String.class, "name", false, "NAME");
        public final static Property Id = new Property(10, Long.class, "id", true, "_id");
        public final static Property State = new Property(11, int.class, "state", false, "STATE");
        public final static Property Workarea_id = new Property(12, int.class, "workarea_id", false, "WORKAREA_ID");
        public final static Property Create_date = new Property(13, long.class, "create_date", false, "CREATE_DATE");
    }


    public AssetResponseDao(DaoConfig config) {
        super(config);
    }
    
    public AssetResponseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ASSET_RESPONSE\" (" + //
                "\"KEEPER_ID\" INTEGER NOT NULL ," + // 0: keeper_id
                "\"BATCH_NO\" TEXT," + // 1: batch_no
                "\"MODEL_ID\" INTEGER NOT NULL ," + // 2: model_id
                "\"BRAND_ID\" INTEGER NOT NULL ," + // 3: brand_id
                "\"RFID_CODE\" TEXT," + // 4: rfid_code
                "\"CATEGORY_ID\" INTEGER NOT NULL ," + // 5: category_id
                "\"DURABLE_YEARS\" INTEGER NOT NULL ," + // 6: durable_years
                "\"PRICE\" REAL NOT NULL ," + // 7: price
                "\"BUY_DATE\" TEXT," + // 8: buy_date
                "\"NAME\" TEXT," + // 9: name
                "\"_id\" INTEGER PRIMARY KEY ," + // 10: id
                "\"STATE\" INTEGER NOT NULL ," + // 11: state
                "\"WORKAREA_ID\" INTEGER NOT NULL ," + // 12: workarea_id
                "\"CREATE_DATE\" INTEGER NOT NULL );"); // 13: create_date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ASSET_RESPONSE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AssetResponse entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getKeeper_id());
 
        String batch_no = entity.getBatch_no();
        if (batch_no != null) {
            stmt.bindString(2, batch_no);
        }
        stmt.bindLong(3, entity.getModel_id());
        stmt.bindLong(4, entity.getBrand_id());
 
        String rfid_code = entity.getRfid_code();
        if (rfid_code != null) {
            stmt.bindString(5, rfid_code);
        }
        stmt.bindLong(6, entity.getCategory_id());
        stmt.bindLong(7, entity.getDurable_years());
        stmt.bindDouble(8, entity.getPrice());
 
        String buy_date = entity.getBuy_date();
        if (buy_date != null) {
            stmt.bindString(9, buy_date);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(10, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(11, id);
        }
        stmt.bindLong(12, entity.getState());
        stmt.bindLong(13, entity.getWorkarea_id());
        stmt.bindLong(14, entity.getCreate_date());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AssetResponse entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getKeeper_id());
 
        String batch_no = entity.getBatch_no();
        if (batch_no != null) {
            stmt.bindString(2, batch_no);
        }
        stmt.bindLong(3, entity.getModel_id());
        stmt.bindLong(4, entity.getBrand_id());
 
        String rfid_code = entity.getRfid_code();
        if (rfid_code != null) {
            stmt.bindString(5, rfid_code);
        }
        stmt.bindLong(6, entity.getCategory_id());
        stmt.bindLong(7, entity.getDurable_years());
        stmt.bindDouble(8, entity.getPrice());
 
        String buy_date = entity.getBuy_date();
        if (buy_date != null) {
            stmt.bindString(9, buy_date);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(10, name);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(11, id);
        }
        stmt.bindLong(12, entity.getState());
        stmt.bindLong(13, entity.getWorkarea_id());
        stmt.bindLong(14, entity.getCreate_date());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10);
    }    

    @Override
    public AssetResponse readEntity(Cursor cursor, int offset) {
        AssetResponse entity = new AssetResponse( //
            cursor.getInt(offset + 0), // keeper_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // batch_no
            cursor.getInt(offset + 2), // model_id
            cursor.getInt(offset + 3), // brand_id
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // rfid_code
            cursor.getInt(offset + 5), // category_id
            cursor.getInt(offset + 6), // durable_years
            cursor.getDouble(offset + 7), // price
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // buy_date
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // name
            cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10), // id
            cursor.getInt(offset + 11), // state
            cursor.getInt(offset + 12), // workarea_id
            cursor.getLong(offset + 13) // create_date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AssetResponse entity, int offset) {
        entity.setKeeper_id(cursor.getInt(offset + 0));
        entity.setBatch_no(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setModel_id(cursor.getInt(offset + 2));
        entity.setBrand_id(cursor.getInt(offset + 3));
        entity.setRfid_code(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCategory_id(cursor.getInt(offset + 5));
        entity.setDurable_years(cursor.getInt(offset + 6));
        entity.setPrice(cursor.getDouble(offset + 7));
        entity.setBuy_date(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setName(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setId(cursor.isNull(offset + 10) ? null : cursor.getLong(offset + 10));
        entity.setState(cursor.getInt(offset + 11));
        entity.setWorkarea_id(cursor.getInt(offset + 12));
        entity.setCreate_date(cursor.getLong(offset + 13));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AssetResponse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AssetResponse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AssetResponse entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
