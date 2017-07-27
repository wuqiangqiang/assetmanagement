package com.awlsoft.asset.contract;

import android.app.Activity;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.AssetAddBean;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BrandResponse;
import com.awlsoft.asset.model.entry.response.CategoryGbResponse;
import com.awlsoft.asset.model.entry.response.CategoryResponse;
import com.awlsoft.asset.model.entry.response.ModelResponse;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

import java.util.List;

/**
 * Created by yejingxian on 2017/5/26.
 */

public interface AssetAddContract {

    interface View extends BaseView<Presenter> {
        Activity getActivity();
        void showRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMap);

        /**
         * 定义抽象方法展示批次号列表
         * @param batchs
         */
        void showBatch(List<AssetBatchResponse> batchs);
        void showBrand(List<BrandResponse> brand);
        void showCategory(List<CategoryResponse> categorys);
        void showCategoryGb(List<CategoryGbResponse> categoryGbs);
        void showModel(List<ModelResponse> models);
    }

    interface Presenter extends BasePresenter {
        void saveAssets(List<AssetAddBean> assets);
        void openDriver();
        void closeDriver();
        void startScanRfid();
        void stopScanRfid();
        boolean isScanning();
        void loadBatch();
        void loadBrand();
        void loadCategory();
        void loadCategoryGb();
        void loadModel();
    }
}
