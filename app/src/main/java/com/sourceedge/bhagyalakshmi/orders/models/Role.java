package com.sourceedge.bhagyalakshmi.orders.models;

public class Role {
    private String id;
    private String name;
    private String timeStamp;

    public Role(){
        id="";
        name="";
        timeStamp="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
