package com.android;

import android.app.Activity;
import android.content.Intent;
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
    final ArrayList<String> prop = new ArrayList<String>();

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
                ArrayList<Map<String, Object>> data;
                try {
                    data = weather.get();
                    if (data != null) {
                        ArrayList<Map<String, Object>> parsedData = new ArrayList<Map<String, Object>>();
                        for (int i = 0; i < data.size(); i++) {
                            parsedData.add(new HashMap<String, Object>());
                            String name1 = data.get(i).get("name") + ", " + data.get(i).get("country");
                            String name2 = data.get(i).get("admin1") + ", " + data.get(i).get("admin2");
                            int woeid = Integer.parseInt((String) data.get(i).get("woeid"));
                            id.add(woeid);
                            cities.add(name1);
                            prop.add(name2);
                            parsedData.get(i).put("name", name1);
                            parsedData.get(i).put("prop", name2);
                        }
                        SimpleAdapter adapter = new SimpleAdapter(Settings.this, parsedData, R.layout.citynode, keys, layouts);
                        listView.setAdapter(adapter);
                    }
                } catch (ExecutionException e) {
                } catch (InterruptedException e) {
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Settings.this, Main.class);
                intent.putExtra("cityName", Settings.this.cities.get(position));
                intent.putExtra("cityId", Settings.this.id.get(position));
                intent.putExtra("cityProp", Settings.this.prop.get(position));
                startActivity(intent);
            }
        });

    }
}