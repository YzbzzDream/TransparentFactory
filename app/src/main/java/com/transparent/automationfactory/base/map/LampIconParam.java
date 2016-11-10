package com.transparent.automationfactory.base.map;


import com.transparent.automationfactory.base.entity.LampListEntity;

import java.util.List;

public class LampIconParam {


    public static  LampIconParam builder(List<LampListEntity.LampEntity> lampInfoEntities) {
        return new  LampIconParam(lampInfoEntities);

    }

    private LampIconParam(List<LampListEntity.LampEntity> lampInfoEntities) {
        this.lampInfoEntityList = lampInfoEntities;

    }

    public LampIconParam withClickLocation(double lat, double lon) {
        this.isClick = true;
        this.clickLat = lat;
        this.clickLon = lon;
        return this;

    }

    public LampIconParam withSearchLocation(double lat, double lon) {
        this.searchLat = lat;
        this.searchLon = lon;
        return this;
    }


    public List<LampListEntity.LampEntity> lampInfoEntityList;
    private boolean isClick = false;
    private double clickLat;
    private double clickLon;
    private double searchLat;
    private double searchLon;

    public List<LampListEntity.LampEntity> getLampInfoEntityList() {
        return lampInfoEntityList;
    }

    public void setParkingInfoEntityList(List<LampListEntity.LampEntity> lampInfoEntityList) {
        this.lampInfoEntityList = lampInfoEntityList;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public double getClickLat() {
        return clickLat;
    }

    public void setClickLat(double clickLat) {
        this.clickLat = clickLat;
    }

    public double getClickLon() {
        return clickLon;
    }

    public void setClickLon(double clickLon) {
        this.clickLon = clickLon;
    }

    public double getSearchLat() {
        return searchLat;
    }

    public void setSearchLat(double searchLat) {
        this.searchLat = searchLat;
    }

    public double getSearchLon() {
        return searchLon;
    }

    public void setSearchLon(double searchLon) {
        this.searchLon = searchLon;
    }
}
