package com.carmelobymelo.dresstoday.parsers;
/**
 * @author Oskar Gliwinski
 */

import com.carmelobymelo.dresstoday.activities.SuggestionActivity;
import com.carmelobymelo.dresstoday.models.WundergroundModel;

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

public class ParseWunderground {

    public void parsingWunderground(int length, int start, List<WundergroundModel> wundergroundList, double lon, double lat) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String WUAPIKey = "fa75ed9c974d0b72";

        String urlAddress = "http://api.wunderground.com/api/" + WUAPIKey + "/hourly/q/" + lat + "," + lon + ".json";
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

            WundergroundModel temp = new WundergroundModel();
            for (int i = start; i < start + length + 1; i++) {
                temp.setTemp(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getJSONObject("temp").getDouble("metric"));
                temp.setCloudiness(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getInt("sky"));
                temp.setHumidity(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getInt("humidity"));
                temp.setFeelsLike(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getJSONObject("feelslike").getDouble("metric"));
                temp.setProbabilityOfPrecipitation(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getInt("pop"));
                temp.setPrecipitationMM(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getJSONObject("qpf").getInt("metric"));
                temp.setSnowPrecip(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getJSONObject("snow").getInt("metric"));
                temp.setWindSpeed(parentObject.getJSONArray("hourly_forecast").
                        getJSONObject(i).getJSONObject("wspd").getInt("metric"));
                if (temp.getSnowPrecip() > 0.2) {
                    SuggestionActivity.setSnowing(true);
                }
                wundergroundList.add(temp);
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