package com.carmelobymelo.dresstoday.parsers;
/**
 * @author Oskar Gliwinski
 */

import com.carmelobymelo.dresstoday.models.AerisWeatherModel;

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

public class ParseAerisWeather {

    public void parsingAerisWeather(int length, int start, List<AerisWeatherModel> aerisweatherList, double lon, double lat) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        String AWAPIKeyClientId = "hqL3NQfYqntP2yiIYkYQl";
        String AWAPIKeyClientSecret = "bc8qOBUvMMoyz9Kq2Cx22z2yzPqB1CWFt3zyLQWd";

        String urlAddress = "http://api.aerisapi.com/forecasts/closest?p=" + lat + "," + lon + "&client_id=" + AWAPIKeyClientId + "&client_secret=" + AWAPIKeyClientSecret + "&filter=1hr&limit=" + (start + length + 3);
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

            AerisWeatherModel temp = new AerisWeatherModel();
            for (int i = start; i < start + length + 1; i++) {
                temp.setTemp(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getDouble("avgTempC"));
                temp.setFeelsLike(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getDouble("feelslikeC"));
                temp.setProbabilityOfPrecipitation(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getInt("pop"));
                temp.setPrecipitationMM(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getDouble("precipMM"));
                temp.setHumidity(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getInt("humidity"));
                temp.setWindSpeed(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getInt("windSpeedKPH"));
                temp.setCloudiness(parentObject.getJSONArray("response").
                        getJSONObject(0).getJSONArray("periods").getJSONObject(i).getInt("sky"));
                aerisweatherList.add(temp);
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
