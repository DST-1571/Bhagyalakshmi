package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.PlaceOrder;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sourceedge.bhagyalakshmi.orders.support.Class_Genric.MyPref;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_SyncApi {
    static SharedPreferences sharedPreferences;
    static int mStatusCode=0;
    static Gson gson;
    static Date date;

    public static void LoginApi(final Context context, final EditText username, final EditText password){
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("UserName",username.getText().toString()));
        params.add(new KeyValuePair("Password",password.getText().toString()));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.Login,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            JSONObject jsonObject=new JSONObject(response);
                            Class_ModelDB.setCurrentuserModel(gson.fromJson(jsonObject.toString(),CurrentUser.class));
                            Toast.makeText(context,"Successfully Logged In",Toast.LENGTH_SHORT).show();
                            ((Activity)context).startActivity(new Intent(context, Dashboard.class));
                            ((Activity)context).finish();
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode = error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        username.setError("Username or Password Invalid");
                        break;
                    case 401:
                        password.setError("Password Invalid");
                        break;
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

    public static void DistributorApi(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Urls.Distributor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            ArrayList<Role> rolelist=new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {}.getType();
                            JSONArray jsonArray=new JSONArray(response);
                            rolelist=gson.fromJson(jsonArray.toString(),listType);
                            Class_ModelDB.setRoleList(rolelist);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id","0d87550e-71ee-460a-a02a-d2ddc1cfcaa0"));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.DistributorId,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            Role role=new Role();
                            JSONObject jsonObject=new JSONObject(response);
                            role=gson.fromJson(jsonObject.toString(),Role.class);
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
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token or Invalid Id",Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(context,"Distributor not Found",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void RetailerApi(final Context context){
        String s="2016-12-06T11:29:26";
        if (s.contains("T"))
        {
            s = s.replace("T", "");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp",s));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.Retailer,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            ArrayList<Role> model=new ArrayList<Role>();
                            Type listType = new TypeToken<ArrayList<Role>>() {}.getType();
                            JSONArray jsonArray=new JSONArray(response);
                            model=gson.fromJson(jsonArray.toString(),listType);
                            Class_ModelDB.setRoleList(model);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token or Invalid Id",Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(context,"Distributor not Found",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void RetailerIdApi(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id","461dbfb3-8ba4-4bf2-ab16-cb8b2b019e8b"));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.RetailerId,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            Role role=new Role();
                            JSONObject jsonObject=new JSONObject(response);
                            role=gson.fromJson(jsonObject.toString(),Role.class);
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
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token or Invalid Id",Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(context,"Retailer not Found",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void ProductApi(final Context context){
        String s="2016-12-06T11:29:26";
        if (s.contains("T"))
        {
            s = s.replace("T", "");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp",s));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.Product,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            ArrayList<Product> model=new ArrayList<Product>();
                            Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
                            JSONArray jsonArray=new JSONArray(response);
                            model=gson.fromJson(jsonArray.toString(),listType);
                            Class_ModelDB.setProductList(model);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void ProductIdApi(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id","effd9b76-4116-4272-93bd-1484361727a8"));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.ProductId,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            Product product=new Product();
                            JSONObject jsonObject=new JSONObject(response);
                            product=gson.fromJson(jsonObject.toString(),Product.class);
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
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void PlaceOrderApi(final Context context){
        String s="2016-12-06T11:29:26";
        if (s.contains("T"))
        {
            s = s.replace("T", "");
        }
        sharedPreferences=context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params1= new ArrayList<KeyValuePair>();
        params1.add(new KeyValuePair("TimeStamp",s));

        OrderProduct obj = new OrderProduct();
        obj.setProductId("effd9b76-4116-4272-93bd-1484361727a8");
        obj.setPrice(10000.00);
        obj.setQuantity(5.00);
        obj.setUnit("KG");
        ArrayList<OrderProduct> products=new ArrayList<OrderProduct>();
        products.add(obj);
        PlaceOrder placeorder = new PlaceOrder();
        placeorder.setUserId("776a663c-71e2-49e4-bda2-70d992c921ed");
        placeorder.setClientId("f15e1cb9-997c-4848-81d4-ffe89a46e4be");
        placeorder.setTotalAmount(1000.00);
        placeorder.setProducts(products);
        JSONObject jsonObject =new JSONObject();
        try {
            jsonObject = new JSONObject(gson.toJson(placeorder));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, Class_Genric.generateUrl(Class_Urls.PlaceOrder, params1),jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                switch (mStatusCode){
                    case 200:
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString(Class_Genric.Sp_OrderNumber,response.optString("OrderNumber"));
                            editor.putString(Class_Genric.Sp_Status,response.optString("Status"));
                            editor.commit();
                            break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void OrderApi(final Context context){
        String s="2016-12-06T11:29:26";
        if (s.contains("T"))
        {
            s = s.replace("T", "");
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("TimeStamp",s));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.Order,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            ArrayList<Order> orders=new ArrayList<Order>();
                            Type listType = new TypeToken<ArrayList<Order>>() {}.getType();
                            JSONArray jsonObject=new JSONArray(response);
                            orders=gson.fromJson(jsonObject.toString(),listType);
                            Class_ModelDB.setOrderList(orders);
                            break;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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

    public static void OrderIdApi(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        ArrayList<KeyValuePair> params= new ArrayList<KeyValuePair>();
        params.add(new KeyValuePair("Id","6724af08-b00b-4294-bc5d-e0417334bb33"));
        StringRequest postRequest = new StringRequest(Request.Method.GET,Class_Genric.generateUrl(Class_Urls.OrderId,params), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                switch (mStatusCode){
                    case 200:
                        try {
                            gson=new Gson();
                            Order order=new Order();
                            JSONObject jsonObject=new JSONObject(response);
                            order=gson.fromJson(jsonObject.toString(),Order.class);
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
                mStatusCode=error.networkResponse.statusCode;
                switch (mStatusCode){
                    case 400:
                        Toast.makeText(context,"Invalid Token or Invalid Id",Toast.LENGTH_SHORT).show();
                        break;
                    case 404:
                        Toast.makeText(context,"Distributor not Found",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
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
