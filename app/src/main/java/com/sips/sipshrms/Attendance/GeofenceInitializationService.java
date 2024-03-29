package com.sips.sipshrms.Attendance;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import android.widget.Toast;

import java.util.Locale;

public class GeofenceInitializationService extends Service {

    private static final String TAG = "class:GeofenceInitializationService";

    private GeofenceBroadcastReceiver geoFenceReceiver;

    private boolean isRunning;

    private Thread backgroundThread;

    public GeofenceInitializationService() {
    }

    @Override
    public void onCreate() {
        this.isRunning = false;
        this.backgroundThread = new Thread(initTask);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoLocationListener());
        }
    }

    private Runnable initTask = new Runnable() {
        public void run() {
            geoFenceReceiver = new GeofenceBroadcastReceiver();
            IntentFilter geoFenceIntentFilter = new IntentFilter("com.epgeotrack.app.ep_geo_tracking.ACTION_RECEIVE_GEOFENCE");
            registerReceiver(geoFenceReceiver, geoFenceIntentFilter);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(geoFenceReceiver);
        this.isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class GeoLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
               Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(), "Location update Lat:%s Long:%s Acc:%s Speed: %s", location.getLatitude(), location.getLongitude(), location.getAccuracy(), location.getSpeed()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
}
