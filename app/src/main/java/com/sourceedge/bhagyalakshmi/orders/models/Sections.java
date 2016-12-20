package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 20-12-2016.
 */

public class Sections {
    public String Id;
    public String Name;
    public String TimeStamp;

    public void Sections(){
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
