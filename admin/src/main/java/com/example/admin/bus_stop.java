package com.example.admin;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.model.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class bus_stop extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {


    private ImageView add_icon;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady:map is ready");
        nMap = googleMap;

        if (mLocationPermissionGranted) {
            //getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            nMap.setMyLocationEnabled(true);
            nMap.getUiSettings().setMyLocationButtonEnabled(false);
            nMap.getUiSettings().setZoomGesturesEnabled(true);
            nMap.getUiSettings().setZoomControlsEnabled(true);
            init();
        }

    }

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private static final int LOCATION_PERMISSION_CODE = 1234;

    //widgets

    private AutoCompleteTextView mSearchText;

    private Boolean mLocationPermissionGranted = false;

    private GoogleMap nMap;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final float DEFAULT_ZOOM = 15f;

    private static final String TAG = "bus_stop";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;

    private GoogleApiClient mGoogleApiClient;

    private static final LatLngBounds LAT_LNG_BOUNDS = new
            LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));

    private PlaceInfo mPlace;

    private PlaceInfo location;

    List<PlaceInfo> values = new ArrayList<PlaceInfo>(); //list to transfer the added location to new activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);



        if (isServiceesOK()) {
            getLocationPermission();
            // init();

        }


        add_icon = (ImageView) findViewById(R.id.add_bus_icon);
        final ImageView finish = (ImageView) findViewById(R.id.finish);


        add_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (location != null) {
                    values.add(location);
                    Toast.makeText(bus_stop.this, "Location Added", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "please select the location", Toast.LENGTH_LONG).show();
                }


            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent check = new Intent();
                Bundle b = new Bundle();
                b.putParcelableArrayList("KEY", (ArrayList<PlaceInfo>) values);
                check.putExtras(b);
                setResult(RESULT_OK, check);
                finish();
            }
        });


    }


    public boolean isServiceesOK() {
        Log.d(TAG, "is service ok checking google service version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(bus_stop.this);

        if (available == ConnectionResult.SUCCESS) {
            //everting is ok
            Log.d(TAG, "is service ok:Google play service is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured
            Log.d(TAG, "isServiceOk:an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(bus_stop.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "We cant take this request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation:geting device location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete:found location:");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())
                            );


                        } else {
                            Log.d(TAG, "onComplete:location is null current");
                            Toast.makeText(bus_stop.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation:SecurityException" + e.getMessage());

        }

    }


    private void init() {

        Log.d(TAG, "init:initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mSearchText.setOnItemClickListener(mAutocompleteClickListener);

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);


//        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this,mGoogleApiClient,
//                LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(mPlaceAutocompleteAdapter);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == keyEvent.KEYCODE_ENTER) {

                    //execute method for searching

                    geoLocate();
                }
                return false;
            }
        });
        hideSoftKeyboard();
    }


    private void geoLocate() {
        Log.d(TAG, "geolocate:geolocating");
        String search = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(bus_stop.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(search, 1);
        } catch (IOException e) {
            Log.e(TAG, "geolocate: IO exception" + e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (list.size() > 0) {
            Address address = list.get(0);

            Log.d(TAG, "geolocate: found location" + address.toString());

            Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();


            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()));

            //   moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM);
        }
    }


    private void moveCamera(LatLng latLng) {
        Log.d(TAG, "moveCamera:moving the camera: lat: " + latLng.latitude + ", lng:" + latLng.longitude);
        nMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        MarkerOptions options = new MarkerOptions()
                .position(latLng);
        options.draggable(true);
        nMap.addMarker(options);
        hideSoftKeyboard();
    }


    private void initMap() {
        Log.d(TAG, "initmap:initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(bus_stop.this);
    }


    public void getLocationPermission() {
        Log.d(TAG, "getLocationPermission:getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions, LOCATION_PERMISSION_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this,
                    permissions, LOCATION_PERMISSION_CODE);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult:called");

        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult:permission failed");
                            return;
                        }
                    }

                    Log.d(TAG, "OnRequestPermissonResult:permisson granted");

                    mLocationPermissionGranted = true;
                    //initilize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /*..........google places api autocomplete suggestion...............*/

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpadtePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpadtePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {

            if (!places.getStatus().isSuccess()) {

                Log.d(TAG, "onResult: Place query did not complete sucessfully" + places.getStatus().toString());

                places.release();
                return;

            }

            final Place place = places.get(0);

            try {
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                mPlace.setAddress(place.getAddress().toString());
                mPlace.setId(place.getId());
                mPlace.setLatLng(place.getLatLng());

                Log.d(TAG, "onResult: " + mPlace.toString());

                //location=place.getAddress().toString();
                location = mPlace;


                //Toast.makeText(bus_stop.this,place.getAddress().toString(), Toast.LENGTH_SHORT).show();


            } catch (NullPointerException e) {
                Log.e(TAG, "onResult:NullPointException" + e.getMessage());
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude));

            places.release();


        }
    };
}





