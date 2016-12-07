package com.sourceedge.bhagyalakshmi.orders.salesperson.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class SalesPerson extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_person);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        Class_Genric.setOrientation(SalesPerson.this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,SalesPerson.this);
        Class_Genric.drawerOnClicks(SalesPerson.this);

    }

}
