package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaibhav.srmu_bus.Model.email_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class stu_reg extends AppCompatActivity implements View.OnClickListener {


    ProgressBar progressBar;
    EditText stu_email,stu_paswrd,stu_repswrd;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;

    private email_model mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_reg);

        stu_email = (EditText) findViewById(R.id.stu_email);
        stu_paswrd = (EditText) findViewById(R.id.stu_paswrd);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);
        stu_repswrd=(EditText) findViewById(R.id.stu_cnfrm_pswrd);
        TextView alreadysignup = (TextView)findViewById(R.id.moveToLogin);
        alreadysignup.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

       // databaseReference = FirebaseDatabase.getInstance().getReference("Credential");

        findViewById(R.id.register_btn).setOnClickListener(this);



    }

    private void register_user()
    {
       // String username=stu_name.getText().toString().trim();
        String paswrd=stu_paswrd.getText().toString();
      //  String rollNo=stu_rollno.getText().toString().trim();
        final String emailId=stu_email.getText().toString().trim();
        String repswrd=stu_repswrd.getText().toString();

        if(emailId.isEmpty())
        {
            stu_email.setError("Email is required");
            stu_email.requestFocus();
            return;
        }



        if(paswrd.isEmpty())
        {
            stu_paswrd.setError("Pasword is required");
            stu_paswrd.requestFocus();
            return;
        }

        if(paswrd.length()<6)
        {
            stu_paswrd.setError("Minimum lenghth of password should be 6");
            stu_paswrd.requestFocus();
            return;
        }

        if (repswrd.isEmpty())
        {
            stu_repswrd.setError("Pasword is required");
            stu_repswrd.requestFocus();
            return;
        }

       if (!repswrd.equals(paswrd))
        {
            stu_repswrd.setError("Pasword not Matched");
            stu_repswrd.requestFocus();
            Toast.makeText(this, "Pasword Not Matched", Toast.LENGTH_SHORT).show();
            return;

        }

       progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(emailId,paswrd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Intent stu_profile = new Intent(stu_reg.this, stu_profile.class);

                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Toast.makeText(stu_reg.this, "User Registers Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),stu_profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //clear all the previous thing if user click back button then come to login screan which is dont want by us
                    startActivity(intent);

                    //saveEmail();

                 //   String emailId1=stu_email.getText().toString().trim();

                   // databaseReference.child(emailId1).setValue("uh");


                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(stu_reg.this, "You are already registerd", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(stu_reg.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    private void saveEmail() {

        databaseReference= FirebaseDatabase.getInstance().getReference("Client_ID");
        String email=stu_email.getText().toString();
        String ps=stu_paswrd.getText().toString();

        mEmail= new email_model();

        mEmail.setEmail(email);

        databaseReference.setValue(mEmail);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId()))
        {
            case R.id.register_btn:
                register_user();

                break;

            case  R.id.moveToLogin:

                finish();
                Intent redirecttostudentlogin = new Intent(stu_reg.this,stu_home.class);
                startActivity(redirecttostudentlogin);
        }

    }
}
