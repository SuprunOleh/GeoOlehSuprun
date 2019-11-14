package com.gmail2548sov.geoolehsuprun;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnMap;
    Button btnLoc;
    TextView text_info_gps;
    TextView text_info_net;
    TextView text_enabled_GPS;
    TextView text_enabled_net;
    StringBuilder sbGPS = new StringBuilder();
    double x, y;


    private LocationManager locationManager;


    LocationListener locationListener = new LocationListener() {


        @Override
        public void onLocationChanged(Location location) {

            Log.d("Usama", location.toString());

            showLocation(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //text_info.setText("Status: " + String.valueOf(status));

        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();

        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMap = (Button) findViewById(R.id.btnMap);
        btnLoc = (Button) findViewById(R.id.btnMyLocat);
        btnMap.setOnClickListener(this);
        text_enabled_GPS = (TextView) findViewById(R.id.locEnabled_GPS);
        text_enabled_net = (TextView) findViewById(R.id.locEnabled_Net);
        text_info_gps = (TextView) findViewById(R.id.locInfoGPS);
        text_info_net = (TextView) findViewById(R.id.locInfoNET);

        btnLoc.setEnabled(false);



        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @SuppressLint("MissingPermission")

    @Override
    protected void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);


        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);
        checkEnabled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }


    private void checkEnabled() {
        text_enabled_GPS.setText(getString(R.string.enabled_gps) + locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
        text_enabled_net.setText(getString(R.string.enabled_network) + locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));


    }

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            text_info_gps.setText(getString(R.string.info_gps) + formatLocation(location));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            text_info_net.setText(getString(R.string.info_net) + formatLocation(location));
        }
    }


    private String formatLocation(Location location) {
        if (location == null)
            return "";
        String s = String.format(
                "coordinates lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
         x = location.getLatitude();
         y = location.getLongitude();

        return s;
    }


    @Override
    public void onClick(View v) {

        Intent intent;

        intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        //intent.setData(Uri.parse("geo:55.754283,37.62002"));

        intent.setData(Uri.parse("geo:"+x+","+y));

        startActivity(intent);

        // text_info.setText("x="+x+", y="+y);


        // startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


       /* Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:49.4098930, 32.0852618"));
        startActivity(intent); */


    }


}
