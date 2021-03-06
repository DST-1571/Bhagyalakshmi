package com.sourceedge.bhagyalakshmi.orders.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Application;
import com.sourceedge.bhagyalakshmi.orders.support.Class_DBHelper;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

public class Splash extends AppCompatActivity {
    static ProgressBar progressBar;
    static SharedPreferences sharedPreferences;
    public static final int SPLASH_DISPLAY_LENGTH = 1000;
    Class_DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Class_Genric.setOrientation(Splash.this);
        dbHelper = new Class_DBHelper(Splash.this);
        dbHelper.loadDraftOrders();

        /*if(Class_Genric.checkAutoTime(Splash.this))
            Toast.makeText(Splash.this,"Auto",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Splash.this,"Manual",Toast.LENGTH_SHORT).show();*/
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, this.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getString(Class_Genric.Sp_Status, "").matches("LoggedIn")) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCurrentUser)) {
                        dbHelper.loadCurrentUser();
                        startActivity(new Intent(Splash.this, Dashboard.class));
                    }
                } else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
