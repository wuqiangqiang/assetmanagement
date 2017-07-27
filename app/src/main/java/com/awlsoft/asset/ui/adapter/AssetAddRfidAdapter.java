package com.awlsoft.asset.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;


import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.WrapInventoryTagMap;
import com.awlsoft.asset.supernfc.reader.model.InventoryBuffer;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.util.RfidUtils;

import java.util.List;

/**
 * Created by yejingxian on 2017/5/19.
 */

public class AssetAddRfidAdapter extends CommonAdapter<WrapInventoryTagMap> {

    private boolean clickAble = true;

    public AssetAddRfidAdapter(Context context, List<WrapInventoryTagMap> datas) {
        super(context, datas, R.layout.item_assetadd_rfid);
    }

    public void setClickAble(boolean bl){
        clickAble = bl;
    }

    protected void clickItem(WrapInventoryTagMap wrapInventoryTagMap){
        wrapInventoryTagMap.checked = !wrapInventoryTagMap.checked;
        notifyDataSetChanged();
    }

    @Override
    public void convert(ViewHolder holder, final WrapInventoryTagMap wrapInventoryTagMap) {
        holder.setChecked(R.id.rfid_tv,wrapInventoryTagMap.checked);
        holder.setText(R.id.rfid_tv, RfidUtils.parseRfidEPC(wrapInventoryTagMap.inventoryTagMap));
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickAble){
                    clickItem(wrapInventoryTagMap);
                }
            }
        });
    }
}
