package com.sourceedge.bhagyalakshmi.orders.models;


import java.math.BigInteger;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Centura on 21-01-2017.
 */

public class LocationModel {
    public Double Latitude;
    public Double Longitude;
    public String Date;

    public LocationModel(){
        Latitude=0.0;
        Longitude=0.0;
        Date= "";
    }

    public LocationModel(Double latitude, Double longitude, String date){
        Latitude=latitude;
        Longitude=longitude;
        Date=date;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
