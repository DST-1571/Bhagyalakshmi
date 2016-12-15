package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Admin_Orders_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Order_Page_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;

public class Admin_Orders extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout orderedLayout,emptyOrders;
    RecyclerView orderPageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderedLayout=(LinearLayout)findViewById(R.id.active_ordered_layout);
        emptyOrders=(LinearLayout)findViewById(R.id.empty_active_orders);
        orderPageRecyclerView=(RecyclerView)findViewById(R.id.active_order_page_recyclerView);
        orderPageRecyclerView.setLayoutManager(new LinearLayoutManager(Admin_Orders.this));
        InitializeAdapter(Admin_Orders.this);
    }

    private void InitializeAdapter(Context context) {
        if(Class_ModelDB.getOrderList().size()!=0){
            emptyOrders.setVisibility(View.GONE);
            orderPageRecyclerView.setVisibility(View.VISIBLE);
            orderPageRecyclerView.setAdapter(new Admin_Orders_Adapter(context, Class_ModelDB.getOrderList()));
        }else {
            orderPageRecyclerView.setVisibility(View.GONE);
            emptyOrders.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
