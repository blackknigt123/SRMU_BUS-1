package com.example.vaibhav.srmu_bus;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class menu4 extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<root_model> items;

    DatabaseReference databaseReference;
    private int count_child;
    private List<ArrayList> number;
    private int ab;
    private String bus_no;
    private double latitude;
    private Double longitude;
    public ProgressBar pb;
    private RecyclerView rv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu4, container, false);



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv=getActivity().findViewById(R.id.rv) ;
        pb=getActivity().findViewById(R.id.pb);
        getActivity().setTitle("Bus Route");




        RV task= new RV();
        task.execute();



       // recyleview();








//        fetching_lat_lan_data();







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
                    bus_no= snap.getKey();
                    root_model model = new root_model(snap.getKey(),"Bus No-");
                    items.add(model);

                }
                adapter = (RecyclerView.Adapter) new root_adapter(getActivity(),items);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        rv.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);



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

    class RV extends AsyncTask<Void,Void,RecyclerView>
    {

        @Override
        protected RecyclerView doInBackground(Void... voids) {

            databaseReference = FirebaseDatabase.getInstance().getReference("Bus Details");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    items = new ArrayList<>();
                    recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));






                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        bus_no= snap.getKey();
                        root_model model = new root_model(snap.getKey(),"Bus No-");
                        items.add(model);

                    }
                    adapter = (RecyclerView.Adapter) new root_adapter(getActivity(),items);
                    recyclerView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return recyclerView;
        }

        @Override
        protected void onPreExecute() {
           // super.onPreExecute();
            rv.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(RecyclerView recyclerView) {
           // super.onPostExecute(recyclerView);
            pb.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }


}

