package com.sourceedge.bhagyalakshmi.orders.models;

import java.sql.Timestamp;

public class Role {
    private String Id;
    private String Name;

    public Role(){
        Id="";
        Name="";
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

}
