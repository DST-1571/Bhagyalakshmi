package com.sourceedge.bhagyalakshmi.orders.location;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.sourceedge.bhagyalakshmi.orders.dashboard.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.login.Splash;
import com.sourceedge.bhagyalakshmi.orders.models.LocationModel;
import com.sourceedge.bhagyalakshmi.orders.support.Class_DBHelper;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_ModelDB;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Static;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {


    private LocationManager locManager;
    private LocationListener locListener;
    private Location mobileLocation;
    private String provider;
    String mLastUpdateTime;
    Class_DBHelper dbHelper;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        /*Intent dialogIntent = new Intent(this, Tracker.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);*/
        showdata();
        return START_STICKY;

    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }*/


    public void showdata() {
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          locManager = (LocationManager) getBaseContext().getSystemService(getBaseContext().LOCATION_SERVICE);
                                          locListener = new LocationListener() {
                                              @Override
                                              public void onStatusChanged(String provider, int status,
                                                                          Bundle extras) {
                                              }

                                              @Override
                                              public void onProviderEnabled(String provider) {
                                              }

                                              @Override
                                              public void onProviderDisabled(String provider) {
                                              }

                                              @Override
                                              public void onLocationChanged(Location location) {
                                                  System.out.println("mobile location is in listener=" + location);
                                                  mobileLocation = location;
                                              }
                                          };
                                          if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                              // TODO: Consider calling
                                              //    ActivityCompat#requestPermissions
                                              // here to request the missing permissions, and then overriding
                                              //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                              //                                          int[] grantResults)
                                              // to handle the case where the user grants the permission. See the documentation
                                              // for ActivityCompat#requestPermissions for more details.
                                              return;
                                          }
                                          locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locListener);
                                          mobileLocation = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                          if (mobileLocation != null) {
                                              locManager.removeUpdates(locListener);
                                              Double longitude =  mobileLocation.getLongitude();
                                              Double latitude =   mobileLocation.getLatitude();
                                              long atTime = mobileLocation.getTime();

                                              mLastUpdateTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date(atTime));
                                              switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
                                                  case Class_Genric.ADMIN:
                                                      break;
                                                  case Class_Genric.DISTRIBUTORSALES:
                                                      break;
                                                  case Class_Genric.DISTRIBUTOR:
                                                      break;
                                                  case Class_Genric.SALESMAN:
                                                      //api();
                                                      ArrayList<LocationModel> templocations=Class_ModelDB.getLocationList();
                                                      templocations.add(new LocationModel(latitude,longitude,mLastUpdateTime));
                                                      Class_ModelDB.setLocationList(templocations);
                                                      dbHelper= new Class_DBHelper(MyService.this);
                                                      dbHelper.saveLocation();
                                                      dbHelper.loadLocation();
                                                      //Class_SyncApi.Location(MyService.this,latitude,longitude,mLastUpdateTime);
                                                      break;
                                                  case Class_Genric.ASM:
                                                      break;

                                              }
                                              /*Toast.makeText(getBaseContext(), "Latitude is = " + latitude + "Longitude is =" + longitude + "Time is ="+mLastUpdateTime, Toast.LENGTH_LONG).show();*/
                                          } else {
                                              System.out.println("in find location 4");
                                              Toast.makeText(getBaseContext(), "Sorry location is not determined", Toast.LENGTH_LONG).show();
                                          }

                showdata();
            }
        }, 5000);
    }


}
