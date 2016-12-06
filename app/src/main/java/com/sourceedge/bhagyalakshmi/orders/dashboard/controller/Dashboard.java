package com.sourceedge.bhagyalakshmi.orders.dashboard.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.view.mySales_Adapter;
import com.sourceedge.bhagyalakshmi.orders.location.controller.Location;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.AdminOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.DistributorOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.DistributorSalesOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.SalesPersonOrders;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    ScrollView dashboard_scrollview;
    CardView total_order,admin_retailers_distributors,distributor_order_details,my_sales_orders,statistics;
    RecyclerView mySalesRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Class_Genric.setOrientation(Dashboard.this);
        sharedPreferences = getSharedPreferences(Class_Genric.MyPref, Dashboard.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("BAGHYALAKSHMI TRADERS");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        dashboard_scrollview=(ScrollView)findViewById(R.id.dashboard_scrollview);

        total_order=(CardView)findViewById(R.id.total_order);   //
        admin_retailers_distributors=(CardView)findViewById(R.id.admin_retailers_distributors);
        distributor_order_details=(CardView)findViewById(R.id.distributor_order_details);
        my_sales_orders=(CardView)findViewById(R.id.my_sales_orders);
        statistics=(CardView)findViewById(R.id.statistics);//

        mySalesRecycler= (RecyclerView) findViewById(R.id.my_sales_recycler);
        mySalesRecycler.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        mySalesRecycler.setAdapter(new mySales_Adapter());
        onClicks();
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Dashboard.this);
        Class_Genric.drawerOnClicks(Dashboard.this);

        switch (Class_Genric.getType(sharedPreferences.getString(Class_Genric.Sp_LoginType, ""))) {
            case Class_Genric.ADMIN:
                my_sales_orders.setVisibility(View.GONE);
                distributor_order_details.setVisibility(View.GONE);
                break;
            case Class_Genric.DISTRIBUTORSALES:
                admin_retailers_distributors.setVisibility(View.GONE);
                my_sales_orders.setVisibility(View.GONE);
                break;
            case Class_Genric.DISTRIBUTOR:
                admin_retailers_distributors.setVisibility(View.GONE);
                distributor_order_details.setVisibility(View.GONE);
                my_sales_orders.setVisibility(View.GONE);
                break;
            case Class_Genric.SALESPERSON:
                admin_retailers_distributors.setVisibility(View.GONE);
                distributor_order_details.setVisibility(View.GONE);
                break;
        }

    }

    private void onClicks() {
        dashboard_scrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
