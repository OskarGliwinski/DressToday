package com.carmelobymelo.dresstoday.activities;
/**
 * @author Oskar Gliwinski
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.carmelobymelo.dresstoday.Functions;
import com.carmelobymelo.dresstoday.R;

public class LocationActivity extends Activity implements LocationListener {

    private LocationManager locationManager;

    ProgressBar location_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button location_exit_button = (Button) findViewById(R.id.location_exit_button);
        location_exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.exitForm(LocationActivity.this);
            }
        });

        location_progress = (ProgressBar) findViewById(R.id.location_progress);
        location_progress.setVisibility(View.VISIBLE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        location_progress.setVisibility(View.INVISIBLE);
        locationManager.removeUpdates(this);
        location_progress.setVisibility(View.INVISIBLE);
        MainActivity.setLat(location.getLatitude());
        MainActivity.setLon(location.getLongitude());
        startActivity(new Intent(LocationActivity.this, Preferences.class));
        finish();
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = this.getIntent();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        }
        startActivity(intent);
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onBackPressed() {
        Functions.exitForm(this);
    }
}
