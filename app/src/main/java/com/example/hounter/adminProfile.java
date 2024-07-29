package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.hounter.adapters.houseCardAdapter;
import com.example.hounter.adapters.userCardAdapter;
import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;
import com.example.hounter.models.property;

import java.util.ArrayList;
import java.util.List;


public class adminProfile extends Fragment {
    private View view;
    private propertyControl dbControlProperty;
    private userControl dbControlUsers;

    public adminProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_profile, container, false);

        Context context = getContext();
        if (context == null) return view;

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        dbControlProperty = new propertyControl(context);
        dbControlUsers = new userControl(context);

        // Directly set admin profile information
        TextView displayUsername = view.findViewById(R.id.display_username);
        displayUsername.setText("ADMIN");
        ImageView displayImage = view.findViewById(R.id.display_picture);
        displayImage.setImageResource(R.drawable.house_cardimage);  // Assuming you have an image resource for admin

        setPropertyList();
        setUsersList();

        Button btnLogout = view.findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "");
                editor.apply();
                Intent in = new Intent(getActivity(), login.class);
                startActivity(in);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        Button btnRegister = view.findViewById(R.id.redirect_to_registry);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), registerProperty.class);
                startActivity(in);
            }
        });

        return view;
    }

    private void setUsersList() {

        ListView usersList = view.findViewById(R.id.usersList);
        User[] data;
        {

            List<User> users = dbControlUsers.getAllUsers();
            if (users.isEmpty()) {
                // Provide default data or show a message
                users = new ArrayList<>();
                users.add(new User("No Users", "", "", "", R.drawable.house_cardimage));  // Assuming you have a 'no_image' drawable
                Toast.makeText(getActivity(), "No users found", Toast.LENGTH_SHORT).show();
            }

            data = users.toArray(new User[0]);

            // Create adapter
            userCardAdapter adapter = new userCardAdapter(getActivity(), data);

            // Set adapter to ListView
            usersList.setAdapter(adapter);
        }
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), profileDetails.class);
                i.putExtra("user_id", data[(int)id].getUserId());
                startActivity(i);
            }
        });
    }

    private void setPropertyList() {
        ListView uploadsList = view.findViewById(R.id.uploadsList);

        if (uploadsList == null) {
            throw new NullPointerException("uploadsList is null. Ensure you have a ListView with ID 'uploadsList' in fragment_admin_profile layout.");
        }

        List<property> properties = dbControlProperty.getAllProperties();
        if (properties.isEmpty()) {
            // Provide default data or show a message
            properties = new ArrayList<>();
            properties.add(new property("","", R.drawable.house_cardimage,"","","",0));  // Assuming you have a 'no_image' drawable
            Toast.makeText(getActivity(), "No properties found", Toast.LENGTH_SHORT).show();
        }

        // Create adapter
        houseCardAdapter adapter = new houseCardAdapter(getActivity(), properties.toArray(new property[0]));

        // Set adapter to ListView
        uploadsList.setAdapter(adapter);

        List<property> finalProperties = properties;
        uploadsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), uploadDetails.class);
                i.putExtra("search_id", finalProperties.get((int)id).getId());
                startActivity(i);
            }
        });
    }
}
