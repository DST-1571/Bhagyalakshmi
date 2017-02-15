package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.graphics.BitmapCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.changepassword.Change_Password;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.location.MapsActivity;
import com.sourceedge.bhagyalakshmi.orders.location.Tracker;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.OfflineModel_Distributor;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.OrderProduct;
import com.sourceedge.bhagyalakshmi.orders.models.OrderRole;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Admin_Orders;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.location.Location;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Search_Customer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.sourceedge.bhagyalakshmi.orders.R.color.view;


/**
 * Created by Centura on 01-12-2016.
 */

public class Class_Genric {
    public static final String MyPref = "MyPref";
    public static final int ADMIN = 1;
    public static final int DISTRIBUTORSALES = 2;
    public static final int DISTRIBUTOR = 3;
    public static final int SALESPERSON = 4;
    public static final int ASM = 5;
    public static final int SALESMAN = 6;


    public static final String Sp_SyncDate = "SyncDate";
    public static final String Sp_Status = "Status";
    public static final String Sp_OfflineTime="OfflineTime";
    public static final String Sp_OrderNumber = "OrderNumber";
    public static final String Sp_OrderStatus = "OrderNumber";
    public static final String Sp_ProductsTS = "ProductsTS";
    public static final String Sp_RolesTS = "RolesTS";
    public static final String Sp_OrdersTS = "OrdersTS";
    public static final String Sp_GroupsTS = "GroupsTS";
    public static final String Sp_CatagoriesTS = "CatagoriesTS";
    public static final String Sp_Companies = "Companies";

    public static boolean progressAlive = false;
    static ProgressDialog pDialog;
    public static final String rupee = "\u20B9 ";
    static DrawerLayout drawer;

    static Button button;
    static Button button1;
    static TextView homeText, synctime;
    static Class_DBHelper dbHelper;

    public static LinearLayout home, myProfile, changePassword, location, distributorSalesMyOrders, asmMyOrders, activeOrders, distributorMyOrders, salesmanMyOrders, salesDistributorRetailers, retailers, distributorSalesPayments, distributorPayments, salesmanPayments, messages, logout, sync;
    public static Activity a;
    static SharedPreferences sharedPreferences;

