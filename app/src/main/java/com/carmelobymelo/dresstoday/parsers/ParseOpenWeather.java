package com.carmelobymelo.dresstoday.parsers;
/**
 * @author Oskar Gliwinski
 */

import com.carmelobymelo.dresstoday.activities.MainActivity;
import com.carmelobymelo.dresstoday.models.OpenWeatherModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseOpenWeather {

    public OpenWeatherModel parsingOpenWeather(double lon, double lat, String language) {

        OpenWeatherModel temp = new OpenWeatherModel();

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String OWAPIKey = "1565be83810683d2faa5743a4dfb61e3";

        String urlAddress = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + OWAPIKey + "&lang=" + language;
        System.err.println(urlAddress);

        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJSON = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJSON);

            MainActivity.setCurrentImageChoose(parentObject.getJSONArray("weather").
                    getJSONObject(0).getString("icon"));
            temp.setDescription(parentObject.getJSONArray("weather").
                    getJSONObject(0).getString("main"));
            temp.setDescriptionMultiLang(parentObject.getJSONArray("weather").
                    getJSONObject(0).getString("description"));
            temp.setTemp(parentObject.getJSONObject("main").getDouble("temp"));
            temp.setWindSpeed((parentObject.getJSONObject("wind").getDouble("speed")) * 3.6);
            temp.setHumidity(parentObject.getJSONObject("main").getDouble("humidity"));
            temp.setCloudiness(parentObject.getJSONObject("clouds").getDouble("all"));

            if (temp.getHumidity() > 100) temp.setHumidity(100);
            if (temp.getCloudiness() > 100) temp.setCloudiness(100);

        } catch (MalformedURLException e) {
        } catch (IOException | JSONException e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }

        }
        return temp;
    }
}
