package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_category")
@Entity
public class CategoryResponse {
    /**
     * name : 新类别
     * pid : 0
     * id : 1
     * pid_all : 4
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

    public CategoryResponse() {
    }


    @Generated(hash = 1688176541)
    public CategoryResponse(String name, int pid, Long id, String pid_all) {
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

    public String getPid_all() {
        return pid_all;
    }

    public void setPid_all(String pid_all) {
        this.pid_all = pid_all;
    }


}
