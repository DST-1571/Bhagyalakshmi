package com.sourceedge.bhagyalakshmi.orders.orderproduct.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Success;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Order_Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.View_Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_DBHelper;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Centura User1 on 13-12-2016.
 */

public class Product_Order_Lookup extends AppCompatActivity {
    Toolbar toolbar;
    public static FloatingActionButton fab;
    //public static EditText retailerSearch;
    //public static TextView retailerSearch;
    public static TextView grandTotal, action_text_pane;
    //public static EditText retailerSearch;
    public static RecyclerView orderProductRecyclerview, retailerList;
    public static LinearLayout scrollView, order_header2, order_header1;
    public static Button submitButton;
    SharedPreferences sharedPreferences;
    static LinearLayout orderProductListLayout, emptyProducts, orderDetailsHeader;//search_pane
    static TextView customername, orderdate, ordernumber, customerlable;
    CoordinatorLayout coordinator;
    TextView placedby;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_look_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Class_ModelDB.AppTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Class_Genric.setOrientation(Product_Order_Lookup.this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        grandTotal = (TextView) findViewById(R.id.grand_total_amount123);
        action_text_pane = (TextView) findViewById(R.id.action_text_pane);
        coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        orderDetailsHeader = (LinearLayout) findViewById(R.id.orderdetailsheader);
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, this.MODE_PRIVATE);
        //retailerSearch = (EditText) findViewById(R.id.retailer_search);
        action_text_pane = (TextView) findViewById(R.id.action_text_pane);
        //retailerSearch = (EditText) findViewById(R.id.retailer_search);
        customerlable = (TextView) findViewById(R.id.customerlable);
        customername = (TextView) findViewById(R.id.customername);
        orderdate = (TextView) findViewById(R.id.orderdate);
        ordernumber = (TextView) findViewById(R.id.ordernumber);
        placedby = (TextView) findViewById(R.id.placedby);
        //retailerSearch = (TextView) findViewById(R.id.retailer_search);
        orderProductListLayout = (LinearLayout) findViewById(R.id.order_product_list_layout);
        orderProductRecyclerview = (RecyclerView) findViewById(R.id.order_product_recyclerview);
        retailerList = (RecyclerView) findViewById(R.id.retailer_list);
        orderProductRecyclerview.setLayoutManager(new LinearLayoutManager(Product_Order_Lookup.this));
        retailerList.setLayoutManager(new LinearLayoutManager(Product_Order_Lookup.this));
        submitButton = (Button) findViewById(R.id.submit_button);
        //searchPane = (LinearLayout) findViewById(R.id.search_pane);
        emptyProducts = (LinearLayout) findViewById(R.id.empty_products);
        order_header1 = (LinearLayout) findViewById(R.id.order_header1);
        order_header2 = (LinearLayout) findViewById(R.id.order_header2);
        scrollView = (LinearLayout) findViewById(R.id.orderitemlist);

