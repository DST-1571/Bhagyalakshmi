package com.sourceedge.bhagyalakshmi.orders.distributorsales.controller;

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

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.view.Retailer_LookUp_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Retailer_LookUp extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    Button select;
    FloatingActionButton fab;
    RecyclerView retailerlookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_look_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Class_Genric.setOrientation(Retailer_LookUp.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        select=(Button)findViewById(R.id.select);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        retailerlookup=(RecyclerView) findViewById(R.id.retailer_lookup);
        retailerlookup.setLayoutManager(new LinearLayoutManager(Retailer_LookUp.this));
        retailerlookup.setAdapter(new Retailer_LookUp_Adapter(Retailer_LookUp.this));
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Retailer_LookUp.this);
        Class_Genric.drawerOnClicks(Retailer_LookUp.this);
        onClicks();


    }

    private void onClicks() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Retailer_LookUp.this,DistributorSales.class));
            }
        });
    }

}
