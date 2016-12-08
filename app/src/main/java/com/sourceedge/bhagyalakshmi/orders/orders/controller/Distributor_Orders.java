package com.sourceedge.bhagyalakshmi.orders.orders.controller;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orders.view.Distributor_Orders_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Distributor_Orders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView distributorOrderRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_orders);
        Class_Genric.setOrientation(Distributor_Orders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.navigation_drawer);
        distributorOrderRecyclerview=(RecyclerView)findViewById(R.id.distributor_order_recyclerview);
        distributorOrderRecyclerview.setLayoutManager(new LinearLayoutManager(Distributor_Orders.this));
        distributorOrderRecyclerview.setAdapter(new Distributor_Orders_Adapter(Distributor_Orders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Distributor_Orders.this);
        Class_Genric.drawerOnClicks(Distributor_Orders.this);


    }

}
