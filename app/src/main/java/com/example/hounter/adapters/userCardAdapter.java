package com.example.hounter.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hounter.R;
import com.example.hounter.models.User;


public class userCardAdapter extends ArrayAdapter<User> {

    private Activity context;
    private User[] users;

    public userCardAdapter(@NonNull Activity context, User[] users) {
        super(context, R.layout.user_card, users);
        this.context = context;
        this.users = users;
    }

    public userCardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public User getItem(int position) {
        return users[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder holder;

        if (listItemView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            listItemView = inflater.inflate(R.layout.user_card, parent, false);

            holder = new ViewHolder();
            holder.imageView = listItemView.findViewById(R.id.profileCard_Image);
            holder.username = listItemView.findViewById(R.id.profileCard_Username);
            holder.contact = listItemView.findViewById(R.id.profileCard_Contact);

            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        // Get the current user
        User currentUser = getItem(position);

        // Set the data into the views
        holder.imageView.setImageResource(currentUser.getImage());
        holder.username.setText(currentUser.getUsername());
        holder.contact.setText(currentUser.getContact());

        return listItemView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView username;
        TextView contact;
    }
}
