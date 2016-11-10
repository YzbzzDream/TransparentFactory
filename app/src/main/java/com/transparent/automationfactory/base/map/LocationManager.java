package com.transparent.automationfactory.base.map;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class LocationManager {
  Context mContext;

  //  LocationClient locationClient;
  public LocationManager(Context context){
    mContext = context;
  }

  public void initLocation(LocationClient locationClient){
    //    locationClient = new LocationClient(sContext);

    LocationClientOption option = new LocationClientOption();
    option.setOpenGps(true);
    option.setCoorType("bd09ll");
//    option.setScanSpan(1000);
    option.setIsNeedAddress(true);
    option.setLocationMode(LocationMode.Hight_Accuracy);
    locationClient.setLocOption(option); 
//    locationClient.setDebug(true);

  }   

  public void startLocation(LocationClient locationClient){
    if(!locationClient.isStarted()){
      locationClient.start();
    }
    locationClient.requestLocation();
  }
  
  public void stopLocation(LocationClient locationClient){
    locationClient.stop();
  }
  
  public void setLocationListener(BDLocationListener bdLocationListener, LocationClient locationClient){
    locationClient.registerLocationListener(bdLocationListener);
  }
}