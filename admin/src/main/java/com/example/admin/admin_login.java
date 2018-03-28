package com.example.admin;

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

public class admin_login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText admin_username,admin_password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar= findViewById(R.id.progressBar);
        admin_username = findViewById(R.id.admin_email);
        admin_password = findViewById(R.id.admin_paswrd);

       findViewById(R.id.admin_login).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               userLogin();

           }
       });



    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(this,admin_main_profile.class));

        }
    }

    private  void userLogin()
    {
        String emailId=admin_username.getText().toString().trim();
        String paswrd=admin_password.getText().toString();


        if(emailId.isEmpty())
        {
            admin_username.setError("Email is required");
            admin_username.requestFocus();
            return;
        }

        if(paswrd.isEmpty())
        {
            admin_password.setError("Pasword is required");
            admin_password.requestFocus();
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
                    Intent intent = new Intent(getApplicationContext(),admin_main_profile.class);
                    //clear all the previous thing if user click back button then come to login screan which is dont want by us
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(admin_login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
