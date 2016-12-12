package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 07-12-2016.
 */

public class Product {

    private String Id;
    private String Name;
    private String Category;
    private String Brand;
    private String Units;
    private Double Price;
    private int Quantity;
    private Double Amount;
    private String Description;

    public Product(){
        Id="";
        Name="";
        Category="";
        Brand="";
        Units="";
        Price=0.0;
        Quantity=0;
        Amount=0.0;
        Description="";
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getUnits() {
        return Units;
    }

    public void setUnits(String units) {
        Units = units;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
