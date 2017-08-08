package com.awlsoft.asset.util;

import android.content.Context;
import android.content.Intent;

import com.awlsoft.asset.model.entry.TaskAssetFound;
import com.awlsoft.asset.model.entry.response.AllocationTaskResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.model.entry.response.BorrowTaskResponse;
import com.awlsoft.asset.model.entry.response.BreakageTaskResponse;
import com.awlsoft.asset.model.entry.response.CategoryResponse;
import com.awlsoft.asset.model.entry.response.KeeperResponse;
import com.awlsoft.asset.model.entry.response.ReceiveTaskResponse;
import com.awlsoft.asset.model.entry.response.ReturnTaskResponse;
import com.awlsoft.asset.model.entry.response.ScrapTaskResponse;
import com.awlsoft.asset.model.entry.response.WorkareaResponse;
import com.awlsoft.asset.orm.DBManager;
import com.awlsoft.asset.orm.greendao.CategoryResponseDao;
import com.awlsoft.asset.orm.greendao.KeeperResponseDao;
import com.awlsoft.asset.orm.greendao.WorkareaResponseDao;
import com.awlsoft.asset.ui.activity.inventory.InventoryDetailActivity;
import com.awlsoft.asset.ui.activity.tasks.AllocationTaskActivity;
import com.awlsoft.asset.ui.activity.tasks.BorrowTaskActivity;
import com.awlsoft.asset.ui.activity.tasks.BreakageTaskActivity;
import com.awlsoft.asset.ui.activity.tasks.ReceiveTaskActivity;
import com.awlsoft.asset.ui.activity.tasks.ReturnTaskActivity;
import com.awlsoft.asset.ui.activity.tasks.ScrapTaskActivity;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by yejingxian on 2017/6/12.
 */

public class TasksUtils {
    public static int getFinishedTaskCount(List<BaseTaskResponse> tasks) {
        int count = 0;
        if (tasks == null || tasks.isEmpty()) {
            count = 0;
        } else {
            for (BaseTaskResponse task : tasks) {
                if (isTaskFinished(task)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 这样的封装有意思吗？到底是为了什么
     * @param task
     * @return
     */
    public static boolean isTaskFinished(BaseTaskResponse task) {
        return task.isFinished();
    }

    public static String getTaskName(BaseTaskResponse task) {
        Preconditions.checkNotNull(task, "task can not be null!");
        return task.getTaskLabel();
    }

    /**
     * 获取任务编号
     * @param task
     * @return
     */
    public static String getTaskId(BaseTaskResponse task) {
        //precondition 前提
        Preconditions.checkNotNull(task, "task can not be null!");
        return task.getTaskId();
    }

    public static int getTaskStatusCode(BaseTaskResponse task) {
        Preconditions.checkNotNull(task, "task can not be null!");
        return task.getStatusCode();
    }

    /**
     * 处理任务
     * @param context
     * @param task
     */
    public static void workWithTask(Context context, BaseTaskResponse task) {
        Preconditions.checkNotNull(task, "task can not be null!");
        Intent intent = null;
        if (task instanceof ReceiveTaskResponse) {//领用任务
            intent = new Intent(context, ReceiveTaskActivity.class);
        } else if (task instanceof BorrowTaskResponse) {//借用任务
            intent = new Intent(context, BorrowTaskActivity.class);
        } else if (task instanceof ScrapTaskResponse) {//报废任务
            intent = new Intent(context, ScrapTaskActivity.class);
        } else if (task instanceof BreakageTaskResponse) {//报损任务
            intent = new Intent(context, BreakageTaskActivity.class);
        } else if (task instanceof ReturnTaskResponse) {//归还任务
            intent = new Intent(context, ReturnTaskActivity.class);
        } else if (task instanceof AllocationTaskResponse) {
            intent = new Intent(context, AllocationTaskActivity.class);
        }
        Preconditions.checkNotNull(intent, "intent can not be null!");
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_ID, task.getTaskId());
        intent.putExtra(InventoryDetailActivity.EXTRA_TASK_EDIT, !TasksUtils.isTaskFinished(task));
        context.startActivity(intent);
    }

    /**
     * 查看已经完成的任务
     * @param context
     * @param task
     */
    public static void openTaskDetail(Context context, BaseTaskResponse task) {
        workWithTask(context, task);
    }

    /**
     * 处理任务
     * @param context
     * @param task
     */
    public static void startTask(Context context, BaseTaskResponse task) {
        workWithTask(context, task);
    }

    public static String getCategoryLabelWithId(Context context, long id) {
        String name = "未知";
        try {
            CategoryResponse category = DBManager.getInstance(context).getDaoSession().getCategoryResponseDao()
                    .queryBuilder().where(CategoryResponseDao.Properties.Id.eq(id)).uniqueOrThrow();
            name = category.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getKeeperNameWithId(Context context, long id){
        String name = "未知";
        try {
            KeeperResponse keeper = DBManager.getInstance(context).getDaoSession().getKeeperResponseDao()
                    .queryBuilder().where(KeeperResponseDao.Properties.Id.eq(id)).uniqueOrThrow();
            name = keeper.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String getRepositoryNameWithId(Context context, long id){
        String name = "未知";
        try {
            WorkareaResponse area = DBManager.getInstance(context).getDaoSession().getWorkareaResponseDao()
                    .queryBuilder().where(WorkareaResponseDao.Properties.Id.eq(id)).uniqueOrThrow();
            name = area.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public static String concatTaskId(List<TaskAssetFound> assets){
        StringBuilder builder = new StringBuilder();
        if (assets != null && assets.size() != 0) {
            for (int i = 0; i < assets.size(); i++) {
                if (i != 0) {
                    builder.append(",");
                }
                builder.append(assets.get(i).getAssetId());
            }
        }
        return builder.toString();
    }

}
