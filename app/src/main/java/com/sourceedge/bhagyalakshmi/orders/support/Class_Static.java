package com.sourceedge.bhagyalakshmi.orders.support;

import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Centura User1 on 10-12-2016.
 */

public class Class_Static {

    public static String timestamp="";
    public static ArrayList<Date> timestamplist=new ArrayList<Date>();
    public static boolean editProductOrder=false;
    public static boolean home=false;
    public static ArrayList<Role> tempRoleList=new ArrayList<Role>();
    public static Role tempRole=new Role();
    public static ArrayList<Product> tempProductList=new ArrayList<Product>();
    public static Product tempProduct=new Product();
    public static ArrayList<Product> tempOrderingProduct=new ArrayList<Product>();
    public static ArrayList<Order> tempOrder=new ArrayList<Order>();

    public static void ClearStaticData(){
        timestamp="";
        timestamplist=new ArrayList<Date>();
        editProductOrder=false;
        home=false;
        tempRoleList=new ArrayList<Role>();
        tempRole=new Role();
        tempProductList=new ArrayList<Product>();
        tempProduct=new Product();
        tempOrderingProduct=new ArrayList<Product>();
    }
}
