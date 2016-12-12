package com.sourceedge.bhagyalakshmi.orders.distributorsales.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.distributorsales.view.Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

public class Distributor_Sales extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView distributorSalesManName,retailerName,productBrand,productCategory,productDescription;
    public static EditText productSearch,productUnit,productQuantity,productPrice;
    Button buttonAdd,buttonReset;
    public static RecyclerView productList;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_sales);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Class_Genric.setOrientation(Distributor_Sales.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        distributorSalesManName=(TextView)findViewById(R.id.user_name);
        retailerName=(TextView)findViewById(R.id.retailer_name);
        productSearch=(EditText)findViewById(R.id.product_search);
        productBrand=(TextView)findViewById(R.id.product_brand);
        productCategory=(TextView)findViewById(R.id.product_category);
        productDescription=(TextView)findViewById(R.id.product_description);
        productUnit=(EditText)findViewById(R.id.product_unit);
        productQuantity=(EditText)findViewById(R.id.product_quantity);
        productPrice=(EditText)findViewById(R.id.product_price);
        buttonAdd=(Button)findViewById(R.id.button_add);
        buttonReset=(Button)findViewById(R.id.button_reset);
        productList=(RecyclerView)findViewById(R.id.product_list);
        productList.setLayoutManager(new LinearLayoutManager(Distributor_Sales.this));

        distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString()+" - Distributor(DSP)");
        retailerName.setText(Class_Static.tempRole.getName().toString());
        if(Class_Static.editProductOrder){
            productSearch.setText(Class_Static.tempProduct.getName());
            productBrand.setText(Class_Static.tempProduct.getBrand());
            productCategory.setText(Class_Static.tempProduct.getCategory());
            productDescription.setText(Class_Static.tempProduct.getDescription());
            productUnit.setText(Class_Static.tempProduct.getUnits());
            productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
            productPrice.setText(Class_Static.tempProduct.getPrice() + "");
            productSearch.setEnabled(false);
        }
        Functionalities(Distributor_Sales.this);
        OnClicks();
    }

    private void OnClicks() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Class_Static.editProductOrder){
                    Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                    Class_Static.tempProduct.setName(productSearch.getText().toString());
                    Class_Static.tempProduct.setBrand(productBrand.getText().toString());
                    Class_Static.tempProduct.setCategory(productCategory.getText().toString());
                    Class_Static.tempProduct.setDescription(productDescription.getText().toString());
                    Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                    Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                    Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                    Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity()*Class_Static.tempProduct.getPrice());
                    for (Product prod:Class_Static.tempOrderingProduct) {
                        if(prod.getId().matches(Class_Static.tempProduct.getId())){
                            Class_Static.tempOrderingProduct.remove(Class_Static.tempProduct);
                        }
                    }
                    Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                    Class_Static.editProductOrder=false;
                }else {
                    Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                    Class_Static.tempProduct.setName(productSearch.getText().toString());
                    Class_Static.tempProduct.setBrand(productBrand.getText().toString());
                    Class_Static.tempProduct.setCategory(productCategory.getText().toString());
                    Class_Static.tempProduct.setDescription(productDescription.getText().toString());
                    Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                    Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                    Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                    Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity()*Class_Static.tempProduct.getPrice());
                    Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                }

                finish();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Functionalities(final Context context) {
        productSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("")) {
                    productList.setVisibility(View.VISIBLE);
                    Class_Static.tempProductList = new ArrayList<Product>();
                    viewHeight = Class_Genric.convertDpToPixels(55, context);
                    viewHeight = viewHeight * ((Class_Static.tempProductList.size()));
                    productList.getLayoutParams().height = viewHeight;
                    productList.setAdapter(new Product_List_Adapter(context,Class_Static.tempProductList));
                } else {
                    productList.setVisibility(View.VISIBLE);
                    Class_Static.tempProductList = new ArrayList<Product>();
                    if (!Class_Static.tempProduct.getName().toLowerCase().matches(s.toString().toLowerCase()))
                        for (Product product : Class_ModelDB.getProductList()) {
                            if (product.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                Class_Static.tempProductList.add(product);
                            }
                        }
                    viewHeight = Class_Genric.convertDpToPixels(55, context);
                    viewHeight = viewHeight * ((Class_Static.tempProductList.size()));
                    productList.getLayoutParams().height = viewHeight;
                    productList.setAdapter(new Product_List_Adapter(context,Class_Static.tempProductList));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        super.onBackPressed();
        finish();
    }
}
