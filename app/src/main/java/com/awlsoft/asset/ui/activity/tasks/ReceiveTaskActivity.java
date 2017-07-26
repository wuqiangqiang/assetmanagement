package com.awlsoft.asset.ui.activity.tasks;

import android.view.View;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.ReceiveTaskContract;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.presenter.ReceiveTaskActivityPresenter;
import com.awlsoft.asset.util.TasksUtils;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/12.
 */

public class ReceiveTaskActivity extends BaseTaskActivity<ReceiveTaskContract.Presenter> implements ReceiveTaskContract.View {
    private TextView adminView, statisticsView, applicantView,
            taskTypeView, assetTypeView, assetCount,
            keeperView, repositoryView, checkCountView;

    @Override
    protected int getListViewID() {
        return R.id.tasks_list;
    }


    @Override
    protected void initView() {
        adminView = (TextView) findViewById(R.id.admin_name);
        statisticsView = (TextView) findViewById(R.id.tasks_statistics);
        applicantView = (TextView) findViewById(R.id.tasks_applicant);
        assetCount = (TextView) findViewById(R.id.task_asset_count);
        taskTypeView = (TextView) findViewById(R.id.task_type);
        assetTypeView = (TextView) findViewById(R.id.task_asset_type);
        keeperView = (TextView) findViewById(R.id.task_asset_keeper);
        repositoryView = (TextView) findViewById(R.id.task_asset_repository);
        checkCountView = (TextView) findViewById(R.id.task_check_count);

        applicantView.setVisibility(View.GONE);
        assetCount.setText(getString(R.string.task_asset_count, 0));
        checkCountView.setText(getString(R.string.task_check_count, 0));
    }

    @Override
    protected int providerLayout() {
        return R.layout.activity_receive;
    }

    @Override
    protected ReceiveTaskContract.Presenter providerPresenter() {
        return new ReceiveTaskActivityPresenter(this, DBManager.getInstance(this), new RfidManager(this), taskId);
    }

    @Override
    public void showFindRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMap) {
        System.out.println("yjx showFindRfids--------------------" + inventoryTagMap.size());
        mPresenter.compareList(inventoryTagMap);
    }

    @Override
    public void showReceiveTaskDetail(ReceiveTaskResponse task) {
        adminView.setText(getString(R.string.tasks_handler, user.getName()));
        taskTypeView.setText(getString(R.string.task_type, task.getTaskLabel()));
        assetTypeView.setText(getString(R.string.task_asset_type, TasksUtils.getCategoryLabelWithId(this, task.getCategory_id())));
        assetCount.setText(getString(R.string.task_asset_count, task.getCount()));
        keeperView.setText(getString(R.string.task_asset_keeper, TasksUtils.getKeeperNameWithId(this, task.getKeeper_id())));
        repositoryView.setText(getString(R.string.task_asset_repository, TasksUtils.getRepositoryNameWithId(this, task.getWorkarea_id())));
        mHandleTaskAdapter.setMaxSelect(task.count);
    }


}
