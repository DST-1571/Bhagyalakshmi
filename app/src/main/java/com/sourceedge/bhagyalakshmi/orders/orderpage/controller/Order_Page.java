package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.content.Context;
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
import android.widget.Button;
import android.widget.LinearLayout;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.controller.Retailer_Lookup;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Order_Page_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

public class Order_Page extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    FloatingActionButton fab;
    static RecyclerView orderPageRecyclerView;
    static LinearLayout orderedLayout, emptyOrders;
    Button orderNow;

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
        orderPageRecyclerView = (RecyclerView) findViewById(R.id.order_page_recyclerView);
        orderedLayout = (LinearLayout) findViewById(R.id.ordered_layout);
        emptyOrders = (LinearLayout) findViewById(R.id.empty_orders);
        orderNow = (Button) findViewById(R.id.order_now);
        orderPageRecyclerView.setLayoutManager(new LinearLayoutManager(Order_Page.this));
        Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, Order_Page.this);
        Class_Genric.drawerOnClicks(Order_Page.this);
        onClicks();
        InitializeAdapter(Order_Page.this);


    }

    private void onClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
                    case Class_Genric.ADMIN:
                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                    case Class_Genric.DISTRIBUTOR:
                    case Class_Genric.SALESPERSON:
                        Class_Static.tempOrderingProduct = new ArrayList<Product>();
                        startActivity(new Intent(Order_Page.this, Retailer_Lookup.class));
                        break;
                }
            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class_Static.tempOrderingProduct = new ArrayList<Product>();
                startActivity(new Intent(Order_Page.this, Retailer_Lookup.class));
            }
        });


    }

    public static void InitializeAdapter(Context context) {
        if (Class_ModelDB.getOrderList().size() != 0) {
            orderedLayout.setVisibility(View.VISIBLE);
            emptyOrders.setVisibility(View.GONE);
            orderPageRecyclerView.setAdapter(new Order_Page_Adapter(context, Class_ModelDB.getOrderList()));
        } else {
            orderedLayout.setVisibility(View.GONE);
            emptyOrders.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        InitializeAdapter(Order_Page.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Class_Static.home=true;
        finish();
    }
}
