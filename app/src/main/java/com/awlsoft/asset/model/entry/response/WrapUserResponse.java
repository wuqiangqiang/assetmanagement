package com.awlsoft.asset.model.entry.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yejingxian on 2017/5/18.
 */

public class WrapUserResponse {

    private boolean result;
    @SerializedName(value = "msg",alternate = {"message"})
    private String msg;

    private UserResponse account;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserResponse getAccount() {
        return account;
    }

    public void setAccount(UserResponse account) {
        this.account = account;
    }
}
