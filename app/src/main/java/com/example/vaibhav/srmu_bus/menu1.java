package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Vaibhav on 1/31/2018.
 */

public class menu1 extends Fragment {

    ImageView profilePic;
    TextView name, email, rollno, phoneno;
    String v1,v2,v3,v4;
    String uid;
    Button updateProfile;

    FirebaseUser user;
    DatabaseReference databaseReference;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu1, null);

    }

    @Override
    public void onStart() {
        super.onStart();
        loadUserInformation();



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("My Profile");


        profilePic=getActivity().findViewById(R.id.profile_pic);
        name=getActivity().findViewById(R.id.stu_name);
        email=getActivity().findViewById(R.id.stu_email);
        rollno=getActivity().findViewById(R.id.stu_roll);
        phoneno=getActivity().findViewById(R.id.stu_phoneno);

        getActivity().findViewById(R.id.update_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),stu_profile.class));

            }
        });

    }

    private void loadUserInformation() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Client");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                v1=dataSnapshot.child(user.getUid()).child("displayName").getValue(String.class);
                v2=dataSnapshot.child(user.getUid()).child("phoneNo").getValue(String.class);
                v3=dataSnapshot.child(user.getUid()).child("rollNo").getValue(String.class);

                name.setText(v1);
                rollno.setText(v3);
                phoneno.setText(v2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (user != null) {


            if (user.getPhotoUrl() != null) {

                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(profilePic);

            }

            if (user.getEmail() != null) {
                email.setText(user.getEmail());
            }



        }


        }

}