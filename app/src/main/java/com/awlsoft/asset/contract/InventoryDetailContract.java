package com.awlsoft.asset.contract;

import android.app.Activity;

import com.awlsoft.asset.basic.BasePresenter;
import com.awlsoft.asset.basic.BaseView;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/1.
 */

public interface InventoryDetailContract {
    interface View extends BaseView<Presenter> {
        Activity getActivity();

        void setLoadingIndicator(boolean active);

        void showAssets(List<AssetResponse> inventoryResponses);

        void showLoadingAssetsError();

        void showNoAssets();

        void showRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMap);

    }

    interface Presenter extends BasePresenter {
        void stop();

        void loadAssets();

        void openDriver();

        void closeDriver();

        void startScanRfid();

        void stopScanRfid();

        boolean isScanning();

        void hangInventory(List<AssetResponse> assets);

        void completeInventory(List<AssetResponse> assets);

    }
}
