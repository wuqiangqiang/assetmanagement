package com.awlsoft.asset.contract;

import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;

import java.util.List;

/**
 * 归还
 * Created by user on 2017/6/13.
 */

public interface ReturnTaskContract {
    interface View extends BaseTaskView<ReturnTaskContract.Presenter> {
        void showReceiveTaskDetail(ReturnTaskResponse task);

        /**
         * 读取完数据库的挂起任务 列表后  通知界面显示
         */
        void showReceiveHandleTask();

        void onAddAssetResponse(AssetResponse ar);

        void onShowSaveSiteDialog(List<WorkareaResponse> list);

        void showCompleteTaskError(String reason);

        WorkareaResponse getSaveSite();
    }

    interface Presenter extends BaseTaskPresenter {
        void loadSaveSiteData();
    }
}
