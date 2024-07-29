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


import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.models.property;

import java.util.Random;

public class registerProperty extends AppCompatActivity {

    private propertyControl dbcontrol;

    public static String generateUniqueId() {
        // Get the current time in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random number
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Generate a 4-digit random number

        // Combine timestamp and random number
        return timestamp + String.valueOf(randomNumber);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_property);
        dbcontrol = new propertyControl(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        Button btnBack = findViewById(R.id.registerProperty_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(registerProperty.this, NavContent.class);
                i.putExtra("fragment", "profile");
                startActivity(i);
            }
        });

        Button btnUpload = findViewById(R.id.registerProperty_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_id = userId;

                EditText priceEditText = findViewById(R.id.registerProperty_price);
                EditText descEditText = findViewById(R.id.registerProperty_desc);
                EditText addressEditText = findViewById(R.id.registerProperty_address);
                EditText roomsEditText = findViewById(R.id.registerProperty_rooms);

                String price = priceEditText.getText().toString();
                String desc = descEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String rooms = roomsEditText.getText().toString();
                String pro_id = generateUniqueId();


                property pro = new property(pro_id, userId, R.drawable.house_cardimage, price, desc, address,
                        Integer.parseInt(rooms));

                if (!(price.isEmpty() || desc.isEmpty() ||
                        address.isEmpty() || rooms.isEmpty() || user_id.isEmpty())){
                    dbcontrol.addProperty(pro);
                    Toast.makeText(registerProperty.this, "Record Uploaded", Toast.LENGTH_SHORT).show();
                    priceEditText.setText("");
                    descEditText.setText("");
                    addressEditText.setText("");
                    roomsEditText.setText("");
                    Intent i = new Intent(registerProperty.this, NavContent.class);
                    i.putExtra("fragment", "profile");
                    startActivity(i);
                } else {
                    Toast.makeText(registerProperty.this, "Alert: An input field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}