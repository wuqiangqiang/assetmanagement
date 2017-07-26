package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_department")
@Entity
public class DepartmentResponse {

    /**
     * name : 一年级
     * pid : 0
     * id : 1
     * pid_all : 1
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

    public DepartmentResponse() {
    }


    @Generated(hash = 1743917295)
    public DepartmentResponse(String name, int pid, Long id, String pid_all) {
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
