package com.awlsoft.asset.contract;

import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;

import java.util.List;

/**
 * 报废
 * Created by user on 2017/6/13.
 */

public interface ScrapTaskContract {
    interface View extends BaseTaskView<ScrapTaskContract.Presenter> {
        void showReceiveTaskDetail(ScrapTaskResponse task);

        /**
         * 读取完数据库的挂起任务 列表后  通知界面显示
         */
        void showReceiveHandleTask();

        void onAddAssetResponse(AssetResponse ar);

        void onShowSaveSiteDialog(List<WorkareaResponse> list);

        void showCompleteTaskError(String reason);

        Double getPrice();

        WorkareaResponse getSaveSite();
    }

    interface Presenter extends BaseTaskPresenter {
        void loadSaveSiteData();
    }
}
