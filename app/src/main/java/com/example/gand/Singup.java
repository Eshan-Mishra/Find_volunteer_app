package com.example.gand;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gand.databinding.ActivitySignupBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Singup extends AppCompatActivity {
    ActivitySignupBinding binding;
    String username,password,email,id,mobile,city;

    ProgressBar progressBar;

    String status = "Hey I'm Using This Application";

    CircleImageView profile_pic;
    FirebaseAuth auth;
    Uri imageUri;
    String imageuri;
    String email_pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseDatabase database;
    FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        profile_pic=binding.profilePic;

        binding.signinButton.setOnClickListener(v -> {

            username=binding.signupUsername.getText().toString();
            password=binding.signinPassword.getText().toString();
            email=binding.signupEmail.getText().toString();
            mobile=binding.mobileNo.getText().toString();
            city=binding.city.getText().toString();
            progressBar=binding.progressBar;


            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            storage = FirebaseStorage.getInstance();

            if (username.isEmpty()||password.isEmpty()||email.isEmpty()||mobile.isEmpty()||city.isEmpty()){
                Toast.makeText(Singup.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();

            }else if (!email.matches(email_pattern)) {
                binding.signupEmail.setError("Give Proper email");

            } else if (password.length()<6) {
                binding.signinPassword.setError("more than six character");
                Toast.makeText(Singup.this, "pass should be more than six character", Toast.LENGTH_SHORT).show();

            }else {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        id=task.getResult().getUser().getUid();


                        DatabaseReference reference=database.getReference().child("user").child(id);
                        StorageReference storageReference=storage.getReference().child("upload").child(id);

                        progressBar.setVisibility(View.VISIBLE);


                        if (imageUri!=null){
                            storageReference.putFile(imageUri).addOnCompleteListener(task1 -> {

                                if (task1.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                        imageuri=uri.toString();
                                        User users=new User(id,username,email,password,imageuri,status,mobile,city);
                                        reference.setValue(users).addOnCompleteListener(task2 -> {
                                            progressBar.setVisibility(View.GONE);
                                            if (task2.isSuccessful()){
                                                Intent intent = new Intent(Singup.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(Singup.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                            }

                                        });
                                    });
                                }

                            });

                        }else {
                            imageuri="https://firebasestorage.googleapis.com/v0/b/gand-48379.appspot.com/o/Screenshot%202024-02-04%20at%207.43.56%E2%80%AFPM.png?alt=media&token=a3ca9bae-87be-4b9a-b3b5-2d3ba3f3706a";
                            User users=new User(id,username,email,password,imageuri,status,mobile,city);
                            reference.setValue(users).addOnCompleteListener(task1 -> {
                                progressBar.setVisibility(View.GONE);
                                if (task1.isSuccessful()){
                                    Intent intent = new Intent(Singup.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Singup.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                }

                            });

                        }


                    }else {
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(Singup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }











        });//==================================================


        // on clicking profile pic using function to select image
        binding.profilePic.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"select picture"),10);
        });


    }//=================================================================================


    // function for letting user select image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10){
            if (data != null) {
                imageUri= data.getData();
                binding.profilePic.setImageURI(imageUri);



            }

        }
    }//================================================


}