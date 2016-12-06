package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.location.controller.Location;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.AdminOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.DistributorOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.DistributorSalesOrders;
import com.sourceedge.bhagyalakshmi.orders.orders.controller.SalesPersonOrders;


/**
 * Created by Centura on 01-12-2016.
 */

public class Class_Genric {

    public static final String MyPref = "MyPref";
    public static final String Sp_LoginType = "LoginType";
    public static final int ADMIN=1;
    public static final int DISTRIBUTORSALES=2;
    public static final int DISTRIBUTOR=3;
    public static final int SALESPERSON=4;
    public static final String Sp_Username = "Username";
    public static final String Sp_Password = "Password";

    public static LinearLayout myProfile, changePassword, location, distributorSalesMyOrders, activeOrders, distributorMyOrders,salesmanMyOrders,salesDistributorRetailers,retailers, distributorSalesPayments,distributorPayments,salesmanPayments, messages, logout;
    public static Activity a;
    static SharedPreferences sharedPreferences;

    public static void setOrientation(Context mContext) {
        if (mContext.getResources().getBoolean(R.bool.isPhone))
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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

    public static int getType(String Sp_LoginType) {
        if (Sp_LoginType.matches("Admin"))
            return ADMIN;
        if (Sp_LoginType.matches("Distributor Sales"))
            return DISTRIBUTORSALES;
        if (Sp_LoginType.matches("Distributor"))
            return DISTRIBUTOR;
        else return SALESPERSON;
    }

    public static void drawerOnClicks(Context context) {
        a = (Activity) context;
        sharedPreferences = a.getSharedPreferences(MyPref, a.MODE_PRIVATE);
        myProfile = (LinearLayout) a.findViewById(R.id.my_profile);
        changePassword = (LinearLayout) a.findViewById(R.id.change_password);
        location = (LinearLayout) a.findViewById(R.id.location);
        distributorSalesMyOrders = (LinearLayout) a.findViewById(R.id.distributor_sales_my_orders);
        distributorMyOrders=(LinearLayout)a.findViewById(R.id.distributor_my_orders);
        salesmanMyOrders=(LinearLayout)a.findViewById(R.id.salesman_my_orders);
        activeOrders = (LinearLayout) a.findViewById(R.id.active_orders);
        salesDistributorRetailers = (LinearLayout) a.findViewById(R.id.sales_distributors_retailers);
        retailers = (LinearLayout) a.findViewById(R.id.retailers);
        distributorSalesPayments = (LinearLayout) a.findViewById(R.id.distributor_sales_payments);
        distributorPayments = (LinearLayout) a.findViewById(R.id.distributor_payments);
        salesmanPayments = (LinearLayout) a.findViewById(R.id.salesman_payments);
        messages = (LinearLayout) a.findViewById(R.id.messages);
        logout = (LinearLayout) a.findViewById(R.id.logout);

        switch (getType(sharedPreferences.getString(Sp_LoginType, ""))) {
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
                        a.finish();
                    }
                });

                activeOrders.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        a.startActivity(new Intent(a, AdminOrders.class));
                        a.finish();
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
                        a.startActivity(new Intent(a, DistributorSalesOrders.class));
                        a.finish();
                    }
                });

                retailers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                        a.startActivity(new Intent(a, DistributorOrders.class));
                        a.finish();
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
                        a.startActivity(new Intent(a, SalesPersonOrders.class));
                        a.finish();
                    }
                });

                salesmanPayments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
        }
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().commit();
                a.startActivity(new Intent(a, Login.class));
                a.finish();
            }
        });
    }
}
