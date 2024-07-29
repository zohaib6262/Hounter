package com.example.hounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;


public class profileDetails extends AppCompatActivity {
    private userControl dbControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        dbControl = new userControl(this);

        // Assuming you pass the user ID to this activity
        String userId = getIntent().getStringExtra("user_id");

        // Initialize views
        ImageView imageView = findViewById(R.id.user_image);
        TextView usernameTextView = findViewById(R.id.user_username);
        TextView contactTextView = findViewById(R.id.user_contact);
        User user = dbControl.getUserById(userId);

        // Set data to views
        imageView.setImageResource(user.getImage());
        usernameTextView.setText(user.getUsername());
        contactTextView.setText(user.getContact());

        Button btnDelete = findViewById(R.id.user_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbControl.deleteUser(user);
                Toast.makeText(profileDetails.this, "User Deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(profileDetails.this, NavContent.class);
                if (userId != "ADMIN")
                    i = new Intent(profileDetails.this, login.class);
                i.putExtra("fragment", "profile");
                startActivity(i);
            }
        });

        Button btnUpdate = findViewById(R.id.user_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(profileDetails.this, profileUpdate.class);
                i.putExtra("update_id", userId);
                startActivity(i);
            }
        });

        Button btnBack = findViewById(R.id.user_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(profileDetails.this, NavContent.class);
                i.putExtra("fragment", "profile");
                startActivity(i);
            }
        });
    }
}