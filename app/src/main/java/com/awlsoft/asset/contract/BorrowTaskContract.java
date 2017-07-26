package com.awlsoft.asset.contract;

import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;

/**
 * 借用
 * Created by user on 2017/6/13.
 */

public interface BorrowTaskContract {
    interface View extends BaseTaskView<BorrowTaskContract.Presenter> {
        void showReceiveTaskDetail(BorrowTaskResponse task);

    }

    interface Presenter extends BaseTaskPresenter {
    }
}
