package com.example.gand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.gand.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;


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
                            String id =auth.getCurrentUser().getUid();
                            SharedPreferences preferences= getSharedPreferences("my prefrence",MODE_PRIVATE);
                            SharedPreferences.Editor editor= preferences.edit().putString("uid",id);
                            editor.apply();

                            Log.d("singup_uid"," id: "+id);

                            String pagal=preferences.getString("uid"," ");

                            Log.d("pagal"," "+pagal);



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