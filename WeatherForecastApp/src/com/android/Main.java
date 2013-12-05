package com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ListView citiesList;


    private void saveCity(String cityName, String cityProp, int cityId) {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        int numOfCities = preferences.getInt("numOfCities", 0);
        boolean added = false;
        for (int i = 0; i < numOfCities && !added; i++) {
            int id = preferences.getInt("cityId" + i, 0);
            if (id == cityId)
                added = true;
        }
        if (!added) {
            SharedPreferences.Editor editor = preferences.edit();
            ++numOfCities;
            editor.putInt("numOfCities", numOfCities);
            editor.putString("cityName" + numOfCities, cityName);
            editor.putString("cityProp" + numOfCities, cityProp);
            editor.putInt("cityId" + numOfCities, cityId);
            editor.commit();
        }
    }

    private ArrayList<Map<String, Object>> getCitiesList() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        int numOfCities = preferences.getInt("numOfCities", 0);
        for (int i = 1; i <= numOfCities; i++) {
            data.add(new HashMap<String, Object>());
            String cityName = preferences.getString("cityName" + i, "");
            int cityId = preferences.getInt("cityId" + i, 0);
            String cityProp = preferences.getString("cityProp" + i, "");
            data.get(i - 1).put("cityName", cityName);
            data.get(i - 1).put("cityId", cityId);
            data.get(i - 1).put("cityProp", cityProp);
        }
        return data;
    }


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
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        String cityProp = intent.getStringExtra("cityProp");
        int id = intent.getIntExtra("cityId", 0);
        if (id != 0) {
            saveCity(cityName, cityProp, id);
        }
        String[] keys = {"cityName", "cityProp"};
        final int[] layouts = {R.id.cityName, R.id.countryName};
        citiesList = (ListView) findViewById(R.id.addedCities);
        final ArrayList<Map<String, Object>> data = getCitiesList();
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.citynode, keys, layouts);
        citiesList.setAdapter(adapter);
        Button renew = (Button) findViewById(R.id.renewAllButton);
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cityId = (Integer) data.get(position).get("cityId");
                String cityName = (String) data.get(position).get("cityName");
                String cityProp = (String) data.get(position).get("cityProp");
                Intent intent = new Intent(Main.this, ForecastActivity.class);
                intent.putExtra("cityId", cityId);
                intent.putExtra("cityName", cityName);
                intent.putExtra("cityProp", cityProp);
                startActivity(intent);
            }
        });

        citiesList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

    }
}