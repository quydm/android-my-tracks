package me.quydo.mytracks;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

@SuppressWarnings({"MissingPermission"})
public class LocationService extends Service implements LocationListener {

    private LocationManager manager;
    private long trackId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        trackId = intent.getLongExtra("track_id", 0);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        manager.removeUpdates(this);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            me.quydo.mytracks.model.Location mLocation = new me.quydo.mytracks.model.Location();
            mLocation.createdTime = String.valueOf(System.currentTimeMillis() / 1000);
            mLocation.lat = String.valueOf(location.getLatitude());
            mLocation.lng = String.valueOf(location.getLongitude());
            mLocation.speed = String.valueOf(location.getSpeed());
            mLocation.trackId = trackId;
            mLocation.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
