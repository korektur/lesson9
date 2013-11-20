package com.android;

import android.app.IntentService;
import android.content.IntentSender;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Ruslan
 * Date: 20.11.13
 * Time: 18:09
 */
public class Weather extends IntentService {
    private static final String requestLink = "http://export.yandex.ru/weather-ng/forecasts/";

    public Weather(){
        super("Weather");
    }

    public static ArrayList<Map<String, Object>> getWeather(int cityId) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(requestLink + cityId + ".xml");
        HttpResponse response;
        InputStream inputStream;
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            inputStream = entity.getContent();
            String[] keys = {"temperature", "weather_type", "wind_speed", "daytime"};
            data = Parser.Parse(inputStream, "fact", keys);
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }
        ;
        return data;
    }
}
