package com.awlsoft.asset.model.entry;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.HashMap;
import java.util.Map;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by yejingxian on 2017/5/19.
 */
@DatabaseTable(tableName="tb_asset_stage")
@Entity
public class AssetAddBean {
    @DatabaseField
    public String name;
    @DatabaseField
    public String batchNo;
    @DatabaseField(id = true)
    @Unique
    public String rfidCode;
    @DatabaseField
    public int brandId;
    @DatabaseField
    public int categoryId;
    @DatabaseField
    public int categoryGbId;
    @DatabaseField
    public int modelId;
    @DatabaseField
    public double price;
    @DatabaseField
    public int officeId;
    @DatabaseField
    public int createId;
    @DatabaseField
    public String buyDate;
    @DatabaseField
    public int durableYears;
    @Id
    public Long id;

    public AssetAddBean() {
    }


    @Generated(hash = 845289320)
    public AssetAddBean(String name, String batchNo, String rfidCode, int brandId,
            int categoryId, int categoryGbId, int modelId, double price,
            int officeId, int createId, String buyDate, int durableYears, Long id) {
        this.name = name;
        this.batchNo = batchNo;
        this.rfidCode = rfidCode;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.categoryGbId = categoryGbId;
        this.modelId = modelId;
        this.price = price;
        this.officeId = officeId;
        this.createId = createId;
        this.buyDate = buyDate;
        this.durableYears = durableYears;
        this.id = id;
    }


    public Map<String, String> generatorParams(){
        Map<String, String> params = new HashMap<>();
        params.put("name",name);
        params.put("batchNo",batchNo);
        params.put("rfidCode",rfidCode);
        params.put("brandId",String.valueOf(brandId));
        params.put("categoryId",String.valueOf(categoryId));
        params.put("categoryGbId",String.valueOf(categoryGbId));
        params.put("modelId",String.valueOf(modelId));
        params.put("price",String.valueOf(price));
        params.put("officeId",String.valueOf(officeId));
        params.put("createId",String.valueOf(createId));
        params.put("buyDate",buyDate);
        params.put("durableYears",String.valueOf(durableYears));
        return params;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getRfidCode() {
        return this.rfidCode;
    }

    public void setRfidCode(String rfidCode) {
        this.rfidCode = rfidCode;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryGbId() {
        return this.categoryGbId;
    }

    public void setCategoryGbId(int categoryGbId) {
        this.categoryGbId = categoryGbId;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getOfficeId() {
        return this.officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getCreateId() {
        return this.createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getBuyDate() {
        return this.buyDate;
    }

    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    public int getDurableYears() {
        return this.durableYears;
    }

    public void setDurableYears(int durableYears) {
        this.durableYears = durableYears;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
