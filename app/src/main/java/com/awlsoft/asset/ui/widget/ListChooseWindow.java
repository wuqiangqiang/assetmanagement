package com.awlsoft.asset.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.ComImgTextInfo;
import com.awlsoft.asset.ui.adapter.CommCallBack;
import com.awlsoft.asset.ui.adapter.ListChooseWindowAdapter;

import java.util.ArrayList;

/**
 * 列表单选弹窗
 * Created by WYZ on 2016-05-11.
 */
public class ListChooseWindow<T extends ComImgTextInfo> {
    private Context mContext;

    private ListView mListView;

    private ListChooseWindowAdapter mAdapter;

    /**
     * 弹窗
     **/
    private PopupWindow mWindow;

    private ArrayList<T> mInfoList;

    private int mChoosedPosition;

    private CommCallBack mCallback;


    public ListChooseWindow(Context context, ArrayList<T> dataList) {
        this.mContext = context;
        if (dataList == null) {
            mInfoList = new ArrayList<>(0);
        } else {
            mInfoList = dataList;
        }
        init();
        setChoosed(0);
    }


    public void setDataList(ArrayList<T> dataList) {
        if (dataList != null && !dataList.isEmpty()) {
            this.mInfoList.clear();
            mInfoList.addAll(dataList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void init() {
        mListView = new ListView(mContext);
        mListView.setBackgroundColor(Color.WHITE);
        mListView.setOnItemClickListener(mItemClick);
        mListView.setSelector(createStateListDrawable(new ColorDrawable(
                Color.TRANSPARENT), new ColorDrawable(0xfff2f2f2)));
        mListView.setDivider(new ColorDrawable(0xfff2f2f2));
        mListView.setDividerHeight(1);
        mAdapter = new ListChooseWindowAdapter(mContext, mInfoList);
        mListView.setAdapter(mAdapter);
    }

    public ListChooseWindow createWindow() {
        mWindow = new PopupWindow(mListView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mWindow.setBackgroundDrawable(new BitmapDrawable());
        mWindow.setOutsideTouchable(true);
        mWindow.setAnimationStyle(R.style.pop_menu_dropdown);
        mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });
        return this;
    }


    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private StateListDrawable createStateListDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{}, normal);
        return sd;
    }

    private AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setChoosed(position);
            if (mWindow != null) {
                mWindow.dismiss();
            } else {
                if (mCallback != null) {
                    mCallback.callBack(mChoosedPosition);
                }
            }
        }
    };

    /**
     * 设置选中
     *
     * @param position
     */
    public void setChoosed(int position) {
        if (position > mInfoList.size() - 1) {
            return;
        }
        this.mChoosedPosition = position;
        resetChoosedPostion(position);
        mAdapter.notifyDataSetChanged();
        if (mCallback != null) {
            mCallback.callBack(position);
        }
    }

    /**
     * 设置选中
     *
     * @param info
     */
    public void setChoosed(ComImgTextInfo info) {
        if (mInfoList.contains(info)) {
            int position = mInfoList.indexOf(info);
            setChoosed(position);
        }
    }

    public int getChoosedPosition() {
        return mChoosedPosition;
    }

    public T getChoosed() {
        return mInfoList.get(mChoosedPosition);
    }

    /**
     * 重新设置选中的位置
     *
     * @param position
     */
    private void resetChoosedPostion(int position) {
        for (int i = 0; i < mInfoList.size(); i++) {
            ComImgTextInfo info = (ComImgTextInfo) mInfoList.get(i);
            if (i == position) {
                info.isChoosed = true;
            } else {
                info.isChoosed = false;
            }
        }
    }

    public void setCallBack(CommCallBack callback) {
        mCallback = callback;
    }

    public void setBackgroundRes(int res) {
        mListView.setBackgroundResource(res);
    }

    /**
     * 在view上显示弹窗
     *
     * @param v
     */
    public void showUp(View v) {
        if (mWindow == null) {
            return;
        }
        mWindow.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0,
                v.getMeasuredHeight());
    }

    public void showDown(View v) {
        if (mWindow == null) {
            return;
        }
        mWindow.showAsDropDown(v, 0, 10);
    }

    public void dismiss() {
        if (mWindow == null) {
            return;
        }
        if (mWindow.isShowing()) {
            mWindow.dismiss();
        }
    }

    public boolean isShowing() {
        if (mWindow == null) {
            return false;
        }
        return mWindow.isShowing();
    }

    public void setWindowsWidth(int width) {
        if (mWindow != null) {
            mWindow.setWidth(width);
        }
    }
}
