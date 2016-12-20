package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 20-12-2016.
 */

public class Catagories {
    public String Id;
    public String SectionId;
    public String Name;
    public String TimeStamp;

    public void Catagories(){
        Id="";
        SectionId="";
        Name="";
        TimeStamp="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String sectionId) {
        SectionId = sectionId;
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
