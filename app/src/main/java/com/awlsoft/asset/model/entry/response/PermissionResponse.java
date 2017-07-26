package com.awlsoft.asset.model.entry.response;

/**
 * Created by yejingxian on 2017/6/19.
 */

public class PermissionResponse {
    private int result;
    private int permission;

    public boolean hasPermission(){
        boolean hasPermission = true;
        if(result == 0 && permission == 0){
            hasPermission = false;
        }
        return hasPermission;
    }
}
