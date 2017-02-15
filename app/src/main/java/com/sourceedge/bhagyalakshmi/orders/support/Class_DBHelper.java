package com.sourceedge.bhagyalakshmi.orders.support;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourceedge.bhagyalakshmi.orders.models.Catagories;
import com.sourceedge.bhagyalakshmi.orders.models.CurrentUser;
import com.sourceedge.bhagyalakshmi.orders.models.LocationModel;
import com.sourceedge.bhagyalakshmi.orders.models.OfflineModel_Distributor;
import com.sourceedge.bhagyalakshmi.orders.models.Order;
import com.sourceedge.bhagyalakshmi.orders.models.Product;
import com.sourceedge.bhagyalakshmi.orders.models.Role;
import com.sourceedge.bhagyalakshmi.orders.models.Sections;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Centura User1 on 08-12-2016.
 */

public class Class_DBHelper extends SQLiteOpenHelper {
    SharedPreferences sharedPreferences = Class_Application.sharedPreferences;
    public static final int DATABASE_Version = 1;
    public static final String DATABASE_NAME = "Bhagyalaksmi_Traders.db";
    Gson gson = new Gson();
    static ContentValues contentValues = new ContentValues();
    public static String InitialData = "InitialData";
    public static String CheckData = "CheckData";
    public static String TableName = "TableName";
    public static String OfflineData = "OfflineData";
    public static String DistibutorID = "DistibutorID";
    public static String Data = "Data";
    public static String IsPresent = "IsPresent";
    public static String DataTableCurrentUser = "CurrentUser";
    public static String DataTableRole = "Role";
    public static String DataTableDratfOrders = "DraftOrders";
    public static String DataTableOrders = "Orders";
    public static String DataTableProduct = "Product";
    public static String DataTableGroup = "Group";
    public static String DataTableCatagory = "Catagory";
    public static String DataTableLocation = "Location";
    public static String DataTableOfflineData = "OfflineData";

    public Class_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
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
        db.execSQL(
                "create table IF NOT EXISTS CheckData " +
                        "(" +
                        "TableName text, " +
                        "IsPresent text)"
        );
        db.execSQL(
                "create table IF NOT EXISTS OfflineData " +
                        "(" +
                        "DistibutorID text, " +
                        "Data text)"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS InitialData");
        db.execSQL("DROP TABLE IF EXISTS CheckData");
        db.execSQL("DROP TABLE IF EXISTS OfflineData");
        onCreate(db);
    }



