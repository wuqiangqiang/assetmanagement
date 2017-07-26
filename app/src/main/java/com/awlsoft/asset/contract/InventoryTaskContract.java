package com.awlsoft.asset.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.response.InventoryResponse;

import java.util.List;

/**
 * Created by yejingxian on 2017/5/31.
 */

public interface InventoryTaskContract {
    interface View extends BaseView<Presenter> {
        Activity getActivity();

        void setLoadingIndicator(boolean active);

        void showTasks(List<InventoryResponse> inventoryResponses);

        void showLoadingTasksError();

        void showNoTasks();

        void showTaskDetailsUi(@NonNull InventoryResponse inventoryResponse);

        void showStartTaskUi(@NonNull InventoryResponse inventoryResponse);
    }

    interface Presenter extends BasePresenter {
        void onStop();

        void loadTasks(boolean forceUpdate);

        void openTaskDetails(@NonNull InventoryResponse inventoryResponse);

        void startTask(@NonNull InventoryResponse inventoryResponse);
    }
}
