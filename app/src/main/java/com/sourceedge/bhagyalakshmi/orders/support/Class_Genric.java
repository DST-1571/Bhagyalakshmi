package com.sourceedge.bhagyalakshmi.orders.support;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.sourceedge.bhagyalakshmi.orders.R;


/**
 * Created by Centura on 01-12-2016.
 */

public class Class_Genric {
public static void setOrientation(Context mContext){
    if(mContext.getResources().getBoolean(R.bool.isPhone))
        ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    else
        ((Activity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
}


    public static void setupDrawer(Toolbar toolbar, DrawerLayout drawer, ActionBarDrawerToggle mDrawerToggle, Context mContext) {
        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle((Activity)mContext, drawer, toolbar, R.string.opendrawer, R.string.closedrawer) {
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
}
