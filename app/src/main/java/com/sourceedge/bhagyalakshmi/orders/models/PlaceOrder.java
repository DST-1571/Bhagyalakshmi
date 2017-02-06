package com.sourceedge.bhagyalakshmi.orders.models;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 09-12-2016.
 */

public class PlaceOrder {
    private String ClientId;
    private String UserId;
    private String OrderDate;
    private ArrayList<OrderProduct> Products;
    private Double TotalAmount;

    public PlaceOrder(){
        UserId="";
        ClientId="";
        Products=new ArrayList<OrderProduct>();
        TotalAmount=0.0;
        OrderDate="";
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

    public ArrayList<OrderProduct> getProducts() {
        return Products;
    }

    public void setProducts(ArrayList<OrderProduct> products) {
        Products = products;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }
}
