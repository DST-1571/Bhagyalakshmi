package com.sourceedge.bhagyalakshmi.orders.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sourceedge.bhagyalakshmi.orders.R;
import com.sourceedge.bhagyalakshmi.orders.sopport.Class_Genric;

public class Login extends AppCompatActivity {
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Class_Genric.setOrientation(Login.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginButton= (Button) findViewById(R.id.login_button);
        setSupportActionBar(toolbar);
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

}
