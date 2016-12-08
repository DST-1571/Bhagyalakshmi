package com.sourceedge.bhagyalakshmi.orders.changepassword;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

public class Change_Password extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Class_Genric.setOrientation(Change_Password.this);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        Class_Genric.applyFontForToolbarTitle(toolbar,Change_Password.this);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar,drawer,mDrawerToggle,Change_Password.this);
        Class_Genric.drawerOnClicks(Change_Password.this);

    }

}
