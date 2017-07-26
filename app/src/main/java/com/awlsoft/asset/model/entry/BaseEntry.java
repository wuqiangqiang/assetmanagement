package com.awlsoft.asset.model.entry;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yejingxian on 2017/4/18.
 */

public class BaseEntry<T> {
    private boolean result;
    @SerializedName(value = "msg",alternate = {"message"})
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
