package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.controller.Retailer_LookUp;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Order_Page_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Order_Page extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    FloatingActionButton fab;
    RecyclerView orderPageRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        Class_Genric.setOrientation(Order_Page.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        orderPageRecyclerView= (RecyclerView) findViewById(R.id.order_page_recyclerView);
        orderPageRecyclerView.setLayoutManager(new LinearLayoutManager(Order_Page.this));
        orderPageRecyclerView.setAdapter(new Order_Page_Adapter(Order_Page.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Order_Page.this);
        Class_Genric.drawerOnClicks(Order_Page.this);
        onClicks();


    }

    private void onClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Order_Page.this, Retailer_LookUp.class));
            }
        });
    }

}
