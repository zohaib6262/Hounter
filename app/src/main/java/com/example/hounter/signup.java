package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;

import java.util.Random;

public class signup extends AppCompatActivity {
    private userControl dbControl;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        dbControl = new userControl(this);
        context = this;

        TextView redirect = (TextView) findViewById(R.id.redirect_to_Login);
        EditText getUsername = findViewById(R.id.signup_username);
        EditText getPassword = findViewById(R.id.signup_password);
        EditText getConfirmPassword = findViewById(R.id.signup_confirmPassword);
        EditText getContact = findViewById(R.id.signup_contact);

        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signup.this, login.class);
                startActivity(i);
            }
        });

        Button btnSignup = findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = getUsername.getText().toString();
                String password = getPassword.getText().toString();
                String confirmPassword = getConfirmPassword.getText().toString();
                String contact = getContact.getText().toString();
                User user = new User(username, password, contact, generateUniqueId(),R.drawable.house_cardimage);
                if (!(username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || contact.isEmpty())){
                    if (password.equals(confirmPassword)) {
                        dbControl.addUser(user);
                        Toast.makeText(context, "Profile created", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(signup.this, login.class);
                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(context, "password does not match confirm password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Input field cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String generateUniqueId() {
        // Get the current time in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random number
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Generate a 4-digit random number

        // Combine timestamp and random number
        return timestamp + String.valueOf(randomNumber);
    }
}