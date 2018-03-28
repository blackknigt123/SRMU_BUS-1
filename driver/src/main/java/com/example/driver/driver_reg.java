package com.example.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class driver_reg extends AppCompatActivity implements View.OnClickListener {

    EditText busNo, driverName;
    String bno;
    String dname;
    String bstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg);

        busNo = findViewById(R.id.bus_no);
        driverName = findViewById(R.id.driver_name);

        findViewById(R.id.moveToLogin).setOnClickListener(this);
        findViewById(R.id.proceed).setOnClickListener(this);


    }


    private void checkDriverInfo() {

        final String busno = busNo.getText().toString();
        final String drivername = driverName.getText().toString();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbref = (DatabaseReference) database.getReference("Bus Details").child(busno);

        if (dbref != null) {

            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {
                        String bno = dataSnapshot.child("busNumber").getValue(String.class);
                        String dname = dataSnapshot.child("driverName").getValue(String.class);
                        String status = dataSnapshot.child("status").getValue(String.class);

                        if (bno .endsWith( busno) && dname .equals( drivername)) {
                            if (status .equals( "Not Activated")) {
                                Toast.makeText(driver_reg.this, "Driver Registration Complete", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(driver_reg.this, "Bus Already Regierd", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(driver_reg.this, "Enter Correct Details", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(driver_reg.this, "Contact Your Admin", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.moveToLogin:
                startActivity(new Intent(this, driver_login.class));
                break;

            case R.id.proceed:
                checkDriverInfo();
        }
    }
}




