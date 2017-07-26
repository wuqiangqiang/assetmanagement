package com.awlsoft.asset.contract;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/12.
 */

public interface TasksContract {
    interface View extends BaseView<Presenter> {
        Activity getActivity();

        void setLoadingIndicator(boolean active);

        void showLoadingTasksError();

        void showTasks(List<BaseTaskResponse> tasks);

        void showNoTasks();

        void showTaskDetailsUi(@NonNull BaseTaskResponse task);

        void showStartTaskUi(@NonNull BaseTaskResponse task);

    }

    interface Presenter extends BasePresenter {
        void onStop();

        void loadTasks(boolean forceUpdate);

        void openTaskDetails(@NonNull BaseTaskResponse task);

        void startTask(@NonNull BaseTaskResponse task);
    }
}
