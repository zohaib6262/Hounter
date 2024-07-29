package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.dbControls.userControl;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        propertyControl dbPropertyControl = new propertyControl(this);
        userControl dbUserControl = new userControl(this);

        dbPropertyControl.createTable();
        dbUserControl.createTable();

        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.contains("userId");
        Context c = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in;
                if (isLoggedIn)
                    in = new Intent(MainActivity.this, NavContent.class);
                else
                    in = new Intent(MainActivity.this, signup.class);

                in.putExtra("fragment", "home");
                startActivity(in);
                finish();
            }
        }, 4000);
    }
}