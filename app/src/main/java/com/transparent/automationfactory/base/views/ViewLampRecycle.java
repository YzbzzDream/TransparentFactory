package com.transparent.automationfactory.base.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.baidu.mapapi.model.LatLng;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.WrapRecyclerView;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.adapter.LampAdapter;
import com.transparent.automationfactory.adapter.LampRecycleViewAdapter;
import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.ArrayList;
import java.util.List;


public class ViewLampRecycle extends LinearLayout {

    private List<LampListEntity.LampEntity> mLampInfos = new ArrayList<>();
    private LampRecycleViewAdapter mLayoutAdapter;
    private RecyclerView mRecyclerView;

    private PullToRefreshLayout mPullToRefreshLayout;
    private boolean isDragRefresh = false;

    private Context mContext;

    public ViewLampRecycle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setVisibility(GONE);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.partial_activity_lamp_recycle, this);
        initView();
    }

    private void initView() {
                    mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
            mPullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
                @Override
                public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                    isDragRefresh = true;
                }

                @Override
                public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                    isDragRefresh = true;
                }
            });
            mRecyclerView = (WrapRecyclerView) mPullToRefreshLayout.getPullableView();

            mLayoutAdapter = new LampRecycleViewAdapter(mContext, mLampInfos);
            LinearLayoutManager linearLayoutManagernew = new LinearLayoutManager(mContext);
            linearLayoutManagernew.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManagernew);
            mRecyclerView.setAdapter(mLayoutAdapter);


//            mRecyclerView = (RecyclerView)findViewById(R.id.recycle_view);
//
//            mLayoutAdapter = new LampRecycleViewAdapter(mContext, mLampInfos);
//            LinearLayoutManager linearLayoutManagernew = new LinearLayoutManager(mContext);
//            linearLayoutManagernew.setOrientation(LinearLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(linearLayoutManagernew);
//            mRecyclerView.setAdapter(mLayoutAdapter);
    }

    public void initData(LatLng myLatLng) {
        mLampInfos = new ArrayList<>();
        mLayoutAdapter = new LampRecycleViewAdapter(
                mContext, mLampInfos);
        mRecyclerView.setAdapter(mLayoutAdapter);

    }

    public void reloadData(LatLng centerLatLng, List<LampListEntity.LampEntity> lampInfoEntityList) {
        mLayoutAdapter.setData(lampInfoEntityList);
        mLayoutAdapter.notifyDataSetChanged();
    }

}
