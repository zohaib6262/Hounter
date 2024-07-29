package com.example.hounter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.hounter.adapters.houseCardAdapter;
import com.example.hounter.dbControls.propertyControl;
import com.example.hounter.models.property;

import java.util.ArrayList;
import java.util.List;


public class marketplace extends Fragment {
    private propertyControl dbControl;
    private Context context;

    public marketplace(Context context) {
        // Required empty public constructor
        this.context = context;
    }

    public marketplace() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);

        if (context == null) {
            context = getContext();
        }

        ListView listView = view.findViewById(R.id.marketplaceList);

        Button btnReload = view.findViewById(R.id.btn_reload);

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProperties(listView);
            }
        });

        // Load properties initially
        loadProperties(listView);

        return view;
    }

    private void loadProperties(ListView listView) {
        dbControl = new propertyControl(context);

        List<property> properties = dbControl.getAllProperties();
        if (properties.isEmpty()) {
            Toast.makeText(context, "No properties found", Toast.LENGTH_SHORT).show();
            properties = new ArrayList<>();
            properties.add(new property("","", R.drawable.house_cardimage,"",
                    "No properties found","",0)); // Assuming you have a 'no_image' drawable
        }

        property[] data = properties.toArray(new property[0]);

        // Create adapter
        houseCardAdapter adapter = new houseCardAdapter(getActivity(), data);

        // Set adapter to ListView
        listView.setAdapter(adapter);

        // Optionally, set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(getActivity(), marketDetails.class);
                i.putExtra("search_id", data[(int)id].getP_id()+"");
                startActivity(i);
            }
        });
    }
}
