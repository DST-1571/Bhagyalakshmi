package com.sourceedge.bhagyalakshmi.orders.models;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Centura on 28-01-2017.
 */

public class OffineModel_Category {
    public String Id;
    public String SectionId;
    public String Category;
    public BigInteger TimeStamp;
    public ArrayList<Product> products;

    public void Catagories(){
        Id="";
        SectionId="";
        Category="";
        TimeStamp= BigInteger.valueOf(0);
        products= new ArrayList<Product>();
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String sectionId) {
        SectionId = sectionId;
    }

    public String getName() {
        return Category;
    }

    public void setName(String name) {
        Category = name;
    }

    public BigInteger getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(BigInteger timeStamp) {
        TimeStamp = timeStamp;
    }
}
