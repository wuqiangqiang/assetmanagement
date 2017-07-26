package com.awlsoft.asset.model.entry.response;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yejingxian on 2017/6/12.
 */

public abstract class BaseTaskResponse {
    //领用
    public static String TYPE_RECEIVE = "receive";
    //借用
    public static String TYPE_BORROW = "borrow";
    //报废
    public static String TYPE_SCRAP = "scrap";
    //报损
    public static String TYPE_BREAKAGE = "breakage";
    //归还
    public static String TYPE_RETURN = "return";
    //内部调拨
    public static String TYPE_ALLOCATIONINTERNAL = "allocationinternal";
    //外部调拨
    public static String TYPE_ALLOCATIONEXTERNAL = "allocationexternal";
    //资产盘点
    public static String TYPE_INVENTORY = "inventory";

    public static final int PENDING = 0;
    public static final int RUNNING = 1;
    public static final int FINISHED = 2;

    @IntDef({PENDING, RUNNING, FINISHED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public abstract boolean isFinished();
    public abstract int getStatusCode();
    public abstract String getTaskId();
    public abstract String getTaskLabel();


}
