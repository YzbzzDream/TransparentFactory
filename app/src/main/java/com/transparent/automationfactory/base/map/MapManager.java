package com.transparent.automationfactory.base.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.List;


public class MapManager {

    private MapStatusUpdate mapStatusUpdate;
    private UiSettings uiSettings;

    private BitmapDescriptor bitmap_tagging_etcp;
    private OverlayOptions overlayOptions;

    private LampIconView lampIconView;

    private Context mContext;
    private MapView mapView;
    private BaiduMap mBaiduMap;
    private TextView markText;
    private View view;
//  private TextView symbolText;


    private double mapCenterLongitude;
    private double mapCenterLatitude;
    private static double myLongitude;
    private static double myLatitude;

    private LatLng clickMarkLatLng = new LatLng(0, 0);

    private LocationClient mLocationClient;
    private LocationManager mLocationManager;


    private String currentCity;
    private String myLocationCity;
    private float myAccracy;
    private GeoCoder mGeocoder;

    //缩放级别  统一为16
    private static final int ZOOM_LEVEL_DEFAULT = 16;

    private ImageView markerImg;

    public MapManager(Context context, MapView parking_etcp_mapview) {
        mContext = context;
        mapView = parking_etcp_mapview;
        mBaiduMap = mapView.getMap();
        lampIconView = new LampIconView(mContext);
        initLocation();

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.layout_marker, null);
        view.setDrawingCacheEnabled(true);
        markText = (TextView) view.findViewById(R.id.text_marker);
//    symbolText = (TextView)view.findViewById(R.id.text_marker_symbol);
        markerImg = (ImageView) view.findViewById(R.id.img_marker);
    }

    public void initLocation() {
        mLocationClient = new LocationClient(mContext);
        mLocationManager = new LocationManager(mContext);
        mLocationManager.initLocation(mLocationClient);
        mLocationManager.setLocationListener(bdLocationListener, mLocationClient);
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mLocationManager.startLocation(mLocationClient);
            }
        });
    }

    private BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {

            if (location == null) {
                return;
            } else {

                MyLocationData myLocationData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        .direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .build();
                mBaiduMap.setMyLocationData(myLocationData);

                myLongitude = location.getLongitude();
                myLatitude = location.getLatitude();

                myLocationCity = location.getCity();

                myAccracy = location.getRadius();

                ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
                LatLng point = new LatLng(myLatitude, myLongitude);
                mReverseGeoCodeOption.location(point);
                mGeocoder.reverseGeoCode(mReverseGeoCodeOption);
                moveMap(location);
            }
        }


    };

    public boolean isLastMarket(Marker marker) {
        return clickMarkLatLng != null &&
                clickMarkLatLng.latitude == marker.getPosition().latitude &&
                clickMarkLatLng.longitude == marker.getPosition().longitude;
    }

    public void initMap() {
        zoomTo(ZOOM_LEVEL_DEFAULT);
        setBaidumap();
    }

    public void zoomTo(float zoomLevel) {
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(zoomLevel);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }

    private void setBaidumap() {
        uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setOverlookingGesturesEnabled(false);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMaxAndMinZoomLevel(20.0F, 4.0F);
    }

    public void addMapOverlay(double lat, double lon) {
        mBaiduMap.clear();
        LatLng latLng = new LatLng(lat, lon);
        markerImg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.icon_pin));

        bitmap_tagging_etcp = BitmapDescriptorFactory.fromBitmap(getViewBitmap());
        overlayOptions = new MarkerOptions().position(latLng).icon(bitmap_tagging_etcp).zIndex(0)
                .animateType(MarkerOptions.MarkerAnimateType.grow);

        mBaiduMap.addOverlay(overlayOptions);

    }

    public void addMapOverlay(LampIconParam lampIconParam) {
        if (lampIconParam.getLampInfoEntityList() == null) {
            mBaiduMap.clear();
            return;
        }
        List<OverlayOptions> markerOptionsList = lampIconView.getOverlays(lampIconParam);
        mBaiduMap.clear();
        mBaiduMap.addOverlays(markerOptionsList);
    }

    public void addMapOverlay(List<LampListEntity.LampEntity> lampEntities, boolean isclick, double lat, double lon) {

        mBaiduMap.clear();
        int rad = 0;
        for (int i = 0; i < lampEntities.size(); i++) {

            LatLng latLng = new LatLng(lampEntities.get(i).getLat(), lampEntities.get(i).getLon());
            //加载网络图片
//            if (parkingInfoEntities.get(i).getParkIcon().trim().length() != 0) {
//
//                BitmapLoadUtils.getInstance().loadBitmaps(markerImg, parkingInfoEntities.get(i).getParkIcon());
//                markText.setText(parkingInfoEntities.get(i).getFeeHour());
//
//            } else {

            rad++;
            int result = rad % 3;

            if (0 == result) {
                markerImg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.marker_red));
            } else if (1 == result) {
                markerImg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.marker_green));
            } else {
                markerImg.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.marker_gray));
            }
