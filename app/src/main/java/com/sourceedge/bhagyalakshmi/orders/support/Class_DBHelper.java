package com.sourceedge.bhagyalakshmi.orders.support;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_DBHelper extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences = Class_Application.sharedPreferences;
    public static final String DATABASE_NAME = "Bhagyalaksmi_Traders.db";
    Gson gson = new Gson();
    ContentValues contentValues = new ContentValues();
    public static String InitialData = "InitialData";
    public static String TableName = "TableName";
    public static String Data = "Data";

    public Class_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        sharedPreferences = context.getSharedPreferences(Class_Genric.MyPref, context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table IF NOT EXISTS InitialData " +
                        "(" +
                        "TableName text, " +
                        "Data text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS InitialData");
        onCreate(db);
    }

    private void saveCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "CurrentUser");
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getCurrentuserModel()).getAsJsonObject().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"CurrentUser"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveRole() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Role");
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getRoleList()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Role"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Product");
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getProductList()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Product"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void saveOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Orders");
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getOrderList()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Orders"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }

    private void loadCurrentUser() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"CurrentUser"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            //Type listType = new TypeToken<ArrayList<CurrentUser>>() {}.getType();
            CurrentUser currentUser = new CurrentUser();
            currentUser = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), CurrentUser.class);
            Class_ModelDB.setCurrentuserModel(currentUser);
            res.moveToNext();
        }
        db.close();
    }

    private void loadRole() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Role"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Role>>() {}.getType();
            ArrayList<Role> rolelist = new ArrayList<Role>();
            rolelist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setRoleList(rolelist);
            res.moveToNext();
        }
        db.close();
    }

    private void loadProduct() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Product"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Product>>() {}.getType();
            ArrayList<Product> productlist = new ArrayList<Product>();
            productlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setProductList(productlist);
            res.moveToNext();
        }
        db.close();
    }
}


