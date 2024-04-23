package com.example.gand;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.gand.databinding.ActivityLoginBinding;
import com.example.gand.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    String email,pass;
    String email_pattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null){
            Intent intent=new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
//==========================================================================================================================

        binding.loginButton.setOnClickListener(v -> {

            email = binding.loginEmail.getText().toString();
            pass = binding.loginPassword.getText().toString();

            if (email.isEmpty()){
                Toast.makeText(Login.this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (pass.isEmpty()) {
                Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_SHORT).show();

            } else if (!email.matches(email_pattern)) {
                binding.loginEmail.setError("Give Proper email");

            } else if (pass.length()<6) {
                binding.loginPassword.setError("more than six character");
                Toast.makeText(Login.this, "pass should be more than six character", Toast.LENGTH_SHORT).show();

            }else {

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        try {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                });
            }


        });//==========================================================================================================================

        binding.signupRedirectText.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Singup.class);
            startActivity(intent);
            finish();
        });
    }
}