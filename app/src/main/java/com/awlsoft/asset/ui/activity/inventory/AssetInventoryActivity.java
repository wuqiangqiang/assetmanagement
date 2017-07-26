package com.awlsoft.asset.ui.activity.inventory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.InventoryTaskContract;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.ui.adapter.InventoryTasksAdapter;
import com.awlsoft.asset.ui.presenter.InventoryTaskPresenter;
import com.awlsoft.asset.util.LoginUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class AssetInventoryActivity extends BaseActivity implements InventoryTaskContract.View {
    private InventoryTaskContract.Presenter mPresenter;
    private View mNoTasksView;
    private TextView mName, mStatistics;
    private LinearLayout mTasksView;
    private ListView mTaskList;

    private UserResponse user;
    private List<InventoryResponse> mTasks = new ArrayList<>();
    private InventoryTasksAdapter mListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_task);

        user = LoginUtils.loginIfNoYet(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mNoTasksView = findViewById(R.id.noTasks);
        mTasksView = (LinearLayout) findViewById(R.id.inventory_container);
        mName = (TextView) findViewById(R.id.inventory_name);
        mStatistics = (TextView) findViewById(R.id.inventory_statistics);
        mName.setText(getString(R.string.inventory_people, user.getName()));

        mListAdapter = new InventoryTasksAdapter(this, mTasks, mItemListener);
        mTaskList = (ListView) findViewById(R.id.tasks_list);
        mTaskList.setAdapter(mListAdapter);

        mPresenter = new InventoryTaskPresenter(this, DBManager.getInstance(this));
    }

    InventoryTasksAdapter.TaskItemListener mItemListener = new InventoryTasksAdapter.TaskItemListener() {
        @Override
        public void onTaskClick(InventoryResponse clickedTask) {
        }

        @Override
        public void onCompleteTaskClick(InventoryResponse completedTask) {
            mPresenter.openTaskDetails(completedTask);
        }

        @Override
        public void onActivateTaskClick(InventoryResponse activatedTask) {
            mPresenter.startTask(activatedTask);
        }
    };

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            showProgressDialog("读取中！");
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void showTasks(List<InventoryResponse> inventoryResponses) {
        System.out.println("yjx showTasks:" + inventoryResponses.size());
        int finished = 0;
        for (InventoryResponse inventoryResponse : inventoryResponses) {
            if (inventoryResponse.isCompleted()) {
                finished++;
            }
        }
        mStatistics.setText(getString(R.string.inventory_task_process, finished, inventoryResponses.size()));
        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
        mTasks.clear();
        mTasks.addAll(inventoryResponses);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadingTasksError() {
        showToast("加载失败！");
    }

    @Override
    public void showNoTasks() {
        showNoTasksViews();
    }

    private void showNoTasksViews() {
        mTasksView.setVisibility(View.GONE);
        mNoTasksView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTaskDetailsUi(@NonNull InventoryResponse inventoryResponse) {
        Intent intent = new Intent(this, InventoryDetailActivity.class);
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_ID, inventoryResponse.getId());
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_EDIT,false);
        startActivity(intent);
    }

    @Override
    public void showStartTaskUi(@NonNull InventoryResponse inventoryResponse) {
        Intent intent = new Intent(this, InventoryDetailActivity.class);
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_ID, inventoryResponse.getId());
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_EDIT,true);
        startActivity(intent);
    }

    @Override
    public void setPresenter(InventoryTaskContract.Presenter presenter) {

    }
}
