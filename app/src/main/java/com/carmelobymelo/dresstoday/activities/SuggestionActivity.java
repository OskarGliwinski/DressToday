package com.carmelobymelo.dresstoday.activities;
/**
 * @author Oskar Gliwinski
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.carmelobymelo.dresstoday.R;

public class SuggestionActivity extends Activity {

    private static String description;
    private static boolean snowing = false;

    private TextView forecast_feels_like_text;
    private TextView forecast_duration;
    private TextView forecast_city;
    private TextView forecast_temperature;
    private TextView forecast_windspeed;
    private TextView forecast_humidity;
    private TextView forecast_cloudiness;
    private TextView forecast_description;
    private ImageView forecast_rainImage;
    private ImageView forecast_snowImage;
    private ImageView forecast_ice_image;

    private Button forecast_weathernow_image;

    private Button forecast_preferences;
    private Button forecast_getDressed;

    public static void setDescription(String description) {
        SuggestionActivity.description = description;
    }

    public static void setSnowing(boolean snowing) {
        SuggestionActivity.snowing = snowing;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_suggestion);

        final ImageView suggestionImage = (ImageView) findViewById(R.id.parse_suggestion_image);
        suggestionImage.setImageResource(getResources().
                getIdentifier("v2" + MainActivity.getGender() + String.valueOf(MainActivity.getResultSuggestion()), "drawable", getPackageName()));

        if (MainActivity.getAverageWeather().getProbabilityOfPrecipitation() > 20 || snowing){
            ImageView umbrella = (ImageView) findViewById(R.id.umbrella);
            umbrella.setVisibility(View.VISIBLE);
        }

        Button seeForecastButton = (Button) findViewById(R.id.seeForecastButton);

        seeForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.forecast);
                if (MainActivity.isSuggestionMade()) {

                    forecast_preferences = (Button) findViewById(R.id.forecast_preferences);
                    forecast_preferences.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(SuggestionActivity.this, Preferences.class));
                        }
                    });

                    forecast_getDressed = (Button) findViewById(R.id.forecast_checkweatherbutton);
                    forecast_getDressed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            startActivity(new Intent(SuggestionActivity.this, SuggestionActivity.class));
                        }
                    });

                    forecast_weathernow_image = (Button) findViewById(R.id.forecast_weathernow_button);
                    forecast_weathernow_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });

                    forecast_feels_like_text = (TextView) findViewById(R.id.forecast_feelslike_text);
                    forecast_duration = (TextView) findViewById(R.id.forecast_duration);
                    forecast_city = (TextView) findViewById(R.id.forecast_city);
                    forecast_temperature = (TextView) findViewById(R.id.forecast_temperature);
                    forecast_windspeed = (TextView) findViewById(R.id.forecast_windspeed);
                    forecast_humidity = (TextView) findViewById(R.id.forecast_humidity);
                    forecast_cloudiness = (TextView) findViewById(R.id.forecast_cloudiness);
                    forecast_description = (TextView) findViewById(R.id.forecast_description);
                    forecast_rainImage = (ImageView) findViewById(R.id.forecast_rain_image);
                    forecast_snowImage = (ImageView) findViewById(R.id.forecast_snow_image);
                    forecast_ice_image = (ImageView) findViewById(R.id.forecast_ice_image);

                    forecast_duration.
                            setText(getString(R.string.x_hour_forecast, MainActivity.getForecastDuration()));
                    forecast_temperature.
                            setText(getString(R.string.temperature, (int) (MainActivity.getAverageWeather().getFeelsLike() + 0.5)));
                    forecast_windspeed.
                            setText(getString(R.string.windspeed, (int) (MainActivity.getAverageWeather().getWindSpeed() + 0.5)));
                    forecast_humidity.
                            setText(getString(R.string.humidity, (int) (MainActivity.getAverageWeather().getHumidity() + 0.5)));
                    forecast_cloudiness.
                            setText(getString(R.string.cloudiness, (int) (MainActivity.getAverageWeather().getCloudiness() + 0.5)));
                    forecast_description.setText(description);
                    forecast_city.setText(MainActivity.getCityName());

                    checkIntensityForecast();
                }
            }

        });

    }

    private void checkIntensityForecast() {
        if (MainActivity.getAverageWeather().getFeelsLike() <= 6) {
            forecast_temperature.setTextColor(Color.rgb(0, 51, 204));
            forecast_feels_like_text.setTextColor(Color.rgb(0, 51, 204));
            if (MainActivity.getAverageWeather().getFeelsLike() <= -3) {
                forecast_ice_image.setVisibility(View.VISIBLE);
            }
        }
        if (MainActivity.getAverageWeather().getFeelsLike() >= 30) {
            forecast_temperature.setTextColor(Color.rgb(204, 0, 0));
            forecast_feels_like_text.setTextColor(Color.rgb(204, 0, 0));
        }
        if (MainActivity.getAverageWeather().getWindSpeed() >= 20) {
            forecast_windspeed.setTextColor(Color.rgb(204, 0, 0));
        }
        if (MainActivity.getAverageWeather().getProbabilityOfPrecipitation() > 20
                && MainActivity.getAverageWeather().getFeelsLike() > -3) {
            forecast_rainImage.setVisibility(View.VISIBLE);

        }
        if (snowing) {
            forecast_snowImage.setVisibility(View.VISIBLE);
        }
    }

}