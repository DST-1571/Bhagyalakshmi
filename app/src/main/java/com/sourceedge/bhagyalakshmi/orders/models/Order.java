package com.sourceedge.bhagyalakshmi.orders.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Centura User1 on 07-12-2016.
 */

public class Order {
    private String Id;
    private ArrayList<OrderProduct> Products;
    private String ClientId;
    private String UserId;
    private Double TotalAmount;
    private String Status;
    private String OrderNumber;
    private String TimeStamp;

    public Order(){
        Id="";
        Products=new ArrayList<OrderProduct>();
        ClientId="";
        UserId="";
        TotalAmount=0.0;
        Status="";
        OrderNumber="";
        TimeStamp="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<OrderProduct> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        Products = products;
    }

    public String getClientId() {
        return ClientId;
    }

    public void setClientId(String clientId) {
        ClientId = clientId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }
}
