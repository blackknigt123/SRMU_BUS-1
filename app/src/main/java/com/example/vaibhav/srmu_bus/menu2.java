package com.example.vaibhav.srmu_bus;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vaibhav.srmu_bus.Model.root_model;
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

public class menu2 extends Fragment {

    String value = null;
    TextView location;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<trackbus_model> items;
    DatabaseReference databaseReference;
    private String busNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu2, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        items = new ArrayList<>();
//        recyclerView = (RecyclerView) getActivity().findViewById(R.id.trackbusrv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        getActivity().setTitle("Menu2");
        recyleview();




        /*getActivity().findViewById(R.id.btn_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),showOnMap.class);
                intent.putExtra("LOCVAL",value);
                startActivity(intent);

            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbref=database.getReference("Bus_Location").child("12").child("location");

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
        });*/
    }

    private void recyleview() {

        databaseReference= FirebaseDatabase.getInstance().getReference("Bus_Location");

//        for (int i = 0; i <= 5; i++) {
//            trackbus_model model = new trackbus_model("12");
//
//            items.add(model);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                items = new ArrayList<>();
                recyclerView = (RecyclerView) getActivity().findViewById(R.id.trackbusrv);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    busNo = snap.getKey();

                    trackbus_model model = new trackbus_model(busNo);
                    items.add(model);


                    String value = String.valueOf(snap.child("location").getValue());
                    String[] sep = value.split(",");
                    String latipos = sep[0].trim();
                    String longipos = sep[1].trim();

                    double dlat=Double.parseDouble(latipos);
                    double dlong=Double.parseDouble(longipos);
                }

                adapter = (RecyclerView.Adapter) new trackbus_adapter(getContext(), items);
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
