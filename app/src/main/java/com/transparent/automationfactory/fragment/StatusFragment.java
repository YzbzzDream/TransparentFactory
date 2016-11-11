package com.transparent.automationfactory.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.adapter.common.DefaultRecycleViewAdapter;
import com.transparent.automationfactory.base.adapter.common.DefaultRecycleViewHolder;
import com.transparent.automationfactory.base.entity.StatusEntity;
import com.transparent.automationfactory.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yzbzz on 2016/11/11.
 */

public class StatusFragment extends BaseFragment {

    private View mView;
    private RecyclerView mRecyclerView;
    private DefaultRecycleViewAdapter mAdapter;

    private List<StatusEntity> mList = new ArrayList<>();

    public static StatusFragment newInstance() {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mList.clear();
        for (int i = 0; i < 5; i++) {
            StatusEntity statusEntity = new StatusEntity();
            statusEntity.setName("KFC立体字");
            statusEntity.setWorkModel("智能模式");
            statusEntity.setOutputMA("30");
            statusEntity.setOutputVoltage("24V");
            statusEntity.setLineT("65C");
            statusEntity.setPeopleCount("10");
            mList.add(statusEntity);
        }
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_status, container, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.rl_map_status);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new DefaultRecycleViewAdapter<StatusEntity>(mContext, mList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.fragment_status_rv_item;
            }

            @Override
            public void bindView(DefaultRecycleViewHolder viewHolder, StatusEntity data, int position) {
                if ((position & 1) == 0) {
                    viewHolder.itemView.setBackgroundResource(R.color.c_999999);
                }
                viewHolder.setText(R.id.tv_product, data.getName());
                viewHolder.setText(R.id.tv_work_model, data.getWorkModel());
                viewHolder.setText(R.id.tv_outputMA, data.getOutputMA());
                viewHolder.setText(R.id.tv_output_voltage, data.getOutputVoltage());
                viewHolder.setText(R.id.tv_lineT, data.getLineT());
                viewHolder.setText(R.id.tv_people_count, data.getPeopleCount());
            }
        };
        mRecyclerView.setAdapter(mAdapter);
        return mView;
    }
}
