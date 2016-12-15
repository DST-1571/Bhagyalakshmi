package com.sourceedge.bhagyalakshmi.orders.orderproduct.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Order_Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Role_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.View_Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;

/**
 * Created by Centura User1 on 13-12-2016.
 */

public class Product_Order_Lookup extends AppCompatActivity {
    Toolbar toolbar;
    public static FloatingActionButton fab;
    public static TextView distributorSalesManName, grandTotal, action_text_pane;
    public static EditText retailerSearch;
    public static RecyclerView orderProductRecyclerview, retailerList;
    public static LinearLayout orderProductListLayout, searchPane, emptyProducts;
    public static LinearLayout scrollView, order_header2, order_header1;
    public static Button submitButton;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_look_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Class_Genric.setOrientation(Product_Order_Lookup.this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        distributorSalesManName = (TextView) findViewById(R.id.user_name);
        grandTotal = (TextView) findViewById(R.id.grand_total_amount123);
        action_text_pane = (TextView) findViewById(R.id.action_text_pane);
        retailerSearch = (EditText) findViewById(R.id.retailer_search);
        orderProductListLayout = (LinearLayout) findViewById(R.id.order_product_list_layout);
        orderProductRecyclerview = (RecyclerView) findViewById(R.id.order_product_recyclerview);
        retailerList = (RecyclerView) findViewById(R.id.retailer_list);
        orderProductRecyclerview.setLayoutManager(new LinearLayoutManager(Product_Order_Lookup.this));
        retailerList.setLayoutManager(new LinearLayoutManager(Product_Order_Lookup.this));
        submitButton = (Button) findViewById(R.id.submit_button);
        searchPane = (LinearLayout) findViewById(R.id.search_pane);
        emptyProducts = (LinearLayout) findViewById(R.id.empty_products);
        order_header1 = (LinearLayout) findViewById(R.id.order_header1);
        order_header2 = (LinearLayout) findViewById(R.id.order_header2);
        scrollView = (LinearLayout) findViewById(R.id.orderitemlist);
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
                searchPane.setVisibility(View.VISIBLE);
                distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Distributor(DSP)");
                break;
            case Class_Genric.DISTRIBUTOR:
                searchPane.setVisibility(View.GONE);
                distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Distributor");
                break;
            case Class_Genric.SALESPERSON:
                searchPane.setVisibility(View.VISIBLE);
                retailerSearch.setHint("Select Distributor");
                distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Sales(SBL)");
                break;
        }

        onClicks();
        Functionalities(Product_Order_Lookup.this);
        InitializeAdapter(Product_Order_Lookup.this);
    }

    public static void InitializeAdapter(Context context) {
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
            case Class_Genric.SALESPERSON:
                if (Class_Static.tempOrderingProduct.size() != 0) {
                    orderProductListLayout.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    if (Class_Static.viewOrderedProducts) {
                        fab.setVisibility(View.GONE);
                        searchPane.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        action_text_pane.setVisibility(View.GONE);
                        order_header1.setVisibility(View.GONE);
                        order_header2.setVisibility(View.VISIBLE);
                        orderProductRecyclerview.setAdapter(new View_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    } else {
                        fab.setVisibility(View.VISIBLE);
                        searchPane.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.VISIBLE);
                        action_text_pane.setVisibility(View.VISIBLE);
                        order_header1.setVisibility(View.VISIBLE);
                        order_header2.setVisibility(View.GONE);
                        orderProductRecyclerview.setAdapter(new Order_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    }
                } else {
                    orderProductListLayout.setVisibility(View.GONE);
                    submitButton.setVisibility(View.GONE);
                }


                break;
            case Class_Genric.DISTRIBUTOR:
                if (Class_Static.tempOrderingProduct.size() != 0) {
                    scrollView.setVisibility(View.VISIBLE);
                    orderProductListLayout.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    emptyProducts.setVisibility(View.GONE);
                    if (Class_Static.viewOrderedProducts) {
                        fab.setVisibility(View.GONE);
                        searchPane.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        action_text_pane.setVisibility(View.GONE);
                        order_header1.setVisibility(View.GONE);
                        order_header2.setVisibility(View.VISIBLE);
                        orderProductRecyclerview.setAdapter(new View_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    } else {
                        fab.setVisibility(View.VISIBLE);
                        searchPane.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.VISIBLE);
                        action_text_pane.setVisibility(View.VISIBLE);
                        order_header1.setVisibility(View.VISIBLE);
                        order_header2.setVisibility(View.GONE);
                        orderProductRecyclerview.setAdapter(new Order_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    }
                } else {
                    scrollView.setVisibility(View.GONE);
                    emptyProducts.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    private void Functionalities(final Context context) {
        retailerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    retailerList.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.GONE);
                    Class_Static.tempRoleList = new ArrayList<Role>();
                    viewHeight = Class_Genric.convertDpToPixels(55, context);
                    viewHeight = viewHeight * ((Class_Static.tempRoleList.size()));
                    retailerList.getLayoutParams().height = viewHeight;
                    retailerList.setAdapter(new Role_List_Adapter(context, Class_Static.tempRoleList));
                } else {
                    retailerList.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.GONE);
                    Class_Static.tempRoleList = new ArrayList<Role>();
                    if (!Class_Static.tempRole.getName().toLowerCase().matches(s.toString().toLowerCase()))
                        for (Role role : Class_ModelDB.getRoleList()) {
                            if (role.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                Class_Static.tempRoleList.add(role);
                            }
                        }
                    viewHeight = Class_Genric.convertDpToPixels(55, context);
                    viewHeight = viewHeight * ((Class_Static.tempRoleList.size()));
                    retailerList.getLayoutParams().height = viewHeight;
                    retailerList.setAdapter(new Role_List_Adapter(context, Class_Static.tempRoleList));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void onClicks() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
                    case Class_Genric.ADMIN:
                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                    case Class_Genric.SALESPERSON:
                        Class_SyncApi.PlaceOrderApi(Product_Order_Lookup.this, Class_ModelDB.getCurrentuserModel().getId(), Class_Static.tempRole.getId(), Class_Static.tempOrderingProduct, grandTotal.getText().toString());
                        break;
                    case Class_Genric.DISTRIBUTOR:
                        Class_SyncApi.PlaceOrderApi(Product_Order_Lookup.this, Class_ModelDB.getCurrentuserModel().getId(), Class_ModelDB.getCurrentuserModel().getId(), Class_Static.tempOrderingProduct, grandTotal.getText().toString());
                        break;
                }


            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
                    case Class_Genric.ADMIN:
                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                        if (retailerSearch.getText().toString().isEmpty() || retailerSearch.getText().toString().length() == 0 || retailerSearch.getText().toString().equals("") || retailerSearch.getText().toString() == null) {
                            Toast.makeText(Product_Order_Lookup.this, "Please Select Retailer", Toast.LENGTH_SHORT).show();
                        } else {
                            retailerSearch.setEnabled(false);
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                        }
                        break;
                    case Class_Genric.DISTRIBUTOR:
                        Class_Static.editProductOrder = false;
                        startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                        break;
                    case Class_Genric.SALESPERSON:
                        if (retailerSearch.getText().toString().isEmpty() || retailerSearch.getText().toString().length() == 0 || retailerSearch.getText().toString().equals("") || retailerSearch.getText().toString() == null) {
                            Toast.makeText(Product_Order_Lookup.this, "Please Select Distributor", Toast.LENGTH_SHORT).show();
                        } else {
                            retailerSearch.setEnabled(false);
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                        }
                        break;
                }
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        InitializeAdapter(Product_Order_Lookup.this);
    }
}

