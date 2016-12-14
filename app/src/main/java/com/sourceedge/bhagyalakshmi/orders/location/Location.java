package com.sourceedge.bhagyalakshmi.orders.location;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Location extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Class_Genric.setOrientation(Location.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Location.this);
        Class_Genric.drawerOnClicks(Location.this);

    }

}