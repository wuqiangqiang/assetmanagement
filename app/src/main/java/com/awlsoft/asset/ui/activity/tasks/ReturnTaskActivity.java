package com.awlsoft.asset.ui.activity.tasks;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.contract.ReturnTaskContract;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.WorkareaResponseDao;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.adapter.CommonAdapter;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.ui.presenter.ReturnTaskActivityPresenter;
import com.awlsoft.asset.util.TasksUtils;

import java.util.List;

/**
 * Created by user on 2017/6/14.
 */

public class ReturnTaskActivity extends BaseTaskActivity<ReturnTaskContract.Presenter> implements ReturnTaskContract.View {
    private TextView adminView, statisticsView, applicantView,
            taskTypeView, assetTypeView, assetCount,
            keeperView, repositoryView, checkCountView, mTvReturnData;
    private WorkareaResponse mWorkareaResponse = null;
    private TextView mTvSaveSite;
    private RelativeLayout mLlSaveSite;

    @Override
    public void showFindRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMap) {
        mPresenter.compareList(inventoryTagMap);
    }

    @Override
    public void showReceiveTaskDetail(ReturnTaskResponse task) {
        mTvReturnData.setText(getString(R.string.task_asset_return_data,
                DateUtils.formatDateTime(this, Long.valueOf(task.getReturn_date()), DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_ALL)));
        adminView.setText(getString(R.string.tasks_handler, user.getName()));
        taskTypeView.setText(getString(R.string.task_type, task.getTaskLabel()));
        assetTypeView.setText(getString(R.string.task_asset_type, TasksUtils.getCategoryLabelWithId(this, task.getCategory_id())));
        assetCount.setText(getString(R.string.task_asset_count, task.getCount()));
        keeperView.setText(getString(R.string.task_asset_keeper, TasksUtils.getKeeperNameWithId(this, task.getKeeper_id())));
        mHandleTaskAdapter.setMaxSelect(-1);
        if (task.getWorkareaId() != null) {
            mWorkareaResponse = DBManager.getInstance(this).getDaoSession().getWorkareaResponseDao()
                    .queryBuilder().where(WorkareaResponseDao.Properties.Id.eq(task.getWorkareaId())).unique();
            mTvSaveSite.setText(mWorkareaResponse.getName());
        }
    }

    @Override
    public void onShowSaveSiteDialog(final List<WorkareaResponse> list) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<WorkareaResponse>(this, list, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, WorkareaResponse categoryResponse) {
                holder.setText(android.R.id.text1, categoryResponse.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mWorkareaResponse = list.get(i);
                mTvSaveSite.setText(mWorkareaResponse.getName());
            }
        }).show();
    }

    @Override
    public void showCompleteTaskError(String reason) {
        showToast(reason);
    }


    @Override
    public WorkareaResponse getSaveSite() {
        return mWorkareaResponse;
    }


    @Override
    protected int getListViewID() {
        return R.id.tasks_list;
    }

    @Override
    protected void initView() {
        adminView = (TextView) findViewById(R.id.admin_name);
        mTvReturnData = (TextView) findViewById(R.id.task_return_data);
        statisticsView = (TextView) findViewById(R.id.tasks_statistics);
        applicantView = (TextView) findViewById(R.id.tasks_applicant);
        assetCount = (TextView) findViewById(R.id.task_asset_count);
        taskTypeView = (TextView) findViewById(R.id.task_type);
        assetTypeView = (TextView) findViewById(R.id.task_asset_type);
        keeperView = (TextView) findViewById(R.id.task_asset_keeper);
        repositoryView = (TextView) findViewById(R.id.task_asset_repository);
        checkCountView = (TextView) findViewById(R.id.task_check_count);
        mTvSaveSite = (TextView) findViewById(R.id.tvSaveSite);
        mLlSaveSite = (RelativeLayout) findViewById(R.id.llSaveSite);
        mLlSaveSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadSaveSiteData();
            }
        });
        mLlSaveSite.setEnabled(editMode);
        applicantView.setVisibility(View.GONE);
        repositoryView.setVisibility(View.GONE);
        assetCount.setText(getString(R.string.task_asset_count, 0));
        checkCountView.setText(getString(R.string.task_check_count, 0));

    }

    @Override
    protected int providerLayout() {
        return R.layout.activity_return;
    }

    @Override
    protected ReturnTaskContract.Presenter providerPresenter() {
        return new ReturnTaskActivityPresenter(this, DBManager.getInstance(this), new RfidManager(this), taskId);
    }
}
