package com.awlsoft.asset.ui.adapter;

import android.content.Context;

import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.KeeperResponseDao;
import com.awlsoft.asset.orm.greendao.WorkareaResponseDao;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/2.
 */

public class InventoryDetailAdapter extends CommonAdapter<AssetResponse> {
    private DBManager mManager;

    public InventoryDetailAdapter(Context context, List<AssetResponse> datas) {
        super(context, datas, R.layout.item_inventory_detail);
        mManager = DBManager.getInstance(context);
    }

    @Override
    public void convert(ViewHolder holder, AssetResponse assetResponse) {
        holder.setText(R.id.asset_rfid, assetResponse.getRfid_code());
        holder.setText(R.id.asset_name, assetResponse.getName());
        String keeper = "未知";
        try {
            keeper = mManager.getDaoSession().getKeeperResponseDao().queryBuilder().where(KeeperResponseDao.Properties.Id.eq(assetResponse.getKeeper_id())).uniqueOrThrow().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setText(R.id.asset_keeper, keeper);
        String area = "未知";
        try {
            area = mManager.getDaoSession().getWorkareaResponseDao().queryBuilder().where(WorkareaResponseDao.Properties.Id.eq(assetResponse.getWorkarea_id())).uniqueOrThrow().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.setText(R.id.asset_area, area);
        holder.getConvertView().setBackgroundResource(assetResponse.hasFound || assetResponse.justFound ?
                R.drawable.inventory_completed_touch_feedback : R.drawable.inventory_touch_feedback);
    }
}
