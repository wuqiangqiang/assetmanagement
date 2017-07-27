package com.awlsoft.asset.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.awlsoft.asset.R;
import com.awlsoft.asset.RfidManager;
import com.awlsoft.asset.basic.BaseActivity;
import com.awlsoft.asset.contract.AssetAddContract;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.WrapInventoryTagMap;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BrandResponse;
import com.awlsoft.asset.model.entry.response.CategoryGbResponse;
import com.awlsoft.asset.model.entry.response.CategoryResponse;
import com.awlsoft.asset.model.entry.response.ModelResponse;
import com.awlsoft.asset.model.entry.response.UserResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.adapter.AssetAddRfidAdapter;
import com.awlsoft.asset.ui.adapter.CommonAdapter;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.ui.presenter.AssetAddPresenter;
import com.awlsoft.asset.util.LoginUtils;
import com.awlsoft.asset.util.RfidUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yejingxian on 2017/5/17.
 */

public class AssetAddActivity extends BaseActivity implements AssetAddContract.View, View.OnClickListener {
    private EditText mAssetName, mDurableYears, mPrice;

    private TextView mAssetBatch, mBrandName, mCategoryGbName, mCategoryName, mModelName, mBuyDate;
    private View mBatchContainer, mBrandContainer, mCategoryGbContainer, mCategoryContainer, mModelContainer, mBuyDateContainer;
    private Button mCommit;
    private ListView mListView;
    private TextView scanCount, createName, selectAll;

    private AssetBatchResponse mBatch;
    private CategoryGbResponse mCategoryGb;
    private CategoryResponse mCategory;
    private ModelResponse mModel;
    private BrandResponse mBrand;
    private String mBuyDay;
    private String mAutoBatch;

    private RfidManager rfidManager;
    private AssetAddContract.Presenter mPresenter;
    private AssetAddRfidAdapter mAdapter;
    private List<WrapInventoryTagMap> mInventoryTagMaps;
    private int foundRfidCount = 0;
    private UserResponse user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        user = LoginUtils.loginIfNoYet(this);
        rfidManager = new RfidManager(this);
        mPresenter = new AssetAddPresenter(this, DBManager.getInstance(this), rfidManager);
        mPresenter.openDriver();

        mAssetName = (EditText) findViewById(R.id.asset_name_context);
        mDurableYears = (EditText) findViewById(R.id.service_time_context);
        mPrice = (EditText) findViewById(R.id.price_context);

        mAssetBatch = (TextView) findViewById(R.id.batch_context);
        mBrandName = (TextView) findViewById(R.id.brand_context);
        mCategoryName = (TextView) findViewById(R.id.category_context);//
        mCategoryGbName = (TextView) findViewById(R.id.categoryGb_context);
        mModelName = (TextView) findViewById(R.id.model_context);
        mBuyDate = (TextView) findViewById(R.id.buy_date_context);

        mBatchContainer = findViewById(R.id.batch_id_container);
        mBatchContainer.setOnClickListener(this);
        mBrandContainer = findViewById(R.id.brand_container);
        mBrandContainer.setOnClickListener(this);
        mCategoryGbContainer = findViewById(R.id.categoryGb_container);
        mCategoryGbContainer.setOnClickListener(this);
        mCategoryContainer = findViewById(R.id.category_container);
        mCategoryContainer.setOnClickListener(this);
        mModelContainer = findViewById(R.id.model_container);
        mModelContainer.setOnClickListener(this);
        mBuyDateContainer = findViewById(R.id.buy_date_container);
        mBuyDateContainer.setOnClickListener(this);

        mCommit = (Button) findViewById(R.id.commit_asset);
        mCommit.setOnClickListener(this);

