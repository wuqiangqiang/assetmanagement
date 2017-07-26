package com.awlsoft.asset.model.entry.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by yejingxian on 2017/5/17.
 */
@Entity
public class UserResponse {

    /**
     * office_id : 1
     * password : 501701949216191DC06958143D6142FA01BE47B605A677233C8A65EC
     * login_name : test
     * name : 小萌萌
     * id : 3
     */

    private int office_id;
    private String password;
    private String login_name;
    private String name;
    @Id
    private Long id;

    @Generated(hash = 1806555280)
    public UserResponse(int office_id, String password, String login_name,
            String name, Long id) {
        this.office_id = office_id;
        this.password = password;
        this.login_name = login_name;
        this.name = name;
        this.id = id;
    }

    @Generated(hash = 2066571754)
    public UserResponse() {
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

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserResponse) {
            UserResponse user = (UserResponse) o;
            return (id == user.id);
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return String.valueOf(id).hashCode();
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
}
