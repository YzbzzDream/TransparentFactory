package com.transparent.automationfactory.activity;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.model.LatLng;
import com.jingchen.pulltorefresh.PullToRefreshLayout;
import com.jingchen.pulltorefresh.WrapRecyclerView;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.adapter.LampRecycleViewAdapter;
import com.transparent.automationfactory.base.entity.LampListEntity;
import com.transparent.automationfactory.base.views.ViewLampList;
import com.transparent.automationfactory.base.views.ViewLampRecycle;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

    private List<LampListEntity.LampEntity> mLampInfos = new ArrayList<>();
    private LampRecycleViewAdapter mLayoutAdapter;
    private WrapRecyclerView mRecyclerView;

    private boolean isDragRefresh = false;

    private PullToRefreshLayout mPullToRefreshLayout;

    private ViewLampRecycle viewLampList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking_now_fragment);
        initData();
        initView();
    }

    private void initData() {
        ArrayList<LatLng> ltLatLng = new ArrayList<>();
        ltLatLng.add(new LatLng(39.906134, 116.474439));
        ltLatLng.add(new LatLng(39.903758, 116.474125));
        ltLatLng.add(new LatLng(39.901919, 116.473789));
        ltLatLng.add(new LatLng(39.912124, 116.473231));
        ltLatLng.add(new LatLng(39.903032, 116.484447));
        ltLatLng.add(new LatLng(39.908437, 116.484901));
        ltLatLng.add(new LatLng(39.91267, 116.483555));
        ltLatLng.add(new LatLng(39.89345, 116.482981));
        ltLatLng.add(new LatLng(39.892563, 116.483764));
        ltLatLng.add(new LatLng(39.921874, 116.47882));

        ltLatLng.add(new LatLng(39.894397, 116.491773));
        ltLatLng.add(new LatLng(39.924271, 116.474453));
        ltLatLng.add(new LatLng(39.891127, 116.457456));
        ltLatLng.add(new LatLng(39.927604, 116.473389));
        ltLatLng.add(new LatLng(39.884128, 116.468966));
        ltLatLng.add(new LatLng(39.896543, 116.44691));
        ltLatLng.add(new LatLng(39.886613, 116.487884));
        ltLatLng.add(new LatLng(39.928004, 116.468727));
        ltLatLng.add(new LatLng(39.925571, 116.487897));
        ltLatLng.add(new LatLng(39.925633, 116.457485));

        ltLatLng.add(new LatLng(39.881719, 116.47665));
        ltLatLng.add(new LatLng(39.894931, 116.444931));
        ltLatLng.add(new LatLng(39.881726, 116.478955));
        ltLatLng.add(new LatLng(39.910914, 116.441175));
        ltLatLng.add(new LatLng(39.907177, 116.50673));
        ltLatLng.add(new LatLng(39.899955, 116.439981));
        ltLatLng.add(new LatLng(39.927669, 116.450111));
        ltLatLng.add(new LatLng(39.878594, 116.484146));
        ltLatLng.add(new LatLng(39.877477, 116.474539));
        ltLatLng.add(new LatLng(39.927963, 116.449181));

        ltLatLng.add(new LatLng(39.930247, 116.452614));

        String address = "灯地址";
        String name = "灯名称";
        for (int index = 0; index < 30; index++) {
            LampListEntity.LampEntity entity = new LampListEntity.LampEntity();
            entity.setLat(ltLatLng.get(index).latitude);
            entity.setLon(ltLatLng.get(index).longitude);
            entity.setAddress(address + index);
            entity.setName(name + index);
            mLampInfos.add(entity);
        }

    }

        private void initView() {
//            mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
//            mPullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
//                @Override
//                public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//                    isDragRefresh = true;
//                }
//
//                @Override
//                public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
//                    isDragRefresh = true;
//                }
//            });
//            mRecyclerView = (WrapRecyclerView) mPullToRefreshLayout.getPullableView();
//
//            mLayoutAdapter = new LampRecycleViewAdapter(this, mLampInfos);
//            LinearLayoutManager linearLayoutManagernew = new LinearLayoutManager(this);
//            linearLayoutManagernew.setOrientation(LinearLayoutManager.VERTICAL);
//            mRecyclerView.setLayoutManager(linearLayoutManagernew);
//            mRecyclerView.setAdapter(mLayoutAdapter);
            viewLampList = (ViewLampRecycle) findViewById(R.id.view_lamp_list);
            viewLampList.initData(new LatLng(0.0, 0.0));
            viewLampList.reloadData(new LatLng(0.0, 0.0), mLampInfos);
    }
}
