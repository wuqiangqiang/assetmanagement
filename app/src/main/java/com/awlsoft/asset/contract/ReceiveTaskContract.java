package com.awlsoft.asset.contract;

import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;

/**
 * Created by yejingxian on 2017/6/9.
 */

public interface ReceiveTaskContract {
    interface View extends BaseTaskView<Presenter> {
        void showReceiveTaskDetail(ReceiveTaskResponse task);


    }

    interface Presenter extends BaseTaskPresenter {
    }
}