        placedby.setText(Class_ModelDB.getCurrentuserModel().getName());
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                customerlable.setText("");
                break;
            case Class_Genric.DISTRIBUTORSALES:
                customerlable.setText("Retailer :");
                customername.setText(Class_Static.tempRole.getName());
                //searchPane.setVisibility(View.GONE);
                break;
            case Class_Genric.DISTRIBUTOR:
                //searchPane.setVisibility(View.GONE);
                break;
            case Class_Genric.SALESMAN:
                //searchPane.setVisibility(View.VISIBLE);
                // retailerSearch.setHint("Select Distributor");
                customerlable.setText("Distributor :");
                customername.setText(Class_Static.tempRole.getName());
                //searchPane.setVisibility(View.GONE);
                //retailerSearch.setHint("Select Distributor");
                //searchPane.setVisibility(View.VISIBLE);
                //retailerSearch.setHint("Select Distributor");
                break;
        }

        onClicks();
        InitializeAdapter(Product_Order_Lookup.this);

    }

    public static void InitializeAdapter(Context context) {
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
            case Class_Genric.SALESMAN:
                if (Class_Static.tempOrderingProduct.size() != 0) {
                    orderProductListLayout.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    if (Class_Static.viewOrderedProducts) {
                        orderDetailsHeader.setVisibility(View.VISIBLE);
                        orderdate.setText(Class_Genric.getDateTime(Class_Static.OrdredProducts));
                        ordernumber.setText(Class_Static.OrdredProducts.getOrderNumber());
                        customername.setText(Class_Static.OrdredProducts.getClient().getName());
                        fab.setVisibility(View.GONE);
                        setMargins(fab, 60, context);
                        //searchPane.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        action_text_pane.setVisibility(View.GONE);
                        order_header1.setVisibility(View.GONE);
                        order_header2.setVisibility(View.VISIBLE);
                        orderProductRecyclerview.setAdapter(new View_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                        deleteOption();
                    } else {
                        orderDetailsHeader.setVisibility(View.GONE);
                        orderdate.setText(Class_Genric.getDate());
                        orderdate.setText("-");
                        fab.setVisibility(View.VISIBLE);
                        //searchPane.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.VISIBLE);
                        action_text_pane.setVisibility(View.VISIBLE);
                        order_header1.setVisibility(View.VISIBLE);
                        order_header2.setVisibility(View.GONE);
                        orderProductRecyclerview.setAdapter(new Order_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    }
                } else {
                    fab.setVisibility(View.VISIBLE);
                    orderProductListLayout.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);
                    grandTotal.setText("");
                    setMargins(fab, 16, context);
                    ((Activity)context).finish();
                }


                break;
            case Class_Genric.DISTRIBUTOR:
                if (Class_Static.tempOrderingProduct.size() != 0) {
                    scrollView.setVisibility(View.VISIBLE);
                    orderProductListLayout.setVisibility(View.VISIBLE);
                    orderProductRecyclerview.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.VISIBLE);
                    setMargins(fab, 60, context);
                    emptyProducts.setVisibility(View.GONE);
                    if (Class_Static.viewOrderedProducts) {
                        orderDetailsHeader.setVisibility(View.VISIBLE);
                        orderdate.setText(Class_Genric.getDateTime(Class_Static.OrdredProducts));
                        ordernumber.setText(Class_Static.OrdredProducts.getOrderNumber());
                        fab.setVisibility(View.GONE);
                        //searchPane.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                        action_text_pane.setVisibility(View.GONE);
                        order_header1.setVisibility(View.GONE);
                        order_header2.setVisibility(View.VISIBLE);
                        orderProductRecyclerview.setAdapter(new View_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                        deleteOption();
                    } else {
                        orderDetailsHeader.setVisibility(View.GONE);
                        orderdate.setText(Class_Genric.getDate());
                        orderdate.setText("-");
                        fab.setVisibility(View.VISIBLE);
                        //searchPane.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.VISIBLE);
                        action_text_pane.setVisibility(View.VISIBLE);
                        order_header1.setVisibility(View.VISIBLE);
                        order_header2.setVisibility(View.GONE);
                        orderProductRecyclerview.setAdapter(new Order_Product_List_Adapter(context, Class_Static.tempOrderingProduct));
                    }
                } else {
                    fab.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    setMargins(fab, 16, context);
                    emptyProducts.setVisibility(View.VISIBLE);
                }
                break;
        }

    }


    public static void deleteOption(){
        if(Class_Static.OrdredProducts.getStatus().contains("Failed")){
            fab.setImageResource(R.drawable.ic_delete_white_24dp);
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void onClicks() {
        if (Class_Static.viewOrderedProducts) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class_ModelDB.getDraftorderList().remove(Class_Static.OrdredProducts);
                    Class_DBHelper dbHelper= new Class_DBHelper(Product_Order_Lookup.this);
                    dbHelper.saveDraftOrders();
                    dbHelper.loadDraftOrders();
                    dbHelper.loadOrders();
                    finish();
                }
            });
        }
        else {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                        case Class_Genric.ADMIN:
                            break;
                        case Class_Genric.DISTRIBUTORSALES:
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                            break;
                        case Class_Genric.DISTRIBUTOR:
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                            break;
                        case Class_Genric.SALESMAN:
                            Class_Static.editProductOrder = false;
                            startActivity(new Intent(Product_Order_Lookup.this, Add_Product.class));
                            break;
                    }
                    finish();
                }
            });
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                    case Class_Genric.ADMIN:
                        break;
                    case Class_Genric.DISTRIBUTORSALES:
                    case Class_Genric.SALESMAN:
                        if (Class_Genric.NetAvailable(Product_Order_Lookup.this))
                            Class_SyncApi.PlaceOrderApiTemp(Product_Order_Lookup.this, Class_ModelDB.getCurrentuserModel().getName(), Class_Static.tempRole.getId(), Class_Static.tempOrderingProduct, grandTotal.getText().toString());
                        else {
                            Order tempOrder = new Order();
                            tempOrder.setProducts(Class_Genric.getOrderProductsFromProducts(Class_Static.tempOrderingProduct));
                            tempOrder.setId("N/A");
                            tempOrder.setUser(Class_Genric.getOrderRoleFromCurrentUser(Class_ModelDB.getCurrentuserModel()));
                            tempOrder.setClient(Class_Genric.getOrderRoleFromCurrentRole(Class_Static.tempRole));
                            tempOrder.setOrderNumber("Offline Order");
                            tempOrder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            tempOrder.setStatus("Draft");
                            if (grandTotal.getText() == null)
                                tempOrder.setTotalAmount(0.0);
                            else if (grandTotal.getText().toString().matches(""))
                                tempOrder.setTotalAmount(0.0);
                            else
                                tempOrder.setTotalAmount(Double.valueOf(grandTotal.getText().toString().substring(8)));

                            Class_ModelDB.DraftorderList.add(tempOrder);
                            Class_DBHelper class_dbHelper = new Class_DBHelper(Product_Order_Lookup.this);
                            class_dbHelper.saveDraftOrders();
                            Intent intent= new Intent(Product_Order_Lookup.this, Order_Success.class);
                            intent.putExtra("OrderNumber",("Offline Order"));
                            startActivity(intent);
                            finish();
                        }
                        break;
                    case Class_Genric.DISTRIBUTOR:
                        if (Class_Genric.NetAvailable(Product_Order_Lookup.this))
                            Class_SyncApi.PlaceOrderApiTemp(Product_Order_Lookup.this, Class_ModelDB.getCurrentuserModel().getName(), Class_ModelDB.getCurrentuserModel().getId(), Class_Static.tempOrderingProduct, grandTotal.getText().toString());
                        else {
                            Order tempOrder = new Order();
                            tempOrder.setProducts(Class_Genric.getOrderProductsFromProducts(Class_Static.tempOrderingProduct));
                            tempOrder.setId("N/A");
                            tempOrder.setUser(Class_Genric.getOrderRoleFromCurrentUser(Class_ModelDB.getCurrentuserModel()));
                            tempOrder.setClient(Class_Genric.getOrderRoleFromCurrentUser(Class_ModelDB.getCurrentuserModel()));
                            tempOrder.setOrderNumber("Offline Order");
                            tempOrder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            tempOrder.setStatus("Draft");
                            if (grandTotal.getText() == null)
                                tempOrder.setTotalAmount(0.0);
                            else if (grandTotal.getText().toString().matches(""))
                                tempOrder.setTotalAmount(0.0);
                            else
                                tempOrder.setTotalAmount(Double.valueOf(grandTotal.getText().toString().substring(8)));
                            Class_ModelDB.DraftorderList.add(tempOrder);
                            Class_DBHelper class_dbHelper = new Class_DBHelper(Product_Order_Lookup.this);
                            class_dbHelper.saveDraftOrders();
                            Intent intent= new Intent(Product_Order_Lookup.this, Order_Success.class);
                            intent.putExtra("OrderNumber",("Offline Order"));
                            startActivity(intent);
                            finish();
                        }
                        break;
                }


            }
        });


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
        /*if (Class_Static.viewOrderedProducts) {
         finish();
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(coordinator, "Changes Will be Discarded", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
            snackbar.show();
        }*/

        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        InitializeAdapter(Product_Order_Lookup.this);
    }


    public static void setMargins(View v, int b, Context context) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            int x = Class_Genric.convertDpToPixels(16, context);
            b = Class_Genric.convertDpToPixels(b, context);
            p.setMargins(x, x, x, b);
            v.requestLayout();
        }
    }
}

