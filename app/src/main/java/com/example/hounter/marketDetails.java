package com.example.hounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;
import com.example.hounter.models.property;


public class marketDetails extends AppCompatActivity {
    private propertyControl dbControl;
    private userControl dbUserControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_details);
        dbControl = new propertyControl(this);
        dbUserControl = new userControl(this);

        // Assuming you pass the property object id to this activity
        String searchId = getIntent().getStringExtra("search_id");
        // Initialize views
       if (searchId != null){
           ImageView imageView = findViewById(R.id.property_image);
           TextView priceTextView = findViewById(R.id.property_price);
           TextView descTextView = findViewById(R.id.property_description);
           TextView addressTextView = findViewById(R.id.property_address);
           TextView roomsTextView = findViewById(R.id.property_rooms);
           TextView contactTextView = findViewById(R.id.property_ownerContact);
           property value = dbControl.getProperty(searchId);

           User owner = dbUserControl.getUserById(value.getUserId());

           // Set data to views
           if (owner != null && value != null){
               imageView.setImageResource(value.getImage_res());
               priceTextView.setText(value.getPrice());
               descTextView.setText(value.getDesc());
               addressTextView.setText(value.getAddress());
               roomsTextView.setText(value.getRooms()+"");
               contactTextView.setText(owner.getContact()+"");
           }
       }
        Button btnBack = findViewById(R.id.redirect_to_marketplace_from_registryForm);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(marketDetails.this, marketplace.class);
                i.putExtra("fragment", "marketplace");
                startActivity(i);
            }
        });
    }
}