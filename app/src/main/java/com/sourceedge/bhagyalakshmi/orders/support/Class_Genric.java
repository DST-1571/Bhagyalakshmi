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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.changepassword.Change_Password;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Admin_Orders;
import com.sourceedge.bhagyalakshmi.orders.orderproduct.controller.Product_Order_Lookup;
import com.sourceedge.bhagyalakshmi.orders.location.Location;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.models.KeyValuePair;
import com.sourceedge.bhagyalakshmi.orders.orderpage.controller.Order_Page;

import org.json.JSONObject;

import java.util.ArrayList;

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
    public static final String Sp_Status = "Status";
    public static final String Sp_OrderNumber = "OrderNumber";
    public static boolean progressAlive = false;
    static ProgressDialog pDialog;
    public static final String rupee = "\u20B9 ";
    static DrawerLayout drawer;

    static Button button;
    static Button button1;
    static TextView homeText;
    static Class_DBHelper dbHelper;

    public static LinearLayout home, myProfile, changePassword, location, distributorSalesMyOrders, activeOrders, distributorMyOrders, salesmanMyOrders, salesDistributorRetailers, retailers, distributorSalesPayments, distributorPayments, salesmanPayments, messages, logout;
    public static Activity a;
    static SharedPreferences sharedPreferences;

    public static void ShowDialog(Context context, String message, Boolean flag) {
        if (flag) {
            if (progressAlive) {
                pDialog.cancel();
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

    public static void setOrientation(Context mContext) {
        if (mContext.getResources().getBoolean(R.bool.isPhone))
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
        if (LoginType.matches("Admin"))
            return ADMIN;
        if (LoginType.matches("DistributorSalesMan"))
            return DISTRIBUTORSALES;
        if (LoginType.matches("Distributor"))
            return DISTRIBUTOR;
        else return SALESPERSON;
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
        salesDistributorRetailers = (LinearLayout) a.findViewById(R.id.sales_distributors_retailers);
        retailers = (LinearLayout) a.findViewById(R.id.retailers);
        distributorSalesPayments = (LinearLayout) a.findViewById(R.id.distributor_sales_payments);
        distributorPayments = (LinearLayout) a.findViewById(R.id.distributor_payments);
        salesmanPayments = (LinearLayout) a.findViewById(R.id.salesman_payments);
        messages = (LinearLayout) a.findViewById(R.id.messages);
        logout = (LinearLayout) a.findViewById(R.id.logout);
        homeText = (TextView) a.findViewById(R.id.home_text);

        switch (getType(Class_ModelDB.getCurrentuserModel().getUserType())) {
            case ADMIN:
                distributorSalesMyOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                salesmanMyOrders.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesmanPayments.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, Location.class));
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
                        a.startActivity(new Intent(a,  Product_Order_Lookup.class));
                        drawer.closeDrawer(Gravity.LEFT);

                    }
                });

                distributorSalesPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                break;


            case SALESPERSON:
                location.setVisibility(View.GONE);
                activeOrders.setVisibility(View.GONE);
                distributorSalesMyOrders.setVisibility(View.GONE);
                distributorMyOrders.setVisibility(View.GONE);
                distributorSalesPayments.setVisibility(View.GONE);
                distributorPayments.setVisibility(View.GONE);
                salesDistributorRetailers.setVisibility(View.GONE);
                retailers.setVisibility(View.GONE);
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
                logout(a);
            }
        });
    }

    public static void logout(Context context) {
        a = ((Activity) context);
         dbHelper= new Class_DBHelper(context);
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

    public static void closeKeyboard(Context mContext){
        InputMethodManager inputManager = (InputMethodManager)
                ((Activity)mContext).getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(),
                InputMethodManager.RESULT_HIDDEN);
    }
}
