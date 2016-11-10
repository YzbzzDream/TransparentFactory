package com.transparent.automationfactory.base.entity;

import java.util.ArrayList;

/**
 * Created by zcq on 2016/8/12.
 */
public class LampListEntity extends BaseEntity{

    private ArrayList<LampEntity> data = new ArrayList<LampEntity>();

    public ArrayList<LampEntity> getData() {
        return data;
    }

    public void setData(ArrayList<LampEntity> data) {
        this.data = data;
    }

    public static class LampEntity {
        private String name;
        private String address;
        private double lat;
        private double lon;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