//            }

            if (isclick && lat == lampEntities.get(i).getLat() &&
                    lon == lampEntities.get(i).getLon()) {

                bitmap_tagging_etcp = BitmapDescriptorFactory.fromBitmap(bigBitmap(getViewBitmap()));
                overlayOptions = new MarkerOptions().position(latLng).icon(bitmap_tagging_etcp).zIndex(lampEntities.size() - 1);

            } else {
                bitmap_tagging_etcp = BitmapDescriptorFactory.fromBitmap(getViewBitmap());
                overlayOptions = new MarkerOptions().position(latLng).icon(bitmap_tagging_etcp).zIndex(i)
                        .animateType(MarkerOptions.MarkerAnimateType.grow);

            }
            mBaiduMap.addOverlay(overlayOptions);

        }
    }

    public void closeMap() {
        mBaiduMap.setMyLocationEnabled(false);
        if (bitmap_tagging_etcp != null) {
            bitmap_tagging_etcp.recycle();
        }
        mapView.onDestroy();
        mapView = null;
    }

    public LatLng getClickMarkLatLng() {
        return clickMarkLatLng;
    }

    public Bitmap getViewBitmap() {

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0,
                view.getMeasuredWidth(),
                view.getMeasuredHeight());

        view.buildDrawingCache();
        Bitmap cacheBitmap = view.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        view.destroyDrawingCache();

        return bitmap;
    }

    private static Bitmap bigBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(1.2f, 1.2f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 移动地图
     */
    public void moveMap(BaiduMap baiduMap, BDLocation location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        if (mapStatusUpdate != null && baiduMap != null) {
            baiduMap.animateMapStatus(mapStatusUpdate);
        }

    }

    public void moveMap(BDLocation location) {

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        if (mapStatusUpdate != null && mBaiduMap != null) {
            mBaiduMap.animateMapStatus(mapStatusUpdate);
        }

    }

    public void moveMarket(LatLng latLng) {
        clickMarkLatLng = latLng;
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory
                .newLatLng(latLng);
        mBaiduMap.animateMapStatus(statusUpdate);
    }

    /**
     * 地图点击
     */
    public void setMapClickListener(OnMapClickListener mapClickListener) {
        mBaiduMap.setOnMapClickListener(mapClickListener);
    }

    /**
     * 地图状态改变
     */
    public void setMapStatusChangeListener(OnMapStatusChangeListener mapStatusChangeListener) {
        mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
    }

    /**
     * Marker点击
     */
    public void setMarkerClickListener(OnMarkerClickListener markerClickListener) {
        mBaiduMap.setOnMarkerClickListener(markerClickListener);
    }

    public void startLocationmanager() {
        mLocationManager.startLocation(mLocationClient);

    }

    public void stopLocationManager() {
        mLocationManager.stopLocation(mLocationClient);
    }
}
