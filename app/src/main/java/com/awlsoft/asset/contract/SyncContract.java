package com.awlsoft.asset.contract;

import android.app.Activity;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;

/**
 * Created by yejingxian on 2017/5/23.
 */

public interface SyncContract {

    enum OperateType {
        UPLOAD_ASSET,
        SYNC_DATA,
    }

    interface View extends BaseView<SyncContract.Presenter> {

        Activity getActivity();

        void showSyncStarted();

        void showUploadStarted();

        void showSyncProgress(int progress);

        void showUploadProgress(int progress);

        void showUploadSuccess();

        void showUploadFalure();

        void showSyncSuccess();

        void showSyncFalure();

        void showNoUpload();

    }

    interface Presenter extends BasePresenter {

        void uploadAll();

        void syncData();
    }
}
