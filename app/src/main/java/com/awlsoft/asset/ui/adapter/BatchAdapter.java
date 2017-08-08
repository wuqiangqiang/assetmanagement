package com.awlsoft.asset.ui.adapter;

import android.content.Context;
import android.view.View;

import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.response.AssetBatchResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.util.TasksUtils;

import java.util.List;

/**
 * Created by W on 2017/7/31.
 */

public class BatchAdapter extends CommonAdapter<AssetBatchResponse> {

    /**
     * 在Activity中被实现
     */
    public interface BatchItemListener {

        void onBatchClick(AssetBatchResponse clickedBatch);

        void onCompleteTaskClick(BaseTaskResponse completedTask);

        void onActivateTaskClick(BaseTaskResponse activatedTask);
    }

    private BatchItemListener mItemListener;

    public BatchAdapter(Context context, List<AssetBatchResponse> assetBatchResponseList, BatchItemListener itemListener) {
        super(context, assetBatchResponseList, R.layout.item_batch);
        this.mItemListener = itemListener;
    }

    @Override
    public void convert(ViewHolder holder, final AssetBatchResponse batch) {
        holder.setText(R.id.batch_id, batch.getId()+"");
        holder.setText(R.id.batch_no, batch.getBatch_no());
        holder.setText(R.id.batch_name, batch.getName());

//        int statusCode = TasksUtils.getTaskStatusCode(batch);

//        holder.setText(R.id.task_status, ( statusCode== BaseTaskResponse.FINISHED) ? "已完成"
//                : (statusCode == BaseTaskResponse.RUNNING) ? "处理中" : "尚未开始");

//        holder.setText(R.id.task_open, (statusCode == InventoryResponse.FINISHED) ? "查看详情" : "处理任务");

//        holder.getConvertView().setBackgroundResource((statusCode == InventoryResponse.FINISHED) ?
//                R.drawable.inventory_completed_touch_feedback : R.drawable.inventory_touch_feedback);

        holder.setOnClickListener(R.id.batch_no, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onBatchClick(batch);
//                if (TasksUtils.isTaskFinished(batch)) {
//                    mItemListener.onCompleteTaskClick(batch);
//                } else {
//                    mItemListener.onActivateTaskClick(batch);
//                }
            }
        });
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mItemListener.onTaskClick(batch);
            }
        });
    }
}
