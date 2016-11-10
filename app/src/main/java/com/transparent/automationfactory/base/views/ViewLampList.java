package com.transparent.automationfactory.base.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.adapter.LampAdapter;
import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.ArrayList;
import java.util.List;


public class ViewLampList extends LinearLayout {

    public ListView parkingListView;
    public LampAdapter mAdapter;
    private TextView tvAddress;
    private ArrayList<LampListEntity.LampEntity> lampList  = new ArrayList<>();

    public ViewLampList(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVisibility(VISIBLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.partial_activity_lamp_list, this);
        initView();
    }

    private void initView() {
        parkingListView = (ListView) findViewById(R.id.lt_lamp);
        tvAddress = (TextView) findViewById(R.id.text_now_address_list);
    }

    public void initData(LatLng myLatLng) {
        lampList = new ArrayList<>();
        mAdapter = new LampAdapter(
                (Activity) getContext(), lampList);
        parkingListView.setAdapter(mAdapter);

    }

    public void reloadData(LatLng centerLatLng, List<LampListEntity.LampEntity> lampInfoEntityList) {
        mAdapter.setData(lampInfoEntityList, centerLatLng.latitude, centerLatLng.longitude);
        mAdapter.notifyDataSetChanged();
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
    }

    public void setSearchListenre(OnClickListener clickListener) {
        tvAddress.setOnClickListener(clickListener);
    }

}
