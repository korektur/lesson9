package com.android;

import android.net.Uri;
import android.os.AsyncTask;
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
 * User: Руслан
 * Date: 21.11.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */
public class WeatherRenew extends AsyncTask<City, Void, ArrayList<City>> {
    private static final String requestLink = "http://weather.yahooapis.com/forecastrss?w=";

    @Override
    public ArrayList<City> doInBackground(City... city) {
        HttpClient httpClient = new DefaultHttpClient();
        ArrayList<City> cities = new ArrayList<City>();
        HttpResponse response;
        InputStream inputStream;
        for (int i = 0; i < city.length; i++) {
            String link = requestLink + city[i] + "&u=c";
            HttpGet httpGet = new HttpGet(link);
            try {
                response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                cities.add(WeatherParser.Parse(inputStream, city[i]));
            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }
            ;

        }
        return cities;
    }

    @Override
    public void onPostExecute(ArrayList<City> res) {
        super.onPostExecute(res);
    }
}
