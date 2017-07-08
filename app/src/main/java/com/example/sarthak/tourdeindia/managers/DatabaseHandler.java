package com.example.sarthak.tourdeindia.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sarthak.tourdeindia.model.Item;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //---------------------------------------------------------------
    // Database constants
    //---------------------------------------------------------------

    // database version
    private static final int DATABASE_VERSION = 1;

    // database Name
    private static final String DATABASE_NAME = "CityManager";

    // city table name
    private static final String TABLE_CITY = "City";

    private static final String KEY_ID = "id";

    // city table column names
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_CITY_DESC = "city_desc";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_CITY_NAME + " TEXT,"
                + KEY_CITY_DESC + " TEXT" + ")";

        db.execSQL(CREATE_CITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);

        // create table again
        onCreate(db);
    }

    public void addCity(Item city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_ID, getCityCount() + 1);
        values.put(KEY_CITY_NAME, city.getName());
        values.put(KEY_CITY_DESC, city.getCity_desc());

        // inserting row
        db.insert(TABLE_CITY, null, values);
        // closing database connection
        db.close();
    }

    public Item getCityDetails(int city_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Item city = new Item();

        Cursor cursor = db.query(TABLE_CITY, new String[] { KEY_ID, KEY_CITY_NAME,
                        KEY_CITY_DESC }, KEY_ID + "=?", new String[] { String.valueOf(city_id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            city = new Item(cursor.getString(1), cursor.getString(2));
        }

        // return city
        return city;
    }

    public String[] getCity(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor crs = db.rawQuery("SELECT * FROM " + TABLE_CITY, null);

        String[] array = new String[getCityCount()];

        int i = 0;
        while(crs.moveToNext()){
            String cname = crs.getString(crs.getColumnIndex(KEY_CITY_NAME));
            array[i] = cname;
            i++;
        }

        return array;
    }

    public List<Item> getAll() {
        List<Item> cityList = new ArrayList<>();

        // select all query
        String selectQuery = "SELECT  * FROM " + TABLE_CITY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Item city = new Item();

                city.setName(cursor.getString(1));
                city.setCity_desc(cursor.getString(2));

                // adding city to list
                cityList.add(city);
            } while (cursor.moveToNext());
        }

        // return city list
        return cityList;
    }

    private void updateCity(int city_id) {

        int updated_city_id = city_id - 1;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("UPDATE " + TABLE_CITY
                + " SET " + KEY_ID + " = " + updated_city_id + " WHERE " + KEY_ID + " = " + city_id, null);

        cursor.moveToFirst();
        cursor.close();
    }

    public void deleteCity(int city_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TABLE_CITY, KEY_ID + " = ?",
                    new String[] { String.valueOf(city_id) });

            for (int i = city_id + 1 ; i <= getCityCount() + 1; i++) {
                updateCity(i);
            }

            db.close();
        }
        catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public int getCityCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CITY;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    public String getName(int city_id) {

        return getCityDetails(city_id).getName();
    }

    public String getCityDesc(int city_id) {

        return getCityDetails(city_id).getCity_desc();
    }
}
