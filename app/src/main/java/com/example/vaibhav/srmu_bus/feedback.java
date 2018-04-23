package com.example.vaibhav.srmu_bus;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Vaibhav on 2/28/2018.
 */

public class feedback extends Fragment{

    FirebaseUser user;
    DatabaseReference databaseReference;
    String v1,v2,v3,v4;
    TextView name,email;
    EditText fed;
    Button save_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feedback,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Feedback Form");

        name=getActivity().findViewById(R.id.email);
        email=getActivity().findViewById(R.id.name);
        fed=getActivity().findViewById(R.id.feedback_text);

       // save_btn=getActivity().
        loadUserInformation();



        getActivity().findViewById(R.id.feedback_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  save_fed();

            }
        });
    }



    private void loadUserInformation() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        if (user != null) {



            if (user.getEmail() != null) {
                name.setText(user.getDisplayName());
                email.setText(user.getEmail());
            }



        }




    }

    private void save_fed()
    {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String fedback=fed.getText().toString();
        Log.e("fedback", String.valueOf(fed));


        databaseReference=FirebaseDatabase.getInstance().getReference("Feedback");

        databaseReference.child(user.getEmail()).setValue(fedback).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getActivity(), "added fedback", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
