package com.awlsoft.asset.ui.widget;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;

import com.awlsoft.asset.R;

/**
 * Created by user on 2017/6/12.
 */

public class SoundPlay {

    /**
     * SoundPool使用音效池的概念来管理多个短促的音效，例如它可以开始就加载20个音效，以后在程序中按音效的ID进行播放
     */
    private SoundPool mSoundPool;
    private int loadId;
    private int playId;
    private int mTime = 2000;
    public static SoundPlay mSoundPlay = null;
    public static SoundPlay getInstance(Context context){
        if(mSoundPlay == null){
            mSoundPlay = new SoundPlay(context);
        }
        return mSoundPlay;
    }

    private Context mContext;
    private Handler mHandler = new Handler();
    private Runnable mDiDiRunnable = new Runnable() {
        @Override
        public void run() {
            mSoundPool.play(loadId, 1, 1, 0, 0, 1);
            mHandler.postDelayed(mDiDiRunnable,mTime);
        }
    };

    private SoundPlay(Context context){
        mContext = context;
        //第一个参数指定支持多少个声音；第二个参数指定声音类型：第三个参数指定声音品质。
        mSoundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,5);
        loadId = mSoundPool.load(context,R.raw.tip02,1);
    }

    public void playSound(){
        mHandler.removeCallbacks(mDiDiRunnable);
        mHandler.postDelayed(mDiDiRunnable,100);
    }
    public void stopSound(){
        mHandler.removeCallbacks(mDiDiRunnable);
        mSoundPool.release();
        mSoundPlay = null;
    }

    /**
     * 设置声音频率
     * @param value
     */
    public void setSpeed(int value){
        mTime = getPlaySpeed(value);
    }

    private int getPlaySpeed(int value){
        if(value < 50){
            return 2000;
        }else if(value>=50 && value <60){
            return 1500;
        }else if(value>=60 && value <70){
            return 1000;
        }else if(value>=70 && value <80){
            return 800;
        }else if(value>=80 && value <90){
            return 600;
        }else if(value>=90 && value <100){
            return 400;
        }else if(value >= 100){
            return 200;
        }
        return 2000;
    }
}
