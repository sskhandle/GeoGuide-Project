package com.cs442_skatkar.geoguidemod1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "places.db";
    public static final String TABLE_PRODUCTS = "places";
    private static final String COLOUM_ID = "_id";
    private static final String COLOUM_PLACENAME = "name";
    private static final String COLOUM_PLACEICON = "icon";
    private static final String TAG = "myClue:";


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLOUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLOUM_PLACENAME + " TEXT, " + COLOUM_PLACEICON + " TEXT " + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + TABLE_PRODUCTS);
        onCreate(db);
    }


    //Add a new row to the database
    public void addProduct(Places ps){
        ContentValues values = new ContentValues();
        values.put(COLOUM_PLACENAME, ps.get_name());
        values.put(COLOUM_PLACEICON, ps.get_vincinity());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
}
