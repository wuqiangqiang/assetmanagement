package com.awlsoft.asset.ui.activity.tasks;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.basic.BaseTaskPresenter;
import com.awlsoft.asset.basic.BaseTaskView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.ui.adapter.HandleTaskAdapter;
import com.awlsoft.asset.util.LoginUtils;
import com.dd.CircularProgressButton;
import com.google.common.base.Preconditions;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by yejingxian on 2017/6/9.
 */

public abstract class BaseTaskActivity<T extends BaseTaskPresenter> extends BaseActivity implements BaseTaskView<T> {
    public static final String EXTRA_TASK_ID = "TASK_ID";
    public static final String EXTRA_TASK_EDIT = "TASK_EDIT";

    protected T mPresenter;
    protected boolean editMode;
    protected boolean showCommitMenu = true;
    protected String taskId;
    protected UserResponse user;

    //ymx add
    protected HandleTaskAdapter mHandleTaskAdapter;
    protected ListView mLvHandleTask;
    private CircularProgressButton mBtnScan;

    private HandleTaskAdapter.SelectCallback mSelectCallback = new HandleTaskAdapter.SelectCallback() {
        @Override
        public void onSelectCount(int count) {
            TextView tvCheckCount = (TextView) findViewById(R.id.task_check_count);
            if (tvCheckCount != null) {
                tvCheckCount.setText(getString(R.string.task_check_count, count));
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(providerLayout());

        user = LoginUtils.loginIfNoYet(this);
        taskId = getIntent().getStringExtra(EXTRA_TASK_ID);
        Preconditions.checkNotNull(taskId, "taskId can not be null!");
        editMode = getIntent().getBooleanExtra(EXTRA_TASK_EDIT, false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnScan = (CircularProgressButton) findViewById(R.id.start_btn);
        mBtnScan.setIndeterminateProgressMode(true);
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode && !mPresenter.isScanning()) {
                    mPresenter.startScanRfid();
                    mBtnScan.setProgress(50);
                } else if (editMode && mPresenter.isScanning()) {
                    mPresenter.stopScanRfid();
                    mBtnScan.setProgress(0);
                }
            }
        });

        TextView tvSelectAll = (TextView) findViewById(R.id.tvSelectAll);
        if (tvSelectAll != null) {
            tvSelectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandleTaskAdapter.setSelectAll(true);
                    mHandleTaskAdapter.notifyDataSetChanged();
                }
            });
        }

        mPresenter = providerPresenter();
        initView();

        //editMode=true,未完成的
        if (editMode) {
            mPresenter.openDriver();
        } else {
            mBtnScan.setVisibility(View.GONE);
            if (tvSelectAll != null) {
                tvSelectAll.setVisibility(View.INVISIBLE);
            }
        }
        setMenuEnabled(editMode);

        //ymx add
        mHandleTaskAdapter = new HandleTaskAdapter(this, mPresenter.getHandleTaskList(), R.layout.layout_handle_tast_list_item);
        mLvHandleTask = (ListView) findViewById(getListViewID());
        mLvHandleTask.setAdapter(mHandleTaskAdapter);
        mHandleTaskAdapter.setEditMode(editMode);
        mHandleTaskAdapter.setSelectCallback(mSelectCallback);
        setCanScan(false);
    }

    //ymx add
    protected abstract int getListViewID();

    protected abstract void initView();

    protected abstract int providerLayout();

    protected abstract T providerPresenter();

    @Override
    public void setPresenter(T presenter) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle(editMode ? R.string.activity_task_start : R.string.activity_task_detail);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopScanAndCloseDriver();
    }

    private void stopScanAndCloseDriver() {
        if (editMode) {
            if (mPresenter.isScanning()) {
                mPresenter.stopScanRfid();
            }
            mPresenter.closeDriver();
        }
    }

    private void openDriverAndStartScan() {
        if (editMode) {
            mPresenter.openDriver();
            mPresenter.startScanRfid();
        }
    }

    private void setMenuEnabled(boolean enabled) {
        if (enabled != showCommitMenu) {
            showCommitMenu = enabled;
            supportInvalidateOptionsMenu();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_commit) {
            attemptCommit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asset_inventory, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_commit).setVisible(showCommitMenu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void attemptCommit() {
        new AlertDialog.Builder(this).setMessage("任务是否已完成").setPositiveButton("完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.onCommitTask(mHandleTaskAdapter.getCommitTask());
                finish();
            }
        }).setNegativeButton("挂起", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.onHandTask(mHandleTaskAdapter.getHandTask());
                finish();
            }
        }).show();
    }

    @Override
    public void showReceiveHandleTask() {
        mHandleTaskAdapter.notifyDataSetChanged();
        setCanScan(true);
        updataScanCount();

        TextView tvCheckCount = (TextView) findViewById(R.id.task_check_count);
        if (tvCheckCount != null) {
            tvCheckCount.setText(getString(R.string.task_check_count, mHandleTaskAdapter.curSelectCount()));
        }
    }

    private void updataScanCount() {
        TextView tvScanCount = (TextView) findViewById(R.id.tasks_statistics);
        if (tvScanCount != null) {
            tvScanCount.setText(getString(R.string.task_scan_count, mHandleTaskAdapter.getCount()));
        }
    }

    @Override
    public void onAddAssetResponse(AssetResponse ar) {
        mHandleTaskAdapter.addAssetResponse(ar);
        mHandleTaskAdapter.notifyDataSetChanged();
        updataScanCount();
    }

    protected void setCanScan(boolean bl) {
        Button scanView = (Button) findViewById(R.id.start_btn);
        if (scanView != null) {
            scanView.setClickable(bl);
        }
    }

}
