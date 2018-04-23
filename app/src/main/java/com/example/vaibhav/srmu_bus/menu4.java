package com.example.vaibhav.srmu_bus;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaibhav on 1/31/2018.
 */

public class menu4 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<recyclerview_model> items;

    DatabaseReference databaseReference;
    private int count_child;
    private List<ArrayList> number;
    private int ab;
    private String bus_no;
    private double latitude;
    private Double longitude;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu4, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyleview();
//        fetching_lat_lan_data();

        getActivity().setTitle("Bus Route");

        //  Intent intentExtra = new Intent(getContext(),direction.class);
        //  intentExtra.putExtra("lat",latitude);
        //  intentExtra.putExtra("lon",longitude);
        //  getActivity().startActivity(intentExtra);


    }

    private void recyleview() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Bus Details");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items = new ArrayList<>();
                recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    bus_no = snap.getKey();
                    recyclerview_model model = new recyclerview_model(
                            (snap.getKey()),
                            "startpoint",
                            "current stop",
                            "nextstop",
                            12);
                    items.add(model);

                }
                adapter = (RecyclerView.Adapter) new recyclerview_adapter(getActivity(), items);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void fetching_lat_lan_data() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("Bus Details")
                .child(bus_no).child("bus_Stop").child("0").child("latLng");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                latitude = (double) dataSnapshot.child("latitude").getValue();
                longitude = (Double) dataSnapshot.child("longitude").getValue();
                // listPoints= (ArrayList<LatLng>) dataSnapshot.getValue();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

