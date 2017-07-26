package com.awlsoft.asset.model.entry.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/6/12.
 */
@Entity
public class ReturnTaskResponse extends BaseTaskResponse {
    public String taskID;
    public String TASK_DEF_KEY_;
    public String BUSINESS_KEY_;
    @Id
    public Long id;
    public int category_id;
    public String ectID;

    public String return_no;
    public long keeper_id;
    public String return_date;
    public int count;
    private volatile int status = PENDING;
    public Long	workareaId;


    @Generated(hash = 1193267912)
    public ReturnTaskResponse() {
    }

    @Generated(hash = 1889947661)
    public ReturnTaskResponse(String taskID, String TASK_DEF_KEY_,
            String BUSINESS_KEY_, Long id, int category_id, String ectID,
            String return_no, long keeper_id, String return_date, int count,
            int status, Long workareaId) {
        this.taskID = taskID;
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
        this.id = id;
        this.category_id = category_id;
        this.ectID = ectID;
        this.return_no = return_no;
        this.keeper_id = keeper_id;
        this.return_date = return_date;
        this.count = count;
        this.status = status;
        this.workareaId = workareaId;
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
        return "归还";
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

    public String getReturn_no() {
        return this.return_no;
    }

    public void setReturn_no(String return_no) {
        this.return_no = return_no;
    }

    public long getKeeper_id() {
        return this.keeper_id;
    }

    public void setKeeper_id(long keeper_id) {
        this.keeper_id = keeper_id;
    }

    public String getReturn_date() {
        return this.return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Status
    public int getStatus() {
        return this.status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }

    public Long getWorkareaId() {
        return this.workareaId;
    }

    public void setWorkareaId(Long workareaId) {
        this.workareaId = workareaId;
    }

}
