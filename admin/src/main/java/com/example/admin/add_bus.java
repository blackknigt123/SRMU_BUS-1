package com.example.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.model.Bus;
import com.example.admin.model.PlaceInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_bus extends Fragment {

    private EditText bus_pass;
    private EditText bus_number;
    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;
    private ListView list;
    private ArrayList<PlaceInfo> addresses;
    private ArrayAdapter<PlaceInfo> arrayAdapter;

    private Bus mbus ;

    public add_bus() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_bus, container, false);

        bus_number = (EditText)v.findViewById(R.id.bus_number);
        bus_pass = (EditText)v.findViewById(R.id.bus_password);
        list = (ListView)v.findViewById(R.id.list_view);

        final Button add_location=(Button)v.findViewById(R.id.add_bus);
        final Button b1=(Button)v.findViewById(R.id.b1) ;
        mAuth = FirebaseAuth.getInstance();


        databaseReference= FirebaseDatabase.getInstance().getReference("Bus Details");

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDriverData();

            }
        });



        return v;


    }


    private void Check(){
        String busNumber =bus_number.getText().toString();
        String driverName =bus_pass.getText().toString();
        String status ="Not Register";


        if(busNumber.isEmpty()){
            bus_number.setError("Please Assign Bus Number");
            bus_number.requestFocus();
            return;
        }



        if(driverName.isEmpty()){
            bus_pass.setError("Please Assign Driver Name");
            bus_pass.requestFocus();
            return;
        }



        Intent map = new Intent(getActivity(),bus_stop.class);
       startActivityForResult(map,90);
    }


    public void setBusStopPoints(Intent data) {

        addresses = data.getParcelableArrayListExtra("KEY");


        arrayAdapter = new ArrayAdapter<PlaceInfo>(getActivity(),android.R.layout.simple_list_item_1, addresses);
        list.setAdapter(arrayAdapter);
    }

    private void saveDriverData()
    {
        String busNumber =bus_number.getText().toString();
        String driverName =bus_pass.getText().toString();
        String status="Not Activated";

        mbus = new Bus();
        mbus.setBusNumber(busNumber);
        mbus.setDriverName(driverName);
        mbus.setBus_Stop(addresses);
        mbus.setStatus(status);
        databaseReference.child(busNumber).setValue(mbus);


    }




    }
