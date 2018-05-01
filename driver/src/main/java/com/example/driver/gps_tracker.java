package com.example.driver;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Vaibhav on 2/13/2018.
 */

public class gps_tracker extends Service implements LocationListener {

    private final Context mConext;

    boolean isGPSEnabled = false;

    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;
    Location location;
    double lat;
    double lon;

    private static final long MIN_DISTANCE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 *1;
    protected LocationManager locationManager;

    public gps_tracker(Context context)
    {
        this.mConext=context;
        getLocation();
        //stopUsingGPS();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mConext.getSystemService(LOCATION_SERVICE);

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            }
            else
                {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                   if (ActivityCompat.checkSelfPermission((Activity)mConext,android.Manifest.permission.ACCESS_FINE_LOCATION)
                           != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                           ((Activity)mConext,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                           != PackageManager.PERMISSION_GRANTED)
                   {
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_FOR_UPDATES,this);

                    if (locationManager!=null)
                    {
                        location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if(location!=null)
                        {
                            lat=location.getLatitude();
                            lon=location.getLongitude();
                        }
                    }

                }

                if (isGPSEnabled)
                {
                    if (location == null)
                    {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_FOR_UPDATES,this);

                        if (locationManager!=null)
                        {
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location!=null)
                            {
                                lat=location.getLatitude();
                                lon=location.getLongitude();

                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;

    }

    public void stopUsingGPS()
    {
        if (locationManager!=null) {


                if (ActivityCompat.checkSelfPermission((Activity)mConext,android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        ((Activity)mConext,android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                return;
            }

            locationManager.removeUpdates(gps_tracker.this);
        }

    }

    public double getLat()
    {
        if (location!=null)
        {
            lat=location.getLatitude();
        }
        return lat;
    }


    public double getLon()
    {
        if (location!=null)
        {
            lat=location.getLongitude();
        }
        return lon;
    }

    public boolean canGetLocation()
    {
        return this.canGetLocation;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mConext);

        alertDialog.setTitle("GPS is settings");

        alertDialog.setMessage("GPS is not enabled. Do you want to go to setting menu");

        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mConext.startActivity(intent);

            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });

        alertDialog.show();
    }



    @Override
    public void onLocationChanged(Location location) {

        getLocation();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
