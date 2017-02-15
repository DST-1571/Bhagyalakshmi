package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.location.MapsActivity;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.CompanyModel;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.models.LocationModel;
import com.sourceedge.bhagyalakshmi.orders.models.OffineModel_Category;
import com.sourceedge.bhagyalakshmi.orders.models.OfflineModel_Distributor;
import com.sourceedge.bhagyalakshmi.orders.models.TrackModel;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.PlaceOrder;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.models.tempjson;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Success;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Add_Product;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Search_Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard.total_order_count;
import static com.sourceedge.bhagyalakshmi.orders.support.Class_Genric.MyPref;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_SyncApi {
    static SharedPreferences sharedPreferences;
    static int mStatusCode = 0;
    static Gson gson;
    static Class_DBHelper dbHelper;
    ArrayList<TrackModel> model;
    public static OfflineModel_Distributor tempOfflineDistributor;

    //Note API Calls
    public static void LoginApi(final Context context, final EditText username, final EditText password) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("username", username.getText().toString().trim()));
        params.add(new KeyValuePair("password", password.getText().toString().trim()));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Login, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            gson = new Gson();
                            JSONObject jsonObject = new JSONObject(response);
                            Class_ModelDB.setCurrentuserModel(gson.fromJson(jsonObject.toString(), CurrentUser.class));
                            editor.putString(Class_Genric.Sp_Status, "LoggedIn");
                            editor.commit();
                            dbHelper.saveCurrentUser();
                            dbHelper.loadCurrentUser();
                            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            Class_Static.home = true;
                            ((Activity) context).startActivity(new Intent(context, Dashboard.class));
                            ((Activity) context).finish();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                username.setError("Username or Password Invalid");
                                break;
                            case 401:
                                password.setError("Password Invalid");
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }


    public static void DistributorApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
    /*    params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_RolesTS, context)));*/
        params.add(new KeyValuePair("name", Class_ModelDB.getCurrentuserModel().getName()));
        Class_Genric.ShowDialog(context, "Loading Distributors.", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Distributor1, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Role> rolelist = new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            rolelist = gson.fromJson(jsonArray.toString(), listType);
                            dbHelper.loadRole();

                            ArrayList<Role> NewRoleList = new ArrayList<Role>();
                            for (Role newrole : rolelist) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getRoleList().size(); i++) {
                                    if (newrole.getId().matches(Class_ModelDB.getRoleList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getRoleList().remove(Class_ModelDB.getRoleList().get(i));
                                        Class_ModelDB.getRoleList().add(i, newrole);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewRoleList.add(newrole);
                            }
                            ArrayList<Role> FinalRoleList = new ArrayList<Role>();
                            FinalRoleList = (ArrayList<Role>) Class_ModelDB.getRoleList().clone();
                            for (Role newroles : NewRoleList) {
                                FinalRoleList.add(newroles);
                            }

                            Class_ModelDB.setRoleList(rolelist);

                            dbHelper.saveRole();
                            dbHelper.loadRole();

                            Class_Static.viewOrderedProducts = false;
                            Class_Static.tempOrderingProduct = new ArrayList<Product>();
                            Class_Static.Flag_SEARCH = Class_Static.SEARCHCUSTOMER;
                            ((Activity) context).startActivity(new Intent(context, Search_Customer.class));

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableRole)) {
                        dbHelper.loadRole();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };


        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void CatagoryApi(final Context context, String Distributorid) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        //params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_CatagoriesTS, context)));
        params.add(new KeyValuePair("Distributorid", Distributorid));
        Class_Genric.ShowDialog(context, "Loading Catagories.", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Category, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Catagories> model = new ArrayList<Catagories>();
                            Type listType = new TypeToken<ArrayList<Catagories>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            /*dbHelper.loadcatagory();

                            ArrayList<Catagories> NewCategoriesList = new ArrayList<Catagories>();
                            for (Catagories newcategory : model) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getCatagoryList().size(); i++) {
                                    if (newcategory.getName().matches(Class_ModelDB.getCatagoryList().get(i).getName())) {
                                        matched = true;
                                        Class_ModelDB.getCatagoryList().remove(Class_ModelDB.getCatagoryList().get(i));
                                        Class_ModelDB.getCatagoryList().add(i, newcategory);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewCategoriesList.add(newcategory);
                            }
                            ArrayList<Catagories> FinalCategoryList = new ArrayList<Catagories>();
                            FinalCategoryList = (ArrayList<Catagories>) Class_ModelDB.getCatagoryList().clone();
                            for (Catagories newcat : NewCategoriesList) {
                                FinalCategoryList.add(newcat);
                            }*/
                            Class_ModelDB.setCatagoryList(model);
                            dbHelper.saveCatagories();
                            dbHelper.loadcatagory();
                            if (Class_Static.CURRENTPAGE == Class_Static.DISTRIBUTORLIST) {
                                ((Activity) context).startActivity(new Intent(context, Add_Product.class));
                                ((Activity) context).finish();
                            } else if (Class_Static.CURRENTPAGE == Class_Static.ORDERS) {
                                //Class_SyncApi.LoadOfflineCatagories(context, Class_ModelDB.getCurrentuserModel().getId());
                                Class_Static.viewOrderedProducts = false;
                                Class_Static.tempOrderingProduct = new ArrayList<Product>();
                                Class_Static.editProductOrder = false;
                                ((Activity) context).startActivity(new Intent(context, Add_Product.class));

                            }

                            /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getCatagoryList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getCatagoryList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_CatagoriesTS, Collections.max(Class_Static.timestamplist), context);*/

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else if (error instanceof NoConnectionError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void ProductApi(final Context context, String id, String name) {
        //GroupsApi(context);
        //CatagoryApi(context);
        dbHelper = new Class_DBHelper(context);
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Distributorid", id));
        params.add(new KeyValuePair("Category", name));
        Class_Genric.ShowDialog(context, "Loading Products.", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Product1, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Product> model = new ArrayList<Product>();
                            Type listType = new TypeToken<ArrayList<Product>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                           /* dbHelper.loadProduct();


                            ArrayList<Product> NewProductsList = new ArrayList<Product>();

                            for (Product newprod : model) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getProductList().size(); i++) {
                                    if (newprod.getId().matches(Class_ModelDB.getProductList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getProductList().remove(Class_ModelDB.getProductList().get(i));
                                        Class_ModelDB.getProductList().add(i, newprod);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewProductsList.add(newprod);
                            }
                            ArrayList<Product> FinalProductList = new ArrayList<Product>();
                            FinalProductList = (ArrayList<Product>) Class_ModelDB.getProductList().clone();
                            for (Product newproduct : NewProductsList) {
                                FinalProductList.add(newproduct);
                            }*/
                            Class_ModelDB.setProductList(model);

                            dbHelper.saveProduct();
                            dbHelper.loadProduct();
                           /* String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                            sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Class_Genric.Sp_SyncDate, date);
                            editor.commit();*/


                            /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getProductList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getProductList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_ProductsTS, Collections.max(Class_Static.timestamplist), context);*/

                            ((Activity) context).finish();


                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else if (error instanceof NoConnectionError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void OrderApi(final Context context) {
        if (Order.LoadOrders) {
            dbHelper = new Class_DBHelper(context);
            RequestQueue queue = Volley.newRequestQueue(context);
            ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
            params.add(new KeyValuePair("name", Class_ModelDB.getCurrentuserModel().getName()));
            Class_Genric.ShowDialog(context, "Loading Orders.", true);
            StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Order1, params), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null) {
                        Class_Genric.ShowDialog(context, "Loading...", false);
                        switch (mStatusCode) {
                            case 200:
                                try {
                                    gson = new Gson();
                                    ArrayList<Order> orders = new ArrayList<Order>();
                                    Type listType = new TypeToken<ArrayList<Order>>() {
                                    }.getType();
                                    JSONArray jsonObject = new JSONArray(response);
                                    orders = gson.fromJson(jsonObject.toString(), listType);
                               /* dbHelper.loadOrders();

                                ArrayList<Order> NewOrdersList = new ArrayList<Order>();

                                for (Order neworder : orders) {
                                    boolean matched = false;
                                    for (int i = 0; i < Class_ModelDB.getOrderList().size(); i++) {
                                        if (neworder.getId().matches(Class_ModelDB.getOrderList().get(i).getId())) {
                                            matched = true;
                                            Class_ModelDB.getOrderList().remove(Class_ModelDB.getOrderList().get(i));
                                            Class_ModelDB.getOrderList().add(i, neworder);
                                            break;
                                        }
                                    }
                                    if (!matched)
                                        NewOrdersList.add(neworder);
                                }
                                ArrayList<Order> FinalOrderList = new ArrayList<Order>();
                                FinalOrderList = (ArrayList<Order>) Class_ModelDB.getOrderList().clone();
                                for (Order neworders : NewOrdersList) {
                                    FinalOrderList.add(neworders);
                                }*/
                                    Class_ModelDB.setOrderList(orders);
                                    dbHelper.saveOrders();
                                    dbHelper.loadOrders();
                                    Order.LoadOrders = false;
                                    //int s= Class_ModelDB.getOrderList().size();
                                    DashboardAPI(context);
                                /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                                for (int j = 0; j < Class_ModelDB.getOrderList().size(); j++)
                                    Class_Static.timestamplist.add(Class_ModelDB.getOrderList().get(j).getTimeStamp());
                                if(Class_Static.timestamplist.size()>0)
                                Class_Genric.setTimeStamp(Class_Genric.Sp_OrdersTS, Collections.max(Class_Static.timestamplist), context);
*/
                                    break;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Class_Genric.ShowDialog(context, "Loading...", false);
                    DashboardAPI(context);
                    if (error instanceof TimeoutError) {
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableOrders)) {
                            dbHelper.loadOrders();
                            Dashboard.animateTextView(0, Class_ModelDB.getOrderList().size(), total_order_count);
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    } else if (error instanceof NoConnectionError) {
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableOrders)) {
                            dbHelper.loadOrders();
                            Dashboard.animateTextView(0, Class_ModelDB.getOrderList().size(), total_order_count);
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    } else {
                        if (error != null && error.networkResponse != null) {
                            mStatusCode = error.networkResponse.statusCode;
                            switch (mStatusCode) {
                                case 400:
                                    Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                    }
                }
            }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(Class_Genric.VolleyTime(postRequest));
        } else
            Dashboard.animateTextView(0, Class_ModelDB.getOrderList().size(), total_order_count);
    }

    public static void ProductPriceApi(final Context context, final Product data, String Distributorid) {
        //GroupsApi(context);
        //CatagoryApi(context);
        dbHelper = new Class_DBHelper(context);
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Distributorid", Distributorid));
        params.add(new KeyValuePair("Description", data.getDescription()));
        params.add(new KeyValuePair("Code", data.getCode()));
        Class_Genric.ShowDialog(context, "Loading Price.", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.ProductPrice, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            /*ArrayList<Product> model = new ArrayList<Product>();
                            Type listType = new TypeToken<ArrayList<Product>>() {
                            }.getType();*/
                            String code = new JSONObject(response).optString("Code");
                            String price = new JSONObject(response).optString("Price");
                            Double price_double = new JSONObject(response).optDouble("Price");

                            Class_Static.tempProduct.setProductDetais(data);
                            Class_Static.tempProduct.setPrice(price_double);
                            Class_Static.tempProduct.setAmount(Class_Genric.CalculateAmount(Class_Static.tempProduct.getPrice(), Class_Static.tempProduct.getQuantity(), Class_Static.tempProduct.getTax()));
                            Class_Static.tempProduct.setQuantity(1);
                            Add_Product.productSearch.setText(Class_Static.tempProduct.getDescription());
                            Add_Product.productUnit.setText(Class_Static.tempProduct.getUnits());
                            Add_Product.productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
                            Add_Product.productPrice.setText(price + "");
                            Add_Product.productTax.setText(Class_Static.tempProduct.getTax() + "");
                            Add_Product.productAmount.setText(Class_Genric.CalculateAmount(Class_Static.tempProduct.getPrice(), Class_Static.tempProduct.getQuantity(), Class_Static.tempProduct.getTax()) + "");

                            ((Activity) context).finish();
                            /*JSONObject jsonArray = new JSONObject(response);
                            String s = gson.fromJson(jsonArray.toString(), String.class);*/
                            // dbHelper.loadProduct();


                           /* ArrayList<Product> NewProductsList = new ArrayList<Product>();

                            for (Product newprod : model) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getProductList().size(); i++) {
                                    if (newprod.getId().matches(Class_ModelDB.getProductList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getProductList().remove(Class_ModelDB.getProductList().get(i));
                                        Class_ModelDB.getProductList().add(i, newprod);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewProductsList.add(newprod);
                            }
                            ArrayList<Product> FinalProductList = new ArrayList<Product>();
                            FinalProductList = (ArrayList<Product>) Class_ModelDB.getProductList().clone();
                            for (Product newproduct : NewProductsList) {
                                FinalProductList.add(newproduct);
                            }
                            Class_ModelDB.setProductList(FinalProductList);

                            dbHelper.saveProduct();
                            dbHelper.loadProduct();
                           *//* String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                            sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Class_Genric.Sp_SyncDate, date);
                            editor.commit();*//*


                            *//*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getProductList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getProductList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_ProductsTS, Collections.max(Class_Static.timestamplist), context);*//*

                            ((Activity)context).finish();*/


                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "This Product is UnAvailable", Toast.LENGTH_SHORT).show();
                Class_Genric.ShowDialog(context, "Loading...", false);
               /* if (error instanceof TimeoutError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else if (error instanceof NoConnectionError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();*/
                //  }
            }
        }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void PlaceOrderApiTemp(final Context context, String UserId, String ClientId, ArrayList<Product> orderedProduct, String total, String CompanyName) {
        ArrayList<OrderProduct> products = new ArrayList<OrderProduct>();
       /* String s = Class_Static.tempRole.getTimeStamp()+"";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < orderedProduct.size(); i++) {
            OrderProduct obj = new OrderProduct();
            obj.setProductId(orderedProduct.get(i).getCode());
            obj.setPrice(orderedProduct.get(i).getPrice());
            obj.setAliasflag(orderedProduct.get(i).getAliasflag());
            obj.setQuantity(orderedProduct.get(i).getQuantity());
            obj.setTaxamount(Class_Genric.CalculateTaxAmount(orderedProduct.get(i).getPrice(), orderedProduct.get(i).getQuantity(), orderedProduct.get(i).getTax()));
            obj.setDescription(orderedProduct.get(i).getDescription());
            obj.setUnit(orderedProduct.get(i).getUnits());
            products.add(obj);
        }
        PlaceOrder placeorder = new PlaceOrder();
        placeorder.setUserId(UserId);
        placeorder.setClientId(ClientId);
        placeorder.setTotalAmount(Double.valueOf(total.substring(8))); //to Trim "Total : 1871.50"
        placeorder.setCompany(CompanyName);
        placeorder.setProducts(products);
        placeorder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class_Genric.ShowDialog(context, "Placing Order .", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.PlaceOrder1, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        //Class_SyncApi.OrderApi(context);
                        Order.LoadOrders = true;
                        Intent intent = new Intent(context, Order_Success.class);
                        intent.putExtra("OrderNumber", response.optString("OrderNumber"));
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                try {
                                    String ErrorMessage="";
                                    if (error.networkResponse != null)
                                        ErrorMessage = (String) (new JSONObject(new String(error.networkResponse.data))).get("Status");
                                    Toast.makeText(context, ErrorMessage, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(context, "Error Response From Server", Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    } else
                        Toast.makeText(context, "Error Response From Server", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(jsonObjectRequest));
    }

    public static void PlaceDraftOrderApi(final Context context, final int pos, final int OrdersCount) {
        if (pos >= OrdersCount) {

            ArrayList<Order> TempDraftorderList = new ArrayList<Order>();
            TempDraftorderList= (ArrayList<Order>) Class_ModelDB.DraftorderList.clone();
            for (Order order : TempDraftorderList) {
                if (order.getStatus().matches("Pending."))
                    Class_ModelDB.DraftorderList.remove(order);
            }
            dbHelper = new Class_DBHelper(context);
            if (Class_ModelDB.DraftorderList.size() > 0)
                dbHelper.saveDraftOrders();
            else
                dbHelper.DeleteDraftOrders();
            Order.LoadOrders = true;
            Class_SyncApi.OrderApi(context);
            return;
        }
        String UserId = Class_ModelDB.DraftorderList.get(pos).getUser().getName();
        String ClientId = Class_ModelDB.DraftorderList.get(pos).getClient().getId();
        ArrayList<OrderProduct> orderedProduct = Class_ModelDB.DraftorderList.get(pos).getProducts();
        String total = Class_ModelDB.DraftorderList.get(pos).getTotalAmount() + "";
        ArrayList<OrderProduct> products = new ArrayList<OrderProduct>();
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        PlaceOrder placeorder = new PlaceOrder();
        placeorder.setUserId(UserId);
        placeorder.setClientId(ClientId);
        placeorder.setCompany(Class_ModelDB.DraftorderList.get(pos).getCompany());
        placeorder.setOrderDate(Class_ModelDB.DraftorderList.get(pos).getOrderDate().substring(0, 10));
        placeorder.setTotalAmount(Double.valueOf(total));
        placeorder.setProducts(orderedProduct);
        placeorder.setTimestamp(Class_ModelDB.DraftorderList.get(pos).getOrderDate());
        JSONObject jsonObject = new JSONObject();
        try {
            gson = new Gson();
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class_Genric.ShowDialog(context, "Placing Offline Orders...", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.PlaceOrderDraft, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        Class_ModelDB.DraftorderList.get(pos).setStatus("Pending.");
                        PlaceDraftOrderApi(context, pos + 1, OrdersCount);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                String ErrorMessage = "";
                try {
                    if (error.networkResponse != null)
                        ErrorMessage = (String) (new JSONObject(new String(error.networkResponse.data))).get("Status");
                    Class_ModelDB.DraftorderList.get(pos).setStatus("Failed - " + ErrorMessage);
                    PlaceDraftOrderApi(context, pos + 1, OrdersCount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                }
            }
        }) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(jsonObjectRequest));
    }

    public static void ComapnyApi(final Context context) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Company, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Type listType = new TypeToken<ArrayList<CompanyModel>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            Class_ModelDB.CompanyList = gson.fromJson(jsonArray.toString(), listType);
                            Product_Order_Lookup.CompanyDialog(context);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {

                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    // Note : Experiment
    public static void PlaceOrderApi(final Context context, String UserId, String ClientId, ArrayList<Product> orderedProduct, String total) {
        ArrayList<OrderProduct> products = new ArrayList<OrderProduct>();
       /* String s = Class_Static.tempRole.getTimeStamp()+"";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < orderedProduct.size(); i++) {
            OrderProduct obj = new OrderProduct();
            obj.setProductId(orderedProduct.get(i).getCode());
            obj.setPrice(orderedProduct.get(i).getPrice());
            obj.setQuantity(orderedProduct.get(i).getQuantity());
            obj.setUnit(orderedProduct.get(i).getUnits());
            products.add(obj);
        }
        PlaceOrder placeorder = new PlaceOrder();
        placeorder.setUserId(UserId);
        placeorder.setClientId(ClientId);
        placeorder.setTotalAmount(Double.valueOf(total.substring(8)));
        placeorder.setProducts(products);
        placeorder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject = new JSONObject(gson.toJson(new tempjson(placeorder)));
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Class_Genric.ShowDialog(context, "Placing Order .", true);
        final JSONObject finalJsonObject = jsonObject;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Class_Urls.PlaceOrder1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                JSONObject jsonObject1 = null;
                switch (mStatusCode) {
                    case 200:
                        try {
                            jsonObject1 = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Class_SyncApi.OrderApi(context);
                        Order.LoadOrders = true;
                        Intent intent = new Intent(context, Order_Success.class);
                        intent.putExtra("OrderNumber", jsonObject1.optString("OrderNumber"));
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("json", finalJsonObject.toString());
                return params;
            }

            @Override
            public byte[] getBody() {
                String data = "json=" + finalJsonObject.toString();
                try {
                    return data.getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(stringRequest);
    }

    public static void PlaceOrderApiTemp2(final Context context, String UserId, String ClientId, ArrayList<Product> orderedProduct, String total) {
        ArrayList<OrderProduct> products = new ArrayList<OrderProduct>();
       /* String s = Class_Static.tempRole.getTimeStamp()+"";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < orderedProduct.size(); i++) {
            OrderProduct obj = new OrderProduct();
            obj.setProductId(orderedProduct.get(i).getCode());
            obj.setPrice(orderedProduct.get(i).getPrice());
            obj.setQuantity(orderedProduct.get(i).getQuantity());
            obj.setUnit(orderedProduct.get(i).getUnits());
            products.add(obj);
        }
        PlaceOrder placeorder = new PlaceOrder();
        placeorder.setUserId(UserId);
        placeorder.setClientId(ClientId);
        placeorder.setTotalAmount(Double.valueOf(total.substring(8)));
        placeorder.setProducts(products);
        placeorder.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JSONObject jsonObject = new JSONObject();
        try {
            //jsonObject = new JSONObject(gson.toJson(new tempjson(placeorder)));
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Class_Genric.ShowDialog(context, "Placing Order .", true);
        final JSONObject finalJsonObject = jsonObject;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Class_Urls.PlaceOrder1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                JSONObject jsonObject1 = null;
                switch (mStatusCode) {
                    case 200:
                        try {
                            jsonObject1 = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Class_SyncApi.OrderApi(context);
                        Order.LoadOrders = true;
                        Intent intent = new Intent(context, Order_Success.class);
                        intent.putExtra("OrderNumber", jsonObject1.optString("OrderNumber"));
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("json", finalJsonObject.toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(stringRequest);
    }

    //Note : Support
    private static void DashboardAPI(Context context) {
        Dashboard.animateTextView(0, Class_ModelDB.getOrderList().size(), total_order_count);
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                //Class_SyncApi.SalesPersonListApi(context);
                /*   Class_Static.Flag_SEARCH = Class_Static.SEARCHSALESPERSON;
                startActivity(new Intent(Dashboard.this, Search_Customer.class));*/
                break;
            case Class_Genric.DISTRIBUTORSALES:
                Class_SyncApi.RetailerApi(context);
                break;
            case Class_Genric.DISTRIBUTOR:

                break;
            case Class_Genric.SALESMAN:

                break;
        }
    }


    public static void ChangePasswordApi(final Context context, EditText oldPassword, EditText newPassword) {

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("OldPassword", oldPassword.getText().toString()));
        params.add(new KeyValuePair("NewPassword", newPassword.getText().toString()));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.ChangePassword, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void DistributorIdApi(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id", "0d87550e-71ee-460a-a02a-d2ddc1cfcaa0"));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.DistributorId, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Role role = new Role();
                            JSONObject jsonObject = new JSONObject(response);
                            role = gson.fromJson(jsonObject.toString(), Role.class);
                            Class_ModelDB.setRoleModel(role);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token or Invalid Id", Toast.LENGTH_SHORT).show();
                                break;
                            case 404:
                                Toast.makeText(context, "Distributor not Found", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void RetailerApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_RolesTS, context)));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.Retailer, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Role> model = new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            dbHelper.loadRole();

                            ArrayList<Role> NewRoleList = new ArrayList<Role>();
                            for (Role newrole : model) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getRoleList().size(); i++) {
                                    if (newrole.getId().matches(Class_ModelDB.getRoleList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getRoleList().remove(Class_ModelDB.getRoleList().get(i));
                                        Class_ModelDB.getRoleList().add(i, newrole);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewRoleList.add(newrole);
                            }
                            ArrayList<Role> FinalRoleList = new ArrayList<Role>();
                            FinalRoleList = (ArrayList<Role>) Class_ModelDB.getRoleList().clone();
                            for (Role newroles : NewRoleList) {
                                FinalRoleList.add(newroles);
                            }
                            Class_ModelDB.setRoleList(FinalRoleList);

                            dbHelper.saveRole();
                            dbHelper.loadRole();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:SS");

                            Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getRoleList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getRoleList().get(j).getTimeStamp());
                            if (Class_Static.timestamplist.size() > 0)
                                Class_Genric.setTimeStamp(Class_Genric.Sp_RolesTS, Collections.max(Class_Static.timestamplist), context);

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableRole)) {
                        dbHelper.loadRole();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                    Toast.makeText(context, "TimeOut!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableRole)) {
                        dbHelper.loadRole();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token or Invalid Id", Toast.LENGTH_SHORT).show();
                                break;
                            case 404:
                                Toast.makeText(context, "Distributor not Found", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();


                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void RetailerIdApi(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id", "461dbfb3-8ba4-4bf2-ab16-cb8b2b019e8b"));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.RetailerId, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Role role = new Role();
                            JSONObject jsonObject = new JSONObject(response);
                            role = gson.fromJson(jsonObject.toString(), Role.class);
                            Class_ModelDB.setRoleModel(role);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token or Invalid Id", Toast.LENGTH_SHORT).show();
                                break;
                            case 404:
                                Toast.makeText(context, "Retailer not Found", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    private static void SyncNow(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("OutDated Data")
                .setMessage("Please Connect to a network and Synchronize with the Server")
                .setCancelable(false)
                .setPositiveButton(R.string.Sync_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //ProductApi(context);
                    }
                })
                .setNegativeButton(R.string.Exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private static boolean loadOffileProducts_Time(Context context) {
        if (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype()) == Class_Genric.DISTRIBUTORSALES) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
                String SyncDateStr = sharedPreferences.getString(Class_Genric.Sp_SyncDate, "");
                String todayStr = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                Date SyncDate = null;
                Date Today = null;
                SyncDate = sdf.parse(SyncDateStr);
                Today = sdf.parse(todayStr);
                if (Today.equals(SyncDate)) {
                    return true;
                } else
                    return false;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else
            return true;
    }

    public static void StockApi(final Context context, final String id, final int currentQty) {
        String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }


        if (Class_Genric.NetAvailable(context)) {
            RequestQueue queue = Volley.newRequestQueue(context);
            ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
            params.add(new KeyValuePair("Id", id));
            Class_Genric.ShowDialog(context, "Loading...", true);
            StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.Stock, params), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Class_Genric.ShowDialog(context, "Loading...", false);
                    switch (mStatusCode) {
                        case 200:
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Double d = Double.valueOf(jsonObject.optString("Stock"));
                                Class_Static.Stock = d.intValue();
                                for (int i = 0; i < Class_ModelDB.getProductList().size(); i++) {
                                    if (Class_ModelDB.getProductList().get(i).getCode().matches(id)) {
                                        Class_ModelDB.getProductList().get(i).setStock(d.intValue());
                                        break;
                                    }
                                }
                                Class_DBHelper class_dbHelper = new Class_DBHelper(context);
                                class_dbHelper.saveProduct();

                                for (Product product : Class_ModelDB.getProductList()) {
                                    if (product.getCategoryId().toString().toLowerCase().matches(Class_Static.tempProduct.getCategoryId().toString().toLowerCase())) {
                                        Class_Static.tempProductList.add(product);
                                    }
                                }
                                Class_Static.AvailableStock = Class_Static.Stock - currentQty;
                                if (Class_Static.AvailableStock > 0) {
                                    Class_Static.tempProduct.setQuantity(1);
                                } else {
                                    Class_Static.tempProduct.setQuantity(0);
                                }
                                Add_Product.productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
                                Add_Product.productStock.setText("Stocks Available : " + Class_Static.AvailableStock);
                                ((Activity) context).finish();
                                break;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Class_Genric.ShowDialog(context, "Loading...", false);
                    if (error instanceof TimeoutError)
                        if (error instanceof NoConnectionError) {
                            Class_Genric.NetCheck(context);
                        } else {
                            if (error != null && error.networkResponse != null) {
                                mStatusCode = error.networkResponse.statusCode;
                                switch (mStatusCode) {
                                    case 400:
                                        Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } else
                                Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                        }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                    return params;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    mStatusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };
            queue.add(postRequest);
        } else {
            int d = (Class_ModelDB.getSingleProduct(id)).getStock();
            Class_Static.Stock = d;
            Class_Static.AvailableStock = Class_Static.Stock - currentQty;
            if (Class_Static.AvailableStock > 0) {
                Class_Static.tempProduct.setQuantity(1);
            } else {
                Class_Static.tempProduct.setQuantity(0);
            }
            Add_Product.productQuantity.setText(Class_Static.tempProduct.getQuantity() + "");
            Add_Product.productStock.setText("Stocks Available : " + Class_Static.AvailableStock);
            ((Activity) context).finish();
        }

    }

    public static void GroupsApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_GroupsTS, context)));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.Section, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Sections> model = new ArrayList<Sections>();
                            Type listType = new TypeToken<ArrayList<Sections>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            dbHelper.loadSections();

                            ArrayList<Sections> NewSectionsList = new ArrayList<Sections>();

                            for (Sections newsec : model) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getSectionList().size(); i++) {
                                    if (newsec.getId().matches(Class_ModelDB.getSectionList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getSectionList().remove(Class_ModelDB.getSectionList().get(i));
                                        Class_ModelDB.getSectionList().add(i, newsec);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewSectionsList.add(newsec);
                            }
                            ArrayList<Sections> FinalSectionList = new ArrayList<Sections>();
                            FinalSectionList = (ArrayList<Sections>) Class_ModelDB.getSectionList().clone();
                            for (Sections newsection : NewSectionsList) {
                                FinalSectionList.add(newsection);
                            }
                            Class_ModelDB.setSectionList(FinalSectionList);

                            dbHelper.saveSections();
                            dbHelper.loadSections();

                            Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getSectionList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getSectionList().get(j).getTimeStamp());
                            if (Class_Static.timestamplist.size() > 0)
                                Class_Genric.setTimeStamp(Class_Genric.Sp_GroupsTS, Collections.max(Class_Static.timestamplist), context);

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableGroup)) {
                        dbHelper.loadSections();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else if (error instanceof NoConnectionError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableGroup)) {
                        dbHelper.loadSections();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void ProductIdApi(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id", "effd9b76-4116-4272-93bd-1484361727a8"));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.ProductId, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Product product = new Product();
                            JSONObject jsonObject = new JSONObject(response);
                            product = gson.fromJson(jsonObject.toString(), Product.class);
                            Class_ModelDB.setProductModel(product);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void OrderIdApi(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id", "6724af08-b00b-4294-bc5d-e0417334bb33"));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.OrderId, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Order order = new Order();
                            JSONObject jsonObject = new JSONObject(response);
                            order = gson.fromJson(jsonObject.toString(), Order.class);
                            Class_ModelDB.setOrderModel(order);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token or Invalid Id", Toast.LENGTH_SHORT).show();
                                break;
                            case 404:
                                Toast.makeText(context, "Distributor not Found", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void Location(final Context context, Double latitude, Double longitude, String date) {

        RequestQueue queue = Volley.newRequestQueue(context);
        LocationModel locationmodel = new LocationModel();
        locationmodel.setLatitude(latitude);
        locationmodel.setLongitude(longitude);
        locationmodel.setDate(date);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(new Gson().toJson(locationmodel));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.Location, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                switch (mStatusCode) {
                    case 200:
                        Log.d("Success", "OK");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
    }

    public static void SalesPersonListApi(final Context context) {

        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_RolesTS, context)));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.SalesPerson, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Role> rolelist = new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            rolelist = gson.fromJson(jsonArray.toString(), listType);
                            dbHelper.loadRole();

                            ArrayList<Role> NewRoleList = new ArrayList<Role>();
                            for (Role newrole : rolelist) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getRoleList().size(); i++) {
                                    if (newrole.getId().matches(Class_ModelDB.getRoleList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getRoleList().remove(Class_ModelDB.getRoleList().get(i));
                                        Class_ModelDB.getRoleList().add(i, newrole);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewRoleList.add(newrole);
                            }
                            ArrayList<Role> FinalRoleList = new ArrayList<Role>();
                            FinalRoleList = (ArrayList<Role>) Class_ModelDB.getRoleList().clone();
                            for (Role newroles : NewRoleList) {
                                FinalRoleList.add(newroles);
                            }
                            Class_ModelDB.setRoleList(FinalRoleList);

                            dbHelper.saveRole();
                            dbHelper.loadRole();
                            Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getRoleList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getRoleList().get(j).getTimeStamp());
                            if (Class_Static.timestamplist.size() > 0)
                                Class_Genric.setTimeStamp(Class_Genric.Sp_RolesTS, Collections.max(Class_Static.timestamplist), context);
                            Activity a = (Activity) context;
                            Class_Static.Flag_SEARCH = Class_Static.SEARCHSALESPERSON;
                            a.startActivity(new Intent(a, Search_Customer.class));
                            a.startActivity(new Intent(a, MapsActivity.class));
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }

    public static void TrackLocationApi(final Context context, String id) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("UserId", id));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.TrackLocation, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.ShowDialog(context, "Loading...", false);

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<TrackModel> model = new ArrayList<TrackModel>();
                            Type listType = new TypeToken<ArrayList<TrackModel>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        context.startActivity(new Intent(context, MapsActivity.class));
                        Log.d("Success", "Ok");
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }


    //Note : Offline save Opertaions

    public static void loadallcategories(int pos, Context context) {
        if (Class_ModelDB.getRoleList().size() == 0) {
            Class_SyncApi.DistributorApiSync(context);
        } else {
            if (pos < Class_ModelDB.getRoleList().size()) {
                tempOfflineDistributor = new OfflineModel_Distributor();
                RecursiveCatagoryApi(context, pos);
            } else {
                loadallProducts(context, 0, 0);
            }
        }

    }

    public static void RecursiveCatagoryApi(final Context context, final int DistributorPos) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        //params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_CatagoriesTS, context)));
        params.add(new KeyValuePair("Distributorid", Class_ModelDB.getRoleList().get(DistributorPos).getId()));
        Class_Genric.SYNCDialog(context, "Loading Category for distributor : " + DistributorPos, true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Category, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<OffineModel_Category> model = new ArrayList<OffineModel_Category>();
                            Type listType = new TypeToken<ArrayList<OffineModel_Category>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            tempOfflineDistributor = new OfflineModel_Distributor(Class_ModelDB.getRoleList().get(DistributorPos));
                            tempOfflineDistributor.setCategories(model);
                            Class_ModelDB.OfflineDistributors.add(tempOfflineDistributor);
                            loadallcategories(DistributorPos + 1, context);
                            /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getCatagoryList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getCatagoryList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_CatagoriesTS, Collections.max(Class_Static.timestamplist), context);*/

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadallcategories(DistributorPos, context);
                Class_Genric.SYNCDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else if (error instanceof NoConnectionError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void DistributorRecursiveCatagoryApi(final Context context, String DistributorId) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        //params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_CatagoriesTS, context)));
        params.add(new KeyValuePair("Distributorid", DistributorId));
        Class_Genric.SYNCDialog(context, "Loading Category:", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Category, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<OffineModel_Category> model = new ArrayList<OffineModel_Category>();
                            Type listType = new TypeToken<ArrayList<OffineModel_Category>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            tempOfflineDistributor = new OfflineModel_Distributor(Class_ModelDB.getCurrentuserModel());
                            tempOfflineDistributor.setCategories(model);
                            Class_ModelDB.OfflineDistributors.add(tempOfflineDistributor);
                            loadallProducts(context, 0, 0);
                            /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getCatagoryList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getCatagoryList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_CatagoriesTS, Collections.max(Class_Static.timestamplist), context);*/

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.SYNCDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else if (error instanceof NoConnectionError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableCatagory)) {
                        dbHelper.loadcatagory();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();

                }
            }
        }) {
            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static OfflineModel_Distributor OfflineDist = new OfflineModel_Distributor();

    public static void loadallProducts(Context context, int Distributorpos, int Categorypos) {


        if ((Distributorpos) < Class_ModelDB.OfflineDistributors.size()) {
            if (Categorypos < Class_ModelDB.OfflineDistributors.get(Distributorpos).getCategories().size()) {
                if (Categorypos == 0) {
                    OfflineDist = new OfflineModel_Distributor();
                    OfflineDist = Class_ModelDB.OfflineDistributors.get(Distributorpos);
                }
                RecursiveProductApi(context, Distributorpos, Categorypos);
            } else if (Distributorpos < Class_ModelDB.OfflineDistributors.size()) {
                dbHelper = new Class_DBHelper(context);
                dbHelper.saveOfflineData(OfflineDist);
                loadallProducts(context, Distributorpos + 1, 0);
            } else {
                //dbHelper = new Class_DBHelper(context);
                //dbHelper.saveOfflineData();
                dbHelper = new Class_DBHelper(context);
                dbHelper.saveOfflineData(OfflineDist);
                Class_Genric.saveOfflineTime(context);
                LoadOfflineComapnyApi(context);
            }
        } else {
            dbHelper = new Class_DBHelper(context);
            //dbHelper.saveOfflineData();
            LoadOfflineComapnyApi(context);
            Class_Genric.saveOfflineTime(context);
            //Class_Genric.SYNCDialog(context, "Loading...", false);
        }

    }

    public static void RecursiveProductApi(final Context context, final int Distributorpos, final int Categorypos) {
        //GroupsApi(context);
        //CatagoryApi(context);
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Distributorid", Class_ModelDB.OfflineDistributors.get(Distributorpos).getId()));
        params.add(new KeyValuePair("Category", Class_ModelDB.OfflineDistributors.get(Distributorpos).getCategories().get(Categorypos).getName()));
        Class_Genric.SYNCDialog(context, "Loading Products for Distributor : " + Distributorpos + " and Category : " + Categorypos + "/" + Class_ModelDB.OfflineDistributors.get(Distributorpos).getCategories().size(), true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Product1, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Product> model = new ArrayList<Product>();
                            Type listType = new TypeToken<ArrayList<Product>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            model = gson.fromJson(jsonArray.toString(), listType);
                            //Class_ModelDB.OfflineDistributors.get(Distributorpos).getCategories().get(Categorypos).setProducts(model);
                            OfflineDist.getCategories().get(Categorypos).setProducts(model);
                            /*dbHelper = new Class_DBHelper(context);
                            dbHelper.saveOfflineData(OfflineDist);*/
                            //saveoffline
                            loadallProducts(context, Distributorpos, Categorypos + 1);
                           /* String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                            sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Class_Genric.Sp_SyncDate, date);
                            editor.commit();*/


                            /*Class_Static.timestamplist = new ArrayList<BigInteger>();
                            for (int j = 0; j < Class_ModelDB.getProductList().size(); j++)
                                Class_Static.timestamplist.add(Class_ModelDB.getProductList().get(j).getTimeStamp());
                            if(Class_Static.timestamplist.size()>0)
                            Class_Genric.setTimeStamp(Class_Genric.Sp_ProductsTS, Collections.max(Class_Static.timestamplist), context);*/


                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadallProducts(context, Distributorpos, Categorypos + 1);
                Class_Genric.SYNCDialog(context, "Loading...", false);
                if (error instanceof TimeoutError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else if (error instanceof NoConnectionError) {
                    if (loadOffileProducts_Time(context))
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
                        } else {
                            Class_Genric.NetCheck(context);
                        }
                    else SyncNow(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", Class_ModelDB.getCurrentuserModel().getToken().toString());
                return params;
            }*/

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(Class_Genric.VolleyTime(postRequest));
    }

    public static void LoadOfflineComapnyApi(final Context context) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        Class_Genric.SYNCDialog(context, "Loading Comapany List...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Company, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Class_Genric.SYNCDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            Type listType = new TypeToken<ArrayList<CompanyModel>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            Class_ModelDB.CompanyList = gson.fromJson(jsonArray.toString(), listType);
                            sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Class_Genric.Sp_Companies, gson.toJsonTree(Class_ModelDB.CompanyList).getAsJsonArray().toString());
                            editor.commit();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.SYNCDialog(context, "Loading...", false);
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Class_Genric.NetCheck(context);
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {

                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(postRequest);
    }


    //Note : Offline load Opertaions
    public static void LoadOfflineDistributors(Context context) {
        if (Class_Genric.CheckOfflineTime(context)) {
            dbHelper = new Class_DBHelper(context);
            dbHelper.loadOfflineDistributorList();
            Class_Static.viewOrderedProducts = false;
            Class_Static.tempOrderingProduct = new ArrayList<Product>();
            Class_Static.Flag_SEARCH = Class_Static.SEARCHCUSTOMER;
            ((Activity) context).startActivity(new Intent(context, Search_Customer.class));
        } else
            Toast.makeText(context, "OfflineSessionTimeOut", Toast.LENGTH_SHORT).show();


    }

    public static void LoadOfflineProducts(Context mContext, String Distributorid, String Catagoryname) {
        if (Class_Genric.CheckOfflineTime(mContext)) {
            dbHelper = new Class_DBHelper(mContext);
            dbHelper.loadOfflineDistributorDataData(Distributorid);
            ArrayList<Product> model = new ArrayList<Product>();
            for (OffineModel_Category tempcatagories : Class_ModelDB.OfflineDistributor.getCategories()) {
                if (Catagoryname.matches(tempcatagories.getName()))
                    model = (tempcatagories.getProducts());
            }
            Class_ModelDB.setProductList(model);
            ((Activity) mContext).finish();
        } else
            Toast.makeText(mContext, "OfflineSessionTimeOut", Toast.LENGTH_SHORT).show();

    }

    public static void LoadOfflineCatagories(Context context, String DistributorId) {
        if (Class_Genric.CheckOfflineTime(context)) {
            dbHelper = new Class_DBHelper(context);
            dbHelper.loadOfflineDistributorDataData(DistributorId);
            ArrayList<Catagories> catagoryList = new ArrayList<Catagories>();
            for (OffineModel_Category tempcatagories : Class_ModelDB.OfflineDistributor.getCategories()) {
                catagoryList.add(getcatagory(tempcatagories));
            }
            Class_ModelDB.setCatagoryList(catagoryList);
            if (Class_Static.CURRENTPAGE == Class_Static.DISTRIBUTORLIST) {
                ((Activity) context).startActivity(new Intent(context, Add_Product.class));
                ((Activity) context).finish();
            }
            if (Class_Static.CURRENTPAGE == Class_Static.ORDERS) {
                Class_Static.viewOrderedProducts = false;
                Class_Static.tempOrderingProduct = new ArrayList<Product>();
                Class_Static.editProductOrder = false;
                ((Activity) context).startActivity(new Intent(context, Add_Product.class));
            }
        } else
            Toast.makeText(context, "OfflineSessionTimeOut", Toast.LENGTH_SHORT).show();
    }


    //Note : DTO method
    public static Role getRole(OfflineModel_Distributor tempDitributor) {
        Role temprole = new Role();
        temprole.setId(tempDitributor.getId());
        temprole.setName(tempDitributor.getName());
        return temprole;
    }

    private static Catagories getcatagory(OffineModel_Category tempOfflinecatagories) {
        Catagories temp_catagory = new Catagories();
        temp_catagory.setName(tempOfflinecatagories.getName());
        temp_catagory.setId(tempOfflinecatagories.getId());
        temp_catagory.setTimeStamp(tempOfflinecatagories.getTimeStamp());
        return temp_catagory;
    }


    //Note : used to load Roles if not Presernt before Sync
    public static void DistributorApiSync(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
    /*    params.add(new KeyValuePair("TimeStamp", Class_Genric.getTimeStamp(Class_Genric.Sp_RolesTS, context)));*/
        params.add(new KeyValuePair("name", Class_ModelDB.getCurrentuserModel().getName()));
        Class_Genric.SYNCDialog(context, "Loading Distributors.", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl1(Class_Urls.Distributor1, params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode) {
                    case 200:
                        try {
                            gson = new Gson();
                            ArrayList<Role> rolelist = new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {
                            }.getType();
                            JSONArray jsonArray = new JSONArray(response);
                            rolelist = gson.fromJson(jsonArray.toString(), listType);
                            dbHelper.loadRole();

                            ArrayList<Role> NewRoleList = new ArrayList<Role>();
                            for (Role newrole : rolelist) {
                                boolean matched = false;
                                for (int i = 0; i < Class_ModelDB.getRoleList().size(); i++) {
                                    if (newrole.getId().matches(Class_ModelDB.getRoleList().get(i).getId())) {
                                        matched = true;
                                        Class_ModelDB.getRoleList().remove(Class_ModelDB.getRoleList().get(i));
                                        Class_ModelDB.getRoleList().add(i, newrole);
                                        break;
                                    }
                                }
                                if (!matched)
                                    NewRoleList.add(newrole);
                            }
                            ArrayList<Role> FinalRoleList = new ArrayList<Role>();
                            FinalRoleList = (ArrayList<Role>) Class_ModelDB.getRoleList().clone();
                            for (Role newroles : NewRoleList) {
                                FinalRoleList.add(newroles);
                            }

                            Class_ModelDB.setRoleList(rolelist);

                            dbHelper.saveRole();
                            dbHelper.loadRole();
                            loadallcategories(0, context);

                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Class_Genric.SYNCDialog(context, "Loading...", false);
                if (error instanceof NoConnectionError || error instanceof TimeoutError) {
                    if (dbHelper.CheckDataExists(Class_DBHelper.DataTableRole)) {
                        dbHelper.loadRole();
                    } else {
                        Class_Genric.NetCheck(context);
                    }
                } else {
                    if (error != null && error.networkResponse != null) {
                        mStatusCode = error.networkResponse.statusCode;
                        switch (mStatusCode) {
                            case 400:
                                Toast.makeText(context, "Invalid Token", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            }
        }) {

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };


        queue.add(Class_Genric.VolleyTime(postRequest));
    }
}