    public void CheckData(String tablename, boolean isPresent) {
        String ispresentint = "";
        if (isPresent)
            ispresentint = "1";
        else
            ispresentint = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        contentValues.put(this.TableName, tablename);
        contentValues.put(this.IsPresent, ispresentint);
        db.delete(this.CheckData, "TableName=?", new String[]{tablename});
        db.insert(this.CheckData, null, contentValues);
    }
    public boolean CheckDataExists(String tablename) {
        String isPresentInt = "";
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + this.CheckData + " where " + this.TableName + "=?", new String[]{tablename});
        if (res.getCount() != 0) {
            if (res.moveToFirst()) {
                do {
                    isPresentInt = res.getString(res.getColumnIndex("IsPresent"));  //your variable
                } while (res.moveToNext());
            }

        }
       /* while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            //Type listType = new TypeToken<ArrayList<CurrentUser>>() {}.getType();
            isPresentInt =res.getString(res.getColumnIndex(IsPresent));
            res.moveToNext();
        }*/
        db.close();
        if (isPresentInt.matches("1"))
            return true;
        else return false;
    }
    public void saveCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableCurrentUser});
        CheckData(DataTableCurrentUser, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableCurrentUser);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getCurrentuserModel()).getAsJsonObject().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableCurrentUser, true);
        db.close();
    }
    public void saveRole() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableRole});
        CheckData(DataTableRole, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableRole);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getRoleList()).getAsJsonArray().toString());

        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableRole, true);
        db.close();
    }
    public void saveProduct() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableProduct});
        CheckData(DataTableProduct, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableProduct);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getProductList()).getAsJsonArray().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableProduct, true);
        db.close();
    }
    public void saveSections() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableGroup});
        CheckData(DataTableGroup, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableGroup);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getSectionList()).getAsJsonArray().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableGroup, true);
        db.close();
    }
    public void saveLocation() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableLocation});
        CheckData(DataTableLocation, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableLocation);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getLocationList()).getAsJsonArray().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableLocation, true);
        db.close();
    }
    public void saveCatagories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableCatagory});
        CheckData(DataTableCatagory, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableCatagory);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getCatagoryList()).getAsJsonArray().toString());

        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableCatagory, true);
        db.close();
    }
    public void saveDraftOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableDratfOrders});
        CheckData(DataTableDratfOrders, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableDratfOrders);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.DraftorderList).getAsJsonArray().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableDratfOrders, true);
        db.close();
        loadOrders();
    }
    public void DeleteDraftOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableDratfOrders});
        CheckData(DataTableDratfOrders, false);
        db.close();
        loadOrders();
    }
    public void loadDraftOrders() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableDratfOrders});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Order>>() {
            }.getType();
            ArrayList<Order> orderlist = new ArrayList<Order>();
            orderlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.DraftorderList=new ArrayList<Order>();
            Class_ModelDB.DraftorderList= (ArrayList<Order>) orderlist.clone();
            res.moveToNext();
        }
        db.close();
    }
    public void saveOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, "TableName=?", new String[]{DataTableOrders});
        CheckData(DataTableOrders, false);
        contentValues = new ContentValues();
        contentValues.put(this.TableName, DataTableOrders);
        contentValues.put(this.Data, gson.toJsonTree(Class_ModelDB.getOrderList()).getAsJsonArray().toString());
        db.insert(this.InitialData, null, contentValues);
        CheckData(DataTableOrders, true);
        db.close();
    }
    public void loadCurrentUser() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableCurrentUser});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            CurrentUser currentUser = new CurrentUser();
            currentUser = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), CurrentUser.class);
            Class_ModelDB.setCurrentuserModel(currentUser);
            res.moveToNext();
        }
        switch (Class_Genric.getType(Class_ModelDB.getCurrentuserModel().getUsertype())) {
            case Class_Genric.ADMIN:
                break;
            case Class_Genric.DISTRIBUTORSALES:
                break;
            case Class_Genric.DISTRIBUTOR:
                Class_Static.tempRole=Class_Genric.getRoleFromCurrentUser(Class_ModelDB.getCurrentuserModel());
                break;
            case Class_Genric.SALESMAN:
                break;
            case Class_Genric.ASM:
                break;
        }
        db.close();
    }
    public void loadLocation() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableLocation});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<LocationModel>>() {
            }.getType();
            ArrayList<LocationModel> locationlist = new ArrayList<LocationModel>();
            locationlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setLocationList(locationlist);
            res.moveToNext();
        }
        db.close();
    }
    public void loadRole() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableRole});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Role>>() {
            }.getType();
            ArrayList<Role> rolelist = new ArrayList<Role>();
            rolelist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setRoleList(rolelist);
            res.moveToNext();
        }
        db.close();
    }
    public void loadProduct() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableProduct});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Product>>() {
            }.getType();
            ArrayList<Product> productlist = new ArrayList<Product>();
            productlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setProductList(productlist);
            res.moveToNext();
        }
        db.close();
    }
    public void loadSections() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableGroup});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Sections>>() {
            }.getType();
            ArrayList<Sections> sectionlist = new ArrayList<Sections>();
            sectionlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setSectionList(sectionlist);
            res.moveToNext();
        }
        db.close();
    }
    public void loadcatagory() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableCatagory});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Catagories>>() {
            }.getType();
            ArrayList<Catagories> catagorylist = new ArrayList<Catagories>();
            catagorylist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            Class_ModelDB.setCatagoryList(catagorylist);
            res.moveToNext();
        }
        db.close();
    }
    public void loadOrders() {
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableOrders});
        res.moveToFirst();
        ArrayList<Order> orderlist = new ArrayList<Order>();

        ArrayList<Order> Temporderlist=new ArrayList<Order>();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Type listType = new TypeToken<ArrayList<Order>>() {
            }.getType();
            orderlist = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);
            res.moveToNext();
        }
        for (Order Draftorder : Class_ModelDB.DraftorderList) {
            orderlist.add(0,Draftorder);
        }
        Class_ModelDB.setOrderList(orderlist);
        db.close();
    }
    public boolean CheckIsDataAlreadyInDBorNot() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        Cursor res = sqldb.rawQuery("select * from InitialData where " + this.TableName + "=?", new String[]{DataTableCurrentUser});
        if (res.getCount() <= 0) {
            res.close();
            return false;
        }
        res.close();
        return true;
    }
    public void ClearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(this.InitialData, null, null);
        db.close();
    }
    public void saveData() {
        saveCurrentUser();
        saveRole();
        saveProduct();
        saveCatagories();
        saveSections();
        saveOrders();
    }
    public void saveOfflineData(OfflineModel_Distributor OfflineDistributor){
        SQLiteDatabase db = this.getWritableDatabase();
        contentValues = new ContentValues();
        db.delete(this.DataTableOfflineData, this.DistibutorID + " = ?",new String[]{OfflineDistributor.getId()});
        contentValues.put(this.DistibutorID, OfflineDistributor.getId());
        try {
            contentValues.put(this.Data,(new JSONObject(gson.toJson(OfflineDistributor))).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.insert(this.OfflineData, null, contentValues);
        db.close();
    }
    public void loadOfflineDistributorList(){
        ArrayList<Role> roleList = new ArrayList<Role>();
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from OfflineData", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            Class_ModelDB.OfflineDistributor=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), OfflineModel_Distributor.class);
            roleList.add(Class_SyncApi.getRole(Class_ModelDB.OfflineDistributor));
            res.moveToNext();
        }
        Class_ModelDB.setRoleList(roleList);
        db.close();
    }
    public void loadOfflineDistributorDataData(String id){
        SQLiteDatabase db = Class_DBHelper.this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from OfflineData where " + this.DistibutorID + "=?", new String[]{id});
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            //initialModel.setSections(gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), ArrayList.class));
            /*Type listType = new TypeToken<ArrayList<OfflineModel_Distributor>>() {
            }.getType();
            ArrayList<OfflineModel_Distributor> OfflineData = new ArrayList<OfflineModel_Distributor>();
            OfflineData = gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), listType);*/
            Class_ModelDB.OfflineDistributor=gson.fromJson(res.getString(res.getColumnIndex("Data")).toString(), OfflineModel_Distributor.class);
            res.moveToNext();
        }
        db.close();
    }
    public void loadData() {
        loadCurrentUser();
        loadRole();
        loadProduct();
        loadcatagory();
        loadSections();
        loadOrders();
    }
}


