package com.awlsoft.asset.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awlsoft.asset.AssetApplication;
import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.AppSettings;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.SyncContract;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.ui.presenter.SyncPresenter;
import com.awlsoft.asset.util.LoginUtils;

/**
 * Created by yejingxian on 2017/5/23.
 */

public class SyncActivity extends BaseActivity implements View.OnClickListener, SyncContract.View {
    private TextView mUpload, mSync;
    private SyncPresenter mPresenter;
    private ProgressBar mProgress;
    private UserResponse user;
    private boolean isSync, isUpload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user = LoginUtils.loginIfNoYet(this);

        mUpload = (TextView) findViewById(R.id.upload_asset);
        mSync = (TextView) findViewById(R.id.sync_data);
        mUpload.setOnClickListener(this);
        mSync.setOnClickListener(this);
        mSync.setEnabled(AppSettings.getSyncAllow(this));

        mProgress = (ProgressBar) findViewById(R.id.progressbar);

        mPresenter = new SyncPresenter(this,user,DBManager.getInstance(this));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upload_asset:
                mPresenter.uploadAll();
                break;
            case R.id.sync_data:
                mPresenter.syncData();
                break;
        }

    }

    public void showResult(String text){
        new AlertDialog.Builder(this).setTitle(text).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showSyncStarted() {
        System.out.println("yjx show sync started:" + Thread.currentThread().getName());
        isSync = true;
        mProgress.setProgress(0);
        setFetruesEnabled(false);
        //清除之前下载的数据
        DBManager.getInstance(this).clearAllSyncData();

    }

    @Override
    public void showUploadStarted() {
        isUpload = true;
        mProgress.setProgress(0);
        setFetruesEnabled(false);
    }

    @Override
    public void showSyncProgress(int progress) {
        mProgress.setProgress(progress);
    }

    @Override
    public void showUploadProgress(int progress) {
        mProgress.setProgress(progress);
    }

    @Override
    public void showUploadSuccess() {
        isUpload = false;
        showResult("上传成功！");
        setFetruesEnabled(true);
        //下次需要登录
        LoginUtils.mustLoginNext(this);
    }

    @Override
    public void showUploadFalure() {
        isUpload = false;
        setFetruesEnabled(true);
        showResult("上传失败，请稍后再试！");
    }

    @Override
    public void showSyncSuccess() {
        isSync = false;
        setFetruesEnabled(true);
        showResult("下载成功！");
        mSync.setEnabled(false);
        //下载成功有可用数据
        AppSettings.setSyncCompleted(this, true);
        //不允许在同步下载数据
        AppSettings.setSyncAllow(this, false);
    }

    @Override
    public void showSyncFalure() {
        isSync = false;
        setFetruesEnabled(true);
        showResult("下载失败，请稍后再试！");
    }

    @Override
    public void showNoUpload() {
        showResult("没有数据需要上传！");
    }

    @Override
    public void setPresenter(SyncContract.Presenter presenter) {

    }

    public void setFetruesEnabled(boolean enabled) {
        mSync.setClickable(enabled);
        mUpload.setClickable(enabled);
    }
}
