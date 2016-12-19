package com.sourceedge.bhagyalakshmi.orders.orderpage.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.orderpage.view.Admin_View_Order_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

public class Admin_View_Order extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    public static TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__view__order);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=(RecyclerView)findViewById(R.id.admin_view_order);
        total=(TextView)findViewById(R.id.total);
        recyclerView.setLayoutManager(new LinearLayoutManager(Admin_View_Order.this));
        InitializeAdapter();

    }

    private void InitializeAdapter() {
        if(Class_Static.tempOrderingProduct.size()!=0){
            recyclerView.setAdapter(new Admin_View_Order_Adapter(Admin_View_Order.this,Class_Static.tempOrderingProduct));
        }else {
            recyclerView.setVisibility(View.GONE);
        }
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
