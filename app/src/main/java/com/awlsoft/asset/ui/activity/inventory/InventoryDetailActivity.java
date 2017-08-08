package com.awlsoft.asset.ui.activity.inventory;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.InventoryDetailContract;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.adapter.InventoryDetailAdapter;
import com.awlsoft.asset.ui.presenter.InventoryDetailPresenter;
import com.awlsoft.asset.util.LoginUtils;
import com.awlsoft.asset.util.RfidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * inventory 存货清单
 * Created by yejingxian on 2017/6/1.
 */

public class InventoryDetailActivity extends BaseActivity implements InventoryDetailContract.View, View.OnClickListener {
    public static final String EXTRA_TASK_ID = "TASK_ID";
    public static final String EXTRA_TASK_EDIT = "TASK_EDIT";
    private TextView mName, mStatistics;
    private LinearLayout mAssetsView;
    private View mNoAssetsView;
    private Button mStartBtn;

    private UserResponse user;
    private InventoryDetailContract.Presenter mPresenter;
    private int foundCount = 0;
    private boolean editMode;
    private boolean showCommitMenu = true;

    private List<AssetResponse> mAssets = new ArrayList<>();
    private InventoryDetailAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);
        user = LoginUtils.loginIfNoYet(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Long taskId = getIntent().getLongExtra(EXTRA_TASK_ID, -1);
        editMode = getIntent().getBooleanExtra(EXTRA_TASK_EDIT, false);

        mName = (TextView) findViewById(R.id.inventory_name);
        mStatistics = (TextView) findViewById(R.id.asset_statistics);
        mName.setText(getString(R.string.inventory_people, user.getName()));

        mNoAssetsView = findViewById(R.id.noAssets);
        mAssetsView = (LinearLayout) findViewById(R.id.asset_container);

        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setVisibility(editMode ? View.VISIBLE : View.GONE);
        mStartBtn.setOnClickListener(this);

        mPresenter = new InventoryDetailPresenter(this, DBManager.getInstance(this), new RfidManager(this), taskId);

        mAdapter = new InventoryDetailAdapter(this, mAssets);
        mListView = (ListView) findViewById(R.id.asset_list);
        mListView.setAdapter(mAdapter);
        //openDriverAndStartScan();
        if (editMode) {
            mPresenter.openDriver();
        } else {

        }
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
        setTitle(editMode ? R.string.activity_inventory_start : R.string.activity_inventory_detail);
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

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            showProgressDialog("读取中！");
        } else {
            dismissProgressDialog();
        }
    }

    @Override
    public void showAssets(List<AssetResponse> inventoryResponses) {
        System.out.println("yjx inventory Assets count:" + inventoryResponses.size());
        setMenuEnabled(editMode);
        mAssetsView.setVisibility(View.VISIBLE);
        mNoAssetsView.setVisibility(View.GONE);

        mAssets.clear();
        mAssets.addAll(inventoryResponses);
        mAdapter.notifyDataSetChanged();
        showStatistics(mAssets);
    }

    @Override
    public void showLoadingAssetsError() {
        showToast("加载失败！");
        stopScanAndCloseDriver();
    }

    @Override
    public void showNoAssets() {
        setMenuEnabled(false);
        showNoAssetsViews();
        stopScanAndCloseDriver();
    }

    @Override
    public void showRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMaps) {
        System.out.println("yjx showRfids:" + inventoryTagMaps.size());
        for (InventoryBuffer.InventoryTagMap inventoryTagMap : inventoryTagMaps) {
            String rfid = RfidUtils.parseRfidEPC(inventoryTagMap);
            for (AssetResponse asset : mAssets) {
                if (!TextUtils.isEmpty(rfid) && rfid.equals(asset.getRfid_code())) {
                    asset.justFound = true;
                    break;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        showStatistics(mAssets);
    }

    private void showNoAssetsViews() {
        mAssetsView.setVisibility(View.GONE);
        mNoAssetsView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(InventoryDetailContract.Presenter presenter) {

    }
    private void setMenuEnabled(boolean enabled){
        if(enabled != showCommitMenu){
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_commit).setVisible(showCommitMenu);
        return super.onPrepareOptionsMenu(menu);
    }

    private void attemptCommit() {
        final List<AssetResponse> list = new ArrayList<>(mAssets);
        new AlertDialog.Builder(this).setMessage("盘点是否已完成").setPositiveButton("盘点完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.completeInventory(list);
                finish();
            }
        }).setNegativeButton("挂起", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.hangInventory(list);
                finish();
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asset_inventory, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showStatistics(List<AssetResponse> assets) {
        if (assets == null || assets.isEmpty()) {
            mStatistics.setText(getString(R.string.find_asset_process, 0, 0));
        } else {
            int completed = 0;
            for (AssetResponse asset : assets) {
                if (asset.hasFound || asset.justFound) {
                    completed++;
                }
            }
            mStatistics.setText(getString(R.string.find_asset_process, completed, assets.size()));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_btn:
                if (editMode && !mPresenter.isScanning()) {
                    mPresenter.startScanRfid();
                }
                break;
        }
    }
}
