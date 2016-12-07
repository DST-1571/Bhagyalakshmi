package com.sourceedge.bhagyalakshmi.orders.distributorsales.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class RetailerLookUp extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_look_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Class_Genric.setOrientation(RetailerLookUp.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        select=(Button)findViewById(R.id.select);
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,RetailerLookUp.this);
        Class_Genric.drawerOnClicks(RetailerLookUp.this);
        onClicks();


    }

    private void onClicks() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RetailerLookUp.this,DistributorSales.class));
            }
        });
    }

}
