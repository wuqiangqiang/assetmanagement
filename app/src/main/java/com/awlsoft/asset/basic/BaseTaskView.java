package com.awlsoft.asset.basic;

import android.app.Activity;

import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/9.
 */

public interface BaseTaskView<T> extends BaseView<T> {
    Activity getActivity();

    void showReceiveHandleTask();
    void onAddAssetResponse(AssetResponse ar);
    void showFindRfids(List<InventoryBuffer.InventoryTagMap> inventoryTagMap);
}
