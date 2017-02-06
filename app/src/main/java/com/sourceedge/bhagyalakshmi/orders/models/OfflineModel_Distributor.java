package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Centura on 28-01-2017.
 */

public class OfflineModel_Distributor {
    private String Id;
    private String Name;
    private BigInteger TimeStamp;
    public ArrayList<OffineModel_Category> categories;

    public OfflineModel_Distributor(){
        Id="";
        Name="";
        TimeStamp= BigInteger.valueOf(0);
        categories=new ArrayList<OffineModel_Category>();
    }

    public OfflineModel_Distributor(Role role) {
        Id=role.getId();
        Name=role.getName();
        TimeStamp= role.getTimeStamp();
        categories=new ArrayList<OffineModel_Category>();
    }

    public OfflineModel_Distributor(CurrentUser distributor) {
        Id=distributor.getId();
        Name=distributor.getName();
        categories=new ArrayList<OffineModel_Category>();
    }

    public ArrayList<OffineModel_Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<OffineModel_Category> categories) {
        this.categories = categories;
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
