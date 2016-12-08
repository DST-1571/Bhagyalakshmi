package com.sourceedge.bhagyalakshmi.orders.support;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences = ApplicationClass.sharedPreferences;
    public static final String DATABASE_NAME = "Bhagyalaksmi_Traders.db";
    Gson gson = new Gson();
    ContentValues contentValues = new ContentValues();
    public static String InitialData = "InitialData";
    public static String TableName = "TableName";
    public static String Data = "Data";

    public DBHelper(Context context) {
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

   /* private void saveSections() {
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, "Sections");
       // contentValues.put(this.Data, gson.toJsonTree(DB.getInitialModel().getSections()).getAsJsonArray().toString());
        db.delete(this.InitialData, "TableName=?", new String[]{"Sections"});
        db.insert(this.InitialData, null, contentValues);
        db.close();
    }*/

    /*private void loadSections(InitialModel initialModel) {
        SQLiteDatabase db = DbHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{"Sections"});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Sections>>() {
            }.getType();
            ArrayList<Sections> sec = new ArrayList<Sections>();
            sec = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            initialModel.setSections(sec);
            res.moveToNext();
        }
        db.close();
    }*/
}


