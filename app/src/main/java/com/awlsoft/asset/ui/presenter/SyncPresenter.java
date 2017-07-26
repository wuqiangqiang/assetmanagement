package com.awlsoft.asset.ui.presenter;

import android.support.annotation.NonNull;

import com.awlsoft.asset.AssetApplication;
import com.awlsoft.asset.cache.AssetAssociationCache;
import com.awlsoft.asset.contract.SyncContract;
import com.awlsoft.asset.model.AssetAssociationUpload;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;


/**
 * Created by yejingxian on 2017/5/23.
 */

public class SyncPresenter implements SyncContract.Presenter, AssetAssociationCache.Callback, AssetAssociationUpload.UploadCallback {
    private SyncContract.View mView;
    private DBManager mManager;
    private UserResponse mUser;
    private AssetAssociationUpload mUploadTask;

    public SyncPresenter(SyncContract.View mView, @NonNull UserResponse user, @NonNull DBManager mManager) {
        this.mView = mView;
        this.mView.setPresenter(this);
        this.mUser = user;
        this.mManager = mManager;
        this.mUploadTask = new AssetAssociationUpload(mUser,mManager);
    }


    @Override
    public void uploadAll() {
        mUploadTask.uploadAllAssociation(this);
    }

    @Override
    public void syncData() {
        AssetApplication.get(mView.getActivity()).getAssociation().syncAssociation(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onSyncStart() {
        System.out.println("yjx sync onSyncStart:"+Thread.currentThread().getName());
        mView.showSyncProgress(0);
        mView.showSyncStarted();
    }

    @Override
    public void onSyncComplete(boolean success) {
        System.out.println("yjx sync onSyncComplete:"+Thread.currentThread().getName()+"->"+success);
        if (success){
            mView.showSyncSuccess();
        }else{
            mView.showSyncFalure();
        }
    }

    @Override
    public void onSyncProgressUpdate(int success, int falure, int all) {
        System.out.println("yjx sync onSyncProgressUpdate:"+Thread.currentThread().getName());
        mView.showSyncProgress(success * 100 / all);
    }

    @Override
    public void onNoUpload() {
        mView.showNoUpload();
    }

    @Override
    public void onUploadStart() {
        System.out.println("yjx sync onUploadStart:"+Thread.currentThread().getName());
        mView.showUploadProgress(0);
        mView.showUploadStarted();
    }

    @Override
    public void onUploadComplete(boolean success) {
        System.out.println("yjx sync onUploadComplete:"+Thread.currentThread().getName()+"->"+success);
        if (success){
            mView.showUploadSuccess();
        }else{
            mView.showUploadFalure();
        }
    }

    @Override
    public void onUploadProgressUpdate(int success, int falure, int all) {
        System.out.println("yjx sync onUploadProgressUpdate:"+Thread.currentThread().getName());
        mView.showUploadProgress(success * 100 / all);
    }
}
