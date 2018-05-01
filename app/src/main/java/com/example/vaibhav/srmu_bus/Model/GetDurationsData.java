package com.example.vaibhav.srmu_bus.Model;

import android.os.AsyncTask;

import com.example.vaibhav.srmu_bus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;

public class GetDurationsData extends AsyncTask<Object,String,String> {

    GoogleMap mMap;
    String url;
    String googleDurationData;
    String duration,distance;
    LatLng latLng;
    @Override
    protected String doInBackground(Object... objects) {
        mMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        latLng=(LatLng)objects[2];

        DownloadUrl downloadUrl=new DownloadUrl();
        try {
            googleDurationData=downloadUrl.readUrl(url);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return googleDurationData;
    }

    @Override
    protected void onPostExecute(String s) {

        HashMap<String,String> directionList;
        DataParser parser = new DataParser();
        directionList=parser.parseDuration(s);

        duration=directionList.get("duration");
        distance=directionList.get("distance");

      mMap.clear();
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.wp));
        markerOptions.position(latLng);
        markerOptions.title("Duration="+duration);
        markerOptions.snippet("Distance="+distance);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));

        mMap.addMarker(markerOptions);

    }
}
