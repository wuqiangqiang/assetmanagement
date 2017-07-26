package com.awlsoft.asset.ui.adapter;

import android.content.Context;


import com.awlsoft.asset.R;
import com.awlsoft.asset.model.entry.ComImgTextInfo;
import com.awlsoft.asset.ui.adapter.viewholder.ViewHolder;

import java.util.List;

/**
 * 列表选择的adapter
 * Created by WYZ on 2016-05-11.
 */
public class ListChooseWindowAdapter<T extends ComImgTextInfo> extends CommonAdapter<T> {


    public ListChooseWindowAdapter(Context context, List<T> datas) {
        super(context, datas, R.layout.charge_list_choose_window_item);
    }


    @Override
    public void convert(ViewHolder holder, T t) {

        holder.setText(R.id.charge_list_choose_item_text, t.getText());

    }
}
