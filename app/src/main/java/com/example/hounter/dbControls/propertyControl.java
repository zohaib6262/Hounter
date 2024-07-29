package com.example.hounter.dbControls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.hounter.DBhelpers.propertyHelper;
import com.example.hounter.R;
import com.example.hounter.models.property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class propertyControl {
    private propertyHelper helper;

    private static final String TABLE_PROPERTIES = "properties";

    private static final String COLUMN_ID = "p_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_IMAGE_RES = "image_res";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ROOMS = "rooms";

    private static List<property> properties = Arrays.asList(
            new property("p001", "user01", R.drawable.house_cardimage, "1000", "Spacious 2-bedroom apartment", "123 Main St", 2),
            new property("p002", "user02", R.drawable.house_cardimage, "1500", "Modern 3-bedroom house", "456 Elm St", 3),
            new property("p003", "user03", R.drawable.house_cardimage, "1200", "Cozy 1-bedroom condo", "789 Maple Ave", 1),
            new property("p004", "user04", R.drawable.house_cardimage, "2000", "Luxury 4-bedroom villa", "101 Pine St", 4),
            new property("p005", "user05", R.drawable.house_cardimage, "950", "Affordable studio apartment", "202 Oak St", 1),
            new property("p006", "user06", R.drawable.house_cardimage, "1100", "Charming 2-bedroom cottage", "303 Birch St", 2),
            new property("p007", "user07", R.drawable.house_cardimage, "1300", "Stylish 3-bedroom townhouse", "404 Cedar St", 3),
            new property("p008", "user08", R.drawable.house_cardimage, "1700", "Elegant 4-bedroom mansion", "505 Walnut St", 4),
            new property("p009", "user09", R.drawable.house_cardimage, "1400", "Spacious 3-bedroom duplex", "606 Chestnut St", 3),
            new property("p010", "user10", R.drawable.house_cardimage, "1600", "Modern 2-bedroom loft", "707 Redwood St", 2)
    );

    public propertyControl(Context context){
        helper = new propertyHelper(context);
    }

    public void createTable(){
        try{
            String selectQuery = "SELECT * FROM " + TABLE_PROPERTIES;

            SQLiteDatabase db = this.helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
        } catch (Exception e){
            try{
                SQLiteDatabase db = this.helper.getWritableDatabase();
                String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                        + COLUMN_ID + " TEXT PRIMARY KEY,"
                        + COLUMN_USER_ID + " TEXT,"
                        + COLUMN_IMAGE_RES + " INTEGER,"
                        + COLUMN_PRICE + " TEXT,"
                        + COLUMN_DESC + " TEXT,"
                        + COLUMN_ADDRESS + " TEXT,"
                        + COLUMN_ROOMS + " INTEGER" + ")";
                db.execSQL(CREATE_PROPERTIES_TABLE);
            } catch (Exception e2) {
                Log.e("error", "table creation error");
            }
        }
    }

    public void addProperty(property property) {
        try {
            SQLiteDatabase db = this.helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, property.getId());
            values.put(COLUMN_USER_ID, property.getUserId());
            values.put(COLUMN_IMAGE_RES, property.getImage_res());
            values.put(COLUMN_PRICE, property.getPrice());
            values.put(COLUMN_DESC, property.getDesc());
            values.put(COLUMN_ADDRESS, property.getAddress());
            values.put(COLUMN_ROOMS, property.getRooms());

            db.insert(TABLE_PROPERTIES, null, values);
            db.close();
        }catch (Exception e){
            System.out.println("cannot add property");
        }
    }

    public property getProperty(String id) {
        try {
            SQLiteDatabase db = this.helper.getReadableDatabase();

            Cursor cursor = db.query(TABLE_PROPERTIES, new String[]{COLUMN_ID, COLUMN_USER_ID, COLUMN_IMAGE_RES,
                            COLUMN_PRICE, COLUMN_DESC, COLUMN_ADDRESS, COLUMN_ROOMS}, COLUMN_ID + "=?",
                    new String[]{id}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return this.properties.get(0);

            property property = new property(
                    cursor.getString(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5),
                    cursor.getInt(6));
            cursor.close();

            return property;
        } catch(Exception e){
            return this.properties.get(0);
        }
    }

    public List<property> getPropertyByUserId(String userId) {
        try {
            List<property> propertyList = new ArrayList<>();
            SQLiteDatabase db = this.helper.getReadableDatabase();

            Cursor cursor = db.query(TABLE_PROPERTIES, new String[]{
                            COLUMN_ID, COLUMN_USER_ID, COLUMN_IMAGE_RES, COLUMN_PRICE, COLUMN_DESC, COLUMN_ADDRESS, COLUMN_ROOMS},
                    COLUMN_USER_ID + "=?", new String[]{userId}, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    property property = new property(
                            cursor.getString(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getInt(6));
                    propertyList.add(property);
                } while (cursor.moveToNext());
                cursor.close();
            } else {
                if (cursor != null) {
                    cursor.close();
                }
            }
            Log.e("msg", propertyList.size()+"");
            if (propertyList.isEmpty())
                return this.properties;
            return propertyList;
        }catch (Exception e){
            return this.properties;
        }
    }



    public List<property> getAllProperties() {
        try {
            List<property> propertyList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_PROPERTIES;

            SQLiteDatabase db = this.helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    property property = new property(
                            cursor.getString(0), cursor.getString(1),
                            cursor.getInt(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getInt(6));
                    propertyList.add(property);
                } while (cursor.moveToNext());
            }
            cursor.close();
            if (propertyList.isEmpty()) {
                propertyList = this.properties;
            }
            return propertyList;
        }catch (Exception e){
            return this.properties;
        }
    }

    public int updateProperty(property property) {
        try {
            SQLiteDatabase db = this.helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, property.getUserId());
            values.put(COLUMN_IMAGE_RES, property.getImage_res());
            values.put(COLUMN_PRICE, property.getPrice());
            values.put(COLUMN_DESC, property.getDesc());
            values.put(COLUMN_ADDRESS, property.getAddress());
            values.put(COLUMN_ROOMS, property.getRooms());

            return db.update(TABLE_PROPERTIES, values, COLUMN_ID + " = ?",
                    new String[]{property.getId()});
        }catch (Exception e){
            System.out.println("property update failed");
            return 0;
        }
    }

    public void deleteProperty(property property) {
        try {
            SQLiteDatabase db = this.helper.getWritableDatabase();
            db.delete(TABLE_PROPERTIES, COLUMN_ID + " = ?",
                    new String[]{property.getId()});
            db.close();
        }catch (Exception e){
            System.out.println("property deletion failed");
        }
    }

    public int getPropertiesCount() {
        String countQuery = "SELECT * FROM " + TABLE_PROPERTIES;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
