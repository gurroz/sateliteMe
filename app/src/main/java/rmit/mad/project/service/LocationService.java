package rmit.mad.project.service;

import android.Manifest;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import rmit.mad.project.receiver.LocationChangeDetector;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class LocationService extends IntentService {

    public final static String LOCATION_NOW = "LOCATION_NOW";

    public LocationService() {
        super("LocationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        boolean getNow = intent.getBooleanExtra(LOCATION_NOW, false);
        if(getNow) {
            getLastLocation();
        } else {
            askForNewLocation();
        }

        stopSelf();

    }

    private void askForNewLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            Intent locationIntent = new Intent(getApplicationContext(), LocationChangeDetector.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, locationIntent, FLAG_CANCEL_CURRENT);
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, pendingIntent);
        }

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            Location location =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Intent locationIntent = new Intent(getApplicationContext(), LocationChangeDetector.class);
            locationIntent.putExtra(android.location.LocationManager.KEY_LOCATION_CHANGED, location);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, locationIntent, FLAG_CANCEL_CURRENT);
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                Log.e("LocationService", e.getLocalizedMessage());
            }
        }
    }
}
