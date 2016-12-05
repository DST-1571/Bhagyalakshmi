package com.sourceedge.bhagyalakshmi.orders.dashboard.controller;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.view.mySales_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    ScrollView dashboard_scrollview;
    RecyclerView mySalesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Class_Genric.setOrientation(Dashboard.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BAGHYALAKSHMI TRADERS");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        dashboard_scrollview=(ScrollView)findViewById(R.id.dashboard_scrollview);
        mySalesRecycler= (RecyclerView) findViewById(R.id.my_sales_recycler);
        mySalesRecycler.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        mySalesRecycler.setAdapter(new mySales_Adapter());

        onClicks();
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Dashboard.this);
        Class_Genric.drawerOnClicks(Dashboard.this);
    }

    private void onClicks() {
        dashboard_scrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
