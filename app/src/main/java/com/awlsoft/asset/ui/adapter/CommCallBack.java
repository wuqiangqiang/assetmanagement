package com.awlsoft.asset.ui.adapter;

/**
 * Created by WYZ on 2016-05-12.
 */
public abstract class CommCallBack {
    public abstract void callBack(Object... object);

    public void progress(String str1, String str2){};
}
