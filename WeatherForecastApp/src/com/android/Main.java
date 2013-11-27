package com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.WeatherForecastApp.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 16:24
 */
public class Main extends Activity {


    SharedPreferences preferences;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button settings = (Button)findViewById(R.id.settingbutton);
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
        for(int i = 0; i < numOfCities; i++){
            cities[i] = preferences.getString("cityName" + i, "");
            citiesId[i] = preferences.getInt("cityId" + i, 0);
        }
        Button renew = (Button)findViewById(R.id.renewbutton);
        renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < numOfCities; i++){

                }
            }
        });

    }
}