    public static void ShowDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                try {
                    pDialog.cancel();
                } catch (Exception e) {
                }
                progressAlive = false;
            }
            {
               /* pDialog=new Dialog(context);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setContentView(R.layout.customload);*/

                pDialog = new ProgressDialog(context);
                pDialog.setMessage(message);
                if (message.contains("Loading"))
                    pDialog.setCanceledOnTouchOutside(false);
                progressAlive = true;
                pDialog.show();
            }
        } else {
            if (progressAlive) {
                pDialog.dismiss();
                pDialog.cancel();
                progressAlive = false;
            }
        }
    }


    static ProgressDialog SyncDialog;
    public static boolean SyncAlive = false;

    public static void SYNCDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (SyncAlive) {
                try {
                    SyncDialog.setMessage(message);
                } catch (Exception e) {
                }
            } else {
               /* pDialog=new Dialog(context);
                pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pDialog.setContentView(R.layout.customload);*/

                SyncDialog = new ProgressDialog(context);
                SyncDialog.setMessage(message);
                SyncDialog.setCanceledOnTouchOutside(false);
                SyncDialog.setCancelable(false);
                SyncAlive = true;
                SyncDialog.show();
            }
        } else {
            if (SyncAlive) {
                SyncDialog.dismiss();
                SyncDialog.cancel();
                SyncAlive = false;
            }
        }
    }

    public static void setOrientation(Context mContext) {
        if (mContext.getResources().getBoolean(R.bool.isPhone))
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*else
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);*/
    }

    public static void applyFontForToolbarTitle(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                Typeface titleFont = Typeface.
                        createFromAsset(context.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
                tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
            }
        }
    }
    public static Long getTimeStamp(int hours,Context context) {
        SharedPreferences sharedPreferences= context.getSharedPreferences(Class_Genric.MyPref,context.MODE_PRIVATE);
        long offlinetime= Long.valueOf(sharedPreferences.getString(Sp_OfflineTime,"0"));
        Date res = new java.util.Date(offlinetime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( res );
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+hours);
        Long data = calendar.getTimeInMillis();
        return data;
    }

    public static Long getCurrentTimeStamp() {
        Date res = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime( res );
        Long data = calendar.getTimeInMillis();
        return data;
    }

    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) ((Activity) context).getSystemService(((Activity) context).INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }


    public static void setupDrawer(Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle mDrawerToggle, Context mContext) {
        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State
    }

    public static boolean NetCheck(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected == false) {
            /*Toast.makeText(context, "Please Check Internet Connectivity", Toast.LENGTH_LONG).show();
            return false;*/
            try {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkinternet);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                Button btn = (Button) dialog.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        NetCheck(context);
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return true;
    }

    public static boolean NetAvailable(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        final boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static ArrayList<OrderProduct> getOrderProductsFromProducts(ArrayList<Product> Products) {
        ArrayList<OrderProduct> tempOrderProducts = new ArrayList<OrderProduct>();
        for (Product product : Products) {
            OrderProduct tempOP = new OrderProduct();
            tempOP.setId("N/A");
            tempOP.setProductId(product.getCode());
            tempOP.setUnit(product.getUnits());
            tempOP.setQuantity(product.getQuantity());
            tempOP.setDescription(product.getDescription());
            tempOP.setPrice(product.getPrice());
            tempOP.setAliasflag(product.getAliasflag());
            tempOP.setTaxamount(Class_Genric.CalculateTaxAmount(product.getPrice(),product.getQuantity(),product.getTax()));
            tempOrderProducts.add(tempOP);
        }
        return tempOrderProducts;
    }

    public static ArrayList<Product> getProductsFromOrderProducts(ArrayList<OrderProduct> Products) {
        ArrayList<Product> tempProducts = new ArrayList<Product>();
        for (OrderProduct Orderproduct : Products) {
            Product tempOP = new Product();
            tempOP.setCode(Orderproduct.getId());
            tempOP.setUnits(Orderproduct.getUnit());
            tempOP.setQuantity(Orderproduct.getQuantity());
            tempOP.setPrice(Orderproduct.getPrice());
            tempProducts.add(tempOP);
        }
        return tempProducts;
    }

    public static OrderRole getOrderRoleFromCurrentUser(CurrentUser User) {
        OrderRole orderRole = new OrderRole();
        orderRole.setId(User.getId());
        orderRole.setName(User.getName());
        orderRole.setUserType(User.getUsertype());
        return orderRole;
    }


    public static OrderRole getOrderRoleFromCurrentRole(Role role) {
        OrderRole orderRole = new OrderRole();
        orderRole.setId(role.getId());
        orderRole.setName(role.getName());
        return orderRole;
    }

    public static Role getRoleFromCurrentUser(CurrentUser User) {
        Role orderRole = new Role();
        orderRole.setId(User.getId());
        orderRole.setName(User.getName());
        return orderRole;
    }


    public static int convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
      /*  DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;*/
    }


    public static boolean success(JSONObject result) {
        if (result.optString("IsSuccess").matches("true"))
            return true;
        else return false;
    }

    public static String getError(JSONObject result) {
        String Error = result.optString("Errors");
        if (Error != null)
            if (Error.length() > 2)
                Error = Error.substring(1, Error.length() - 2);
        return Error;
    }


    public static void apiError(final Context context, VolleyError volleyError) {
        if (volleyError instanceof NoConnectionError) {
            try {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkinternet);
                dialog.show();
                Button btn = (Button) dialog.findViewById(R.id.btn);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        //NetCheck(context);
                    }
                });
            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            Toast.makeText(context, "Server Down! Please get back Later", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getType(String LoginType) {
        if (LoginType.toString().toLowerCase().matches(("admin").toLowerCase())||LoginType.toString().toLowerCase().matches(("Sales Head").toLowerCase()))
            return ADMIN;
        if (LoginType.toLowerCase().matches(("distributorsalesperson").toLowerCase()))
            return DISTRIBUTORSALES;
        if (LoginType.toLowerCase().matches(("Distributor").toLowerCase()))
            return DISTRIBUTOR;
        if (LoginType.toLowerCase().matches(("salesperson").toLowerCase()))
            return SALESPERSON;
        if (LoginType.toLowerCase().matches(("ASM").toLowerCase()))
            return ASM;
        if (LoginType.toLowerCase().matches(("Sales Man").toLowerCase()))
            return SALESMAN;
        else return 0;
    }

    public static void drawerOnClicks(final Context context) {
        a = (Activity) context;
        sharedPreferences = a.getSharedPreferences(MyPref, a.MODE_PRIVATE);
        home = (LinearLayout) a.findViewById(R.id.home);
        drawer = (DrawerLayout) a.findViewById(R.id.navigation_drawer);
        myProfile = (LinearLayout) a.findViewById(R.id.my_profile);
        changePassword = (LinearLayout) a.findViewById(R.id.change_password);
        location = (LinearLayout) a.findViewById(R.id.location);
        distributorSalesMyOrders = (LinearLayout) a.findViewById(R.id.distributor_sales_my_orders);
        distributorMyOrders = (LinearLayout) a.findViewById(R.id.distributor_my_orders);
        salesmanMyOrders = (LinearLayout) a.findViewById(R.id.salesman_my_orders);
        activeOrders = (LinearLayout) a.findViewById(R.id.active_orders);
        asmMyOrders = (LinearLayout) a.findViewById(R.id.asm_my_orders);
        salesDistributorRetailers = (LinearLayout) a.findViewById(R.id.sales_distributors_retailers);
        retailers = (LinearLayout) a.findViewById(R.id.retailers);
        distributorSalesPayments = (LinearLayout) a.findViewById(R.id.distributor_sales_payments);
        distributorPayments = (LinearLayout) a.findViewById(R.id.distributor_payments);
        salesmanPayments = (LinearLayout) a.findViewById(R.id.salesman_payments);
        messages = (LinearLayout) a.findViewById(R.id.messages);
        logout = (LinearLayout) a.findViewById(R.id.logout);
        homeText = (TextView) a.findViewById(R.id.home_text);
        sync = (LinearLayout) a.findViewById(R.id.sync);
        synctime=(TextView) a.findViewById(R.id.synctime);

        switch (getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case ADMIN:
                distributorSalesMyOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                salesmanMyOrders.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesmanPayments.setVisibility(View.GONE);
                asmMyOrders.setVisibility(View.GONE);
                sync.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Class_SyncApi.SalesPersonListApi(context);
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });

                activeOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, Admin_Orders.class));
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });
                salesDistributorRetailers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });

                break;


            case DISTRIBUTORSALES:
                location.setVisibility(View.GONE);
                activeOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                salesmanMyOrders.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesmanPayments.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                sync.setVisibility(View.VISIBLE);
                UpdateSyncTime(context);
                salesDistributorRetailers.setVisibility(View.GONE);
                distributorSalesMyOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, Order_Page.class));
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                });

                retailers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Class_Static.viewOrderedProducts = false;
                        a.startActivity(new Intent(a, Product_Order_Lookup.class));
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                });

                distributorSalesPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class_SyncApi.loadallcategories(0, context);
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });
                break;


            case DISTRIBUTOR:
                location.setVisibility(View.GONE);
                activeOrders.setVisibility(View.GONE);
                distributorSalesMyOrders.setVisibility(View.GONE);
                salesmanMyOrders.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                salesmanPayments.setVisibility(View.GONE);
                salesDistributorRetailers.setVisibility(View.GONE);
                asmMyOrders.setVisibility(View.GONE);
                sync.setVisibility(View.VISIBLE);
                UpdateSyncTime(context);
                distributorMyOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, Order_Page.class));
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });

                distributorPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class_ModelDB.OfflineDistributors = new ArrayList<OfflineModel_Distributor>();
                        Class_SyncApi.DistributorRecursiveCatagoryApi(context, Class_ModelDB.getCurrentuserModel().getId());
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });
                break;

