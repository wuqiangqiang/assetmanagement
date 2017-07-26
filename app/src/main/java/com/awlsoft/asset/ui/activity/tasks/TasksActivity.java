package com.awlsoft.asset.ui.activity.tasks;

import android.app.Activity;
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
import com.awlsoft.asset.contract.TasksContract;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.ui.adapter.TasksAdapter;
import com.awlsoft.asset.ui.presenter.TasksPresenter;
import com.awlsoft.asset.util.LoginUtils;
import com.awlsoft.asset.util.TasksUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yejingxian on 2017/6/12.
 */

public class TasksActivity extends BaseActivity implements TasksContract.View, TasksAdapter.TaskItemListener {
    private View mNoTasksView;
    private TextView mName, mStatistics;
    private LinearLayout mTasksView;
    private ListView mTaskList;

    private UserResponse user;
    private TasksAdapter mAdapter;
    private TasksContract.Presenter mPresenter;
    private List<BaseTaskResponse> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user = LoginUtils.loginIfNoYet(this);

        mNoTasksView = findViewById(R.id.noTasks);

        mTasksView = (LinearLayout) findViewById(R.id.tasks_container);
        mName = (TextView) findViewById(R.id.admin_name);

        mStatistics = (TextView) findViewById(R.id.tasks_statistics);
        mName.setText(getString(R.string.task_people, user.getName()));

        mTaskList = (ListView) findViewById(R.id.tasks_list);
        mAdapter = new TasksAdapter(this, mTasks, this);
        mTaskList.setAdapter(mAdapter);

        mPresenter = new TasksPresenter(this, DBManager.getInstance(this));

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
    public void showLoadingTasksError() {
        showToast("加载失败！");
    }

    @Override
    public void showTasks(List<BaseTaskResponse> tasks) {
        System.out.println("yjx my showtasks--------------" + tasks.size());
        int finished = TasksUtils.getFinishedTaskCount(tasks);
        mStatistics.setText(getString(R.string.task_process, finished, tasks.size()));
        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);

        mTasks.clear();
        mTasks.addAll(tasks);
        mAdapter.notifyDataSetChanged();
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
    public void showTaskDetailsUi(@NonNull BaseTaskResponse task) {
        TasksUtils.openTaskDetail(this, task);
    }

    @Override
    public void showStartTaskUi(@NonNull BaseTaskResponse task) {
        TasksUtils.startTask(this, task);
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {

    }

    @Override
    public void onTaskClick(BaseTaskResponse clickedTask) {

    }

    @Override
    public void onCompleteTaskClick(BaseTaskResponse completedTask) {
        mPresenter.openTaskDetails(completedTask);
    }

    @Override
    public void onActivateTaskClick(BaseTaskResponse activatedTask) {
        mPresenter.startTask(activatedTask);
    }
}
