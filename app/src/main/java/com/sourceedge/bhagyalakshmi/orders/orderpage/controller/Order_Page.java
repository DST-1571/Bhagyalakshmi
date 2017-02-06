package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.OfflineModel_Distributor;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Order_Page_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Search_Customer;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;

public class Order_Page extends AppCompatActivity {
    Toolbar toolbar;
    // DrawerLayout drawer;
    //ActionBarDrawerToggle mDrawerToggle;
    FloatingActionButton fab;
    static RecyclerView orderPageRecyclerView;
    static LinearLayout orderedLayout, emptyOrders;
    EditText orderSearch;
    static ArrayList<Order> LocalOrder = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        Class_Genric.setOrientation(Order_Page.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Class_ModelDB.AppTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        orderPageRecyclerView = (RecyclerView) findViewById(R.id.order_page_recyclerView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        orderedLayout = (LinearLayout) findViewById(R.id.ordered_layout);
        emptyOrders = (LinearLayout) findViewById(R.id.empty_orders);

        orderPageRecyclerView.setLayoutManager(new LinearLayoutManager(Order_Page.this));
        orderSearch = (EditText) findViewById(R.id.order_search);
       /* Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, Order_Page.this);
        Class_Genric.drawerOnClicks(Order_Page.this);*/
        onClicks();
        InitializeAdapter(Order_Page.this);
        Functionality(Order_Page.this);
    }

    private void Functionality(final Context context) {
        orderSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    orderPageRecyclerView.setAdapter(new Order_Page_Adapter(context, Class_ModelDB.getOrderList()));
                } else {
                    Class_Static.tempOrder = new ArrayList<Order>();
                    for (Order temporder : LocalOrder) {
                        Boolean matched = false;
                        if (temporder.getOrderNumber().toLowerCase().contains(s.toString().toLowerCase()))
                            matched = true;
                        if (temporder.getClient().getName().toLowerCase().contains(s.toString().toLowerCase()))
                            matched = true;
                        if (matched)
                            Class_Static.tempOrder.add(temporder);
                    }
                    orderPageRecyclerView.setAdapter(new Order_Page_Adapter(context, Class_Static.tempOrder));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                    case Class_Genric.ADMIN:
                        break;

                    case Class_Genric.DISTRIBUTOR:
                        if (Class_Genric.NetAvailable(Order_Page.this)) {
                            Class_Static.CURRENTPAGE = Class_Static.ORDERS;
                            if (Class_ModelDB.getCatagoryList().size() == 0)
                                Class_SyncApi.CatagoryApi(Order_Page.this, Class_ModelDB.getCurrentuserModel().getId());
                            else {
                                Class_Static.viewOrderedProducts = false;
                                Class_Static.tempOrderingProduct = new ArrayList<Product>();
                                Class_Static.editProductOrder = false;
                                startActivity(new Intent(Order_Page.this, Add_Product.class));
                            }
                        } else {
                            Class_SyncApi.LoadOfflineCatagories(Order_Page.this, Class_ModelDB.getCurrentuserModel().getId());
                            Class_Static.viewOrderedProducts = false;
                            Class_Static.tempOrderingProduct = new ArrayList<Product>();
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Order_Page.this, Add_Product.class));
                        }

                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                    case Class_Genric.SALESMAN:
                        if (Class_Genric.NetAvailable(Order_Page.this)) {
                            if (Class_ModelDB.getRoleList().size() == 0)
                                Class_SyncApi.DistributorApi(Order_Page.this);
                            else {
                                Class_Static.viewOrderedProducts = false;
                                Class_Static.tempOrderingProduct = new ArrayList<Product>();
                                Class_Static.Flag_SEARCH = Class_Static.SEARCHCUSTOMER;
                                startActivity(new Intent(Order_Page.this, Search_Customer.class));
                            }
                        } else {
                            Class_SyncApi.LoadOfflineDistributors(Order_Page.this);
                            Class_Static.viewOrderedProducts = false;
                            Class_Static.tempOrderingProduct = new ArrayList<Product>();
                            Class_Static.Flag_SEARCH = Class_Static.SEARCHCUSTOMER;
                            startActivity(new Intent(Order_Page.this, Search_Customer.class));
                        }

                        break;
                }
            }
        });

    }

    public static void InitializeAdapter(Context context) {
        LocalOrder = (ArrayList<Order>) Class_ModelDB.getOrderList().clone();
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
