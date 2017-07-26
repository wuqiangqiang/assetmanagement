package com.awlsoft.asset.ui.adapter;

import android.content.Context;
import android.view.View;

import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.InventoryResponse;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.util.TasksUtils;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/1.
 */

public class TasksAdapter extends CommonAdapter<BaseTaskResponse> {

    public interface TaskItemListener {

        void onTaskClick(BaseTaskResponse clickedTask);

        void onCompleteTaskClick(BaseTaskResponse completedTask);

        void onActivateTaskClick(BaseTaskResponse activatedTask);
    }

    private TaskItemListener mItemListener;

    public TasksAdapter(Context context, List<BaseTaskResponse> datas, TaskItemListener itemListener) {
        super(context, datas, R.layout.item_task);
        this.mItemListener = itemListener;
    }

    @Override
    public void convert(ViewHolder holder, final BaseTaskResponse task) {
        holder.setText(R.id.task_id, TasksUtils.getTaskId(task));
        holder.setText(R.id.task_type, TasksUtils.getTaskName(task));
        int statusCode = TasksUtils.getTaskStatusCode(task);
        holder.setText(R.id.task_status, ( statusCode== BaseTaskResponse.FINISHED) ? "已完成"
                : (statusCode == BaseTaskResponse.RUNNING) ? "处理中" : "尚未开始");
        holder.setText(R.id.task_open, (statusCode == InventoryResponse.FINISHED) ? "查看详情" : "处理任务");
        holder.getConvertView().setBackgroundResource((statusCode == InventoryResponse.FINISHED) ?
                R.drawable.inventory_completed_touch_feedback : R.drawable.inventory_touch_feedback);
        holder.setOnClickListener(R.id.task_open, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TasksUtils.isTaskFinished(task)) {
                    mItemListener.onCompleteTaskClick(task);
                } else {
                    mItemListener.onActivateTaskClick(task);
                }
            }
        });
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onTaskClick(task);
            }
        });
    }
}
