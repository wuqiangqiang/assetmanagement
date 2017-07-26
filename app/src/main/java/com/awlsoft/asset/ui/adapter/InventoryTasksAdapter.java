package com.awlsoft.asset.ui.adapter;

import android.content.Context;
import android.view.View;

import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/1.
 */

public class InventoryTasksAdapter extends CommonAdapter<InventoryResponse> {

    public interface TaskItemListener {

        void onTaskClick(InventoryResponse clickedTask);

        void onCompleteTaskClick(InventoryResponse completedTask);

        void onActivateTaskClick(InventoryResponse activatedTask);
    }

    private TaskItemListener mItemListener;

    public InventoryTasksAdapter(Context context, List<InventoryResponse> datas, TaskItemListener itemListener) {
        super(context, datas, R.layout.item_inventory_task);
        this.mItemListener = itemListener;
    }

    @Override
    public void convert(ViewHolder holder, final InventoryResponse inventoryResponse) {
        holder.setText(R.id.inventory_id, String.valueOf(inventoryResponse.getTaskID()));
        holder.setText(R.id.inventory_name, inventoryResponse.getName());
        holder.setText(R.id.inventory_status, (inventoryResponse.getStatus() == InventoryResponse.FINISHED) ? "已完成"
                : (inventoryResponse.getStatus() == InventoryResponse.RUNNING) ? "盘点中" : "尚未开始");
        holder.setText(R.id.inventory_open, (inventoryResponse.getStatus() == InventoryResponse.FINISHED) ? "盘点结果" : "开始盘点");
        holder.getConvertView().setBackgroundResource((inventoryResponse.getStatus() == InventoryResponse.FINISHED) ?
                R.drawable.inventory_completed_touch_feedback : R.drawable.inventory_touch_feedback);
        holder.setOnClickListener(R.id.inventory_open, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inventoryResponse.isCompleted()) {
                    mItemListener.onCompleteTaskClick(inventoryResponse);
                } else {
                    mItemListener.onActivateTaskClick(inventoryResponse);
                }
            }
        });
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onTaskClick(inventoryResponse);
            }
        });
    }
}
