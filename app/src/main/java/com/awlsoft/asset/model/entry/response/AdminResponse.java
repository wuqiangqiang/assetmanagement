package com.awlsoft.asset.model.entry.response;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by yejingxian on 2017/5/17.
 */
@DatabaseTable(tableName = "tb_admin")
@Entity
public class AdminResponse {

    /**
     * office_id : 1
     * password : 501701949216191DC06958143D6142FA01BE47B605A677233C8A65EC
     * login_name : ruge
     * role_id : 4
     * name : 成丹青
     * id : 2
     * login_flag : 1
     */
    @DatabaseField
    private int office_id;
    @DatabaseField
    private String password;
    @DatabaseField
    private String login_name;
    @DatabaseField
    private int role_id;
    @DatabaseField
    private String name;
    @DatabaseField(id = true)
    @Id
    private Long id;
    @DatabaseField
    private int login_flag;

    public AdminResponse() {
    }


    @Generated(hash = 914379983)
    public AdminResponse(int office_id, String password, String login_name,
                         int role_id, String name, Long id, int login_flag) {
        this.office_id = office_id;
        this.password = password;
        this.login_name = login_name;
        this.role_id = role_id;
        this.name = name;
        this.id = id;
        this.login_flag = login_flag;
    }

    public UserResponse generatorUserResponse() {
        return new UserResponse(office_id, password, login_name, name, id);
    }


    public int getOfficeId() {
        return office_id;
    }

    public void setOfficeId(int office_id) {
        this.office_id = office_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginName() {
        return login_name;
    }

    public void setLoginName(String login_name) {
        this.login_name = login_name;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int role_id) {
        this.role_id = role_id;
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

    public int getLoginFlag() {
        return login_flag;
    }

    public void setLoginFlag(int login_flag) {
        this.login_flag = login_flag;
    }

    public int getOffice_id() {
        return this.office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getLogin_name() {
        return this.login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public int getRole_id() {
        return this.role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getLogin_flag() {
        return this.login_flag;
    }

    public void setLogin_flag(int login_flag) {
        this.login_flag = login_flag;
    }
}
