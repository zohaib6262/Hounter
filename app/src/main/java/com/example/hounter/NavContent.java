package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavContent extends AppCompatActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_content);
        context = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        if (savedInstanceState == null) {
            loadFragment(new Home());
        }

        Intent getter = getIntent();
        String loader = getter.getStringExtra("fragment");

        if (loader != null) {
            switch (loader) {
                case "home":
                    loadFragment(new Home());
                    break;
                case "marketplace":
                    loadFragment(new marketplace(context));
                    break;
                case "profile":
                    if ("ADMIN".equals(userId)) {
                        loadFragment(new adminProfile());
                    } else {
                        loadFragment(new profile());
                    }
                    break;
                default:
                    // Handle unexpected values
                    break;
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId() == R.id.menu_home) {
                    selectedFragment = new Home();
                } else if (item.getItemId() == R.id.menu_marketplace) {
                    selectedFragment = new marketplace(context);
                } else if (item.getItemId() == R.id.menu_profile) {
                    if ("ADMIN".equals(userId)) {
                        selectedFragment = new adminProfile();
                    } else {
                        selectedFragment = new profile();
                    }
                }
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_host, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
