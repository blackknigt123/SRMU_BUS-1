package com.example.admin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.admin.model.waylist_model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class way_point_list extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<waylist_model> items;
    private String busNo,stopNo;
    DatabaseReference databaseReference;
    private int c;
    TextView b_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_point_list);
        items = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        busNo = getIntent().getStringExtra("busNo");
        stopNo=getIntent().getStringExtra("busStop");

        b_no=(TextView) findViewById(R.id.bus_no);
        b_no.setText(busNo);

        fetch_stop();



//
//        for(int i=0;i<10;i++)
//        {
//            waylist_model model= new waylist_model( "hi there i finally did it");
//
//            items.add(model);
//        }
//
//        adapter=new waylist_adapter(this,items);
//        recyclerView.setAdapter(adapter);
    }

    private void fetch_stop() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        databaseReference = database.getReference("Bus Details")
                .child(busNo).child("bus_Stop");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c = (int) dataSnapshot.getChildrenCount();

                for (int i=0;i<c;i++)
                {
                    String name= (String) dataSnapshot.child(String.valueOf(i)).child("name").getValue();
                    waylist_model model= new waylist_model(name,String.valueOf(i+1),busNo);

                    items.add(model);
                }
                adapter=new waylist_adapter(way_point_list.this,items);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
