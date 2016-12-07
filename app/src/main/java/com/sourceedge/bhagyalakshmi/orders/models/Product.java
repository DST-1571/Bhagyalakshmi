package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 07-12-2016.
 */

public class Product {

    private String Id;
    private String Name;
    private String Category;
    private String Brand;
    private String Unit;
    private String Price;
    private String Description;

    public Product(){
        Id="";
        Name="";
        Category="";
        Brand="";
        Unit="";
        Price="";
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

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
