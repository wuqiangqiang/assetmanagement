package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_categoryGb")
@Entity
public  class CategoryGbResponse {
    /**
     * name : 土地，房屋及构筑物
     * id : 1
     * type : 2
     */
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;
    @DatabaseField
    private int type;

    public CategoryGbResponse() {
    }


    @Generated(hash = 264387091)
    public CategoryGbResponse(String name, Long id, int type) {
        this.name = name;
        this.id = id;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
