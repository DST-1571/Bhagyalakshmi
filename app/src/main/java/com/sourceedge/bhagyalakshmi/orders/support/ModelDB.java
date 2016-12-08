package com.sourceedge.bhagyalakshmi.orders.support;

import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.User;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class ModelDB {

    private static Order orderModel=new Order();
    private static ArrayList<Order> orderList=new ArrayList<Order>();

    private static OrderProduct orderProductModel=new OrderProduct();
    private static ArrayList<OrderProduct> orderProductList=new ArrayList<OrderProduct>();

    private static Product productModel=new Product();
    private static ArrayList<Product> productList=new ArrayList<Product>();

    private static Role roleModel=new Role();
    private static ArrayList<Role> roleList=new ArrayList<Role>();

    private static User userModel=new User();
    private static ArrayList<User> userList=new ArrayList<User>();




    //Getter and Setter of all above models

    public static Order getOrderModel() {
        return orderModel;
    }

    public static void setOrderModel(Order orderModel) {
        ModelDB.orderModel = orderModel;
    }

    public static ArrayList<Order> getOrderList() {
        return orderList;
    }

    public static void setOrderList(ArrayList<Order> orderList) {
        ModelDB.orderList = orderList;
    }

    public static OrderProduct getOrderProductModel() {
        return orderProductModel;
    }

    public static void setOrderProductModel(OrderProduct orderProductModel) {
        ModelDB.orderProductModel = orderProductModel;
    }

    public static ArrayList<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public static void setOrderProductList(ArrayList<OrderProduct> orderProductList) {
        ModelDB.orderProductList = orderProductList;
    }

    public static Product getProductModel() {
        return productModel;
    }

    public static void setProductModel(Product productModel) {
        ModelDB.productModel = productModel;
    }

    public static ArrayList<Product> getProductList() {
        return productList;
    }

    public static void setProductList(ArrayList<Product> productList) {
        ModelDB.productList = productList;
    }

    public static Role getRoleModel() {
        return roleModel;
    }

    public static void setRoleModel(Role roleModel) {
        ModelDB.roleModel = roleModel;
    }

    public static ArrayList<Role> getRoleList() {
        return roleList;
    }

    public static void setRoleList(ArrayList<Role> roleList) {
        ModelDB.roleList = roleList;
    }

    public static User getUserModel() {
        return userModel;
    }

    public static void setUserModel(User userModel) {
        ModelDB.userModel = userModel;
    }

    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        ModelDB.userList = userList;
    }

    public static void ClearDB(){
        orderModel=new Order();
        orderList=new ArrayList<Order>();

        orderProductModel=new OrderProduct();
        orderProductList=new ArrayList<OrderProduct>();

        productModel=new Product();
        productList=new ArrayList<Product>();

        roleModel=new Role();
        roleList=new ArrayList<Role>();

        userModel=new User();
        userList=new ArrayList<User>();
    }
}
