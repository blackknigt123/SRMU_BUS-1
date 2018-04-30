package com.example.admin;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.model.GetDirectionsData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class direction extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    ArrayList<LatLng> listPoints;
    private String busNo;
    DatabaseReference databaseReference;
    private List<Double> lat;
    private List<Double> lon;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    int PROXIMITY_RADIUS = 10000;
    public static double latitude;
    public static double longitude;
    double end_latitude = 26.891716;
    double end_longitude = 81.073357;
    private LatLng latLng;
    private LatLng latLng_end;
    private StringBuilder googleDirectionsUrl;
    StringBuilder way_point;
    private double[] way_lat;
    private double[] way_lon;
    public int c;
    private StringBuilder fine_way_point;
    private StringBuilder way_point1;
    private LatLng latLng_way;
    private String[] stop_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        busNo = getIntent().getStringExtra("busNo");
        Toast.makeText(this, busNo, Toast.LENGTH_SHORT).show();
        double[] lati = fetching_lat_lan_data();
        Log.e("fine", lati[0] + " " + lati[1]);



        }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //

        mMap = googleMap;
        boolean success = mMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.map_style));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        buildGoogleApiClient();

        latLng_end = new LatLng(end_latitude, end_longitude);


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng_end);
        markerOptions.title("SRMU");
        markerOptions.icon(BitmapDescriptorFactory
                .fromResource(R.drawable.e));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_end));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));



        }




    double[] fetching_lat_lan_data() {
        final int i = 0;
        final double[] position = new double[2];
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("Bus Details")
                .child(busNo).child("bus_Stop");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map latlng = (Map) dataSnapshot.child("0").child("latLng").getValue();

                latitude = (double) latlng.get("latitude");
                longitude = (double) latlng.get("longitude");
                Log.e("direction", latitude + " " + longitude);
                Log.e("direction", latlng.get("latitude").toString() + " or " + latlng.get("longitude").toString());
                position[0] = latitude;
                position[1] = longitude;

                c = (int) dataSnapshot.getChildrenCount();
                Log.e("child cunt", String.valueOf(c));

                way_lat = new double[c];
                way_lon = new double[c];
                stop_name = new String[c];


                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    way_lat[Integer.parseInt(snap.getKey())] = (double) snap.child("latLng").child("latitude").getValue();

                    way_lon[Integer.parseInt(snap.getKey())] = (double) snap.child("latLng").child("longitude").getValue();
                    //Toast.makeText(direction.this, (int) way_lat[Integer.parseInt(snap.getKey())], Toast.LENGTH_SHORT).show();
                    stop_name[Integer.parseInt(snap.getKey())]= (String) snap.child("name").getValue();



                }
                way_point=new StringBuilder("");
                MarkerOptions markerOptions = new MarkerOptions();
                for (int i = 1; i <= c - 2; i++) {

                    latLng_way = new LatLng(way_lat[i],way_lon[i]);

                    markerOptions.position(latLng_way);
                    markerOptions.title(stop_name[i]);
                    markerOptions.icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.w));
                    mCurrLocationMarker = mMap.addMarker(markerOptions);


                    way_point.append("via:"+way_lat[i]+","+way_lon[i]+"|");
                  //  Log.e("wawy_lat1", String.valueOf(way_point));


                }
                Toast.makeText(direction.this, way_point, Toast.LENGTH_SHORT).show();
                Log.e("final trail", String.valueOf(way_point));




                Object dataTransfer[] = new Object[2];
                String url;

                dataTransfer = new Object[3];
                url = getDirectionsUrl(position,way_point);
                GetDirectionsData getDirectionsData = new GetDirectionsData();
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                dataTransfer[2] = new LatLng(end_latitude, end_longitude);
                getDirectionsData.execute(dataTransfer);


                latLng = new LatLng(latitude, longitude);


                markerOptions.position(latLng);
                markerOptions.title(stop_name[0]);
                markerOptions.icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.s));
                mCurrLocationMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return position;
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private String getDirectionsUrl(double[] liveposition,StringBuilder way_point) {


        Log.e("zxcvbnm", String.valueOf(way_point));


        googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        Log.e("data", liveposition[0] + " " + liveposition[1]);
        googleDirectionsUrl.append("origin=" + latitude + "," + longitude);
        googleDirectionsUrl.append("&destination=" + end_latitude + "," + end_longitude+"&transit_mode=bus&transit_mode=bus"+"&waypoints=");
        googleDirectionsUrl.append(way_point);
        googleDirectionsUrl.append("&key=" + "AIzaSyC3WEXxdVvh9GQAtdSxPSlgTCS0ryhL5MQ");


        return googleDirectionsUrl.toString();
    }



    @Override
        public void onLocationChanged (Location location){

        }

        @Override
        public void onStatusChanged (String s,int i, Bundle bundle){

        }

        @Override
        public void onProviderEnabled (String s){

        }

        @Override
        public void onProviderDisabled (String s){

        }

        @Override
        public void onConnected (@Nullable Bundle bundle){

        }

        @Override
        public void onConnectionSuspended ( int i){

        }

        @Override
        public void onConnectionFailed (@NonNull ConnectionResult connectionResult){

        }

        @Override
        public boolean onMarkerClick (Marker marker){
            return false;
        }

        @Override
        public void onMarkerDragStart (Marker marker){

        }

        @Override
        public void onMarkerDrag (Marker marker){

        }

        @Override
        public void onMarkerDragEnd (Marker marker){

        }



    @Override
    public void onInfoWindowClick(Marker marker) {


    }


}

