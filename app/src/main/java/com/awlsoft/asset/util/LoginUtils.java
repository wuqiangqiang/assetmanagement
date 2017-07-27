package com.awlsoft.asset.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.awlsoft.asset.AssetApplication;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.model.api.RetrofitManager;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.ui.activity.LoginActivity;

import okhttp3.HttpUrl;

/**
 * Created by yejingxian on 2017/5/24.
 */

public class LoginUtils {

    public static UserResponse loginIfNoYet(Activity activity) {
        if (AssetApplication.get(activity).getUser() == null) {
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
        return AssetApplication.get(activity).getUser();
    }

    public static void logout(Activity activity) {
        AppSettings.setNeedLogin(activity, true);
        AppSettings.setSyncAllow(activity, true);
        AssetApplication.get(activity).setUser(null);
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void mustLoginNext(Activity activity){
        AppSettings.setNeedLogin(activity, true);
        AppSettings.setSyncAllow(activity, true);
    }

}
