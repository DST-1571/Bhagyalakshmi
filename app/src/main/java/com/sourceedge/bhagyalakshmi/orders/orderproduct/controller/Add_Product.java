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
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.util.ArrayList;
import java.util.Iterator;

public class Add_Product extends AppCompatActivity {
    Toolbar toolbar;
    public static TextView productSearch, productStock;
    public static TextView retailerName, productGroup, productCategory;
    public static EditText productUnit, productQuantity, productPrice;
    ImageView searchGroupIcon, searchCategoryIcon, searchIcon;
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
        Class_Genric.setOrientation(Add_Product.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Class_Genric.setOrientation(Add_Product.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Class_ModelDB.AppTitle);
        setSupportActionBar(toolbar);
        coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //distributorSalesManName = (TextView) findViewById(R.id.user_name);
        retailerName = (TextView) findViewById(R.id.retailer_name);
        searchGroupIcon = (ImageView) findViewById(R.id.search_group_icon);
        searchCategoryIcon = (ImageView) findViewById(R.id.search_category_icon);
        searchIcon = (ImageView) findViewById(R.id.search_icon);
        productSearch = (TextView) findViewById(R.id.product_search);
        productStock = (TextView) findViewById(R.id.product_stock);
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

        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                } else {
                    productPrice.setEnabled(false);
                }
               /* Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                Product prod = new Product();
                while (iter.hasNext()) {
                    prod = iter.next();
                    if (prod.getId().matches(Class_Static.tempProduct.getId())){
                        productStock.setText("Stock Available : "+Class_Static.AvailableStock);
                        productStock.setVisibility(View.GONE);
                    }
                }*/
                break;
            case Class_Genric.DISTRIBUTOR:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                } else {
                    productPrice.setEnabled(false);
                }
                retailerLayout.setVisibility(View.GONE);
                productStock.setVisibility(View.GONE);
                break;
            case Class_Genric.SALESMAN:
                if (Class_ModelDB.getCurrentuserModel().getACL().matches("editprice")) {
                    productPrice.setEnabled(true);
                } else {
                    productPrice.setEnabled(false);
                }
                productStock.setVisibility(View.GONE);
                //distributorSalesManName.setText(Class_ModelDB.getCurrentuserModel().getName().toString() + " - Sales(SBL)");
                break;
        }
        retailerName.setText(Class_Static.tempRole.getName().toString());

        if (Class_Static.editProductOrder) {
            switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                case Class_Genric.DISTRIBUTORSALES:
                    productStock.setText("Stock Available : " + Class_Static.Stock);
                    productStock.setVisibility(View.VISIBLE);
                    break;
            }
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

        } else {
            Class_Static.tempProduct = new Product();
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
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                    case Class_Genric.DISTRIBUTORSALES:
                        if ("".equals(productQuantity.getText().toString().trim())) {
                            productQuantity.setText("0");
                        }
                        if (Class_Static.editProductOrder) {

                            if (Integer.parseInt(productQuantity.getText().toString()) >= Class_Static.Stock) {
                                productQuantity.setError("Out of Stock");
                            } else {
                                productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) + 1) + "");
                            }
                        } else {
                            if (Integer.parseInt(productQuantity.getText().toString()) >= Class_Static.AvailableStock) {
                                productQuantity.setError("Out of Stock");
                            } else {
                                productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) + 1) + "");
                            }
                        }

                        break;
                    case Class_Genric.DISTRIBUTOR:
                    case Class_Genric.SALESMAN:
                        if ("".equals(productQuantity.getText().toString().trim())) {
                            productQuantity.setText("0");
                        }
                        productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) + 1) + "");
                        //productStock.setText("Stock Available : " + (Class_Static.Stock - Integer.parseInt(productQuantity.getText().toString())));
                        break;
                }
            }
        });

        decrementQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                    case Class_Genric.DISTRIBUTORSALES:
                        if ("".equals(productQuantity.getText().toString().trim())) {
                            productQuantity.setText("1");
                        }
                        if (productQuantity.getText().toString().matches("0")) {
                            Toast.makeText(Add_Product.this, "Min Count is 0", Toast.LENGTH_SHORT).show();
                        } else if (!productQuantity.getText().toString().matches("1")) {
                            productQuantity.setError(null);
                            productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) - 1) + "");
                            //productStock.setText("Stock Available : " + ((Integer.parseInt(productStock.getText().toString().split(": ")[1]) + 1) + ""));
                        } else
                            Toast.makeText(Add_Product.this, "Min Count is 1", Toast.LENGTH_SHORT).show();
                        break;
                    case Class_Genric.DISTRIBUTOR:
                    case Class_Genric.SALESMAN:
                        if ("".equals(productQuantity.getText().toString().trim())) {
                            productQuantity.setText("1");
                        }
                        if (!productQuantity.getText().toString().matches("1")) {
                            productQuantity.setText((Integer.parseInt(productQuantity.getText().toString()) - 1) + "");
                            //productStock.setText("Stock Available : " + ((Integer.parseInt(productStock.getText().toString().split(": ")[1]) + 1) + ""));
                        } else
                            Toast.makeText(Add_Product.this, "Min Count is 1", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

        });


        buttonAdd.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (Class_Static.editProductOrder) {
                                                 switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                                                     case Class_Genric.DISTRIBUTORSALES:
                                                         if (Integer.parseInt(productQuantity.getText().toString()) > Class_Static.Stock) {
                                                             productQuantity.setError("Out of Stock");
                                                         } else {
                                                             Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
                                                             Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                             Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                             Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                             Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                             Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                             Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                             Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());

                                                             Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                             Product prod = new Product();
                                                             while (iter.hasNext()) {
                                                                 prod = iter.next();
                                                                 if (prod.getCode().matches(Class_Static.tempProduct.getCode()))
                                                                     iter.remove();
                                                             }
                                                             if (Integer.parseInt(productQuantity.getText().toString()) > Class_Static.Stock) {
                                                                 productQuantity.setError("Out of Stock");
                                                             } else {
                                                                 Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                                                                 Class_Static.AvailableStock = (Class_Static.Stock - Integer.parseInt(productQuantity.getText().toString()));
                                                                 Class_Static.editProductOrder = false;
                                                                 startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                                 finish();
                                                             }
                                                         }
                                                         break;
                                                     case Class_Genric.DISTRIBUTOR:
                                                     case Class_Genric.SALESMAN:
                                                         Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
                                                         Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                         Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                         Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                         Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                         Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                         Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                         Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                         Iterator<Product> iter1 = Class_Static.tempOrderingProduct.iterator();
                                                         while (iter1.hasNext()) {
                                                             Product prod = iter1.next();
                                                             if (prod.getCode().matches(Class_Static.tempProduct.getCode()))
                                                                 iter1.remove();
                                                         }
                                                         Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);
                                                         Class_Static.editProductOrder = false;
                                                         startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                         finish();
                                                         break;
                                                 }

                                             } else {
                                                 switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                                                     case Class_Genric.DISTRIBUTORSALES:
                                                         if (productSearch.getText().toString().isEmpty() ||
                                                                 productSearch.getText().toString().length() == 0 ||
                                                                 productSearch.getText().toString().equals("") ||
                                                                 productSearch.getText().toString() == null ||
                                                                 Integer.parseInt(productQuantity.getText().toString()) == 0) {
                                                             startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                             finish();
                                                             //Toast.makeText(Add_Product.this, "Select Product", Toast.LENGTH_SHORT).show();
                                                         } else if (Integer.parseInt(productQuantity.getText().toString()) > Class_Static.AvailableStock) {
                                                             productQuantity.setError("Out of Stock");
                                                         } else {
                                                             Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                             boolean found = false;
                                                             int qty = 0;
                                                             Product prod = new Product();
                                                             while (iter.hasNext()) {
                                                                 prod = iter.next();
                                                                 if (prod.getCode().matches(Class_Static.tempProduct.getCode())) {
                                                                     qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                                     prod.setQuantity(qty);
                                                                     Class_Static.AvailableStock = Class_Static.AvailableStock - Integer.parseInt(productQuantity.getText().toString());
                                                                     prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                                     found = true;
                                                                     break;
                                                                 }
                                                             }
                                                             if (!found) {
                                                                 if (qty > Class_Static.AvailableStock) {
                                                                     Toast.makeText(Add_Product.this, "Product is Already Added with Stock " + prod.getStock(), Toast.LENGTH_SHORT).show();
                                                                 } else {
                                                                     Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                                     Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
                                                                     Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                                     Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                                     Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                                     Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                                     Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                                     Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                                     Product tempProd = new Product(Class_Static.tempProduct);
                                                                     Class_Static.AvailableStock = Class_Static.AvailableStock - Integer.parseInt(productQuantity.getText().toString());
                                                                     Class_Static.tempOrderingProduct.add(tempProd);

                                                                 }
                                                             }
                                                             startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                             finish();
                                                         }
                                                         break;
                                                     case Class_Genric.DISTRIBUTOR:
                                                     case Class_Genric.SALESMAN:
                                                         if (productSearch.getText().toString().isEmpty() ||
                                                                 productSearch.getText().toString().length() == 0 ||
                                                                 productSearch.getText().toString().equals("") ||
                                                                 productSearch.getText().toString() == null ||
                                                                 Integer.parseInt(productQuantity.getText().toString()) == 0) {
                                                             startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                             finish();
                                                         } else {
                                                             Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                             boolean found = false;
                                                             while (iter.hasNext()) {
                                                                 Product prod = iter.next();
                                                                 if (prod.getCode().matches(Class_Static.tempProduct.getCode())) {
                                                                     int qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                                     prod.setQuantity(qty);
                                                                     prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                                     found = true;
                                                                     break;
                                                                 }
                                                             }
                                                             if (!found) {
                                                                 Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                                 Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
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
                                                         startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                         finish();
                                                         break;
                                                 }
                                             }

                                         }
                                     }

        );

        buttonAddNew.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (buttonAddNew.getText().toString().toLowerCase().matches(("CANCEL").toLowerCase())) {
                                                    startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
                                                    finish();
                                                } else {
                                                    if (productSearch.getText().toString().isEmpty() || productSearch.getText().toString().length() == 0 || productSearch.getText().toString().equals("") || productSearch.getText().toString() == null) {
                                                        Toast.makeText(Add_Product.this, "Select Product", Toast.LENGTH_SHORT).show();
                                                    } else if (Integer.parseInt(productQuantity.getText().toString()) == 0) {
                                                        cleanScreen();
                                                    } else {
                                                        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                                                            case Class_Genric.DISTRIBUTORSALES:
                                                                if (Integer.parseInt(productQuantity.getText().toString()) > Class_Static.AvailableStock) {
                                                                    productQuantity.setError("Out of Stock");
                                                                } else {
                                                                    Iterator<Product> iter = Class_Static.tempOrderingProduct.iterator();
                                                                    boolean found = false;
                                                                    int qty = 0;
                                                                    Product prod = new Product();
                                                                    while (iter.hasNext()) {
                                                                        prod = iter.next();
                                                                        if (prod.getCode().matches(Class_Static.tempProduct.getCode())) {
                                                                            qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                                            prod.setQuantity(qty);
                                                                            Class_Static.AvailableStock = Class_Static.AvailableStock - qty;
                                                                            prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                                            found = true;
                                                                            break;
                                                                        }
                                                                    }
                                                                    if (!found) {
                                                                        if (qty > Class_Static.AvailableStock) {
                                                                            Toast.makeText(Add_Product.this, "Product is Already Added with Stock " + prod.getStock(), Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                                            Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
                                                                            Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                                            Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                                            Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                                            Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                                            Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                                            Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                                            Product tempProd = new Product(Class_Static.tempProduct);
                                                                            Class_Static.AvailableStock = Class_Static.AvailableStock - Integer.parseInt(productQuantity.getText().toString());
                                                                            Class_Static.tempOrderingProduct.add(tempProd);

                                                                        }
                                                                    }
                                                                    cleanScreen();
                                                                }


                                                                      /*  Class_Static.tempProduct.setId(Class_Static.tempProduct.getId());
                                                                        Class_Static.tempProduct.setDescription(productSearch.getText().toString());
                                                                        Class_Static.tempProduct.setSectionName(productGroup.getText().toString());
                                                                        Class_Static.tempProduct.setCatagoryName(productCategory.getText().toString());
                                                                        Class_Static.tempProduct.setUnits(productUnit.getText().toString());
                                                                        Class_Static.tempProduct.setQuantity(Integer.parseInt(productQuantity.getText().toString()));
                                                                        Class_Static.tempProduct.setPrice(Double.valueOf(productPrice.getText().toString()));
                                                                        Class_Static.tempProduct.setAmount(Class_Static.tempProduct.getQuantity() * Class_Static.tempProduct.getPrice());
                                                                        Class_Static.tempOrderingProduct.add(Class_Static.tempProduct);*/


                                                                break;
                                                            case Class_Genric.DISTRIBUTOR:
                                                            case Class_Genric.SALESMAN:
                                                                Iterator<Product> iter1 = Class_Static.tempOrderingProduct.iterator();
                                                                boolean found1 = false;
                                                                while (iter1.hasNext()) {
                                                                    Product prod = iter1.next();
                                                                    if (prod.getCode().matches(Class_Static.tempProduct.getCode())) {
                                                                        int qty = (prod.getQuantity() + Integer.parseInt(productQuantity.getText().toString()));
                                                                        prod.setQuantity(qty);
                                                                        prod.setAmount(prod.getQuantity() * prod.getPrice());
                                                                        found1 = true;
                                                                        break;
                                                                    }
                                                                }
                                                                if (!found1) {
                                                                    Class_Static.tempProduct.setCode(Class_Static.tempProduct.getCode());
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
                                                                break;
                                                        }

                                                        scrollview.fullScroll(ScrollView.FOCUS_UP);
                                                        Class_Static.tempProduct = new Product();
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
                //if (!Class_Static.tempProduct.getSectionId().matches("")) {
                    Class_Static.Flag_SEARCH = Class_Static.SEARCHCATAGORY;
                    startActivity(new Intent(Add_Product.this, Search_Customer.class));
               // } else Toast.makeText(context, "Please Select A Group", Toast.LENGTH_SHORT).show();

            }
        });
        productSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if (!Class_Static.tempProduct.getSectionId().matches("")) {
                  //  if (!Class_Static.tempProduct.getCategoryId().matches("")) {
                        Class_Static.Flag_SEARCH = Class_Static.SEARCHPRODUCT;
                        startActivity(new Intent(Add_Product.this, Search_Customer.class));
                    //} else
                      //  Toast.makeText(context, "Please Select a Catagory", Toast.LENGTH_SHORT).show();
                //} else
                  //  Toast.makeText(context, "Please Select a Group", Toast.LENGTH_SHORT).show();
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
   /*     Snackbar snackbar = Snackbar
                .make(coordinator, "Changes Will be Discarded", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));

                    }
                });
        snackbar.show();*/
        finish();
        startActivity(new Intent(Add_Product.this, Product_Order_Lookup.class));
        finish();

    }

    private void cleanScreen() {
        productSearch.setText("");
        productGroup.setText("");
        productCategory.setText("");
        productUnit.setText("");
        productPrice.setText("");
        productQuantity.setText("");
        productStock.setText("");
    }
}
