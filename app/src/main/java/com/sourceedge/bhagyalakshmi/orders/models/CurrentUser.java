package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class CurrentUser {
    private String id;
    private String name;
    private String userType;
    private String token;
    private String acl;

    public CurrentUser(){
        id="";
        name="";
        userType="";
        token="";
        acl="";
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAcl() {
        return acl;
    }

    public void setAcl(String acl) {
        this.acl = acl;
    }
}
