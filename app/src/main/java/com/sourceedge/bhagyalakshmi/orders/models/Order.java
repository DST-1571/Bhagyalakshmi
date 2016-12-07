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
    private Double Quantity;
    private Double Price;
    private Double TotalAmount;
    private String Unit;
    private String Status;
    private String OrderNumber;
    private Date TimeStamp;

    public Order(){
        Id="";
        Products=new ArrayList<OrderProduct>();
        ClientId="";
        UserId="";
        Quantity=0.0;
        Price=0.0;
        TotalAmount=0.0;
        Unit="";
        Status="";
        OrderNumber="";
        TimeStamp=new Date();
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

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public Double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
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

    public Date getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        TimeStamp = timeStamp;
    }
}
