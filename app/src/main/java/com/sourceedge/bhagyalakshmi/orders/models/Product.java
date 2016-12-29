package com.sourceedge.bhagyalakshmi.orders.models;

/**
 * Created by Centura User1 on 07-12-2016.
 */

public class Product {

    private String Id;
    private String SectionId;
    private String CategoryId;
    private String SectionName;
    private String CatagoryName;
    private String Units;
    private Double Price;
    private int Quantity;
    private int Stock;
    private Double Amount;
    private String Description;
    private String Code;

    public Product(){
        Id="";
        Units="";
        Price=0.0;
        Quantity=0;
        Stock=0;
        Amount=0.0;
        Description="";
        SectionId="";
        CategoryId="";
        Code="";
        SectionName="";
        CatagoryName="";
    }

    public void setProductDetais(Product tempPtoduct){
        Id=tempPtoduct.getId();
        Units=tempPtoduct.getUnits();
        Price=tempPtoduct.getPrice();
        Quantity=tempPtoduct.getQuantity();
        Stock=tempPtoduct.getStock();
        Amount=tempPtoduct.getAmount();
        Description=tempPtoduct.getDescription();
        SectionId=tempPtoduct.getSectionId();
        CategoryId=tempPtoduct.getCategoryId();
        Code=tempPtoduct.getCode();
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String sectionId) {
        SectionId = sectionId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    public String getCatagoryName() {
        return CatagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        CatagoryName = catagoryName;
    }

    public Product(Product tempProduct) {
        Id=tempProduct.getId();
        Units=tempProduct.getUnits();
        Price=tempProduct.getPrice();
        Quantity=tempProduct.getQuantity();
        Stock=tempProduct.getStock();
        Amount=tempProduct.getAmount();
        Description=tempProduct.getDescription();
        CatagoryName= tempProduct.getCatagoryName();
        CategoryId= tempProduct.getCategoryId();
        SectionName= tempProduct.getSectionName();
        SectionId= tempProduct.getSectionId();
        Code=tempProduct.getCode();
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }
}
