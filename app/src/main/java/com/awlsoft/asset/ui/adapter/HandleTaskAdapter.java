package com.awlsoft.asset.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.response.AssetResponse;
import com.awlsoft.asset.model.entry.response.BaseTaskResponse;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;
import com.awlsoft.asset.util.TasksUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/13.
 */

public class HandleTaskAdapter extends CommonAdapter<AssetResponse>{
    private int mMaxSelect = -1;
    private boolean mEditMode = false;
    public HandleTaskAdapter(Context context, List<AssetResponse> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    private SelectCallback mSelectCallback;
    public void setSelectCallback(SelectCallback selectCallback){
        mSelectCallback = selectCallback;
    }
    public interface SelectCallback{
        void onSelectCount(int count);
    }

    public void setEditMode(boolean bl){
        mEditMode = bl;
    }



    public List<AssetResponse> getHandTask(){

        return mDatas;
    }

    public void setMaxSelect(int max){
        mMaxSelect = max;
    }

    public int curSelectCount(){
        int count = 0;
        for(AssetResponse ar:mDatas){
            if(ar.justFound){
                count ++;
            }
        }
        return count;
    }

    public boolean addAssetResponse(AssetResponse assetResponse){
        boolean isAdd = true;
        for(AssetResponse ar:mDatas){
            if(ar.getRfid_code().equals(assetResponse.getRfid_code())){
                isAdd = false;
                break;
            }
        }
        if(isAdd){
            mDatas.add(assetResponse);
        }
        return isAdd;
    }

    public List<AssetResponse> getCommitTask(){
        List<AssetResponse> list = new ArrayList<>();
        for(AssetResponse ar:mDatas){
            if(ar.justFound){
                list.add(ar);
            }
        }
        return list;
    }

    public boolean itemClick(int positon){

        if(mMaxSelect != -1 &&  curSelectCount() >= mMaxSelect){
            return false;
        }

        mDatas.get(positon).justFound = !mDatas.get(positon).justFound;
        notifyDataSetChanged();
        return true;
    }

    public boolean itemClick(AssetResponse assetResponse){

        if(!assetResponse.justFound && mMaxSelect != -1 &&  curSelectCount() >= mMaxSelect){
            return false;
        }
        assetResponse.justFound = !assetResponse.justFound;
        notifyDataSetChanged();
        return true;
    }

    public void setSelectAll(boolean bl){
        if(mMaxSelect != -1 && curSelectCount() >= mMaxSelect){
            return;
        }else if(mMaxSelect != -1){
            int selectCount = mMaxSelect - curSelectCount();
            int curSelect = 0;
            for(AssetResponse ar:mDatas){
                if(selectCount <= curSelect){
                    break;
                }
                if(!ar.justFound){
                    ar.justFound = true;
                    curSelect++;
                }
            }
        }else{
            for(AssetResponse ar:mDatas){
                ar.justFound = true;
            }
        }
        if(mSelectCallback != null){
            mSelectCallback.onSelectCount(curSelectCount());
        }
    }

    @Override
    public void convert(ViewHolder holder, final AssetResponse assetResponse) {
        holder.setChecked(R.id.checkBox,assetResponse.justFound);
        holder.setText(R.id.tvRFID,assetResponse.getRfidCode());
        holder.setText(R.id.tvAssetType, TasksUtils.getCategoryLabelWithId(mContext,assetResponse.getCategoryId()));
        holder.setText(R.id.tvAssetName,assetResponse.getName());
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditMode && !itemClick(assetResponse)){
                    Toast.makeText(mContext,"超过最大数量！",Toast.LENGTH_LONG).show();
                }
                if(mSelectCallback != null){
                    mSelectCallback.onSelectCount(curSelectCount());
                }
            }
        });
    }
}
