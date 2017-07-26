package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_workarea")
@Entity
public class WorkareaResponse {

    /**
     * name : 一号楼
     * pid : 0
     * id : 7
     * pid_all : 7
     */
    @DatabaseField
    private String name;
    @DatabaseField
    private int pid;
    @DatabaseField(id = true)
    @Id
    private Long id;
    @DatabaseField
    private String pid_all;

    public WorkareaResponse() {
    }

    @Generated(hash = 899683093)
    public WorkareaResponse(String name, int pid, Long id, String pid_all) {
        this.name = name;
        this.pid = pid;
        this.id = id;
        this.pid_all = pid_all;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPidAll() {
        return pid_all;
    }

    public void setPidAll(String pid_all) {
        this.pid_all = pid_all;
    }

    public String getPid_all() {
        return this.pid_all;
    }

    public void setPid_all(String pid_all) {
        this.pid_all = pid_all;
    }
}
