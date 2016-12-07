package com.sourceedge.bhagyalakshmi.orders.orders.controller;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.orders.view.DistributorSalesOrdersAdapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class DistributorSalesOrders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView distributorSalesOrdersRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_sales_orders);
        Class_Genric.setOrientation(DistributorSalesOrders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.navigation_drawer);
        distributorSalesOrdersRecyclerview=(RecyclerView)findViewById(R.id.distributor_sales_orders_recyclerview);
        distributorSalesOrdersRecyclerview.setLayoutManager(new LinearLayoutManager(DistributorSalesOrders.this));
        distributorSalesOrdersRecyclerview.setAdapter(new DistributorSalesOrdersAdapter(DistributorSalesOrders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,DistributorSalesOrders.this);
        Class_Genric.drawerOnClicks(DistributorSalesOrders.this);


    }

}
