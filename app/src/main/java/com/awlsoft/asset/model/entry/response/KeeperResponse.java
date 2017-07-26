package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_keeper")
@Entity
public class KeeperResponse {

    /**
     * name : 戴蒙
     * id : 4
     * dept_id : 16
     */
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;
    @DatabaseField
    private int dept_id;

    public KeeperResponse() {
    }


    @Generated(hash = 387917772)
    public KeeperResponse(String name, Long id, int dept_id) {
        this.name = name;
        this.id = id;
        this.dept_id = dept_id;
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

    public int getDeptId() {
        return dept_id;
    }

    public void setDeptId(int dept_id) {
        this.dept_id = dept_id;
    }

    public int getDept_id() {
        return this.dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }
}
