package com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.WeatherForecastApp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 16:24
 */
public class Main extends Activity {
    static SharedPreferences preferences;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button settings = (Button) findViewById(R.id.settingbutton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
            }
        });
        preferences = getPreferences(MODE_PRIVATE);
        final int numOfCities = preferences.getInt("numOfCities", 0);
        final String[] cities = new String[numOfCities];
        final int[] citiesId = new int[numOfCities];
        for (int i = 0; i < numOfCities; i++) {
            cities[i] = preferences.getString("cityName" + i, "");
            citiesId[i] = preferences.getInt("cityId" + i, 0);
        }
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < numOfCities; i++) {
            data.add(new HashMap<String, Object>());
            data.get(i).put("cityName", cities[i]);
            data.get(i).put("cityId", citiesId[i]);
        }
        ListView citiesList = (ListView) findViewById(R.id.addedCities);
        //SimpleAdapter adapter = new SimpleAdapter(Main.this, data, )
        Button renew = (Button) findViewById(R.id.renewbutton);
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < numOfCities; i++) {

                }
            }
        });

    }
}