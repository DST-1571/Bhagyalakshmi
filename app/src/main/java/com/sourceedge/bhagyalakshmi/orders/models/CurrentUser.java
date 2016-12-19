package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class CurrentUser {
    private String Id;
    private String Name;
    private String UserType;
    private String Token;
    private String ACL;

    public CurrentUser(){
        Id="";
        Name="";
        UserType="";
        Token="";
        ACL="";
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

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getACL() {
        return ACL;
    }

    public void setACL(String ACL) {
        this.ACL = ACL;
    }
}
