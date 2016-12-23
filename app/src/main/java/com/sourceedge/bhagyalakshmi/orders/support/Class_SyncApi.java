package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.changepassword.Change_Password;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.PlaceOrder;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Success;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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

    public static void LoginApi(final Context context, final EditText username, final EditText password) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("UserName", username.getText().toString()));
        params.add(new KeyValuePair("Password", password.getText().toString()));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Genric.generateUrl(Class_Urls.Login, params), new Response.Listener<String>() {
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
                            String s = Class_ModelDB.getCurrentuserModel().getUserType();
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

    public static void DistributorApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        /*ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));*/
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Distributor/*Class_Genric.generateUrl(Class_Urls.Distributor, params)*/, new Response.Listener<String>() {
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
                            for (int i = 0; i < rolelist.size(); i++) {
                                if (rolelist.get(i).getTimeStamp().contains("T")) {
                                    rolelist.get(i).setTimeStamp(rolelist.get(i).getTimeStamp().replace("T", ""));
                                }
                            }
                            Class_ModelDB.setRoleList(rolelist);
                            dbHelper.saveRole();
                            dbHelper.loadRole();

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:SS");

                            for (int j = 0; j < Class_ModelDB.getRoleList().size(); j++) {
                                try {
                                    Date date = format.parse(Class_ModelDB.getRoleList().get(j).getTimeStamp());
                                    Class_Static.timestamplist.add(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            SortedSet<Date> set = new TreeSet<Date>();
                            for (int k = 0; k < Class_Static.timestamplist.size(); k++) {
                                set.add(Class_Static.timestamplist.get(k));
                            }
                            Date max = new Date();
                            if (set.size() > 0)
                                max = set.last();
                            Class_Static.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(max);
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
                if (error instanceof NoConnectionError) {
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
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Retailer/*Class_Genric.generateUrl(Class_Urls.Retailer, params)*/, new Response.Listener<String>() {
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
                            for (int i = 0; i < model.size(); i++) {
                                if (model.get(i).getTimeStamp().contains("T")) {
                                    model.get(i).setTimeStamp(model.get(i).getTimeStamp().replace("T", ""));
                                }
                            }
                            Class_ModelDB.setRoleList(model);
                            dbHelper.saveRole();
                            dbHelper.loadRole();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:SS");
                            for (int j = 0; j < Class_ModelDB.getRoleList().size(); j++) {
                                try {
                                    Date date = format.parse(Class_ModelDB.getRoleList().get(j).getTimeStamp());
                                    Class_Static.timestamplist.add(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            SortedSet<Date> set = new TreeSet<Date>();
                            for (int k = 0; k < Class_Static.timestamplist.size(); k++) {
                                set.add(Class_Static.timestamplist.get(k));
                            }

                            Date max = new Date();
                            if (set.size() > 0)
                                max = set.last();
                            Class_Static.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(max);
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
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
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

    public static void ProductApi(final Context context) {
        GroupsApi(context);
        CatagoryApi(context);
        dbHelper = new Class_DBHelper(context);
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Product/*Class_Genric.generateUrl(Class_Urls.Product, params)*/, new Response.Listener<String>() {
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
                            Class_ModelDB.setProductList(model);
                            dbHelper.saveProduct();
                            dbHelper.loadProduct();

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
                        if (dbHelper.CheckDataExists(Class_DBHelper.DataTableProduct)) {
                            dbHelper.loadProduct();
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

    public static void GroupsApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Section/*Class_Genric.generateUrl(Class_Urls.Product, params)*/, new Response.Listener<String>() {
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
                            Class_ModelDB.setSectionList(model);
                            dbHelper.saveSections();
                            dbHelper.loadSections();
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

    public static void CatagoryApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));
        Class_Genric.ShowDialog(context, "Loading...", true);

        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Catagory/*Class_Genric.generateUrl(Class_Urls.Product, params)*/, new Response.Listener<String>() {
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
                            Class_ModelDB.setCatagoryList(model);
                            dbHelper.saveCatagories();
                            dbHelper.loadcatagory();

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

    public static void PlaceOrderApi(final Context context, String UserId, String ClientId, ArrayList<Product> orderedProduct, String total) {
        ArrayList<OrderProduct> products = new ArrayList<OrderProduct>();
        /*String s = Class_Static.tempRole.getTimeStamp();
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params1 = new ArrayList<KeyValuePair>();
        params1.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));

        for (int i = 0; i < orderedProduct.size(); i++) {
            OrderProduct obj = new OrderProduct();
            obj.setProductId(orderedProduct.get(i).getId());
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
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Class_Genric.ShowDialog(context, "Loading...", true);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Class_Urls.PlaceOrder/*Class_Genric.generateUrl(Class_Urls.PlaceOrder, params1)*/, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Class_Genric.ShowDialog(context, "Loading...", false);
                switch (mStatusCode) {
                    case 200:
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Class_Genric.Sp_OrderNumber, "Your Order Id is "+response.optString("OrderNumber"));
                        editor.putString(Class_Genric.Sp_OrderStatus, response.optString("Status"));
                        editor.commit();
                        ((Activity) context).startActivity(new Intent(context, Order_Success.class));
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
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        queue.add(jsonObjectRequest);
    }

    public static void OrderApi(final Context context) {
        dbHelper = new Class_DBHelper(context);
        /*String s = "2016-12-06T11:29:26";
        if (s.contains("T")) {
            s = s.replace("T", "");
        }*/
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params = new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp", Class_Static.timestamp));
        Class_Genric.ShowDialog(context, "Loading...", true);
        StringRequest postRequest = new StringRequest(Request.Method.GET, Class_Urls.Order/*Class_Genric.generateUrl(Class_Urls.Order, params)*/, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                            Class_ModelDB.setOrderList(orders);
                            dbHelper.saveOrders();
                            dbHelper.loadOrders();
                            Dashboard.animateTextView(0, Class_ModelDB.getOrderList().size(), total_order_count);
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

}
