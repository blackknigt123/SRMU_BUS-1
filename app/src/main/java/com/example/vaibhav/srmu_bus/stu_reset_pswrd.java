package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class stu_reset_pswrd extends AppCompatActivity {
    
    FirebaseAuth mAuth;
    EditText email;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_reset_pswrd);

        email = (EditText) findViewById(R.id.stu_emailBox_reset);
        Button btnResetPassword = (Button) findViewById(R.id.stu_reset_pswrd);
        pb=findViewById(R.id.pb);

        
        mAuth = FirebaseAuth.getInstance();
        
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String emailBox = email.getText().toString().trim();
                
                if (TextUtils.isEmpty(emailBox))
                {
                    Toast.makeText(stu_reset_pswrd.this, "Enter Your email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pb.setVisibility(View.VISIBLE);
                
                mAuth.sendPasswordResetEmail(emailBox).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        
                        if (task.isSuccessful())
                        {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(stu_reset_pswrd.this, "Check email to reset your password", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(stu_reset_pswrd.this,stu_home.class));

                        }

                        else
                        {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(stu_reset_pswrd.this, "Fail to send reset password email", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

    }
}
