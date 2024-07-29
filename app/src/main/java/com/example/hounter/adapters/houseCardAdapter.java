package com.example.hounter.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapp.R;
import com.example.myapp.models.property;

public class houseCardAdapter extends ArrayAdapter<property> {

    private final Activity context;
    private final property[] cards;

    public houseCardAdapter(@NonNull Activity context, property[] data) {
        super(context, R.layout.house_card, data);
        this.context = context;
        this.cards = data;
    }

    @Override
    public int getCount() {
        return cards.length;
    }

    @Override
    public property getItem(int position) {
        return cards[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder holder;

        if (listItemView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            listItemView = inflater.inflate(R.layout.house_card, parent, false);

            holder = new ViewHolder();
            holder.imageView = listItemView.findViewById(R.id.cardImage);
            holder.itemTitle = listItemView.findViewById(R.id.cardTitle);
            holder.descTextView = listItemView.findViewById(R.id.cardDesc);

            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        // Get the current property
        property currentProperty = getItem(position);

        // Set the data into the views
        holder.imageView.setImageResource(currentProperty.getImage_res()); // Assuming property has an image resource ID
        holder.itemTitle.setText("$"+currentProperty.getPrice()); // Assuming property has a title
        holder.descTextView.setText(currentProperty.getDesc()); // Assuming property has a description

        return listItemView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView itemTitle;
        TextView descTextView;
    }
}
