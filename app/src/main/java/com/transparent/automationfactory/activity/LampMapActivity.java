package com.transparent.automationfactory.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.activity.BaseTitleBarActivity;
import com.transparent.automationfactory.base.entity.LampListEntity;
import com.transparent.automationfactory.base.map.LampIconParam;
import com.transparent.automationfactory.base.map.LocationManager;
import com.transparent.automationfactory.base.map.MapManager;
import com.transparent.automationfactory.base.views.SlidingDrawer;
import com.transparent.automationfactory.base.views.ViewLampList;
import com.transparent.automationfactory.base.views.ViewLampRecycle;
import com.transparent.automationfactory.base.widget.TowRotateAnimation;

import java.util.ArrayList;
import java.util.List;

public class LampMapActivity extends BaseTitleBarActivity implements BaiduMap.OnMapClickListener, BaiduMap.OnMarkerClickListener, BaiduMap.OnMapStatusChangeListener, BDLocationListener {
    //地图相关
    private Context mContext;
    private BaiduMap mBaiduMap;
    private MapManager mapManager;
    private Marker myMarker;
    private MapView mMapView = null;
    private LatLng markLatLng;

    private LocationClient mLocationClient;
    private LocationManager mLocationManager;

    // marker经度
    private double markerLongitude;
    // marker纬度
    private double markerLatitude;
    private boolean isClick = false;

    //旋转动画
    private TowRotateAnimation animaton;

    //设备布局
    private FrameLayout lampFrameLayout;
    //地图布局
    private FrameLayout mapFrameLayout;

    private ViewLampRecycle viewLampList;

    private SlidingDrawer mSlidingDrawer;
    private ImageView mHandleUpImg;

    ArrayList<LampListEntity.LampEntity> mLampList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();;
        setContentView(R.layout.activity_lamp_map);
        mMapView = (MapView) findViewById(R.id.bmapView);
        initData();
        init();

