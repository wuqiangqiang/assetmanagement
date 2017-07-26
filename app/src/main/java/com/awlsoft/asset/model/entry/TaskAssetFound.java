package com.awlsoft.asset.model.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/6/13.
 */
@Entity
public class TaskAssetFound {
    @Id(autoincrement = true)
    private Long id;
    @Index
    private Long assetId;
    private String taskId;
    private boolean checked;
    @Generated(hash = 1073883199)
    public TaskAssetFound(Long id, Long assetId, String taskId, boolean checked) {
        this.id = id;
        this.assetId = assetId;
        this.taskId = taskId;
        this.checked = checked;
    }
    @Generated(hash = 1523012868)
    public TaskAssetFound() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public boolean getChecked() {
        return this.checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
