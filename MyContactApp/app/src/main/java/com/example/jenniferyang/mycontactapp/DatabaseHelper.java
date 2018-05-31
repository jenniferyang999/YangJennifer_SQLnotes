package com.example.jenniferyang.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "Contact2018_0.db";
    public static final String TABLE_NAME = "Contact2018_table";
    public static final String ID = "ID";
    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String PHONE_NUMBER = "phonenumber";
    public static final String ADDRESS_NAME = "address";



    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME_CONTACT + " TEXT," +  PHONE_NUMBER  + " TEXT," + ADDRESS_NAME + " TEXT)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("MYCONTACTAPP", "DatabaseHelper: constructed DatabaseHelper");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MYCONTACTAPP", "DatabaseHelper: creating Database");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d("MYCONTACTAPP", "DatabaseHelper: upgrading Database");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(String name, String number, String address){
        Log.d("MYCONTACTAPP", "DatabaseHelper: inserting Database");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_CONTACT, name);
        contentValues.put(PHONE_NUMBER, number);
        contentValues.put(ADDRESS_NAME, address);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            Log.d("MYCONTACTAPP", "DatabaseHelper: Contact insert - F" +
                    "AILED");
            return false;
        }
        else {
            Log.d("MYCONTACTAPP", "DatabaseHelper: Contact insert - PASSED");
                return true;
        }
    }

    public Cursor getAllData(){
        Log.d("MYCONTACTAPP", "DatabaseHelper: pulling all data records from db");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select *  from  " + TABLE_NAME, null);
        return res;
    }


}
