package com.awlsoft.asset.supernfc;


import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;


import com.awlsoft.asset.supernfc.reader.server.ReaderHelper;
import com.awlsoft.asset.supernfc.utils.Tools;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class UHFApplication extends Application {

    private Socket mTcpSocket = null;
    private BluetoothSocket mBtSocket = null;
    private static Context context;
    private List<Activity> activities = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public void onCreate() {

        super.onCreate();
        context = getApplicationContext();
        Tools.InitMedia(context);

        try {
            ReaderHelper.setContext(context);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        saveSoftSound(0);//默认关闭声音

//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (Activity activity : activities) {
            try {
                activity.finish();
            } catch (Exception e) {
            }
        }
        for (Fragment fragment : fragments) {
            try {
                fragment.getActivity().finish();
            } catch (Exception e) {
            }
        }

        try {
            if (mTcpSocket != null) mTcpSocket.close();
            if (mBtSocket != null) mBtSocket.close();
        } catch (IOException e) {
        }

        mTcpSocket = null;
        mBtSocket = null;

        if (BluetoothAdapter.getDefaultAdapter() != null)
            BluetoothAdapter.getDefaultAdapter().disable();
        Tools.stopMedia();
        System.exit(0);
    }

    public void setTcpSocket(Socket socket) {
        this.mTcpSocket = socket;
    }

    public void setBtSocket(BluetoothSocket socket) {
        this.mBtSocket = socket;
    }

    static public void saveBeeperState(int state) {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt("_state", state);
        editor.commit();
    }

    static int _SoftSound = 2;

    static public int appGetSoftSound() {
        if (_SoftSound == 2)
            _SoftSound = getSoftSound();
        return _SoftSound;
    }

    static public int getVeeperState() {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        int state = spf.getInt("_state", 0);
        return state;
    }

    public void saveSoftSound(int state) {
        _SoftSound = 0;//默认关闭声音
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt("_software_sound", state);
        editor.commit();
    }

    static public int getSoftSound() {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        int state = spf.getInt("_software_sound", 1);
        return state;
    }

    static public void saveSessionState(int state) {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt("_session", state);
        editor.commit();
    }

    static public int getSessionState() {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        int state = spf.getInt("_session", 0);
        return state;
    }

    static public void saveFlagState(int state) {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putInt("_flag", state);
        editor.commit();
    }

    static public int getFlagState() {
        SharedPreferences spf = context.getSharedPreferences("setting", Activity.MODE_PRIVATE);
        int state = spf.getInt("_flag", 0);
        return state;
    }
}
