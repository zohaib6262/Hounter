package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        dbPropertyControl = new propertyControl(this);
        dbUserControl = new userControl(this);

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
                    in = new Intent(splashScreen.this, NavContent.class);
                else
                    in = new Intent(splashScreen.this, signup.class);

                in.putExtra("fragment", "home");
                startActivity(in);
                finish();
            }
        }, 4000);
    }
}