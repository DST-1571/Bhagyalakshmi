package com.sourceedge.bhagyalakshmi.orders.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Search_Customer;
import com.sourceedge.bhagyalakshmi.orders.support.Class_DBHelper;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sharedPreferences;
    ScrollView dashboard_scrollview;
    public static TextView total_order_count;
    CardView adminRetailersDistributors, statistics;
    Class_DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_dashboard);
        Class_Genric.setOrientation(Dashboard.this);
        Class_Static.page(Class_Static.DASHBOARD);
        sharedPreferences = getSharedPreferences(Class_Genric.MyPref, Dashboard.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Class_ModelDB.AppTitle);
        setSupportActionBar(toolbar);
        Class_Genric.applyFontForToolbarTitle(toolbar, Dashboard.this);
        drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        total_order_count = (TextView) findViewById(R.id.total_order_count);
        dbHelper = new Class_DBHelper(Dashboard.this);
        /*
        datetime Research
        String timeInMillis="1464978600";
        Toast.makeText(Dashboard.this,Class_Genric.getDateTime(timeInMillis),Toast.LENGTH_LONG).show();*/
        //Class_SyncApi.ProductApi(Dashboard.this);

        dashboard_scrollview = (ScrollView) findViewById(R.id.dashboard_scrollview);
        adminRetailersDistributors = (CardView) findViewById(R.id.admin_retailers_distributors);
        statistics = (CardView) findViewById(R.id.statistics);//
        onClicks();
        Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, Dashboard.this);
        Class_Genric.drawerOnClicks(Dashboard.this);
    }

    private void onClicks() {
        dashboard_scrollview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
 /*   @Override
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

    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static void animateTextView(int initialValue, int finalValue, final TextView textview) {
        textview.setText(finalValue + "");
        /*DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.5f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 20) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(finalCount + "");
                }
            }, time);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Class_Genric.NetAvailable(Dashboard.this) && Class_ModelDB.DraftorderList.size() > 0) {
            Class_SyncApi.PlaceDraftOrderApi(Dashboard.this, 0, Class_ModelDB.DraftorderList.size());
        }
        Class_Genric.UpdateSyncTime(Dashboard.this);
        Class_SyncApi.OrderApi(Dashboard.this);
    }
}
