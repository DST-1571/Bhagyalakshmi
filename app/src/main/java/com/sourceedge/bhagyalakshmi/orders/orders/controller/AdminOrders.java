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
import com.sourceedge.bhagyalakshmi.orders.orders.view.AdminOrdersAdapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class AdminOrders extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView adminOrderRecyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        Class_Genric.setOrientation(AdminOrders.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BAGHYALAKSHMI TRADERS");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        adminOrderRecyclerview=(RecyclerView)findViewById(R.id.admin_order_recyclerview);
        adminOrderRecyclerview.setLayoutManager(new LinearLayoutManager(AdminOrders.this));
        adminOrderRecyclerview.setAdapter(new AdminOrdersAdapter(AdminOrders.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,AdminOrders.this);
        Class_Genric.drawerOnClicks(AdminOrders.this);

    }

}
