package com.transparent.automationfactory.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.List;

/**
 * Created by zcq on 2016/8/13.
 */
public class LampAdapter extends BaseAdapter {
    private Activity adapterContext;
    private List<LampListEntity.LampEntity> adapterLampInfoEntities;
    private LayoutInflater mLayoutInflater;

    public LampAdapter(Activity mContext, List<LampListEntity.LampEntity> entities) {
        this.adapterContext = mContext;
        this.adapterLampInfoEntities = entities;
        this.mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        if (adapterLampInfoEntities == null) {
            return 0;
        }
        return adapterLampInfoEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterLampInfoEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_lamp_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.setData(adapterLampInfoEntities.get(position));
        return convertView;
    }

    public void setData(List<LampListEntity.LampEntity> lampInfoEntityList, double lat, double lon) {
        adapterLampInfoEntities.clear();
        adapterLampInfoEntities.addAll(lampInfoEntityList);
    }

    static  class ViewHolder {
        private TextView tvName;
        private TextView rvAddress;
        public ViewHolder(View convertView) {
            this.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            this.rvAddress = (TextView) convertView.findViewById(R.id.tv_address);
        }
        public void setData(LampListEntity.LampEntity entity ) {
            this.tvName.setText(entity.getName());
            this.rvAddress.setText(entity.getAddress());
        }
    }
}