        setTabTitle(getString(R.string.nearby));
        setRightText(getString(R.string.list));
        setLeftImageOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBack();
            }
        });
    }

    private void init() {
        initViews();
        initMap();
        initLocation();
        initListener();
        showMarker(mLampList);
    }

    /**
     * 初始化定位
     */
    public void initLocation() {
        mLocationClient = new LocationClient(mContext);
        mLocationManager = new LocationManager(mContext);
        mLocationManager.initLocation(mLocationClient);
        mLocationManager.setLocationListener(this, mLocationClient);
    }


    private void initMap() {
        mBaiduMap = mMapView.getMap();
        mapManager = new MapManager(mContext, mMapView);
        mapManager.initMap();

        mapManager.setMapClickListener(this);
        mapManager.setMapStatusChangeListener(this);
        mapManager.setMarkerClickListener(this);
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                mLocationManager.startLocation(mLocationClient);
            }
        });

        //test
        BDLocation mLocation = new BDLocation();
        mLocation.setLatitude(39.906134);
        mLocation.setLongitude(116.474439);
        mapManager.moveMap(mBaiduMap,mLocation);
    }

    private void initViews() {
        animaton = new TowRotateAnimation();

        lampFrameLayout = (FrameLayout) findViewById(R.id.frame_lamp);
        mapFrameLayout = (FrameLayout) findViewById(R.id.frame_map);
        viewLampList = (ViewLampRecycle) findViewById(R.id.view_lamp_list);
        viewLampList.initData(new LatLng(0.0, 0.0));
        viewLampList.reloadData(new LatLng(0.0, 0.0), mLampList);

        mSlidingDrawer = (SlidingDrawer) findViewById(R.id.drawer_bottom_up);

        OnDrawerScrollListener onDrawerScrollListener = new OnDrawerScrollListener();
        mSlidingDrawer.setOnDrawerScrollListener(onDrawerScrollListener);
        mSlidingDrawer.setVisibility(View.INVISIBLE);
        mHandleUpImg = (ImageView) findViewById(R.id.img_handle_up);
    }

    private void initListener() {
        setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lampFrameLayout.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
                if (mapFrameLayout.getVisibility() == View.VISIBLE) {
                    animaton.clickFrontViewAnimation(lampFrameLayout, mapFrameLayout, viewLampList);
                    setRightText(getString(R.string.map));
                } else {
                    animaton.clickBackViewAnimation(lampFrameLayout, mapFrameLayout, viewLampList);
                    setRightText(getString(R.string.list));
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mSlidingDrawer.isOpened()) {
            mSlidingDrawer.close();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && mapFrameLayout.getVisibility() == View.GONE) {
            animaton.clickBackViewAnimation(lampFrameLayout, mapFrameLayout, viewLampList);
            setRightText(getString(R.string.list));
            setRightImageInvisible();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            return onBack();
        }
        return super.onKeyDown(keyCode, event);
    }


    private boolean onBack() {

        if (mapFrameLayout.getVisibility() == View.GONE) {
            animaton.clickBackViewAnimation(lampFrameLayout, mapFrameLayout, viewLampList);
            setRightText(getString(R.string.list));
            setRightImageInvisible();
            return true;
        } else if (mSlidingDrawer.isOpened()) {
            mSlidingDrawer.close();
            return true;
        } else {
            finish();
            return true;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapManager.stopLocationManager();
        mapManager.closeMap();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }


    private LampListEntity.LampEntity clickLampInfo = null;
    private int marketPostion;
    private String mLampName;
    private double mLat;
    private double mLon;

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (clickLampInfo != null && mapManager.isLastMarket(marker)) {
            mSlidingDrawer.setVisibility(View.VISIBLE);
        } else {
            List<LampListEntity.LampEntity> lampInfoEntities = getLampDatas(mLampList, 0);
            if (lampInfoEntities != null) {
                marketPostion = marker.getZIndex();
                LampListEntity.LampEntity lampInfoEntity = lampInfoEntities.get(marker.getZIndex());
                LatLng latLng = new LatLng(lampInfoEntity.getLat(), lampInfoEntity.getLon());
                mLampName = lampInfoEntity.getName();

                mSlidingDrawer.setVisibility(View.VISIBLE);
                if (clickLampInfo == null) {
                    mapManager.moveMarket(marker.getPosition());
                }
                clickLampInfo = lampInfoEntity;

                LampIconParam parkIconParam = LampIconParam.builder(lampInfoEntities)
                        .withSearchLocation(mLat, mLon)
                        .withClickLocation(latLng.latitude, latLng.longitude);
                mapManager.addMapOverlay(parkIconParam);
            }
        }

//        if (markLatLng != null && markLatLng.latitude == marker.getPosition().latitude
//                && markLatLng.longitude == marker.getPosition().longitude) {
////        } else if (marker.getPosition().latitude == mapLatitude
////                && marker.getPosition().longitude == mapLongitude) {
//        } else {
//            markLatLng = marker.getPosition();
//            MapStatusUpdate statusUpdate = MapStatusUpdateFactory
//                    .newLatLng(markLatLng);
//            mBaiduMap.animateMapStatus(statusUpdate);
//            myMarker = marker;
//            List<LampListEntity.LampEntity> markerData = getLampDatas(mLampList, 0);
//            if (markerData != null) {
//
//                markerLatitude = markerData
//                        .get(myMarker.getZIndex()).getLat();
//                markerLongitude = markerData
//                        .get(myMarker.getZIndex()).getLon();
////                mParkName = markerData.get(myMarker.getZIndex())
////                        .getParkName().toString();
//
//                mSlidingDrawer.setVisibility(View.INVISIBLE);
//                mapManager.moveMarket(new LatLng(markerLatitude, markerLongitude));
//            }
//        }
        isClick = true;
        return true;
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

    }

    private class OnDrawerScrollListener implements
            SlidingDrawer.OnDrawerScrollListener {
        @Override
        public void onPreScrollStarted() {
        }

        @Override
        public void onScrollStarted() {
        }

        @Override
        public void onScroll(boolean willBackward) {
            if (willBackward) {
            }
        }

        @Override
        public void onScrollEnded() {
            if (mSlidingDrawer.isOpened()) {
                mHandleUpImg.setBackgroundResource(R.drawable.img_handle_up);
            } else {
                mHandleUpImg.setBackgroundResource(R.drawable.img_handle_down);
            }
        }
    }

    public void showMarker(List<LampListEntity.LampEntity> mLampInfoEntities) {
        //停车场标记
//        mapManager.addMapOverlay(
//                mLampList, isClick,
//                markerLatitude, markerLongitude);

        List<LampListEntity.LampEntity> tempList = getLampDatas(mLampInfoEntities, 0);

        LampIconParam parkIconParam = LampIconParam.builder(tempList).withSearchLocation(mLat, mLon);
        if (clickLampInfo != null) {
            parkIconParam
                    .withClickLocation(mapManager.getClickMarkLatLng().latitude, mapManager.getClickMarkLatLng().longitude);
        }
        mapManager.addMapOverlay(parkIconParam);
    }

    private List<LampListEntity.LampEntity> getLampDatas(List<LampListEntity.LampEntity> entities, int type) {
        return entities;
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
            mLampList.add(entity);
        }

    }
}
