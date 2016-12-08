package com.sourceedge.bhagyalakshmi.orders.orders.controller;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orders.view.Sales_Person_Orders_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Sales_Person_Orders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView salesPersonOrdersRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person_orders);
        Class_Genric.setOrientation(Sales_Person_Orders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.navigation_drawer);
        salesPersonOrdersRecyclerview=(RecyclerView)findViewById(R.id.sales_person_orders_recyclerview);
        salesPersonOrdersRecyclerview.setLayoutManager(new LinearLayoutManager(Sales_Person_Orders.this));
        salesPersonOrdersRecyclerview.setAdapter(new Sales_Person_Orders_Adapter(Sales_Person_Orders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Sales_Person_Orders.this);
        Class_Genric.drawerOnClicks(Sales_Person_Orders.this);

    }

}
