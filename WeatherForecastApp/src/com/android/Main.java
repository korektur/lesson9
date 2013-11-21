package com.android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
        Weather weather = new Weather();
        weather.execute("Moscow");
        Button settings = (Button)findViewById(R.id.settingbutton);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}