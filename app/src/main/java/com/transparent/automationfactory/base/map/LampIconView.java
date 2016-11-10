package com.transparent.automationfactory.base.map;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.transparent.automationfactory.R;
import com.transparent.automationfactory.base.entity.LampListEntity;
import com.transparent.automationfactory.base.util.BitmapUtil;


import java.util.ArrayList;
import java.util.List;

public class LampIconView extends FrameLayout {

    public static LampListEntity.LampEntity sLastSearchLamp = null;

    private TextView markText;
    private ImageView markerImg;
    private BitmapDescriptor bitmapDescriptor;

    public LampIconView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_lamp_icon_marker, this);
        setDrawingCacheEnabled(true);
        markText = (TextView) findViewById(R.id.text_marker);
        markerImg = (ImageView) findViewById(R.id.img_marker);
    }


    private MarkerOptions getLampMaker(LampIconParam lampIconParam, int index) {

        int size = lampIconParam.getLampInfoEntityList().size();
        if (index < 0 || index >= size) {
            return null;
        }
        MarkerOptions overlayOptions;
        LampListEntity.LampEntity lampInfoEntity = lampIconParam.getLampInfoEntityList().get(index);
        if (lampIconParam.isClick() &&
                lampIconParam.getClickLat() == lampInfoEntity.getLat() &&
                lampIconParam.getClickLon() == lampInfoEntity.getLon()) {
            overlayOptions = getClickOverlay(lampInfoEntity, index);
        } else {
            overlayOptions = getNormalOverlay(lampInfoEntity, index);

        }


        return overlayOptions;

    }

    private MarkerOptions getNormalOverlay(LampListEntity.LampEntity lampInfoEntity, int index) {
        markText.setVisibility(GONE);
//        if (lampInfoEntity.isNervous()) {
//            markerImg.setImageResource(R.drawable.mapssearch_icon_normal_nervous);
//        } else {
            markerImg.setImageResource(R.drawable.mapssearch_icon_normal);
//        }
        LatLng latLng = new LatLng(lampInfoEntity.getLat(), lampInfoEntity.getLon());
        return getOverLayOptions(latLng, index);
    }

    private MarkerOptions getSearchOverlay(double lat, double lon) {
        markText.setVisibility(GONE);
        markerImg.setImageResource(R.drawable.icon_search_location_pin);
        LatLng latLng = new LatLng(lat, lon);
        return getOverLayOptions(latLng, -1);


    }

    private MarkerOptions getOverLayOptions(LatLng latLng, int index) {
        Bitmap bitmap = BitmapUtil.getViewBitmap(LampIconView.this);
        bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        MarkerOptions overlayOptions = new MarkerOptions().position(latLng).icon(bitmapDescriptor).zIndex(index)
                .animateType(MarkerOptions.MarkerAnimateType.none);
        return overlayOptions;

    }

    private MarkerOptions getClickOverlay(LampListEntity.LampEntity lampInfoEntity, int index) {
        markText.setVisibility(VISIBLE);

//        if (lampInfoEntity.isNervous()) {
//            markerImg.setImageResource(R.drawable.mapssearch_icon_big_nervous);
//            markText.setTextColor(getResources().getColor(R.color.marker_nervous));
//        } else {
            markerImg.setImageResource(R.drawable.mapssearch_icon_big);
            markText.setTextColor(getResources().getColor(R.color.marker_normal));
//        }
//        markText.setText(lampInfoEntity.getFeeHour());

        LatLng latLng = new LatLng(lampInfoEntity.getLat(), lampInfoEntity.getLon());
        return getOverLayOptions(latLng, index);

    }

    public void closeMap() {
        if (bitmapDescriptor != null) {
            bitmapDescriptor.recycle();
        }
    }

    public List<OverlayOptions> getOverlays(LampIconParam lampIconParam) {
        List<OverlayOptions> markerOptionsList = new ArrayList<>();
        int size = lampIconParam.getLampInfoEntityList().size();
        LampListEntity.LampEntity clickLampInfo = null;
        int clickIndex = 0;
        LampListEntity.LampEntity  searchParkingInfo = null;
        int searchIndex = 0;
        boolean hasSearch = lampIconParam.getSearchLat() > 0 && lampIconParam.getSearchLon() > 0;
        for (int i = 0; i < size; i++) {
            LampListEntity.LampEntity lampInfoEntity = lampIconParam.getLampInfoEntityList().get(i);
            if (hasSearch &&
                    lampIconParam.getSearchLat() == lampInfoEntity.getLat() &&
                    lampIconParam.getSearchLon() == lampInfoEntity.getLon()) {
                if (sLastSearchLamp == null || !sLastSearchLamp.equals(lampInfoEntity)) {
                    searchParkingInfo = lampInfoEntity;
                    searchIndex = i;
                    sLastSearchLamp = searchParkingInfo;
                    continue;
                }
            }

            if (lampIconParam.isClick() &&
                    lampIconParam.getClickLat() == lampInfoEntity.getLat() &&
                    lampIconParam.getClickLon() == lampInfoEntity.getLon()) {
                clickLampInfo = lampInfoEntity;
                clickIndex = i;
                continue;
            }
            MarkerOptions overlayOptions = getNormalOverlay(lampInfoEntity, i);
            markerOptionsList.add(overlayOptions);

        }
        if (searchParkingInfo == null) {
            MarkerOptions overlayOptions =
                    getSearchOverlay(lampIconParam.getSearchLat()
                            , lampIconParam.getSearchLon());
            overlayOptions.zIndex(lampIconParam.getLampInfoEntityList().size());
            markerOptionsList.add(overlayOptions);
        } else {
            clickLampInfo = searchParkingInfo;
            clickIndex = searchIndex;
        }
        if (clickLampInfo != null) {
            MarkerOptions overlayOptions = getClickOverlay(clickLampInfo, clickIndex);
            overlayOptions.zIndex(lampIconParam.getLampInfoEntityList().size());
            markerOptionsList.add(overlayOptions);
        }

        return markerOptionsList;
    }


}
