package com.awlsoft.asset.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.BatchContract;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.WrapInventoryTagMap;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.ui.adapter.BatchAdapter;
import com.awlsoft.asset.ui.presenter.BatchPresenter;
import com.awlsoft.asset.util.LoginUtils;
import com.awlsoft.asset.util.RfidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wuqiangqiang on 2017/7/31.
 */

public class BatchActivity extends BaseActivity implements BatchContract.View, BatchAdapter.BatchItemListener, View.OnClickListener  {

    /**
     * 批次号为空的视图
     */
    private View mNoBatchView;

    private LinearLayout mBatchView;
    private ListView mBatchListView;

    private UserResponse user;
    private BatchAdapter mAdapter;
    /**
     * 建立presenter（主导器，通过iView和iModel接口操作model和view），
     * activity可以把所有逻辑给presenter处理，这样java逻辑就从手机的activity中分离出来。
     */
    private BatchContract.Presenter mPresenter;
    private List<AssetBatchResponse> mBatchList = new ArrayList<>();
    private EditText batchName,batchRemark;
    private Button mCommit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user = LoginUtils.loginIfNoYet(this);


        mBatchView = (LinearLayout) findViewById(R.id.batch_container);

        mBatchListView = (ListView) findViewById(R.id.batch_list);
        mAdapter = new BatchAdapter(this, mBatchList, this);
        mBatchListView.setAdapter(mAdapter);

        mPresenter = new BatchPresenter(this, DBManager.getInstance(this));

        batchName = (EditText)findViewById(R.id.et_batch_name);
        batchRemark = (EditText)findViewById(R.id.batch_remark);

        mCommit = (Button) findViewById(R.id.commit_batch);
        mCommit.setOnClickListener(this);
    }

    /**
     * onStart可见与onStop不可见
     */
    @Override
    protected void onStart() {
        super.onStart();
        //loadBatch->showBatch
        mPresenter.start();
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

    /**
     * 处理界面相关操作
     * 在present中被调用
     * @param assetBatchResponseList
     */
    @Override
    public void showBatch(List<AssetBatchResponse> assetBatchResponseList) {
//        int finished = TasksUtils.getFinishedTaskCount(batchResponseList);

        mBatchView.setVisibility(View.VISIBLE);
//        mNoBatchView.setVisibility(View.GONE);

        mBatchList.clear();
        mBatchList.addAll(assetBatchResponseList);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showNoTasks() {
        showNoTasksViews();
    }

    @Override
    public void showAssetAddActivity(AssetBatchResponse assetBatchResponse) {

        Intent intent = new Intent(this,AssetAddActivity.class);
        intent.putExtra("batch_no", assetBatchResponse.getBatch_no());
        this.startActivity(intent);

    }

    @Override
    public void showNewBatch() {
        Intent intent = new Intent(this,BatchAddActivity.class);
        startActivity(intent);
    }

    private void showNoTasksViews() {
        mBatchView.setVisibility(View.GONE);
        mNoBatchView.setVisibility(View.VISIBLE);
    }


    @Override
    public void setPresenter(BatchContract.Presenter presenter) {

    }


    @Override
    public void onBatchClick(AssetBatchResponse clickedBatch) {

        mPresenter.openAssetAddActivity(clickedBatch);

    }

    @Override
    public void onCompleteTaskClick(BaseTaskResponse completedTask) {

    }

    @Override
    public void onActivateTaskClick(BaseTaskResponse activatedTask) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_batch_add, menu);
        return true;
    }

    //重写activity类的 onOptionsItemSelected(MenuItem)回调方法，每当有菜单项被点击时，android就会调用该方法，并传入被点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new) {

            mPresenter.loadNewBatch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_batch:
                commitBatch();
                break;
        }
    }

    private void commitBatch() {


        if (TextUtils.isEmpty(batchName.getText())) {
            showToast("请填写批次名称!");
            return;
        } else if (TextUtils.isEmpty(batchRemark.getText())) {
            showToast("请填写批次备注!");
            return;
        }

        AssetBatchResponse assetBatchResponse = new AssetBatchResponse();
        assetBatchResponse.setName(batchName.getText().toString());
        assetBatchResponse.setRemark(batchRemark.getText().toString());
        mPresenter.saveBatch(assetBatchResponse);
        mPresenter.start();
//        finish();


    }
}
