package com.awlsoft.asset.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;

import java.util.List;

/**
 * Created by W on 2017/7/31.
 */

public interface BatchContract {

    /**
     *
     * Is implemented by     com.awlsoft.asset.ui.activity.BatchActivity
     * Click or press Ctrl+Alt+B to navigate
     *
     */
    interface View extends BaseView<Presenter> {

        Activity getActivity();

        void setLoadingIndicator(boolean active);

        void showLoadingTasksError();

        void showBatch(List<AssetBatchResponse> assetBatchResponseList);

        void showNoTasks();

        void showAssetAddActivity(AssetBatchResponse assetBatchResponse);

        void showNewBatch();

    }

    interface Presenter extends BasePresenter {

        void loadBatch();

        void openAssetAddActivity(AssetBatchResponse assetBatchResponse);

        void loadNewBatch();

        void saveBatch(AssetBatchResponse assetBatchResponse);
    }

}
