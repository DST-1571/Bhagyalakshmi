package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

public class Order_Success extends AppCompatActivity {
    Toolbar toolbar;
    TextView orderNumber,order_Placedby;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        sharedPreferences = getSharedPreferences(Class_Genric.MyPref, Order_Success.MODE_PRIVATE);
        Class_Genric.setOrientation(Order_Success.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        orderNumber=(TextView)findViewById(R.id.order_number);
        order_Placedby=(TextView)findViewById(R.id.order_placedby);
        Class_SyncApi.OrderApi(Order_Success.this);
        orderNumber.setText("Your Order Id is "+sharedPreferences.getString(Class_Genric.Sp_OrderNumber,""));
        order_Placedby.setText("Order Placed By "+ Class_ModelDB.getCurrentuserModel().getName()+"("+Class_ModelDB.getCurrentuserModel().getUserType()+")");
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
