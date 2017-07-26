package com.awlsoft.asset.model.entry.response;

import android.support.annotation.IntDef;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yejingxian on 2017/5/31.
 */
@Entity
public class InventoryResponse {

    /**
     * office_id : 2
     * TASK_DEF_KEY_ : manager
     * BUSINESS_KEY_ : AssetInventory.6
     * category_id : 9
     * taskID : 802647
     * name : 电脑盘点
     * id : 6
     * inventory_no : 20170507162856
     * workarea_id : 40
     */
    public static final int PENDING = 0;
    public static final int RUNNING = 1;
    public static final int FINISHED = 2;

    private int office_id;
    private String TASK_DEF_KEY_;
    private String BUSINESS_KEY_;
    private int category_id;
    private String taskID;
    private String name;
    @Id
    private Long id;
    private String inventory_no;
    private int workarea_id;
    private String ectID;

    private volatile int status = PENDING;


    @Generated(hash = 1413716128)
    public InventoryResponse() {
    }

    @Generated(hash = 1261226374)
    public InventoryResponse(int office_id, String TASK_DEF_KEY_,
            String BUSINESS_KEY_, int category_id, String taskID, String name,
            Long id, String inventory_no, int workarea_id, String ectID,
            int status) {
        this.office_id = office_id;
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
        this.category_id = category_id;
        this.taskID = taskID;
        this.name = name;
        this.id = id;
        this.inventory_no = inventory_no;
        this.workarea_id = workarea_id;
        this.ectID = ectID;
        this.status = status;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getTASK_DEF_KEY_() {
        return TASK_DEF_KEY_;
    }

    public void setTASK_DEF_KEY_(String TASK_DEF_KEY_) {
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
    }

    public String getBUSINESS_KEY_() {
        return BUSINESS_KEY_;
    }

    public void setBUSINESS_KEY_(String BUSINESS_KEY_) {
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
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

    public String getInventory_no() {
        return inventory_no;
    }

    public void setInventory_no(String inventory_no) {
        this.inventory_no = inventory_no;
    }

    public int getWorkarea_id() {
        return workarea_id;
    }

    public void setWorkarea_id(int workarea_id) {
        this.workarea_id = workarea_id;
    }

    @Status
    public int getStatus() {
        return this.status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }
    public boolean isCompleted(){
        return status == FINISHED;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getEctID() {
        return this.ectID;
    }

    public void setEctID(String ectID) {
        this.ectID = ectID;
    }

    @IntDef({PENDING, RUNNING, FINISHED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }
}
