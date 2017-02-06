package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;

/**
 * Created by Centura User1 on 20-12-2016.
 */

public class Sections {
    public String Id;
    public String Name;
    public BigInteger TimeStamp;

    public void Sections(){
        Id="";
        Name="";
        TimeStamp= BigInteger.valueOf(0);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public BigInteger getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(BigInteger timeStamp) {
        TimeStamp = timeStamp;
    }
}
