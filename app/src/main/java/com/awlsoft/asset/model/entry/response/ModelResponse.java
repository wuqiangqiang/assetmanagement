package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName="tb_model")
@Entity
public class ModelResponse {
    /**
     * cg_id : 9
     * name : A01
     * id : 1
     */
    @DatabaseField
    private int cg_id;
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;

    public ModelResponse() {
    }


    @Generated(hash = 1349916280)
    public ModelResponse(int cg_id, String name, Long id) {
        this.cg_id = cg_id;
        this.name = name;
        this.id = id;
    }


    public int getCg_id() {
        return cg_id;
    }

    public void setCg_id(int cg_id) {
        this.cg_id = cg_id;
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
