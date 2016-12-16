package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Admin_Orders_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Order_Page_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

public class Admin_Orders extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout orderedLayout,emptyOrders;
    RecyclerView orderPageRecyclerView;
    EditText orderSearch;
    static ArrayList<Order> LocalOrder=new ArrayList<Order>();



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
        orderSearch=(EditText)findViewById(R.id.order_search);
        orderPageRecyclerView=(RecyclerView)findViewById(R.id.active_order_page_recyclerView);
        orderPageRecyclerView.setLayoutManager(new LinearLayoutManager(Admin_Orders.this));
        Functionality(Admin_Orders.this);
        InitializeAdapter(Admin_Orders.this);
    }


    private void Functionality(final Context context) {
        orderSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().matches(""))
                {
                    orderPageRecyclerView.setAdapter(new Admin_Orders_Adapter(context, Class_ModelDB.getOrderList()));
                }
                else {
                    Class_Static.tempOrder=new ArrayList<Order>();
                    for (Order temporder:LocalOrder) {
                        Boolean matched=false;
                        if(temporder.getOrderNumber().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(temporder.getClient().getName().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(temporder.getUser().getName().toLowerCase().contains(s.toString().toLowerCase()))
                            matched=true;
                        if(matched)
                            Class_Static.tempOrder.add(temporder);
                    }
                    orderPageRecyclerView.setAdapter(new Admin_Orders_Adapter(context,Class_Static.tempOrder));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void InitializeAdapter(Context context) {
        LocalOrder= (ArrayList<Order>) Class_ModelDB.getOrderList().clone();
        if(Class_ModelDB.getOrderList().size()!=0){
            emptyOrders.setVisibility(View.GONE);
            orderedLayout.setVisibility(View.VISIBLE);
            orderPageRecyclerView.setVisibility(View.VISIBLE);
            orderPageRecyclerView.setAdapter(new Admin_Orders_Adapter(context, Class_ModelDB.getOrderList()));
        }else {
            orderPageRecyclerView.setVisibility(View.GONE);
            orderedLayout.setVisibility(View.GONE);
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
