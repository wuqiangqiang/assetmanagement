package com.awlsoft.asset.model.entry;

import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;

/**
 * Created by yejingxian on 2017/6/14.
 */

public class WrapInventoryTagMap {
    public InventoryBuffer.InventoryTagMap inventoryTagMap;
    public boolean checked;

    public WrapInventoryTagMap(InventoryBuffer.InventoryTagMap inventoryTagMap) {
        this.inventoryTagMap = inventoryTagMap;
    }
}
