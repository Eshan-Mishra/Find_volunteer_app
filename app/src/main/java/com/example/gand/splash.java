package com.example.gand;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth= FirebaseAuth.getInstance();

        if (auth.getCurrentUser()==null){
            Intent intent= new Intent(splash.this, Login.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent= new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}