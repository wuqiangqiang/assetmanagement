package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_asset_batch")
@Entity
public class AssetBatchResponse {
    /**
     * batch_no : 1489457391221
     * name : 测试设备
     * id : 5
     */
    @DatabaseField
    private String batch_no;

    @DatabaseField
    private String name;

    @DatabaseField
    private String remark;
    
    @DatabaseField(id = true)
    @Id
    private Long id;

    public AssetBatchResponse() {
    }


    @Generated(hash = 1394077896)
    public AssetBatchResponse(String batch_no, String name, String remark,
            Long id) {
        this.batch_no = batch_no;
        this.name = name;
        this.remark = remark;
        this.id = id;
    }


    public String getBatchNo() {
        return batch_no;
    }

    public void setBatchNo(String batch_no) {
        this.batch_no = batch_no;
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

    public String getBatch_no() {
        return this.batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }


    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }
}
