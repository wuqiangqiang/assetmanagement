package com.awlsoft.asset.util;

import android.text.TextUtils;

import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

/**
 * Created by yejingxian on 2017/5/31.
 */

public class RfidUtils {
    public static String parseRfidEPC(InventoryBuffer.InventoryTagMap inventoryTagMap) {
        if (inventoryTagMap == null || TextUtils.isEmpty(inventoryTagMap.strEPC)) {
            return null;
        }
        return inventoryTagMap.strEPC.replace(" ", "");
    }
}
