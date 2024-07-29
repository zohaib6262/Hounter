package com.example.hounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;


public class profileUpdate extends AppCompatActivity {
    userControl dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);

        // Retrieve user ID from Intent
        Intent in = getIntent();
        String userId = in.getStringExtra("update_id");

        // Initialize EditText fields
        EditText usernameEditText = findViewById(R.id.userUpdate_username);
        EditText nPasswordEditText = findViewById(R.id.userUpdate_password);
        EditText opasswordEditText = findViewById(R.id.userUpdate_oldPassword);
        EditText contactEditText = findViewById(R.id.userUpdate_contact);
        ImageView profileImageView = findViewById(R.id.user_profile_image);

        // Fetch user details from the database
        dbHelper = new userControl(this);
        User user = dbHelper.getUserById(userId);
        //Toast.makeText(this, "user = " + userId, Toast.LENGTH_SHORT).show();


        // Handle 'Back' button click
        Button btnBack = findViewById(R.id.userUpdate_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to the previous activity (e.g., UserDetails)
                Intent intent = new Intent(profileUpdate.this, NavContent.class);
                intent.putExtra("fragment", "profile");
                startActivity(intent);
            }
        });

        // Handle 'Update' button click
        Button btnUpdate = findViewById(R.id.userUpdate_updateBtn);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 0;
                // Update user data
                String username = usernameEditText.getText().toString();
                String password = nPasswordEditText.getText().toString();
                String contact = contactEditText.getText().toString();
                if (!(username.isEmpty() || password.isEmpty() || contact.isEmpty())) {
                    // Update user object
                    if (opasswordEditText.getText().toString().equals(user.getPassword())) {
                        user.setUserId(userId);
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setContact(contact);
                        // Update profile image if you allow changing it (example, default drawable used)
                        user.setImage(R.drawable.house_cardimage);

                        // Save updated user to database
                        result = dbHelper.updateUser(user);

                        // Notify user and go back to user details activity
                        if (result != 0)
                            Toast.makeText(profileUpdate.this, "User details updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(profileUpdate.this, NavContent.class);
                        intent.putExtra("user_id", userId);
                        intent.putExtra("fragment", "profile");
                        startActivity(intent);
                    } else {
                        Toast.makeText(profileUpdate.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(profileUpdate.this, "Input field empty", Toast.LENGTH_SHORT).show();
                }
           }
        });
    }
}