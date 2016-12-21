package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 07-12-2016.
 */

public class OrderProduct {

    private String Id;
    private String ProductId;
    private int Quantity;
    private Double Price;
    private String Unit;
   // private String SectionId;
    //private String CategoryId;
   // private String SectionName;
   // private String CatagoryName;
    //private String Description;
   // private String Code;

    public OrderProduct(){
        Id="";
        ProductId="";
        Quantity=0;
        Price=0.0;
        Unit="";
       // Description="";
       // SectionId="";
       // CategoryId="";
       // Code="";
      //  SectionName="";
      //  CatagoryName="";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }


}
