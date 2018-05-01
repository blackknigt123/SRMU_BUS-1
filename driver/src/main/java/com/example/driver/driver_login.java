package com.example.driver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class driver_login extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText driver_username,driver_password;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_driver_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
        driver_username = findViewById(R.id.driver_email);
        driver_password = findViewById(R.id.driver_paswrd);

        findViewById(R.id.driver_login).setOnClickListener(this);
        findViewById(R.id.driver_reg).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(this,driver_profile.class));

        }
    }

    private  void userLogin()
    {
        String emailId=driver_username.getText().toString().trim();
        String paswrd=driver_password.getText().toString();


        if(emailId.isEmpty())
        {
            driver_username.setError("Email is required");
            driver_username.requestFocus();
            return;
        }

        if(paswrd.isEmpty())
        {
            driver_password.setError("Pasword is required");
            driver_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailId,paswrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),driver_profile.class);
                    //clear all the previous thing if user click back button then come to login screan which is dont want by us
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(driver_login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.driver_login:
               // userLogin();
                login();
                break;

            case R.id.driver_reg:
                startActivity(new Intent(this,driver_reg.class));
        }
    }

    private void login() {

        progressBar.setVisibility(View.VISIBLE);
        final String emailId=driver_username.getText().toString().trim();
        final String paswrd=driver_password.getText().toString();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference dbref = (DatabaseReference) database.getReference("Driver").child(emailId);

        if (dbref!=null)
        {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String ps=dataSnapshot.child("Paswrd").getValue(String.class);

                    if (paswrd .equals(ps))
                    {
                        progressBar.setVisibility(View.GONE);

                        Intent intentExtra = new Intent(driver_login.this,driver_profile.class);
                        intentExtra.putExtra("busNo",emailId);
                        driver_login.this.startActivity(intentExtra);
                        //Intent intent = new Intent(getApplicationContext(),driver_profile.class);
                        //clear all the previous thing if user click back button then come to login screan which is dont want by us
                     //   intentExtra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(intent);

                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(driver_login.this, "Wrong Pasword", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else {
            Toast.makeText(this, "You are not registerd", Toast.LENGTH_SHORT).show();
        }
    }
}
