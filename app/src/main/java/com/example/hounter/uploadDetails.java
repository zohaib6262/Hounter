package com.example.hounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.models.property;

public class uploadDetails extends AppCompatActivity {

    private propertyControl dbControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_details);
        dbControl = new propertyControl(this);

        // Assuming you pass the property object to this activity
        String searchId = getIntent().getStringExtra("search_id");
        property value;
        Toast.makeText(this, "id = " + searchId, Toast.LENGTH_SHORT).show();

        if (searchId != null) {
            // Initialize views
            ImageView imageView = findViewById(R.id.property_image);
            TextView priceTextView = findViewById(R.id.property_price);
            TextView descTextView = findViewById(R.id.property_description);
            TextView addressTextView = findViewById(R.id.property_address);
            TextView roomsTextView = findViewById(R.id.property_rooms);

            value = dbControl.getProperty(searchId);

            // Set data to views
            imageView.setImageResource(value.getImage_res());
            priceTextView.setText(value.getPrice()+"");
            descTextView.setText(value.getDesc());
            addressTextView.setText(value.getAddress());
            roomsTextView.setText(value.getRooms()+"");


            Button btnDelete = (findViewById(R.id.uploadsDelete));
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbControl.deleteProperty(value);
                    Toast.makeText(uploadDetails.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(uploadDetails.this, NavContent.class);
                    i.putExtra("fragment", "profile");
                    startActivity(i);
                }
            });

            Button btnUpdate = (findViewById(R.id.uploadsUpdate));
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(uploadDetails.this, uploadUpdates.class);
                    i.putExtra("update_id", searchId);
                    startActivity(i);
                }
            });
        }

        Button btnBack = findViewById(R.id.redirect_to_profile_from_uploadsDetails);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(uploadDetails.this, NavContent.class);
                i.putExtra("fragment", "profile");
                startActivity(i);
            }
        });
    }
}