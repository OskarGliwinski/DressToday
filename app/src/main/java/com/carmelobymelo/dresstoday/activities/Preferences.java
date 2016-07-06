package com.carmelobymelo.dresstoday.activities;
/**
 * @author Oskar Gliwinski
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.carmelobymelo.dresstoday.R;

public class Preferences extends Activity {

    private final int FORECAST_LENGTH = 8;
    private final int FORECAST_START = 0;
    private final String GENDER = "male";
    SharedPreferences sharedPreferences;

    private RadioButton gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        final EditText lengthOfForecast = (EditText) findViewById(R.id.preferences_lengthforecast);
        final EditText startWhen = (EditText) findViewById(R.id.preferences_startWhen);
        lengthOfForecast.setText(String.valueOf(sharedPreferences.getInt("forecastDuration", FORECAST_LENGTH)));
        startWhen.setText(String.valueOf(sharedPreferences.getInt("startWhen", FORECAST_START)));

        if (sharedPreferences.getString("gender", GENDER).equals("male")) {
            gender = (RadioButton) findViewById(R.id.choiceMale);
            gender.setChecked(true);
        } else {
            gender = (RadioButton) findViewById(R.id.choiceFemale);
            gender.setChecked(true);
        }

        final RadioGroup genderChoice = (RadioGroup) findViewById(R.id.genderChoice);

        Button applyButton = (Button) findViewById(R.id.preferences_applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (TextUtils.isEmpty(lengthOfForecast.getText().toString())
                        || Integer.parseInt(lengthOfForecast.getText().toString()) < 1
                        || Integer.parseInt(lengthOfForecast.getText().toString()) > 24) {
                    lengthOfForecast.setError(getString(R.string.error_length));
                    check++;
                }
                if (TextUtils.isEmpty(startWhen.getText().toString())
                        || Integer.parseInt(startWhen.getText().toString()) > 10) {
                    startWhen.setError(getString(R.string.error_start));
                    check++;
                }
                if (check < 1) {
                    if ((Integer.parseInt(lengthOfForecast.getText().toString()) >= 1
                            && Integer.parseInt(lengthOfForecast.getText().toString()) <= 24)
                            && Integer.parseInt(startWhen.getText().toString()) >= 0
                            && Integer.parseInt(startWhen.getText().toString()) <= 10) {

                        editor.putInt("forecastDuration", Integer.parseInt(lengthOfForecast.getText().toString()));
                        editor.putInt("startWhen", Integer.parseInt(startWhen.getText().toString()));

                        int genderID = genderChoice.getCheckedRadioButtonId();

                        //ID 2131492983 == male ID 2131492984 == female
                        if (genderID == R.id.genderChoice+1) {
                            editor.putString("gender", "male");
                        } else {
                            editor.putString("gender", "female");
                        }

                        editor.apply();

                        Toast.makeText(getApplicationContext(), R.string.new_preferences, Toast.LENGTH_SHORT).show();
                        {
                            Intent intent = new Intent(Preferences.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        finish();
                    }
                }
            }
        });
    }
}