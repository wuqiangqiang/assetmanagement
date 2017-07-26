package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_brand")
@Entity
public class BrandResponse {
    /**
     * name : 华硕
     * id : 1
     */
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;

    public BrandResponse() {
    }


    @Generated(hash = 413049076)
    public BrandResponse(String name, Long id) {
        this.name = name;
        this.id = id;
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
}
