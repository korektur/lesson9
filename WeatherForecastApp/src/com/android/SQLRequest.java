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

    private static final String weatherTableName = "WeatherTable";
    private static final String keyTempNow = "tempNowKey";
    private static final String keyWeatherNow = "weatherNowKey";
    private static final String[] keyTempLow = {"tempLowKey1", "tempLowKey2", "tempLowKey3"};
    private static final String[] keyTempHigh = {"tempHighKey1", "tempHighKey2", "tempHighKey3"};
    private static final String[] keyWeatherThen = {"weatherThenKey1", "weatherThenKey2", "weatherThenKey3"};
    private static final String keyId = "_id";
    private static final String keyCityId = "cityId";
    private static final String keyCountryName = "countryKey";
    private static final String keyCityName = "cityNameKey";
    private static final String[] keyDate = {"date1Key", "date2Key", "date3Key"};
    private static final int Database_Version = 1;

    private boolean opened;
    private Context context;
    private SQLiteDatabase database;
    private DBHelper helper;


    private static final String weatherDataBaseCreator = "CREATE TABLE " + weatherTableName + " (" + keyId +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + keyCityName + " TEXT NOT NULL, " + keyCountryName + " TEXT NOT NULL, "
            + keyTempNow + " DOUBLE, " + keyWeatherNow + " TEXT NOT NULL, " + keyTempLow[0] + " DOUBLE, " +
            keyTempLow[1] + " DOUBLE, " + keyTempLow[2] + " DOUBLE, " + keyTempHigh[0] + " TEXT NOT NULL, " +
            keyTempHigh[1] + " TEXT NOT NULL, " + keyTempHigh[2] + " TEXT NOT NULL, " + keyWeatherThen[0] + " TEXT NOT NULL, " +
            keyWeatherThen[2] + " TEXT NOT NULL, " + keyWeatherThen[3] + " TEXT NOT NULL, " + keyDate[0] + " TEXT NOT NULL, "
            + keyDate[1] + " TEXT NOT NULL, " + keyDate[2] + " TEXT NOT NULL, " + keyCityId + " INTEGER);";

    private static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context){
            super(context, DataBaseName, null, Database_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase database){
            database.execSQL(weatherDataBaseCreator);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + weatherTableName);
        }
    }

    public SQLRequest(Context context){
        this.context = context;
        this.opened = false;
    }

    public void openDB(){
        opened = true;
    }

    public void addCity(City city){
        if (opened){
            ContentValues values = new ContentValues();
            values.put(keyCityName, city.name);
            values.put(keyCityId, city.id);
            values.put(keyCountryName, city.country);
            values.put(keyTempNow, city.tempNow);
            values.put(keyWeatherNow, city.weatherNow);
            for(int i = 0; i < 3; i++){
                values.put(keyTempLow[i], city.tempHigh[i]);
                values.put(keyWeatherThen[i], city.weather[i]);
                values.put(keyTempHigh[i], city.tempHigh[i]);
                values.put(keyTempLow[i], city.tempLow[i]);

            }

            database.insert(weatherTableName, null, values);
        }
    }


    public Cursor getAddedCities(){
        return database.query(weatherTableName, null, null, null, null, null, keyId +  " asc", null);
    }

    public void deleteCity(int id){
        if (opened){
            database.delete(weatherTableName, keyCityId + "=?", null);
        }
    }
}
