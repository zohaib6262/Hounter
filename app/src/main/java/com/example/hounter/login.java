package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;


public class login extends AppCompatActivity {

    private userControl dbControl;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        // Initialize the database helper
        dbControl = new userControl(context);

        Button btnLogin = findViewById(R.id.btn_login);
        EditText getUsername = findViewById(R.id.username);
        EditText getPassword = findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Intent in = new Intent(login.this, NavContent.class);

                if (!(username.isEmpty() || password.isEmpty())) {
                    if (username.equals("ADMIN") && password.equals("ADMIN")) {
                        editor.putString("userId", "ADMIN");
                        editor.apply();
                        startActivity(in);
                        finish(); // Finish the login activity
                    } else {
                        User user = new User(username, password, "", "", 0);
                        String userId = dbControl.getUserId(user);
                        if (!(userId.equals(""))) {
                            editor.putString("userId", userId);
                            editor.apply();
                            startActivity(in);
                            finish(); // Finish the login activity
                        } else {
                            Toast.makeText(context, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(context, "Empty Input Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(login.this, signup.class);
                startActivity(in);
                finish(); // Finish the login activity
            }
        });
    }
}
