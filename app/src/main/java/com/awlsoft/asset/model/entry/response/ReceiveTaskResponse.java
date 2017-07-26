package com.awlsoft.asset.model.entry.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by yejingxian on 2017/6/12.
 */
@Entity
public class ReceiveTaskResponse extends BaseTaskResponse {
    public String taskID;
    public String TASK_DEF_KEY_;
    public String BUSINESS_KEY_;
    @Id
    public Long id;
    public int category_id;
    public String ectID;
    public long keeper_id;
    public String receive_no;
    public int count;
    public long workarea_id;
    private volatile int status = PENDING;

    @Generated(hash = 1310886108)
    public ReceiveTaskResponse(String taskID, String TASK_DEF_KEY_,
                               String BUSINESS_KEY_, Long id, int category_id, String ectID,
                               long keeper_id, String receive_no, int count, long workarea_id,
                               int status) {
        this.taskID = taskID;
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
        this.id = id;
        this.category_id = category_id;
        this.ectID = ectID;
        this.keeper_id = keeper_id;
        this.receive_no = receive_no;
        this.count = count;
        this.workarea_id = workarea_id;
        this.status = status;
    }

    @Generated(hash = 1829549772)
    public ReceiveTaskResponse() {
    }

    public long getKeeper_id() {
        return this.keeper_id;
    }

    public void setKeeper_id(long keeper_id) {
        this.keeper_id = keeper_id;
    }

    public String getReceive_no() {
        return this.receive_no;
    }

    public void setReceive_no(String receive_no) {
        this.receive_no = receive_no;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getWorkarea_id() {
        return this.workarea_id;
    }

    public void setWorkarea_id(long workarea_id) {
        this.workarea_id = workarea_id;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTASK_DEF_KEY_() {
        return this.TASK_DEF_KEY_;
    }

    public void setTASK_DEF_KEY_(String TASK_DEF_KEY_) {
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
    }

    public String getBUSINESS_KEY_() {
        return this.BUSINESS_KEY_;
    }

    public void setBUSINESS_KEY_(String BUSINESS_KEY_) {
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCategory_id() {
        return this.category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getEctID() {
        return this.ectID;
    }

    public void setEctID(String ectID) {
        this.ectID = ectID;
    }

    @Status
    public int getStatus() {
        return this.status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }

    @Override
    public boolean isFinished() {
        return status == FINISHED;
    }

    @Override
    public int getStatusCode() {
        return status;
    }

    @Override
    public String getTaskId() {
        return taskID;
    }

    @Override
    public String getTaskLabel() {
        return "领用";
    }
}
