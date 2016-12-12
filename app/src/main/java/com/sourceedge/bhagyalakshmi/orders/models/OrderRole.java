package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 10-12-2016.
 */

public class OrderRole {

    private String Id;
    private String Name;
    private String Address;

    public OrderRole(){
        Id="";
        Name="";
        Address="";
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
