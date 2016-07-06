package com.carmelobymelo.dresstoday.parsers;
/**
 * @author Oskar Gliwinski
 */

import com.carmelobymelo.dresstoday.activities.SuggestionActivity;
import com.carmelobymelo.dresstoday.models.ForecastIOModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseForecastIO {

    public void parsingForecastIO(int length, int start, List<ForecastIOModel> forecastioList, double lon, double lat, String language) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String FIOAPIKey = "be26fc5e03717db6a7b2363e57bf4dda";

        String urlAddress = "https://api.forecast.io/forecast/" + FIOAPIKey + "/" + lat + "," + lon + "?units=si&lang=" + language;
        System.err.println(urlAddress);
        try {
            URL url = new URL(urlAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJSON = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJSON);

            //description for middle point of forecast
            int midPoint = (2 * start + length + 5) / 2;

            SuggestionActivity.setDescription(parentObject.getJSONObject("hourly").
                    getJSONArray("data").getJSONObject(midPoint).getString("summary"));

            ForecastIOModel temp = new ForecastIOModel();
            for (int i = start + 2; i < start + length + 3; i++) {
                temp.setTemp(parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("temperature"));
                temp.setFeelsLike(parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("apparentTemperature"));
                temp.setProbabilityOfPrecipitation((parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("precipProbability")) * 100);
                temp.setPrecipitationMM((parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("precipIntensity")) * 2.54);
                temp.setHumidity((parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("humidity")) * 100);
                temp.setWindSpeed((parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("windSpeed")) * 3.6);
                temp.setCloudiness((parentObject.getJSONObject("hourly").
                        getJSONArray("data").getJSONObject(i).getDouble("cloudCover")) * 100);
                forecastioList.add(temp);
            }

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
    }
}
