package com.android;

import android.app.Activity;
import android.os.Bundle;
import com.example.WeatherForecastApp.R;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 16:24
 */
public class Main extends Activity {



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Weather weather = new Weather();
        weather.execute("Moscow");
    }
}