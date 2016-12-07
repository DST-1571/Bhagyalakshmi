package com.sourceedge.bhagyalakshmi.orders.changepassword;

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

public class ChangePassword extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Class_Genric.setOrientation(ChangePassword.this);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        Class_Genric.applyFontForToolbarTitle(toolbar,ChangePassword.this);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);

    }

}
