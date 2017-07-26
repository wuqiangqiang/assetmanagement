package com.awlsoft.asset.model.entry.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/6/12.
 */
@Entity
public class ScrapTaskResponse extends BaseTaskResponse {
    public String taskID;
    public String TASK_DEF_KEY_;
    public String BUSINESS_KEY_;
    @Id
    public Long id;
    public int category_id;
    public String ectID;

    public String scrap_reson;
    public String scrap_no;
    private volatile int status = PENDING;
    public Long	workareaId;
    public Double price;

    @Generated(hash = 167844458)
    public ScrapTaskResponse() {
    }

    @Generated(hash = 472918134)
    public ScrapTaskResponse(String taskID, String TASK_DEF_KEY_,
            String BUSINESS_KEY_, Long id, int category_id, String ectID,
            String scrap_reson, String scrap_no, int status, Long workareaId,
            Double price) {
        this.taskID = taskID;
        this.TASK_DEF_KEY_ = TASK_DEF_KEY_;
        this.BUSINESS_KEY_ = BUSINESS_KEY_;
        this.id = id;
        this.category_id = category_id;
        this.ectID = ectID;
        this.scrap_reson = scrap_reson;
        this.scrap_no = scrap_no;
        this.status = status;
        this.workareaId = workareaId;
        this.price = price;
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
        return "报废";
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

    public String getScrap_reson() {
        return this.scrap_reson;
    }

    public void setScrap_reson(String scrap_reson) {
        this.scrap_reson = scrap_reson;
    }

    public String getScrap_no() {
        return this.scrap_no;
    }

    public void setScrap_no(String scrap_no) {
        this.scrap_no = scrap_no;
    }

    @Status
    public int getStatus() {
        return this.status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }


    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getWorkareaId() {
        return this.workareaId;
    }

    public void setWorkareaId(Long workareaId) {
        this.workareaId = workareaId;
    }


}
