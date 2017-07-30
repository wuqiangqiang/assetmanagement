package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/7/28.
 */
@Entity
public class GreenDaoResponse {
    @Id
    private long id;
    @DatabaseField
    private String name;

    @Generated(hash = 1304475520)
    public GreenDaoResponse() {
    }
    @Generated(hash = 2093332815)
    public GreenDaoResponse(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
