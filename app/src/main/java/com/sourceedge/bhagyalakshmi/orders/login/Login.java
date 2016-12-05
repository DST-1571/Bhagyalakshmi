package com.sourceedge.bhagyalakshmi.orders.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.dashboard.controller.Dashboard;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button loginButton;
    Spinner spinner;
    String item="";
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Class_Genric.setOrientation(Login.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences = this.getSharedPreferences(Class_Genric.MyPref, this.MODE_PRIVATE);
        loginButton= (Button) findViewById(R.id.login_button);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Admin");
        categories.add("Distributor Sales");
        categories.add("Distributor");
        categories.add("Sales Person");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        onClicks();
    }

    private void onClicks() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Dashboard.class));
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        item = parent.getItemAtPosition(position).toString();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Class_Genric.Sp_LoginType,item);
        editor.commit();
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
