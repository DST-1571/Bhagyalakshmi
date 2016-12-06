package com.sourceedge.bhagyalakshmi.orders.orders.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.orders.view.AdminOrdersAdapter;
import com.sourceedge.bhagyalakshmi.orders.orders.view.DistributorOrdersAdapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class DistributorOrders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView distributorOrderRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_orders);
        Class_Genric.setOrientation(DistributorOrders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BAGHYALAKSHMI TRADERS");
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.navigation_drawer);
        distributorOrderRecyclerview=(RecyclerView)findViewById(R.id.distributor_order_recyclerview);
        distributorOrderRecyclerview.setLayoutManager(new LinearLayoutManager(DistributorOrders.this));
        distributorOrderRecyclerview.setAdapter(new DistributorOrdersAdapter(DistributorOrders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,DistributorOrders.this);
        Class_Genric.drawerOnClicks(DistributorOrders.this);


    }

}
