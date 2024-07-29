package com.example.hounter.DBhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class propertyHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "hounter.db";
    private static final String TABLE_PROPERTIES = "properties";

    // Column names
    private static final String COLUMN_ID = "p_id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_IMAGE_RES = "image_res";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ROOMS = "rooms";



    public propertyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_USER_ID + " TEXT,"
                + COLUMN_IMAGE_RES + " INTEGER,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_DESC + " TEXT,"
                + COLUMN_ADDRESS + " TEXT,"
                + COLUMN_ROOMS + " INTEGER" + ")";
        db.execSQL(CREATE_PROPERTIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        onCreate(db);
    }

}

