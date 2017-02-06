package com.sourceedge.bhagyalakshmi.orders.support;

import com.sourceedge.bhagyalakshmi.orders.location.Tracker;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.TrackModel;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by Centura User1 on 10-12-2016.
 */

public class Class_Static {

    public static int DASHBOARD=1;
    public static int ORDERS=2;
    public static int DISTRIBUTORLIST=3;
    public static int ADDPRODUCT=4;
    public static int CATAGORIESLIST=5;
    public static int PRODUCTSLIST=6;
    public static int ORDERPRODUCTSLIST=7;
    public static int ORDERPLACED=8;
    public static int CURRENTPAGE=-1;

    public static int Stock=0;
    public static int AvailableStock=0;
    public static ArrayList<BigInteger> timestamplist=new ArrayList<BigInteger>();
    public static boolean editProductOrder=false;
    public static boolean viewOrderedProducts=false;
    public static boolean home=false;
    public static int Flag_SEARCH=-1;
    public static ArrayList<Role> tempRoleList=new ArrayList<Role>();
    public static Role tempRole=new Role();
    public static ArrayList<Product> tempProductList=new ArrayList<Product>();
    public static ArrayList<Sections> tempGroupList=new ArrayList<Sections>();
    public static ArrayList<Catagories> tempCategotyList=new ArrayList<Catagories>();
    public static Product tempProduct=new Product();
    public static ArrayList<Product> tempOrderingProduct=new ArrayList<Product>();
    public static ArrayList<Order> tempOrder=new ArrayList<Order>();
    public static ArrayList<Role> tempSalesPersonList=new ArrayList<Role>();
    public static ArrayList<TrackModel> tempLocation= new ArrayList<TrackModel>();

    public static Order OrdredProducts=new Order();
    public static OrderProduct Ordered=new OrderProduct();
    public static Tracker tracker= new Tracker();


    public static int SEARCHPRODUCT=1;
    public static int SEARCHGROUP=2;
    public static int SEARCHCATAGORY=3;
    public static int SEARCHCUSTOMER=4;
    public static int SEARCHSALESPERSON=5;



    public static void ClearStaticData(){
        timestamplist=new ArrayList<BigInteger>();
        editProductOrder=false;
        home=false;
        tempRoleList=new ArrayList<Role>();
        tempRole=new Role();
        tempProductList=new ArrayList<Product>();
        tempProduct=new Product();
        tempOrderingProduct=new ArrayList<Product>();
        OrdredProducts=new Order();
        tempCategotyList=new ArrayList<Catagories>();
        tempOrder=new ArrayList<Order>();
        tempGroupList=new ArrayList<Sections>();
        tempRoleList=new ArrayList<Role>();
    }

    public static void page(int page) {
        CURRENTPAGE=-page;
    }
}
