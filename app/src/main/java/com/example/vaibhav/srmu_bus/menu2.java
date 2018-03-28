package com.example.vaibhav.srmu_bus;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Vaibhav on 1/31/2018.
 */

public class menu2 extends Fragment {

    String value = null;
    TextView location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    return inflater.inflate(R.layout.menu2,container,false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Menu2");
        getActivity().findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),showOnMap.class);
                intent.putExtra("LOCVAL",value);
                startActivity(intent);

            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref=database.getReference("Location");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                value = dataSnapshot.getValue(String.class);
                location=getActivity().findViewById(R.id.location);
                location.setText(value);
                String[] seprated = value.split(",");
                String latipos=seprated[0].trim();
                String longiPos=seprated[1].trim();

                double dlat=Double.parseDouble(latipos);
                double dlong=Double.parseDouble(longiPos);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
