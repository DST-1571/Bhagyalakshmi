package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Role {
    private String Id;
    private String Name;
    private BigInteger TimeStamp;

    public Role(){
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
