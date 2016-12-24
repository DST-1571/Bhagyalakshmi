package com.sourceedge.bhagyalakshmi.orders.support;

import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.models.User;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_ModelDB {
    private static int APPVERSION = 0;
    public static String AppTitle="Bhagyalakshmi Enterprises";
    private static Order orderModel = new Order();
    private static ArrayList<Order> orderList = new ArrayList<Order>();
    public static ArrayList<Order> DraftorderList = new ArrayList<Order>();
    private static ArrayList<Catagories> catagoryList = new ArrayList<Catagories>();

    private static Role roleModel = new Role();
    private static ArrayList<Role> roleList = new ArrayList<Role>();

    private static User userModel = new User();
    private static ArrayList<User> userList = new ArrayList<User>();

    private static CurrentUser currentuserModel = new CurrentUser();

    private static OrderProduct orderProductModel = new OrderProduct();
    private static ArrayList<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

    private static Product productModel = new Product();
    private static ArrayList<Product> productList = new ArrayList<Product>();

    private static Sections sectionsModel = new Sections();
    private static ArrayList<Sections> sectionList = new ArrayList<Sections>();

    private static Catagories CatagoryModel = new Catagories();


    public static void ClearDB() {
        orderModel = new Order();
        orderList = new ArrayList<Order>();
        DraftorderList = new ArrayList<Order>();
        orderProductModel = new OrderProduct();
        orderProductList = new ArrayList<OrderProduct>();
        catagoryList = new ArrayList<Catagories>();
        productModel = new Product();
        productList = new ArrayList<Product>();
        currentuserModel = new CurrentUser();
        roleModel = new Role();
        roleList = new ArrayList<Role>();
        sectionsModel = new Sections();
        userModel = new User();
        userList = new ArrayList<User>();
        sectionList = new ArrayList<Sections>();
    }


    //Getter and Setter of all above models

    public static Sections getSectionsModel() {
        return sectionsModel;
    }

    public static void setSectionsModel(Sections sectionsModel) {
        Class_ModelDB.sectionsModel = sectionsModel;
    }

    public static ArrayList<Sections> getSectionList() {
        return sectionList;
    }

    public static void setSectionList(ArrayList<Sections> sectionList) {
        Class_ModelDB.sectionList = sectionList;
    }

    public static Catagories getCatagoryModel() {
        return CatagoryModel;
    }

    public static void setCatagoryModel(Catagories catagoryModel) {
        CatagoryModel = catagoryModel;
    }

    public static ArrayList<Catagories> getCatagoryList() {
        return catagoryList;
    }

    public static void setCatagoryList(ArrayList<Catagories> catagoryList) {
        Class_ModelDB.catagoryList = catagoryList;
    }

    public static Order getOrderModel() {
        return orderModel;
    }

    public static void setOrderModel(Order orderModel) {
        Class_ModelDB.orderModel = orderModel;
    }

    public static ArrayList<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(ArrayList<Order> orderList) {
        Class_ModelDB.orderList = orderList;
    }

    public static OrderProduct getOrderProductModel() {
        return orderProductModel;
    }

    public static void setOrderProductModel(OrderProduct orderProductModel) {
        Class_ModelDB.orderProductModel = orderProductModel;
    }

    public static ArrayList<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public static void setOrderProductList(ArrayList<OrderProduct> orderProductList) {
        Class_ModelDB.orderProductList = orderProductList;
    }

    public static Product getProductModel() {
        return productModel;
    }

    public static void setProductModel(Product productModel) {
        Class_ModelDB.productModel = productModel;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static void setProductList(ArrayList<Product> productList) {
        Class_ModelDB.productList = productList;
    }

    public static Role getRoleModel() {
        return roleModel;
    }

    public static void setRoleModel(Role roleModel) {
        Class_ModelDB.roleModel = roleModel;
    }

    public static ArrayList<Role> getRoleList() {
        return roleList;
    }

    public static void setRoleList(ArrayList<Role> roleList) {
        Class_ModelDB.roleList = roleList;
    }

    public static User getUserModel() {
        return userModel;
    }

    public static void setUserModel(User userModel) {
        Class_ModelDB.userModel = userModel;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        Class_ModelDB.userList = userList;
    }

    public static CurrentUser getCurrentuserModel() {
        return currentuserModel;
    }

    public static void setCurrentuserModel(CurrentUser currentuserModel) {
        Class_ModelDB.currentuserModel = currentuserModel;
    }


}
