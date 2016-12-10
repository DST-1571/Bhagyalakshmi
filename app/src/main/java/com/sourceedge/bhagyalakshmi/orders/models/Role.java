package com.sourceedge.bhagyalakshmi.orders.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Role {
    private String Id;
    private String Name;
    private String TimeStamp;

    public Role(){
        Id="";
        Name="";
        TimeStamp="";
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

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
