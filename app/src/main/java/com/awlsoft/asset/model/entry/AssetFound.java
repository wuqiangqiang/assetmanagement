package com.awlsoft.asset.model.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/6/5.
 */
@Entity
public class AssetFound {
    @Id(autoincrement = true)
    private Long id;
    @Index
    private Long inventoryId;
    @Index
    private Long assetId;
    private String taskId;
    @Generated(hash = 44532771)
    public AssetFound(Long id, Long inventoryId, Long assetId, String taskId) {
        this.id = id;
        this.inventoryId = inventoryId;
        this.assetId = assetId;
        this.taskId = taskId;
    }
    @Generated(hash = 1795563067)
    public AssetFound() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getInventoryId() {
        return this.inventoryId;
    }
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }
    public Long getAssetId() {
        return this.assetId;
    }
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
    public String getTaskId() {
        return this.taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
