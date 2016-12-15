package com.sourceedge.bhagyalakshmi.orders.changepassword;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.login.Login;
import com.sourceedge.bhagyalakshmi.orders.support.Class_Genric;
import com.sourceedge.bhagyalakshmi.orders.support.Class_SyncApi;

public class Change_Password extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    Button cancelButton, saveButton;
    EditText oldPassword, newPassword, confirmPassword;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Class_Genric.setOrientation(Change_Password.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bhagyalakshmi Traders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Class_Genric.applyFontForToolbarTitle(toolbar, Change_Password.this);
       /* drawer = (DrawerLayout) findViewById(R.id.navigation_drawer);
        Class_Genric.setupDrawer(toolbar, drawer, mDrawerToggle, Change_Password.this);
        Class_Genric.drawerOnClicks(Change_Password.this);*/
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword = (EditText) findViewById(R.id.new_password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        saveButton = (Button) findViewById(R.id.save_button);
        OnClicks();
    }

    private void OnClicks() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!("".equals(oldPassword.getText().toString().trim()))) {
                    if (!("".equals(newPassword.getText().toString().trim()))) {
                        if (!("".equals(confirmPassword.getText().toString().trim()))) {
                            if (newPassword.getText().toString().matches(confirmPassword.getText().toString())) {
                                Class_SyncApi.ChangePasswordApi(Change_Password.this, oldPassword, confirmPassword);
                            }else confirmPassword.setError("Password Mismatch");
                        } else confirmPassword.setError("Field cannot be empty");
                    } else newPassword.setError("Field cannot be empty");
                } else oldPassword.setError("Field cannot be empty");
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
