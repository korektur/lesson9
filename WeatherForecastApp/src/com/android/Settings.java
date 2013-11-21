package com.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.WeatherForecastApp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 18:04
 */
public class Settings extends Activity {


    final String[] keys = {"name", "prop"};
    final int[] layouts = {R.id.cityName, R.id.countryName};
    final ArrayList<Integer> id = new ArrayList<Integer>();
    final ArrayList<String> cities = new ArrayList<String>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Button findCity = (Button) findViewById(R.id.findbutton);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final ListView listView = (ListView) findViewById(R.id.listView);
        findCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Weather weather = new Weather();
                weather.execute(name);
                ArrayList<Map<String, Object>> data = null;
                try {
                    data = weather.get();
                    ArrayList<Map<String, Object>> parsedData = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < data.size(); i++) {
                        parsedData.add(new HashMap<String, Object>());
                        String name1 = (String) data.get(i).get("name") + ", " + (String) data.get(i).get("country");
                        String name2 = (String) data.get(i).get("admin1") + ", " + (String) data.get(i).get("admin2");
                        int woeid = Integer.parseInt((String)data.get(i).get("woeid"));
                        id.add(woeid);
                        cities.add((String) data.get(i).get("name"));
                        parsedData.get(i).put("name", name1);
                        parsedData.get(i).put("prop", name2);
                    }
                    SimpleAdapter adapter = new SimpleAdapter(Settings.this, parsedData, R.layout.citynode, keys, layouts);
                    listView.setAdapter(adapter);
                } catch (ExecutionException e) {
                } catch (InterruptedException e) {
                }
                ;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                int num = preferences.getInt("numOfCities", 0);
                editor.putInt("numOfCities", num + 1);
                editor.putString("cityName" + num, cities.get(position));
                editor.putInt("cityId" + num, Settings.this.id.get(position));
                editor.commit();
            }
        });

    }
}