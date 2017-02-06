package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;

/**
 * Created by Centura on 19-01-2017.
 */

public class TrackModel {
    public Double Latitude;
    public Double Longitude;
    public BigInteger TimeStamp;

    public TrackModel(){
        Latitude=0.0;
        Longitude=0.0;
        TimeStamp= BigInteger.valueOf(0);
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

    public BigInteger getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(BigInteger timeStamp) {
        TimeStamp = timeStamp;
    }
}
