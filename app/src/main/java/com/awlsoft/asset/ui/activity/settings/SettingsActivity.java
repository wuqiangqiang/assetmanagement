package com.awlsoft.asset.ui.activity.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.SettingsContract;
import com.awlsoft.asset.ui.presenter.SettingsPresenter;
import com.awlsoft.asset.util.LoginUtils;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class SettingsActivity extends BaseActivity implements SettingsContract.View, View.OnClickListener {
    private View powerContainer;
    private Button logout;
    private SettingsContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mPresenter = new SettingsPresenter(this);

        powerContainer = findViewById(R.id.power_container);
        powerContainer.setOnClickListener(this);
        logout = (Button) findViewById(R.id.btn_logout);
        logout.setOnClickListener(this);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showSetOutputPower() {
        Intent intent = new Intent(this,PowerSettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLogout() {
        LoginUtils.logout(this);
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_logout:
                mPresenter.logOut();
                break;
            case R.id.power_container:
                mPresenter.setOutputPower();
                break;
        }

    }
}
