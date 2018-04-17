package com.example.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class driver_cred_reg extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText dri_email,dri_paswrd,dri_repswrd;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_cred_reg);

        dri_email = (EditText) findViewById(R.id.driver_email);
        dri_paswrd = (EditText) findViewById(R.id.driver_paswrd);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);
        dri_repswrd=(EditText) findViewById(R.id.driver_repaswrd);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.register_btn).setOnClickListener(this);

    }

    private void register_user()
    {
        // String username=stu_name.getText().toString().trim();
        String paswrd=dri_paswrd.getText().toString();
        //  String rollNo=stu_rollno.getText().toString().trim();
         final String emailId=dri_email.getText().toString().trim();
        String repswrd=dri_repswrd.getText().toString();

        if(emailId.isEmpty())
        {
            dri_email.setError("Email is required");
            dri_email.requestFocus();
            return;
        }



        if(paswrd.isEmpty())
        {
            dri_paswrd.setError("Pasword is required");
            dri_paswrd.requestFocus();
            return;
        }

        if(paswrd.length()<6)
        {
            dri_paswrd.setError("Minimum lenghth of password should be 6");
            dri_paswrd.requestFocus();
            return;
        }

        if (repswrd.isEmpty())
        {
            dri_repswrd.setError("Pasword is required");
            dri_repswrd.requestFocus();
            return;
        }

        if (!repswrd.equals(paswrd))
        {
            dri_repswrd.setError("Pasword not Matched");
            dri_repswrd.requestFocus();
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
                    Toast.makeText(driver_cred_reg.this, "User Registers Successfull", Toast.LENGTH_SHORT).show();

                   startActivity(new Intent(driver_cred_reg.this,driver_profile.class));
                    saveemail();

                }
                else
                {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(driver_cred_reg.this, "Email Id  already registerd, Use Differnt Id", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(driver_cred_reg.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    private void saveemail() {
        String emailId=dri_email.getText().toString();
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Driver_Email");
       // databaseReference.setValue(emailId);
        databaseReference.child(emailId).setValue(emailId);
    }

    @Override
    public void onClick(View view) {

        switch ((view.getId())) {
            case R.id.register_btn:
                register_user();

                break;
        }

    }
}
