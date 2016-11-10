package com.transparent.automationfactory.adapter;

import android.content.Context;

import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.adapter.common.DefaultRecycleViewAdapter;
import com.transparent.automationfactory.base.adapter.common.DefaultRecycleViewHolder;
import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.List;

/**
 * Created by zcq on 2016/8/13.
 */
public class LampRecycleViewAdapter extends DefaultRecycleViewAdapter<LampListEntity.LampEntity> {

    private Context mContext;

    public LampRecycleViewAdapter(Context context, List<LampListEntity.LampEntity> items) {
        super(context, items);
        mContext = context;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_lamp_layout;
    }

    @Override
    public void bindView(DefaultRecycleViewHolder viewHolder, LampListEntity.LampEntity data, int position) {
            viewHolder.setText(R.id.tv_name, data.getName());
            viewHolder.setText(R.id.tv_address, data.getAddress());
    }

    public void setData( List<LampListEntity.LampEntity> lampInfoEntityList) {
        mItems.clear();
        mItems.addAll(lampInfoEntityList);
        notifyDataSetChanged();
    }
}
