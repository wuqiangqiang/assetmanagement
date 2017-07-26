package com.awlsoft.asset.basic;

import android.content.Context;
import android.preference.Preference;
import android.text.TextUtils;

import com.awlsoft.asset.util.PreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yejingxian on 2017/5/22.
 */

public class AppSettings {

    public static String getServerAddress(Context context) {
        return PreferencesUtils.getString(context, "server_address", "");
    }

    public static void setServerAddress(Context context, String address) {
        PreferencesUtils.putString(context, "server_address", address);
    }

    public static void setOutputPower(Context context, int power){
        PreferencesUtils.putInt(context,"output_power", power);
    }

    public static int getOutputPower(Context context){
        return PreferencesUtils.getInt(context, "output_power", 33);
    }

    //是否需要下载数据
    public static boolean getSyncCompleted(Context context) {
        return PreferencesUtils.getBoolean(context, "sync_complete", false);
    }

    public static void setSyncCompleted(Context context, boolean completed) {
        PreferencesUtils.putBoolean(context, "sync_complete", completed);
    }

    //是否可以下载数据
    public static boolean getSyncAllow(Context context) {
        return PreferencesUtils.getBoolean(context, "sync_allow", true);
    }

    public static void setSyncAllow(Context context, boolean completed) {
        PreferencesUtils.putBoolean(context, "sync_allow", completed);
    }

    //下次是否需要登录
    public static boolean getNeedLogin(Context context){
        return PreferencesUtils.getBoolean(context, "need_login", true);
    }

    public static void setNeedLogin(Context context, boolean needLogin){
        PreferencesUtils.putBoolean(context, "need_login", needLogin);
    }

    public static List<String> getUserNames(Context context) {
        String usernamestr = PreferencesUtils.getString(context, "user_names", "");
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(usernamestr)) {

        } else {
            String[] usernames = usernamestr.split("###");
            list.addAll(Arrays.asList(usernames));
        }
        return list;
    }

    public static void addUserName(Context context, String username) {
        List<String> usernames = getUserNames(context);
        if (!usernames.contains(username))
            usernames.add(0, username);
        int len = usernames.size();
        StringBuilder build = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                build.append("###");
            }
            build.append(usernames.get(i));
        }
        PreferencesUtils.putString(context, "user_names", build.toString());
    }
}
