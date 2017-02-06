package com.sourceedge.bhagyalakshmi.orders.location;

import android.app.Application;
import android.content.Intent;

/**
 * Created by Centura on 13-01-2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
   /*     startService(new Intent(this, MyService.class));*/


        //startService(new Intent(this,Tracker.class));
    }
}
