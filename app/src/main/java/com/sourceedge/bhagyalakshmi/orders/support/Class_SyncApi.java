package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.models.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_SyncApi {

    static int mStatusCode=0;
    static Gson gson;

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

    public static void DistributorIdApi(final Context context){
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
