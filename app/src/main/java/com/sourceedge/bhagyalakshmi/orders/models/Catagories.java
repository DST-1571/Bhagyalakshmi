package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;

/**
 * Created by Centura User1 on 20-12-2016.
 */

public class Catagories {
    public String Id;
    public String SectionId;
    public String Category;
    public BigInteger TimeStamp;

    public void Catagories(){
        Id="";
        SectionId="";
        Category="";
        TimeStamp= BigInteger.valueOf(0);
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
        return Category;
    }

    public void setName(String name) {
        Category = name;
    }

    public BigInteger getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(BigInteger timeStamp) {
        TimeStamp = timeStamp;
    }
}
