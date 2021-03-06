package com.sourceedge.bhagyalakshmi.orders.orderproduct.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Catagory_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Group_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Product_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Role_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.view.Sales_Person_List_Adapter;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;

import java.util.ArrayList;

public class Search_Customer extends AppCompatActivity {

    public static EditText retailerSearch;
    public static RecyclerView retailerList;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__customer);
        Class_Genric.setOrientation(Search_Customer.this);
        retailerSearch = (EditText) findViewById(R.id.retailer_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        retailerList = (RecyclerView) findViewById(R.id.retailer_list);
        retailerList.setLayoutManager(new LinearLayoutManager(Search_Customer.this));

        if (Class_Static.Flag_SEARCH == Class_Static.SEARCHCUSTOMER) {
            switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                case Class_Genric.ADMIN:
                    break;
                case Class_Genric.DISTRIBUTORSALES:
                    retailerSearch.setHint("Search Retailer");
                    break;
                case Class_Genric.DISTRIBUTOR:
                    retailerSearch.setHint("Search");
                    break;
                case Class_Genric.ASM:
                case Class_Genric.SALESMAN:
                    retailerSearch.setHint("Search Distributor");
                    break;
            }
            Class_Static.tempRoleList = new ArrayList<Role>();
            Class_Static.tempRoleList = Class_ModelDB.getRoleList();

            retailerList.setAdapter(new Role_List_Adapter(Search_Customer.this, Class_Static.tempRoleList));

            //
            retailerSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().matches("")) {
                        retailerList.setVisibility(View.VISIBLE);
                        Class_Static.tempRoleList = new ArrayList<Role>();
                        Class_Static.tempRoleList = Class_ModelDB.getRoleList();
                        retailerList.setAdapter(new Role_List_Adapter(Search_Customer.this, Class_Static.tempRoleList));
                    } else {
                        retailerList.setVisibility(View.VISIBLE);
                        Class_Static.tempRoleList = new ArrayList<Role>();
                        if (!Class_Static.tempRole.getName().toLowerCase().matches(s.toString().toLowerCase()))
                            for (Role role : Class_ModelDB.getRoleList()) {
                                if (role.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                    Class_Static.tempRoleList.add(role);
                                }
                            }
                        retailerList.setAdapter(new Role_List_Adapter(Search_Customer.this, Class_Static.tempRoleList));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } else if (Class_Static.Flag_SEARCH == Class_Static.SEARCHPRODUCT) {
            Class_Static.tempProductList = new ArrayList<Product>();
            Class_Static.tempProductList = Class_ModelDB.getProductList();
           /* for (Product product : Class_ModelDB.getProductList()) {
                if (product.getCategoryId().toString().toLowerCase().matches(Class_Static.tempProduct.getCategoryId().toString().toLowerCase())) {
                    Class_Static.tempProductList.add(product);
                }
            }*/
            retailerList.setAdapter(new Product_List_Adapter(Search_Customer.this, Class_Static.tempProductList));
            retailerSearch.setHint("Search Product");

            //
            retailerSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().matches("")) {
                        Class_Static.tempProductList = new ArrayList<Product>();
                        for (Product product : Class_ModelDB.getProductList()) {
                            if (product.getCategoryId().toString().toLowerCase().matches(Class_Static.tempProduct.getCategoryId().toString().toLowerCase())) {
                                Class_Static.tempProductList.add(product);
                            }
                        }
                        retailerList.setAdapter(new Product_List_Adapter(Search_Customer.this, Class_Static.tempProductList));
                    } else {
                        Class_Static.tempProductList = new ArrayList<Product>();
                        for (Product product : Class_ModelDB.getProductList()) {
                            if (product.getDescription().toString().toLowerCase().contains(s.toString().toLowerCase()) && product.getCategoryId().toString().toLowerCase().matches(Class_Static.tempProduct.getCategoryId().toString().toLowerCase())) {
                                Class_Static.tempProductList.add(product);
                            }
                        }
                        retailerList.setAdapter(new Product_List_Adapter(Search_Customer.this, Class_Static.tempProductList));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if (Class_Static.Flag_SEARCH == Class_Static.SEARCHGROUP) {
            Class_Static.tempGroupList = new ArrayList<Sections>();
            Class_Static.tempGroupList = Class_ModelDB.getSectionList();
            retailerList.setAdapter(new Group_List_Adapter(Search_Customer.this, Class_Static.tempGroupList));
            retailerSearch.setHint("Search Category Group");

            //
            retailerSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().matches("")) {
                        Class_Static.tempGroupList = new ArrayList<Sections>();
                        Class_Static.tempGroupList = Class_ModelDB.getSectionList();
                        retailerList.setAdapter(new Group_List_Adapter(Search_Customer.this, Class_Static.tempGroupList));
                    } else {
                        Class_Static.tempGroupList = new ArrayList<Sections>();
                            for (Sections section : Class_ModelDB.getSectionList()) {
                                if (section.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                    Class_Static.tempGroupList.add(section);
                                }
                            }
                        retailerList.setAdapter(new Group_List_Adapter(Search_Customer.this, Class_Static.tempGroupList));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } else if (Class_Static.Flag_SEARCH == Class_Static.SEARCHCATAGORY) {
            Class_Static.tempCategotyList = new ArrayList<Catagories>();
            Class_Static.tempCategotyList = Class_ModelDB.getCatagoryList();
            //retailerList.setAdapter(new Group_List_Adapter(Search_Customer.this, Class_Static.tempCategotyList));
            /*Class_Static.tempCategotyList = new ArrayList<Catagories>();
            for (Catagories catagory : Class_ModelDB.getCatagoryList()) {
                if (catagory.getSectionId().toString().toLowerCase().matches(Class_Static.tempProduct.getSectionId().toString().toLowerCase())) {
                    Class_Static.tempCategotyList.add(catagory);
                }
            }*/
            retailerList.setAdapter(new Catagory_List_Adapter(Search_Customer.this, Class_Static.tempCategotyList));
            retailerSearch.setHint("Search Category");

            //
            retailerSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().matches("")) {
                        Class_Static.tempCategotyList = new ArrayList<Catagories>();
                        for (Catagories catagory : Class_ModelDB.getCatagoryList()) {
                            if (catagory.getSectionId().toString().toLowerCase().matches(Class_Static.tempProduct.getSectionId().toString().toLowerCase())) {
                                Class_Static.tempCategotyList.add(catagory);
                            }
                        }
                        retailerList.setAdapter(new Catagory_List_Adapter(Search_Customer.this, Class_Static.tempCategotyList));
                    } else {
                        Class_Static.tempCategotyList = new ArrayList<Catagories>();
                            for (Catagories catagory : Class_ModelDB.getCatagoryList()) {
                                if (catagory.getName().toString().toLowerCase().contains(s.toString().toLowerCase())&& catagory.getSectionId().toString().toLowerCase().matches(Class_Static.tempProduct.getSectionId().toString().toLowerCase())) {
                                    Class_Static.tempCategotyList.add(catagory);
                                }
                            }
                        retailerList.setAdapter(new Catagory_List_Adapter(Search_Customer.this, Class_Static.tempCategotyList));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }else if (Class_Static.Flag_SEARCH == Class_Static.SEARCHSALESPERSON) {
            switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                case Class_Genric.ADMIN:
                    retailerSearch.setHint("Search SalesPerson");
                    break;
                case Class_Genric.DISTRIBUTORSALES:
                case Class_Genric.ASM:
                case Class_Genric.DISTRIBUTOR:
                case Class_Genric.SALESMAN:
                    break;
            }
            Class_Static.tempRoleList = new ArrayList<Role>();
            Class_Static.tempRoleList = Class_ModelDB.getRoleList();
            retailerList.setAdapter(new Sales_Person_List_Adapter(Search_Customer.this, Class_Static.tempRoleList));

            //
            retailerSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().matches("")) {
                        retailerList.setVisibility(View.VISIBLE);
                        Class_Static.tempSalesPersonList = new ArrayList<Role>();
                        Class_Static.tempSalesPersonList = Class_ModelDB.getRoleList();
                        retailerList.setAdapter(new Sales_Person_List_Adapter(Search_Customer.this, Class_Static.tempSalesPersonList));
                    } else {
                        retailerList.setVisibility(View.VISIBLE);
                        Class_Static.tempSalesPersonList = new ArrayList<Role>();
                        if (!Class_Static.tempRole.getName().toLowerCase().matches(s.toString().toLowerCase()))
                            for (Role role : Class_ModelDB.getRoleList()) {
                                if (role.getName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                    Class_Static.tempSalesPersonList.add(role);
                                }
                            }
                        retailerList.setAdapter(new Sales_Person_List_Adapter(Search_Customer.this, Class_Static.tempSalesPersonList));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

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
