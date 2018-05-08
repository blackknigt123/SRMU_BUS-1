package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class stu_home extends AppCompatActivity implements View.OnClickListener {


    private static final int RC_SIGN_IN = 234;
    private static final String TAG = "XYZ";

    GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;
    EditText stu_email,stu_paswrd;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_home);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        findViewById(R.id.google_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        mAuth=FirebaseAuth.getInstance();

        stu_email = findViewById(R.id.stu_email);
        stu_paswrd = findViewById(R.id.stu_paswrd);
        progressBar= findViewById(R.id.progressbar);


        findViewById(R.id.stu_login_btn).setOnClickListener(this);
        findViewById(R.id.stu_reg_btn).setOnClickListener(this);
        findViewById(R.id.stu_forgot_pasword).setOnClickListener(this);
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null )
        {
            finish();
            startActivity(new Intent(this,stu_main_profile.class));

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(stu_home.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),stu_main_profile.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //clear all the previous thing if user click back button then come to login screan which is dont want by us
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(stu_home.this, "Authentication Failed", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }








    private void userLogin()
    {
        String emailId=stu_email.getText().toString().trim();
        String paswrd=stu_paswrd.getText().toString();


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



        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(emailId,paswrd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Intent intent=new Intent(getApplicationContext(),stu_main_profile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //clear all the previous thing if user click back button then come to login screan which is dont want by us
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(stu_home.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.stu_login_btn:
                userLogin();
                break;

            case R.id.stu_reg_btn:
              //  Intent i = new Intent(stu_home.this,stu_reg.class);
                finish();
                Toast.makeText(this, "Redirected to Sign Up Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,stu_reg.class));
                break;

            case  R.id.stu_forgot_pasword:
                startActivity(new Intent(this,stu_reset_pswrd.class));
                break;



            
        }


    }
}
