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


public class uploadUpdates extends AppCompatActivity {

    private propertyControl dbControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_updates);
        dbControl = new propertyControl(this);
        String updateId = getIntent().getStringExtra("update_id");


        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");


        Button btnBack = findViewById(R.id.uploadsUpdate_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(uploadUpdates.this, uploadDetails.class);
                i.putExtra("search_id", updateId);
                startActivity(i);
            }
        });

        Button btnUpdate = findViewById(R.id.uploadsUpdate_updateBtn);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText priceEditText = findViewById(R.id.uploadsUpdate_price);
                EditText descEditText = findViewById(R.id.uploadsUpdate_desc);
                EditText addressEditText = findViewById(R.id.uploadsUpdate_address);
                EditText roomsEditText = findViewById(R.id.uploadsUpdate_rooms);

                String price = priceEditText.getText().toString();
                String desc = descEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String rooms = roomsEditText.getText().toString();
                String pro_id = updateId;

                if(!(price.isEmpty() || desc.isEmpty() || address.isEmpty() || rooms.isEmpty())) {
                    property value =
                            new property(pro_id, userId, R.drawable.house_cardimage, price, desc, address,
                                    Integer.parseInt(rooms));

                    dbControl.updateProperty(value);
                    Intent i = new Intent(uploadUpdates.this, uploadDetails.class);
                    i.putExtra("search_id", updateId);
                    startActivity(i);
                } else {
                    Toast.makeText(uploadUpdates.this, "Input field Empty", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(uploadUpdates.this, "Record Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
}