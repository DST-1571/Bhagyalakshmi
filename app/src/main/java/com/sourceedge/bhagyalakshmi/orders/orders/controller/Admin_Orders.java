package com.sourceedge.bhagyalakshmi.orders.orders.controller;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orders.view.Admin_Orders_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Admin_Orders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView adminOrderRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        Class_Genric.setOrientation(Admin_Orders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        adminOrderRecyclerview=(RecyclerView)findViewById(R.id.admin_order_recyclerview);
        adminOrderRecyclerview.setLayoutManager(new LinearLayoutManager(Admin_Orders.this));
        adminOrderRecyclerview.setAdapter(new Admin_Orders_Adapter(Admin_Orders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Admin_Orders.this);
        Class_Genric.drawerOnClicks(Admin_Orders.this);

    }

}
