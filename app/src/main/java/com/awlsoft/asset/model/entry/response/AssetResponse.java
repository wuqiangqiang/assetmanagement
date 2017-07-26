package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_asset")
@Entity
public class AssetResponse {
    /**
     * keeper_id : 1
     * batch_no : 1489050832181
     * model_id : 1
     * brand_id : 1
     * rfid_code : 111111
     * category_id : 1
     * durable_years : 0
     * price : 1.0
     * buy_date : 2017-03-09
     * name : a
     * id : 1
     * state : 1
     * workarea_id : 11
     * create_date : 1489206609000
     */
    @DatabaseField
    private int keeper_id;
    @DatabaseField
    private String batch_no;
    @DatabaseField
    private int model_id;
    @DatabaseField
    private int brand_id;
    @DatabaseField
    private String rfid_code;
    @DatabaseField
    private int category_id;
    @DatabaseField
    private int durable_years;
    @DatabaseField
    private double price;
    @DatabaseField
    private String buy_date;
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;
    @DatabaseField
    private int state;
    @DatabaseField
    private int workarea_id;
    @DatabaseField
    private long create_date;

    @Transient
    public boolean hasFound;
    @Transient
    public boolean justFound;

    public AssetResponse() {
    }


    @Generated(hash = 2060078916)
    public AssetResponse(int keeper_id, String batch_no, int model_id, int brand_id,
            String rfid_code, int category_id, int durable_years, double price,
            String buy_date, String name, Long id, int state, int workarea_id,
            long create_date) {
        this.keeper_id = keeper_id;
        this.batch_no = batch_no;
        this.model_id = model_id;
        this.brand_id = brand_id;
        this.rfid_code = rfid_code;
        this.category_id = category_id;
        this.durable_years = durable_years;
        this.price = price;
        this.buy_date = buy_date;
        this.name = name;
        this.id = id;
        this.state = state;
        this.workarea_id = workarea_id;
        this.create_date = create_date;
    }


    public int getKeeperId() {
        return keeper_id;
    }

    public void setKeeperId(int keeper_id) {
        this.keeper_id = keeper_id;
    }

    public String getBatchNo() {
        return batch_no;
    }

    public void setBatchNo(String batch_no) {
        this.batch_no = batch_no;
    }

    public int getModelId() {
        return model_id;
    }

    public void setModelId(int model_id) {
        this.model_id = model_id;
    }

    public int getBrandId() {
        return brand_id;
    }

    public void setBrandId(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getRfidCode() {
        return rfid_code;
    }

    public void setRfidCode(String rfid_code) {
        this.rfid_code = rfid_code;
    }

    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    public int getDurableYears() {
        return durable_years;
    }

    public void setDurableYears(int durable_years) {
        this.durable_years = durable_years;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBuyDate() {
        return buy_date;
    }

    public void setBuyDate(String buy_date) {
        this.buy_date = buy_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getWorkareaId() {
        return workarea_id;
    }

    public void setWorkareaId(int workarea_id) {
        this.workarea_id = workarea_id;
    }

    public long getCreateDate() {
        return create_date;
    }

    public void setCreateDate(long create_date) {
        this.create_date = create_date;
    }

    public int getKeeper_id() {
        return this.keeper_id;
    }

    public void setKeeper_id(int keeper_id) {
        this.keeper_id = keeper_id;
    }

    public String getBatch_no() {
        return this.batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public int getModel_id() {
        return this.model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public int getBrand_id() {
        return this.brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }

    public String getRfid_code() {
        return this.rfid_code;
    }

    public void setRfid_code(String rfid_code) {
        this.rfid_code = rfid_code;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDurable_years() {
        return this.durable_years;
    }

    public void setDurable_years(int durable_years) {
        this.durable_years = durable_years;
    }

    public String getBuy_date() {
        return this.buy_date;
    }

    public void setBuy_date(String buy_date) {
        this.buy_date = buy_date;
    }

    public int getWorkarea_id() {
        return this.workarea_id;
    }

    public void setWorkarea_id(int workarea_id) {
        this.workarea_id = workarea_id;
    }

    public long getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(long create_date) {
        this.create_date = create_date;
    }
}
