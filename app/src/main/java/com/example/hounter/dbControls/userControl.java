package com.example.hounter.dbControls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapp.DBhelpers.userHelper;
import com.example.myapp.R;
import com.example.myapp.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class userControl {

    private userHelper helper;
    private static final String TABLE_USERS = "users";

    // Column names
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CONTACT = "contact";
    private static final String COLUMN_IMAGE = "image"; // New column for image

    private static final List<User> users = Arrays.asList(
            new User("ADMIN", "ADMIN", "123-456-7890", "ADMIN", R.drawable.house_cardimage),
            new User("jane_smith", "securePass", "234-567-8901", "user02", R.drawable.house_cardimage),
            new User("mike_jones", "qwerty", "345-678-9012", "user03", R.drawable.house_cardimage),
            new User("sara_lee", "password", "456-789-0123", "user04", R.drawable.house_cardimage),
            new User("tom_brown", "letmein", "567-890-1234", "user05", R.drawable.house_cardimage),
            new User("emma_clark", "pass1234", "678-901-2345", "user06", R.drawable.house_cardimage),
            new User("liam_white", "12345678", "789-012-3456", "user07", R.drawable.house_cardimage),
            new User("olivia_harris", "mypassword", "890-123-4567", "user08", R.drawable.house_cardimage),
            new User("noah_martin", "abcdefg", "901-234-5678", "user09", R.drawable.house_cardimage),
            new User("ava_thomas", "passw0rd", "012-345-6789", "user10",R.drawable.house_cardimage)
    );

    public userControl(Context context){
        helper = new userHelper(context);
    }

    public void createTable(){
        try{
            String selectQuery = "SELECT * FROM " + TABLE_USERS;

            SQLiteDatabase db = this.helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e){
            try{
                SQLiteDatabase db = this.helper.getWritableDatabase();
                String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                        + COLUMN_USER_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_USERNAME + " TEXT,"
                        + COLUMN_PASSWORD + " TEXT,"
                        + COLUMN_CONTACT + " TEXT,"
                        + COLUMN_IMAGE + " INTEGER" + ")"; // Add the image column
                db.execSQL(CREATE_USERS_TABLE);
            } catch (Exception e2) {
                Log.e("error", "table creation error");
            }
        }
    }

    public String addUser(User user) {
        try {
            SQLiteDatabase db = this.helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, user.getUserId());
            values.put(COLUMN_USERNAME, user.getUsername());
            values.put(COLUMN_PASSWORD, user.getPassword());
            values.put(COLUMN_CONTACT, user.getContact());
            values.put(COLUMN_IMAGE, user.getImage()); // Add the image field

            long id = db.insert(TABLE_USERS, null, values);
            db.close();

            return String.valueOf(id);
        }catch (Exception e){
            System.out.println("cannot add user");
            return "";
        }
    }

    public String getUserId(User user) {
        try {
            SQLiteDatabase db = this.helper.getReadableDatabase();

            Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID},
                    COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                    new String[]{user.getUsername(), user.getPassword()}, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String userId = cursor.getString(0);
                cursor.close();
                return userId;
            } else {
                if (cursor != null) {
                    cursor.close();
                }
                return "";
            }
        } catch (Exception e){
            return "";
        }
    }

    public User getUserById(String userId) {

        SQLiteDatabase db = this.helper.getReadableDatabase();

        try {
            Cursor cursor = db.query(TABLE_USERS, null, COLUMN_USER_ID + "=?",
                    new String[]{userId}, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                // Assuming you have a column named COLUMN_IMAGE for the image resource in the database
                User user = new User(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                );
                cursor.close();
                return user;
            } else {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
        } catch (Exception e){
            return null;
        }
    }


    public List<User> getAllUsers() {
        try {
            List<User> userList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_USERS;

            SQLiteDatabase db = this.helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    User user = new User(
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTACT)),
                            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))); // Get image
                    userList.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (!userList.isEmpty())
                return userList;
            else
                return this.users;
        } catch (Exception e){
            return this.users;
        }
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.helper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, user.getUsername());
            values.put(COLUMN_PASSWORD, user.getPassword());
            values.put(COLUMN_CONTACT, user.getContact());
            values.put(COLUMN_IMAGE, user.getImage()); // Add the image field

            return db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?",
                    new String[]{user.getUserId()});
        } catch (Exception e){
            return 0;
        }
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?",
                new String[]{user.getUserId()});
        db.close();
    }

    public int getUsersCount() {
        try {
            String countQuery = "SELECT * FROM " + TABLE_USERS;
            SQLiteDatabase db = this.helper.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            int count = cursor.getCount();
            cursor.close();
            return count;
        }catch (Exception e){
            return 0;
        }
    }
}
