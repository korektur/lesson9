package com.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 16:41
 */
public class SQLRequest {

    private static final String DataBaseName = "WeatherForecasts";
    private static final String cityTableName = "CityTable";
    private static final String keyId = "_id";
    private static final String keyCityId = "cityId";
    private static final String keyCountryName = "countryKey";
    private static final String keyCityName = "cityNameKey";
    private static final int Database_Version = 1;

    private boolean opened;
    private Context context;
    private SQLiteDatabase database;
    private DBHelper helper;

    private static final String citiesDataBaseCreator = "CREATE TABLE " + cityTableName + " (" + keyId +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + keyCityName + " TEXT NOT NULL, " + keyCountryName + " TEXT NOT NULL, "
            + keyCityId + " INTEGER);";




    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context){
            super(context, DataBaseName, null, Database_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase database){
            database.execSQL(citiesDataBaseCreator);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + cityTableName);
        }
    }

    public SQLRequest(Context context){
        this.context = context;
        this.opened = false;
    }

    public void openDB(){
        opened = true;
    }

    public void addCity(String cityName, String countryName, int id){
        if (opened){
            ContentValues values = new ContentValues();
            values.put(keyCityName, cityName);
            values.put(keyCountryName, countryName);
            values.put(keyCityId, id);
            database.insert(cityTableName, null, values);
        }
    }

    public Cursor getCities(String cityName){
        return database.query(cityTableName, null, keyCityName + "=" + cityName + "%",null, null, null, keyId + " asc", null);
    }

}
