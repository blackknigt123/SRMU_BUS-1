package com.example.driver;

import android.Manifest;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class driver_profile extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbref;
    FirebaseAuth mAuth;


    Button showlocation;
    private static final int REQUEST_CODE_PERMISSION=2;
    String mPermossion = Manifest.permission.ACCESS_FINE_LOCATION;

    gps_tracker gps;
    TextView location,tv;
    private String busNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        mAuth=FirebaseAuth.getInstance();

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermossion) != MockPackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermossion}, REQUEST_CODE_PERMISSION);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        busNo = getIntent().getStringExtra("busNo");
       // Toast.makeText(this, busNo, Toast.LENGTH_SHORT).show();
        tv=findViewById(R.id.tv);
        tv.setText("Bus No-"+busNo);

        click();
        showlocation=(Button) findViewById(R.id.button2);

        final Handler handler = new Handler();
        final int delay = 5000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                showlocation.performClick();
               // Toast.makeText(driver_profile.this, "clicked", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, delay);
            }
        }, delay);

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gps= new gps_tracker(driver_profile.this);
                gps.stopUsingGPS();
            }
        });




    }

    private void click() {
        showlocation=(Button) findViewById(R.id.button2);
        showlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               gps = new gps_tracker(driver_profile.this);
                location=(TextView) findViewById(R.id.t1);
                if (gps.canGetLocation())
                {

                    double lat=gps.getLat();
                    double lon=gps.getLon();
                    database=FirebaseDatabase.getInstance();
                    location.setText(lat+""+lon);
                    dbref=database.getReference("Bus_Location").child(busNo);
                    FirebaseUser user=mAuth.getCurrentUser();
                    // dbref.child(user.getUid()).setValue(lat+","+lon);
                    dbref.child("location").setValue(lat+","+lon);

                }

                else
                {
                    gps.showSettingsAlert();
                }
            }
        });
    }

}


