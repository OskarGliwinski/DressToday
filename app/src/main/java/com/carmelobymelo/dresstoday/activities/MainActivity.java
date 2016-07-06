package com.carmelobymelo.dresstoday.activities;

/**
 * @author Oskar Gliwinski
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carmelobymelo.dresstoday.Formula;
import com.carmelobymelo.dresstoday.Functions;
import com.carmelobymelo.dresstoday.R;
import com.carmelobymelo.dresstoday.models.AerisWeatherModel;
import com.carmelobymelo.dresstoday.models.ForecastIOModel;
import com.carmelobymelo.dresstoday.models.OpenWeatherModel;
import com.carmelobymelo.dresstoday.models.WeatherModel;
import com.carmelobymelo.dresstoday.models.WundergroundModel;
import com.carmelobymelo.dresstoday.parsers.ParseAerisWeather;
import com.carmelobymelo.dresstoday.parsers.ParseForecastIO;
import com.carmelobymelo.dresstoday.parsers.ParseOpenWeather;
import com.carmelobymelo.dresstoday.parsers.ParseWunderground;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    SharedPreferences sharedPreferences;

    private static double lat;
    private static double lon;
    private static String gender;
    private static boolean SUGGESTION_MADE = false;
    private static int forecastDuration;
    private static int startWhen;
    private static int resultSuggestion = 0;
    private static String cityName = "";
    private static String currentImageChoose;
    private static OpenWeatherModel currentWeather = new OpenWeatherModel();
    private static List<WundergroundModel> wunderList = new ArrayList();
    private static List<ForecastIOModel> forecastioList = new ArrayList();
    private static List<AerisWeatherModel> aerisweatherList = new ArrayList();
    private static WeatherModel averageWeather = new WeatherModel();

    private TextView dwnldData;
    private Button checkWeather;
    private Button preferences;
    private ProgressBar progressBar;

    private TextView city;
    private TextView temperature;
    private TextView windspeed;
    private TextView humidity;
    private TextView cloudiness;
    private TextView description;
    private ImageView weatherImage;
    private ImageView rainImage;
    private ImageView snowImage;
    private ImageView iceImage;

    private ImageView temperatureImage;
    private ImageView windImage;
    private ImageView humidityImage;
    private ImageView cloudinessImage;

    private TextView text01;
    private TextView text02;
    private TextView text03;
    private TextView text05;
    private TextView text07;
    private TextView text09;

    public static void setLat(double lat) {
        MainActivity.lat = lat;
    }

    public static void setLon(double lon) {
        MainActivity.lon = lon;
    }

    public static boolean isSuggestionMade() {
        return SUGGESTION_MADE;
    }

    public static String getGender() {
        return gender;
    }


    public static int getForecastDuration() {
        return forecastDuration;
    }

    public static int getStartWhen() {
        return startWhen;
    }

    public static int getResultSuggestion() {
        return resultSuggestion;
    }

    public static String getCityName() {
        return cityName;
    }

    public static void setCurrentImageChoose(String currentImageChoose) {
        MainActivity.currentImageChoose = currentImageChoose;
    }

    public static WeatherModel getAverageWeather() {
        return averageWeather;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        forecastDuration = sharedPreferences.getInt("forecastDuration", 8);
        startWhen = sharedPreferences.getInt("startWhen", 0);
        gender = sharedPreferences.getString("gender", "male");

        checkWeather = (Button) findViewById(R.id.checkweatherbutton);
        preferences = (Button) findViewById(R.id.main_preferences);
        dwnldData = (TextView) findViewById(R.id.feedback_text);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        city = (TextView) findViewById(R.id.main_city);
        temperature = (TextView) findViewById(R.id.main_temperature);
        windspeed = (TextView) findViewById(R.id.main_windspeed);
        humidity = (TextView) findViewById(R.id.main_humidity);
        cloudiness = (TextView) findViewById(R.id.main_cloudiness);
        description = (TextView) findViewById(R.id.main_description);
        weatherImage = (ImageView) findViewById(R.id.main_image);
        rainImage = (ImageView) findViewById(R.id.main_rain_image);
        snowImage = (ImageView) findViewById(R.id.main_snow_image);
        iceImage = (ImageView) findViewById(R.id.main_ice_image);

        temperatureImage = (ImageView) findViewById(R.id.main_temperature_image);
        windImage = (ImageView) findViewById(R.id.main_windspeed_image);
        humidityImage = (ImageView) findViewById(R.id.main_humidity_image);
        cloudinessImage = (ImageView) findViewById(R.id.main_cloudiness_image);

        text01 = (TextView) findViewById(R.id.textView01);
        text02 = (TextView) findViewById(R.id.textView02);
        text03 = (TextView) findViewById(R.id.textView03);
        text05 = (TextView) findViewById(R.id.textView05);
        text07 = (TextView) findViewById(R.id.textView07);
        text09 = (TextView) findViewById(R.id.textView09);

        hideTextonMainActivity();

        if (Functions.isNetworkAvailable(this)) {
            new DownloadWeather().execute();
        } else {
            dwnldData.setVisibility(View.VISIBLE);
            dwnldData.setText(getString(R.string.no_internet));
            progressBar.setVisibility(View.INVISIBLE);
            checkWeather.setVisibility(View.VISIBLE);
        }

        checkWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Functions.isNetworkAvailable(MainActivity.this)) {
                    if (!SUGGESTION_MADE) {
                        new DownloadWeather().execute();
                    } else {
                        startActivity(new Intent(MainActivity.this, SuggestionActivity.class));
                        showTextonMainActivity();
                    }
                } else if (SUGGESTION_MADE) {
                    startActivity(new Intent(MainActivity.this, SuggestionActivity.class));
                    showTextonMainActivity();
                } else {
                    hideTextonMainActivity();
                    dwnldData.setVisibility(View.VISIBLE);
                    dwnldData.setText(getString(R.string.no_internet));
                }
            }
        });

        preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Preferences.class));
            }
        });
    }

    private class DownloadWeather extends AsyncTask<URL, String, String> {

        @Override
        protected void onPreExecute() {
            dwnldData.setText(getString(R.string.downloading));
            dwnldData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            checkWeather.setVisibility(View.INVISIBLE);
            preferences.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {

            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
                if (!addresses.isEmpty()) {
                    cityName = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //averageWeather.clear();
            wunderList.clear();
            aerisweatherList.clear();
            forecastioList.clear();

            ParseOpenWeather parseOpenWeatherObject = new ParseOpenWeather();
            currentWeather = parseOpenWeatherObject.
                    parsingOpenWeather(lon, lat, getString(R.string.language));

            ParseWunderground parseWundergroundObject = new ParseWunderground();
            parseWundergroundObject.
                    parsingWunderground(forecastDuration, startWhen, wunderList, lon, lat);

            ParseForecastIO parseForecastIOObject = new ParseForecastIO();
            parseForecastIOObject.
                    parsingForecastIO(forecastDuration, startWhen, forecastioList, lon, lat, getString(R.string.language));

            ParseAerisWeather parseAerisWeatherObject = new ParseAerisWeather();
            parseAerisWeatherObject.
                    parsingAerisWeather(forecastDuration, startWhen, aerisweatherList, lon, lat);

            return String.valueOf(Formula.resultFormula(wunderList, forecastioList, aerisweatherList, averageWeather, forecastDuration));
        }

        @Override
        protected void onPostExecute(String s) {
            SUGGESTION_MADE = true;
            showTextonMainActivity();
            checkIntensity();
            city.setText(cityName);
            Resources res = getResources();
            int suggestionDrawableID = res.getIdentifier("ow" + currentImageChoose, "drawable", getPackageName());
            if(Build.VERSION.SDK_INT < 22){
                Drawable drawable = res.getDrawable(suggestionDrawableID);
                weatherImage.setImageDrawable(drawable);
            }else {
                weatherImage.setImageDrawable(getDrawable(suggestionDrawableID));
            }
            temperature.setText(getString(R.string.temperature, (int) (currentWeather.getTemp() + 0.5)));
            windspeed.setText(getString(R.string.windspeed, (int) (currentWeather.getWindSpeed() + 0.5)));
            humidity.setText(getString(R.string.humidity, (int) (currentWeather.getHumidity() + 0.5)));
            cloudiness.setText(getString(R.string.cloudiness, (int) (currentWeather.getCloudiness() + 0.5)));
            description.setText(String.valueOf((currentWeather.getDescriptionMultiLang())));
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), R.string.data_downloaded, Toast.LENGTH_SHORT).show();
            resultSuggestion = Integer.parseInt(s);
            checkWeather.setVisibility(View.VISIBLE);
            preferences.setVisibility(View.VISIBLE);
            if (resultSuggestion == 0) {
                setContentView(R.layout.noservice);
                Button noservice_preferences = (Button) findViewById(R.id.noservice_preferences);
                Button noservice_exit_button = (Button) findViewById(R.id.noservice_exit_button);
                noservice_preferences.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(new Intent(MainActivity.this, Preferences.class));
                    }
                });
                noservice_exit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Functions.exitForm(MainActivity.this);
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Functions.isNetworkAvailable(this)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        } else return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, Preferences.class));
                break;
            case R.id.action_forecast:
                startActivity(new Intent(MainActivity.this, SuggestionActivity.class));
                break;
            case R.id.action_exit:
                Functions.exitForm(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showTextonMainActivity() {
        dwnldData.setVisibility(View.INVISIBLE);
        city.setVisibility(View.VISIBLE);
        temperature.setVisibility(View.VISIBLE);
        windspeed.setVisibility(View.VISIBLE);
        humidity.setVisibility(View.VISIBLE);
        cloudiness.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        weatherImage.setVisibility(View.VISIBLE);

        temperatureImage.setVisibility(View.VISIBLE);
        windImage.setVisibility(View.VISIBLE);
        humidityImage.setVisibility(View.VISIBLE);
        cloudinessImage.setVisibility(View.VISIBLE);

        text01.setVisibility(View.VISIBLE);
        text02.setVisibility(View.VISIBLE);
        text03.setVisibility(View.VISIBLE);
        text05.setVisibility(View.VISIBLE);
        text07.setVisibility(View.VISIBLE);
        text09.setVisibility(View.VISIBLE);
    }

    private void hideTextonMainActivity() {
        city.setVisibility(View.INVISIBLE);
        temperature.setVisibility(View.INVISIBLE);
        windspeed.setVisibility(View.INVISIBLE);
        humidity.setVisibility(View.INVISIBLE);
        cloudiness.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        weatherImage.setVisibility(View.INVISIBLE);

        temperatureImage.setVisibility(View.INVISIBLE);
        windImage.setVisibility(View.INVISIBLE);
        humidityImage.setVisibility(View.INVISIBLE);
        cloudinessImage.setVisibility(View.INVISIBLE);

        text01.setVisibility(View.INVISIBLE);
        text02.setVisibility(View.INVISIBLE);
        text03.setVisibility(View.INVISIBLE);
        text05.setVisibility(View.INVISIBLE);
        text07.setVisibility(View.INVISIBLE);
        text09.setVisibility(View.INVISIBLE);
    }

    private void checkIntensity() {
        if (currentWeather.getTemp() <= 6) {
            temperature.setTextColor(Color.rgb(0, 51, 204));
            if (currentWeather.getTemp() <= -3) {
                iceImage.setVisibility(View.VISIBLE);
            }
        }
        if (currentWeather.getTemp() >= 30) {
            temperature.setTextColor(Color.rgb(204, 0, 0));
        }
        if (currentWeather.getWindSpeed() >= 20) {
            windspeed.setTextColor(Color.rgb(204, 0, 0));
        }
        if (currentWeather.isRaining() && currentWeather.getTemp() > -3) {
            rainImage.setVisibility(View.VISIBLE);
        }
        if (currentWeather.isSnowing()) {
            snowImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        Functions.exitForm(this);
    }
}