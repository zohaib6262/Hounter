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

import androidx.fragment.app.Fragment;

import com.example.hounter.adapters.houseCardAdapter;
import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.dbControls.userControl;
import com.example.hounter.models.User;
import com.example.hounter.models.property;

import java.util.List;


public class profile extends Fragment {

    private propertyControl dbControl;

    public profile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbControl = new propertyControl(getContext());

        ListView listView = view.findViewById(R.id.uploadsList);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserPreferences",
                Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", "");

        userControl dbUserControl = new userControl(getContext());
        User profileSetup = dbUserControl.getUserById(userId);

        if(profileSetup != null) {
            TextView displayUsername = (TextView) view.findViewById(R.id.display_username);
            displayUsername.setText(profileSetup.getUsername());
            ImageView displayImage = (ImageView) view.findViewById(R.id.display_picture);
            displayImage.setImageResource(profileSetup.getImage());

            List<property> properties = dbControl.getPropertyByUserId(userId);

            // Create adapter
            houseCardAdapter adapter = new houseCardAdapter(getActivity(), properties.toArray(new property[0]));

            // Set adapter to ListView
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    Intent i = new Intent(getActivity(), uploadDetails.class);
                    i.putExtra("search_id", properties.get((int) id).getId());
                    startActivity(i);
                }
            });
        }
        Button btnRegister = view.findViewById(R.id.redirect_to_registry);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), registerProperty.class);
                startActivity(in);
            }
        });

        Button btnLogout = view.findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userId", "");
                editor.commit();
                Intent in = new Intent(getActivity(), login.class);
                startActivity(in);

            }
        });

        Button btnInfo = view.findViewById(R.id.btn_info);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), profileDetails.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
            }
        });

        return view;
    }
}