        scanCount = (TextView) findViewById(R.id.scan_count);
        selectAll = (TextView) findViewById(R.id.select_all_none);
        createName = (TextView) findViewById(R.id.create_name);
        createName.setText(getString(R.string.create_name, user.getName()));
        scanCount.setText(getString(R.string.scan_count, 0));//共扫描到%1$d条数据
        selectAll.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.rfid_list);
        mInventoryTagMaps = new ArrayList<>();
        mAdapter = new AssetAddRfidAdapter(this, mInventoryTagMaps);
        mListView.setAdapter(mAdapter);

    }

    //重写activity类的 onOptionsItemSelected(MenuItem)回调方法，每当有菜单项被点击时，android就会调用该方法，并传入被点击菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_scan) {
            foundRfidCount = 0;
            mInventoryTagMaps.clear();
            mPresenter.startScanRfid();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_asset_add, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter.isScanning()) {
            mPresenter.stopScanRfid();
        }
        mPresenter.closeDriver();
    }

    public void showMenuDialog(Activity activity, String[] menuArr, DialogInterface.OnClickListener onItemClickListener) {
        new AlertDialog.Builder(activity)
                .setItems(menuArr, onItemClickListener)
                .show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.batch_id_container:
                mPresenter.loadBatch();
                break;
            case R.id.brand_container:
                mPresenter.loadBrand();
                break;
            case R.id.categoryGb_container:
                mPresenter.loadCategoryGb();
                break;
            case R.id.category_container:
                mPresenter.loadCategory();
                break;
            case R.id.model_container:
                mPresenter.loadModel();
                break;
            case R.id.buy_date_container:
                selectBuyDay();
                break;
            case R.id.commit_asset:
                attemptCommitAsset();
                break;
            case R.id.select_all_none:
                selectAllRfid();
                break;
            default:
                break;
        }
    }

    private void selectAllRfid() {
        for(WrapInventoryTagMap tag : mInventoryTagMaps){
            tag.checked = true;
        }
        mAdapter.notifyDataSetChanged();
    }

    private void selectBuyDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mBuyDay = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                mBuyDate.setText(mBuyDay);
            }
        };

        DatePickerDialog dpd = new DatePickerDialog(this, datelistener, year, month, day);
        dpd.show();
    }

    private void attemptCommitAsset() {

        final List<WrapInventoryTagMap> inventoryTags = new ArrayList<>();
        final List<String> checkRfid = new ArrayList<>();

        for (WrapInventoryTagMap tag : mInventoryTagMaps) {
            if (tag.checked) {
                inventoryTags.add(tag);
                checkRfid.add(RfidUtils.parseRfidEPC(tag.inventoryTagMap));
            }
        }

        if (TextUtils.isEmpty(mAutoBatch) && mBatch == null) {
            showToast("请选择批次号!");
            return;
        } else if (TextUtils.isEmpty(mAssetName.getText())) {
            showToast("请填写资产名!");
            return;
        } else if (TextUtils.isEmpty(mDurableYears.getText())) {
            showToast("请填写使用年限!");
            return;
        } else if (TextUtils.isEmpty(mPrice.getText())) {
            showToast("请填写价格!");
            return;
        } else if (TextUtils.isEmpty(mBuyDay)) {
            showToast("请选择购置时间!");
            return;
        } else if (mBrand == null) {
            showToast("请选择品牌!");
            return;
        } else if (mCategoryGb == null) {
            showToast("请选择类型(国标)!");
            return;
        } else if (mCategory == null) {
            showToast("请选择类型(自定义)!");
            return;
        } else if (mModel == null) {
            showToast("请选择型号!");
            return;
        } else if (inventoryTags.size() == 0) {
            showToast("请扫描并选择标签!");
            return;
        }

        final List<AssetAddBean> assetAdd = new ArrayList<>();
        new AlertDialog.Builder(this).setCancelable(false).setTitle("是否绑定以下标签?")
                .setItems(checkRfid.toArray(new String[checkRfid.size()]), null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (String rfid : checkRfid) {
                    assetAdd.add(new AssetAddBean(mAssetName.getText().toString(), TextUtils.isEmpty(mAutoBatch) ? mBatch.getBatchNo() : mAutoBatch,
                            rfid, mBrand.getId().intValue(), mCategory.getId().intValue(),
                            mCategoryGb.getId().intValue(), mModel.getId().intValue(),
                            Double.valueOf(mPrice.getText().toString()), user.getOfficeId(),
                            user.getId().intValue(), mBuyDay, Integer.valueOf(mDurableYears.getText().toString()), null));
                }
                mPresenter.saveAssets(assetAdd);
                finish();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMaps) {
        if (inventoryTagMaps.size() > foundRfidCount) {
            for (int i = foundRfidCount; i < inventoryTagMaps.size(); i++) {
                mInventoryTagMaps.add(new WrapInventoryTagMap(inventoryTagMaps.get(i)));
            }
            foundRfidCount = inventoryTagMaps.size();
            scanCount.setText(getString(R.string.scan_count, mInventoryTagMaps.size()));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showBatch(final List<AssetBatchResponse> batchs) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<AssetBatchResponse>(this, batchs, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, AssetBatchResponse assetBatchResponse) {
                holder.setText(android.R.id.text1, assetBatchResponse.getBatchNo());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAutoBatch = null;
                mBatch = batchs.get(i);
                mAssetBatch.setText(mBatch.getBatchNo());
            }
        }).setPositiveButton("自动生成", new DialogInterface.OnClickListener() {//positive 积极的、正数
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAutoBatch = String.valueOf(System.currentTimeMillis());
                mAssetBatch.setText(mAutoBatch);
            }
        }).show();
    }

    @Override
    public void showBrand(final List<BrandResponse> brand) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<BrandResponse>(this, brand, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, BrandResponse brandResponse) {
                holder.setText(android.R.id.text1, brandResponse.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mBrand = brand.get(i);
                mBrandName.setText(mBrand.getName());
            }
        }).show();
    }

    @Override
    public void showCategory(final List<CategoryResponse> categorys) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<CategoryResponse>(this, categorys, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, CategoryResponse categoryResponse) {
                holder.setText(android.R.id.text1, categoryResponse.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCategory = categorys.get(i);
                mCategoryName.setText(mCategory.getName());
            }
        }).show();
    }

    @Override
    public void showCategoryGb(final List<CategoryGbResponse> categoryGbs) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<CategoryGbResponse>(this, categoryGbs, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, CategoryGbResponse categoryGbResponse) {
                holder.setText(android.R.id.text1, categoryGbResponse.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mCategoryGb = categoryGbs.get(i);
                mCategoryGbName.setText(mCategoryGb.getName());
            }
        }).show();
    }

    @Override
    public void showModel(final List<ModelResponse> models) {
        new AlertDialog.Builder(this).setAdapter(new CommonAdapter<ModelResponse>(this, models, android.R.layout.simple_list_item_1) {
            @Override
            public void convert(ViewHolder holder, ModelResponse modelResponse) {
                holder.setText(android.R.id.text1, modelResponse.getName());
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mModel = models.get(i);
                mModelName.setText(mModel.getName());
            }
        }).show();
    }


    @Override
    public void setPresenter(AssetAddContract.Presenter presenter) {

    }
}