/*
            case SALESPERSON:
                location.setVisibility(View.GONE);
                activeOrders.setVisibility(View.GONE);
                distributorSalesMyOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesDistributorRetailers.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                sync.setVisibility(View.VISIBLE);

                salesmanMyOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, Order_Page.class));
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                });

                salesmanPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class_SyncApi.loadallcategories(0,context);
                    }
                });
                break;*/
            case Class_Genric.ASM:
            case SALESMAN:
                location.setVisibility(View.GONE);
                activeOrders.setVisibility(View.GONE);
                distributorSalesMyOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesDistributorRetailers.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                asmMyOrders.setVisibility(View.GONE);
                sync.setVisibility(View.VISIBLE);
                UpdateSyncTime(context);
                salesmanMyOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                            case ASM:
                                a.startActivity(new Intent(a, Admin_Orders.class));
                                drawer.closeDrawer(Gravity.LEFT);
                                break;
                            default:
                                a.startActivity(new Intent(a, Order_Page.class));
                                drawer.closeDrawer(Gravity.LEFT);
                                break;
                        }
                    }
                });

                salesmanPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

                sync.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Class_SyncApi.loadallcategories(0, context);
                        drawer.closeDrawer(Gravity.LEFT);
                    }
                });
                break;
        }


        if (Class_Static.home) {
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(Gravity.LEFT);
                }
            });
            Class_Static.home = false;
        } else {
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    a.startActivity(new Intent(a, Dashboard.class));
                    a.finish();
                    drawer.closeDrawer(Gravity.LEFT);
                }
            });
        }


        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a.startActivity(new Intent(a, Change_Password.class));
                drawer.closeDrawer(Gravity.LEFT);

            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(context);
            }
        });
    }

    public static void UpdateSyncTime(Context context) {
        SharedPreferences sharedPreferences= context.getSharedPreferences(Class_Genric.MyPref,context.MODE_PRIVATE);
        long timestamp= Long.valueOf(sharedPreferences.getString(Sp_OfflineTime,"0"));
        if(timestamp!=0)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("d MMM h:mm a");
            synctime.setText("last Sync :\n "+sdf.format(new java.util.Date(getTimeStamp(0,context) * 1000))+"");
        }
        else synctime.setText("");
    }

    public static void logout(Context context) {
        a = ((Activity) context);
        dbHelper = new Class_DBHelper(context);
        sharedPreferences = a.getSharedPreferences(Class_Genric.MyPref, a.MODE_PRIVATE);
        final Dialog dialog = new Dialog(a);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.logout_alert);
        button = (Button) dialog.findViewById(R.id.btn);
        button1 = (Button) dialog.findViewById(R.id.btn1);
        dialog.show();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                dbHelper.ClearAllData();
                Class_ModelDB.ClearDB();
                Class_Static.ClearStaticData();
                Order.LoadOrders=true;
                a.startActivity(new Intent(a, Login.class));
                a.finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public static String generateUrl(String Url, ArrayList<KeyValuePair> params) {
        if (params.size() > 0) {
            Url += "?";
            for (KeyValuePair data : params) {
                if (data.getKey().trim().length() > 0)
                    Url += data.getKey() + "=" + data.getValue() + "&&";
            }
            Url = Url.substring(0, Url.length() - 2);
        }
        return Url;
    }

    public static String generateUrl1(String Url, ArrayList<KeyValuePair> params) {
        try {
            if (params.size() > 0) {
                //Url += "&";
                for (KeyValuePair data : params) {
                    if (data.getKey().trim().length() > 0)
                        Url += "&" + data.getKey() + "=" + URLEncoder.encode(data.getValue(), "utf-8");
                }
                //Url = Url.substring(0, Url.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Url;
    }

    public static void closeKeyboard(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager)
                ((Activity) mContext).getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_HIDDEN);
    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getDate(String datetime) {
        if (datetime.length() > 11)
            return datetime.substring(0, 10);
        else
            return datetime;
    }

    public static boolean checkAutoTime(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME)) == 1) && ((Settings.Global.getInt(context.getContentResolver(), Settings.Global.AUTO_TIME_ZONE)) == 1)) {
                    return true;
                } else return false;
            } else {
                if (((Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME, 0)) == 1) && ((Settings.System.getInt(context.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0)) == 1)) {
                    return true;
                } else return false;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String getTimeStamp(String timestamp, Context context) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        String TIMESTAMP = "";
        switch (timestamp) {
            case Sp_ProductsTS:
                TIMESTAMP = sharedPreferences.getString(Sp_ProductsTS, "0");
                break;
            case Sp_RolesTS:
                TIMESTAMP = sharedPreferences.getString(Sp_RolesTS, "0");
                break;
            case Sp_GroupsTS:
                TIMESTAMP = sharedPreferences.getString(Sp_GroupsTS, "0");
                break;
            case Sp_CatagoriesTS:
                TIMESTAMP = sharedPreferences.getString(Sp_CatagoriesTS, "0");
                break;
            case Sp_OrdersTS:
                TIMESTAMP = sharedPreferences.getString(Sp_OrdersTS, "0");
                break;
        }
        return TIMESTAMP;
    }

    public static void setTimeStamp(String type, BigInteger timestamp, Context context) {
        sharedPreferences = context.getSharedPreferences(MyPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (type) {
            case Sp_ProductsTS:
                editor.putString(Sp_ProductsTS, timestamp + "");
                break;
            case Sp_RolesTS:
                editor.putString(Sp_RolesTS, timestamp + "");
                break;
            case Sp_GroupsTS:
                editor.putString(Sp_GroupsTS, timestamp + "");
                break;
            case Sp_CatagoriesTS:
                editor.putString(Sp_CatagoriesTS, timestamp + "");
                break;
            case Sp_OrdersTS:
                editor.putString(Sp_OrdersTS, timestamp + "");
                break;
        }
        editor.commit();
        return;
    }

    public static StringRequest VolleyTime(StringRequest postRequest) {
        postRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 2f));

        return postRequest;
    }

    public static JsonObjectRequest VolleyTime(JsonObjectRequest postRequest) {
        postRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 2f));

        return postRequest;
    }

    public static String getDateTime(Order order) {
        order.getOrderDate();
        if(order.getOrderNumber().toLowerCase().contains(("Offline").toLowerCase()))
        {
            return  order.getOrderDate();
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return sdf.format(new java.util.Date(Long.parseLong((order.getOrderDate())) * 1000));
        }
    }

    public static Double CalculateTaxAmount(Double price,int qty,Double tax){
        Double Amount=0.0;
        Amount=price*qty;
        Amount=(Amount*(tax/100.0f));
        Amount = Double.valueOf(String.format("%.2f", Amount));
        return  Amount;
    }

    public static Double CalculateAmount(Double price,int qty,Double tax){
        Double Amount=0.0;
        Amount=price*qty;
        Amount=(Amount*(tax/100.0f))+Amount;
        Amount = Double.valueOf(String.format("%.2f", Amount));
        return  Amount;
    }

    public static void saveOfflineTime(Context context) {
        SharedPreferences sharedPreferences= context.getSharedPreferences(Class_Genric.MyPref,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Class_Genric.Sp_OfflineTime,getCurrentTimeStamp()+"");
        editor.commit();
    }

    public static boolean CheckOfflineTime(Context context) {
        long ThresHoldTime = getTimeStamp(6,context);
        if(getCurrentTimeStamp()>ThresHoldTime)
            return false;
        else return true;
    }
}
