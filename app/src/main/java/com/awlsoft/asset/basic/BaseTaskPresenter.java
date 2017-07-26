package com.awlsoft.asset.basic;

import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/9.
 */

public interface BaseTaskPresenter extends BasePresenter {
    void stop();

    void openDriver();

    void closeDriver();

    void startScanRfid();

    void stopScanRfid();

    boolean isScanning();

    //ymx add
    void compareList(List<InventoryBuffer.InventoryTagMap> list);

    List<AssetResponse> getHandleTaskList();

    void onHandTask(List<AssetResponse> list);

    void onCommitTask(List<AssetResponse> list);
}
