package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.vaibhav.srmu_bus.Model.saveUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;


public class stu_profile extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    ImageView profile_pic;
    EditText stuname,sturollno,stuphoneno,stuemail;

    Uri uriProfileImage;

    ProgressBar progressBar,progressBar2;

    String profileImageUrl;

    FirebaseAuth mAuth;

    private DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_profile);

        mAuth=FirebaseAuth.getInstance();

        profile_pic=findViewById(R.id.stu_pic);
        stuname=(EditText) findViewById(R.id.stu_name);
        sturollno=(EditText) findViewById(R.id.stu_rollno);
        stuphoneno=(EditText) findViewById(R.id.stu_phoneno);
        stuemail=(EditText) findViewById(R.id.stu_email);


        progressBar = findViewById(R.id.progressBar);
        progressBar2=findViewById(R.id.progressBar2);


        databaseReference = FirebaseDatabase.getInstance().getReference("Client");



        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageChooser();

            }
        });


        loadUserInformation();


        findViewById(R.id.stu_profile_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserData();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null )
        {
            finish();
            startActivity(new Intent(this,stu_home.class));

        }
    }

    private void loadUserInformation() {

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {


           if (user.getPhotoUrl() != null) {

             Glide.with(this)
                     .load(user.getPhotoUrl().toString())
                     .into(profile_pic);

            }



        }
   }

    private void saveUserData() {


        progressBar2.setVisibility(View.VISIBLE);

        String displayName=stuname.getText().toString();
        String rollNo=sturollno.getText().toString();
        String phoneNo=stuphoneno.getText().toString();
        String email=stuemail.getText().toString();

            if (displayName.isEmpty()) {
                stuname.setError("Email is required");
                stuname.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                stuemail.setError("Email is required");
                stuemail.requestFocus();
                return;
            }

            if (phoneNo.isEmpty()) {
                stuphoneno.setError("Phone No required");
                stuphoneno.requestFocus();
                return;
            }

            if (rollNo.isEmpty()) {
                sturollno.setError("Roll No required");
                sturollno.requestFocus();
                return;
            }


        saveUserData saveUserData = new saveUserData(displayName,rollNo,phoneNo,email);

        FirebaseUser user=mAuth.getCurrentUser();


        databaseReference.child(user.getUid()).setValue(saveUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())

                {
                    progressBar2.setVisibility(View.GONE);
                    startActivity(new Intent(stu_profile.this, stu_main_profile.class));


                }
            }
        });

        if (user!=null && profileImageUrl!=null)
        {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                     .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(stu_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();



                            }
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data !=null &data.getData()!=null)
        {
            uriProfileImage=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                profile_pic.setImageBitmap(bitmap);

                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        final StorageReference profileImageRef=
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+ " .jpg");

        if (uriProfileImage !=null)
        {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);

                            profileImageUrl= taskSnapshot.getDownloadUrl().toString();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(stu_profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }

    private void showImageChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"), CHOOSE_IMAGE);

    }

}
