package com.awlsoft.asset.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.awlsoft.asset.R;
import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.AssetResponseDao;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.widget.RadarView;
import com.awlsoft.asset.ui.widget.SoundPlay;
import com.awlsoft.asset.util.LoginUtils;
import com.awlsoft.asset.util.RfidUtils;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 2017/6/12.
 */

public class FindAssetActivity extends BaseActivity implements View.OnClickListener{
    private RadarView mRadarView;

    private EditText mEtInputID;
    private Button mBtnOK;

    private LinearLayout mLlRFID;
    private TextView mTvRFID;
    private TextView mTvAssetName;

    private DiscreteSeekBar mDsbPower;

    private Button mBtnSearch;
    private RfidManager mRfidManager;
    private boolean mIsSearching = false;
    private Handler mHandler = new Handler();
    private boolean hasFound = false;

    private Runnable mSearchRunnable = new Runnable() {
        @Override
        public void run() {
            List<InventoryBuffer.InventoryTagMap> list = new ArrayList<>();
            list.addAll(mRfidManager.getInventory());
            boolean isFound = false;
            int rssi = 0;
            System.out.println("yjx find asset found size:"+list.size());
            for(InventoryBuffer.InventoryTagMap tag:list){
                if(RfidUtils.parseRfidEPC(tag).trim().equals(mTvRFID.getText().toString().trim())){
                    isFound = true;
                    rssi = Integer.valueOf(tag.strRSSI);
                    System.out.println("yjx find asset RSSI:"+RfidUtils.parseRfidEPC(tag).trim()+"->"+tag.strRSSI);
                    break;
                }
            }
            if(!hasFound && isFound){
                SoundPlay.getInstance(FindAssetActivity.this).playSound();
                mLlRFID.setBackgroundColor(Color.parseColor("#169BD5"));
            }

            if(isFound){
                mRadarView.setRadarSpeed(rssi / 10 - 4);
                SoundPlay.getInstance(FindAssetActivity.this).setSpeed(rssi);
            }

            if(hasFound && !isFound){
                SoundPlay.getInstance(FindAssetActivity.this).stopSound();
                mLlRFID.setBackgroundColor(Color.TRANSPARENT);
                mRadarView.setRadarSpeed(1);
            }

            hasFound = isFound;

            if(mIsSearching){
                mRfidManager.clearInventory();
                mHandler.postDelayed(mSearchRunnable,500);
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_asset);
        LoginUtils.loginIfNoYet(this);
        initToolbar();
        initView();
        mRfidManager = new RfidManager(this);
        try {
            mRfidManager.openDriver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRfidManager.setOutputPower(mDsbPower.getProgress());
    }

    private void initView(){
        mEtInputID = (EditText)findViewById(R.id.etInputID);
        mBtnOK = (Button)findViewById(R.id.btnOK);
        mLlRFID = (LinearLayout)findViewById(R.id.llRFID);
        mTvRFID = (TextView)findViewById(R.id.tvRFID);
        mTvAssetName = (TextView)findViewById(R.id.tvAssetName);
        mDsbPower = (DiscreteSeekBar)findViewById(R.id.power_seekbar);
        mBtnSearch = (Button)findViewById(R.id.btnSearch);
        mRadarView = (RadarView) findViewById(R.id.radar_view);
        mBtnSearch.setOnClickListener(this);
        mBtnOK.setOnClickListener(this);
        mDsbPower.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {



            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                mRfidManager.setOutputPower(seekBar.getProgress());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopFind();
        mRfidManager.stopScan();
        mRfidManager.closeDriver();
    }

    private void startFind(){
        if(mIsSearching == false){
            mIsSearching = true;
            mRfidManager.startScan();
            mRadarView.setSearching(true);
            mHandler.removeCallbacks(mSearchRunnable);
            mHandler.post(mSearchRunnable);
            //直到找到标签才开始播放声音
            //SoundPlay.getInstance(FindAssetActivity.this).playSound();
        }
    }
    private void stopFind(){
        if(mIsSearching){
            mIsSearching = false;
            mHandler.removeCallbacks(mSearchRunnable);
            mRadarView.setSearching(false);
            SoundPlay.getInstance(FindAssetActivity.this).stopSound();
        }

    }
    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showAsset(AssetResponse asset){
        mTvRFID.setText(asset.getRfid_code());
        mTvAssetName.setText(asset.getName());
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnOK:
                AssetResponse asset = null;
                if(!mEtInputID.getText().toString().trim().equals("")){
                    try {
                        asset = DBManager.getInstance(this).getDaoSession().getAssetResponseDao()
                                .queryBuilder().where(AssetResponseDao.Properties.Rfid_code.eq(mEtInputID.getText().toString().trim())).unique();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    if(asset == null){
                        showToast("查找失败");
                    }else{
                        showAsset(asset);
                    }
                }else{
                    showToast("请输入要查找的RFID编号");
                }

                break;
            case R.id.btnSearch:
                if(!mTvRFID.getText().toString().trim().equals("")){
                    startFind();
                }else{
                    showToast("请先输入RFID编号，并确定有此ID");
                }

                break;
        }
    }
}
