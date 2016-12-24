package com.sourceedge.bhagyalakshmi.orders.orderproduct.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;
import java.util.Iterator;

public class Add_Product extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView productSearch;
    public static TextView retailerName, productGroup, productCategory;
    public static EditText productUnit, productQuantity, productPrice;
    ImageView searchGroupIcon,searchCategoryIcon,searchIcon;
    ImageButton incrementQuantity, decrementQuantity;
    Button buttonAdd, buttonReset, buttonAddNew;
    LinearLayout retailerLayout, searchPane;
    ScrollView scrollview;
    CoordinatorLayout coordinator;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_sales);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Class_Genric.setOrientation(Add_Product.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Class_ModelDB.AppTitle);
        setSupportActionBar(toolbar);
        coordinator= (CoordinatorLayout) findViewById(R.id.coordinator);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //distributorSalesManName = (TextView) findViewById(R.id.user_name);
        retailerName = (TextView) findViewById(R.id.retailer_name);
        searchGroupIcon=(ImageView)findViewById(R.id.search_group_icon);
        searchCategoryIcon=(ImageView) findViewById(R.id.search_category_icon);
        searchIcon= (ImageView) findViewById(R.id.search_icon);
        productSearch = (TextView) findViewById(R.id.product_search);
        productGroup = (TextView) findViewById(R.id.product_group);
        productCategory = (TextView) findViewById(R.id.product_category);
        productUnit = (EditText) findViewById(R.id.product_unit);
        productQuantity = (EditText) findViewById(R.id.product_quantity);
        productPrice = (EditText) findViewById(R.id.product_price);
        incrementQuantity = (ImageButton) findViewById(R.id.increment_quantity);
        decrementQuantity = (ImageButton) findViewById(R.id.decrement_quantity);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonReset = (Button) findViewById(R.id.button_reset);
        buttonAddNew = (Button) findViewById(R.id.button_add_new);
        retailerLayout = (LinearLayout) findViewById(R.id.retailer_layout);

        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                }else{
                    productPrice.setEnabled(false);
                }
                //distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Distributor(DSP)");
                break;
            case Class_Genric.DISTRIBUTOR:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                }else{
                    productPrice.setEnabled(false);
                }
                retailerLayout.setVisibility(View.GONE);
                //distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Distributor");
                break;
            case Class_Genric.SALESPERSON:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                }else{
                    productPrice.setEnabled(false);
                }
                //distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Sales(SBL)");
                break;
        }
        retailerName.setText(Class_Static.tempRole.getName().toString());
        if (Class_Static.editProductOrder) {
            productSearch.setText(Class_Static.tempProduct.getDescription());
            productGroup.setText(Class_Static.tempProduct.getSectionName());
            productCategory.setText(Class_Static.tempProduct.getCatagoryName());
            productUnit.setText(Class_Static.tempProduct.getUnits());
            productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
            productPrice.setText(Class_Static.tempProduct.getPrice() + "");
            productSearch.setEnabled(false);
            searchCategoryIcon.setVisibility(View.INVISIBLE);
            searchIcon.setVisibility(View.INVISIBLE);
            searchGroupIcon.setVisibility(View.INVISIBLE);
            productGroup.setEnabled(false);
            productCategory.setEnabled(false);
            buttonAdd.setText("SAVE");
            buttonAddNew.setText("CANCEL");

        } else{
            Class_Static.tempProduct= new Product();
            searchCategoryIcon.setVisibility(View.VISIBLE);
            searchIcon.setVisibility(View.VISIBLE);
            searchGroupIcon.setVisibility(View.VISIBLE);
            buttonAddNew.setText("SAVE AND NEW");
        }

        Functionalities(Add_Product.this);
        OnClicks();

    }

    private void OnClicks() {

        incrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(productQuantity.getText().toString().trim())) {
                    productQuantity.setText("0");
                }
                productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) + 1) + "");
            }
        });

        decrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(productQuantity.getText().toString().trim())) {
                    productQuantity.setText("1");
                }
                if (!productQuantity.getText().toString().matches("1")) {
                    productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) - 1) + "");
                } else
                    Toast.makeText(Add_Product.this, "Min Count is 1", Toast.LENGTH_SHORT).show();
            }

        });


        buttonAdd.setOnClickListener(new View.OnClickListener()

                                     {
                                         @Override
                                         public void onClick(View v) {
                                             if (Class_Static.editProductOrder) {
                                                 Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                                                 Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                 Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                 Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                 Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                 Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                 Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                 Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                 Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                 while (iter.hasNext()) {
                                                     Product prod = iter.next();

                                                     if (prod.getId().matches(Class_Static.tempProduct.getId()))
                                                         iter.remove();
                                                 }
                                                 Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                                                 Class_Static.editProductOrder = false;
                                             } else {
                                                 if (productSearch.getText().toString().isEmpty() || productSearch.getText().toString().length() == 0 || productSearch.getText().toString().equals("") || productSearch.getText().toString() == null) {
                                                     //Toast.makeText(Add_Product.this, "Select Product", Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                     boolean found = false;
                                                     while (iter.hasNext()) {
                                                         Product prod = iter.next();
                                                         if (prod.getId().matches(Class_Static.tempProduct.getId())) {
                                                             int qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                             prod.setQuantity(qty);
                                                             prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                             found = true;
                                                             break;
                                                         }
                                                     }
                                                     if (!found) {
                                                         Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                         Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                                                         Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                         Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                         Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                         Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                         Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                         Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                         Product tempProd = new Product(Class_Static.tempProduct);
                                                         Class_Static.tempOrderingProduct.add(tempProd);
                                                     }
                                                 }
                                             }
                                             startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                             finish();
                                         }
                                     }

        );

        buttonAddNew.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v) {
                                                if(buttonAddNew.getText().toString().toLowerCase().matches(("CANCEL").toLowerCase()))
                                                    finish();
                                                else {
                                                    if (productSearch.getText().toString().isEmpty() || productSearch.getText().toString().length() == 0 || productSearch.getText().toString().equals("") || productSearch.getText().toString() == null) {
                                                        Toast.makeText(Add_Product.this, "Select Product", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                        boolean found = false;
                                                        while (iter.hasNext()) {
                                                            Product prod = iter.next();
                                                            if (prod.getId().matches(Class_Static.tempProduct.getId())) {
                                                                int qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                                prod.setQuantity(qty);
                                                                prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                                found = true;
                                                                break;
                                                            }
                                                        }
                                                        if (!found) {
                                                            Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                                                            Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                            Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                            Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                            Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                            Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                            Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                            Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                            Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                                                            cleanScreen();
                                                        }
                                                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                                                        Class_Static.tempProduct= new Product();
                                                    }
                                                }
                                            }
                                        }

        );

        buttonReset.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               if (Class_Static.editProductOrder) {
                                                   finish();
                                               } else {
                                                   productSearch.setText("");
                                                   productGroup.setText("");
                                                   productCategory.setText("");
                                                   productUnit.setText("");
                                                   productPrice.setText("");
                                                   productQuantity.setText("");
                                               }
                                           }
                                       }

        );
    }

    private void Functionalities(final Context context) {

        productGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class_Static.Flag_SEARCH = Class_Static.SEARCHGROUP;
                startActivity(new Intent(Add_Product.this, Search_Customer.class));
            }
        });
        productCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Class_Static.tempProduct.getSectionId().matches("")) {
                    Class_Static.Flag_SEARCH = Class_Static.SEARCHCATAGORY;
                    startActivity(new Intent(Add_Product.this, Search_Customer.class));
                } else Toast.makeText(context, "Please Select A Group", Toast.LENGTH_SHORT).show();

            }
        });
        productSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Class_Static.tempProduct.getSectionId().matches("")) {
                    if (!Class_Static.tempProduct.getCategoryId().matches("")) {
                        Class_Static.Flag_SEARCH = Class_Static.SEARCHPRODUCT;
                        startActivity(new Intent(Add_Product.this, Search_Customer.class));
                    } else
                        Toast.makeText(context, "Please Select A Catagory", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(context, "Please Select A Group", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
        finish();

    }

    private void cleanScreen(){
        productSearch.setText("");
        productGroup.setText("");
        productCategory.setText("");
        productUnit.setText("");
        productPrice.setText("");
        productQuantity.setText("");
    }
}
