package com.sourceedge.bhagyalakshmi.orders.models;

import java.sql.Timestamp;

/**
 * Created by Deekshith on 06-12-2016.
 */

public class Role {
    private String Id;
    private String Name;

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

    public Role()
    {
        Id="";
        Name="";

    }

}
