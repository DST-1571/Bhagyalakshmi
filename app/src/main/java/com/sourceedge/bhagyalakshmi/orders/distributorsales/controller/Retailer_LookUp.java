package com.sourceedge.bhagyalakshmi.orders.distributorsales.controller;

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
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.view.Order_Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.view.Retailer_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;
import com.sourceedge.bhagyalakshmi.orders.salesperson.controller.Sales_Person;
import com.sourceedge.bhagyalakshmi.orders.salesperson.controller.Sales_Person_Lookup;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

public class Retailer_Lookup extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab;
    public static TextView distributorSalesManName,grandTotal;
    public static EditText retailerSearch;
    public static RecyclerView orderProductRecyclerview,retailerList;
    Button submitButton;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_look_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Class_Genric.setOrientation(Retailer_Lookup.this);

        fab=(FloatingActionButton)findViewById(R.id.fab);
        distributorSalesManName=(TextView)findViewById(R.id.user_name);
        grandTotal=(TextView)findViewById(R.id.total_amount);
        retailerSearch=(EditText)findViewById(R.id.retailer_search);
        orderProductRecyclerview=(RecyclerView)findViewById(R.id.order_product_recyclerview);
        retailerList=(RecyclerView)findViewById(R.id.retailer_list);
        retailerList.setLayoutManager(new LinearLayoutManager(Retailer_Lookup.this));
        submitButton=(Button)findViewById(R.id.submit_button);
        String s=Class_ModelDB.getCurrentuserModel().getName().toString();
        distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString()+" - Distributor(DSP)");

        onClicks();
        Functionalities(Retailer_Lookup.this);
        InitializeAdapter(Retailer_Lookup.this);


    }

    private void InitializeAdapter(Context context) {
        orderProductRecyclerview.setVisibility(View.VISIBLE);
        orderProductRecyclerview.setAdapter(new Order_Product_List_Adapter(context));
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
                    viewHeight = Class_Genric.convertDpToPixels(145, context);
                    viewHeight = viewHeight * ((Class_Static.tempRoleList.size()));
                    retailerList.getLayoutParams().height = viewHeight;
                    retailerList.setAdapter(new Retailer_List_Adapter(context,Class_Static.tempRoleList));
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
                    viewHeight = Class_Genric.convertDpToPixels(145, context);
                    viewHeight = viewHeight * ((Class_Static.tempRoleList.size()));
                    retailerList.getLayoutParams().height = viewHeight;
                    retailerList.setAdapter(new Retailer_List_Adapter(context,Class_Static.tempRoleList));
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

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Class_Genric.getType(Class_Genric.LoginType)){
                    case Class_Genric.ADMIN:
                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                        startActivity(new Intent(Retailer_Lookup.this,Distributor_Sales.class));
                        break;
                    case Class_Genric.DISTRIBUTOR:
                        break;
                    case Class_Genric.SALESPERSON:
                        startActivity(new Intent(Retailer_Lookup.this,Sales_Person.class));
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

}
