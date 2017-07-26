package com.awlsoft.asset.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.ui.activity.inventory.AssetInventoryActivity;
import com.awlsoft.asset.ui.activity.settings.SettingsActivity;
import com.awlsoft.asset.ui.activity.tasks.TasksActivity;
import com.awlsoft.asset.util.LoginUtils;

/**
 * Created by yejingxian on 2017/5/22.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mAdd, mFind, mInventory, mMy, mSync, mSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LoginUtils.loginIfNoYet(this);

        mAdd = (TextView) findViewById(R.id.add_asset);
        mFind = (TextView) findViewById(R.id.find_asset);
        mInventory = (TextView) findViewById(R.id.inventory_asset);
        mMy = (TextView) findViewById(R.id.my_asset);
        mSync = (TextView) findViewById(R.id.sync_asset);
        mSettings = (TextView) findViewById(R.id.settings_asset);

        mAdd.setOnClickListener(this);
        mFind.setOnClickListener(this);
        mInventory.setOnClickListener(this);
        mMy.setOnClickListener(this);
        mSync.setOnClickListener(this);
        mSettings.setOnClickListener(this);
    }

    public void startActivityIfHasData(Intent intent) {
        boolean hasData = AppSettings.getSyncCompleted(this);
        if(hasData){
            startActivity(intent);
        }else{
            showToast("请先下载数据！");
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.add_asset:
                intent = new Intent(this, AssetAddActivity.class);
                startActivityIfHasData(intent);
                break;
            case R.id.find_asset:
                intent = new Intent(this, FindAssetActivity.class);
                startActivity(intent);
                break;
            case R.id.inventory_asset:
                intent = new Intent(this,AssetInventoryActivity.class);
                startActivityIfHasData(intent);
                break;
            case R.id.my_asset:
                intent = new Intent(this,TasksActivity.class);
                startActivityIfHasData(intent);
                break;
            case R.id.sync_asset:
                intent = new Intent(this, SyncActivity.class);
                startActivity(intent);
                break;
            case R.id.settings_asset:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
