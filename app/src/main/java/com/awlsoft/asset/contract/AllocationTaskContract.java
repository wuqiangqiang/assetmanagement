package com.awlsoft.asset.contract;

import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.AssetResponse;

/**
 * Created by user on 2017/6/13.
 */

public interface AllocationTaskContract {
    interface View extends BaseTaskView<AllocationTaskContract.Presenter> {
        void showReceiveTaskDetail(AllocationTaskResponse task);

        /**
         * 读取完数据库的挂起任务 列表后  通知界面显示
         */
        void showReceiveHandleTask();

        void onAddAssetResponse(AssetResponse ar);
    }

    interface Presenter extends BaseTaskPresenter {
    }
}
