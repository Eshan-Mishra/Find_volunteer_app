package com.example.gand;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gand.customer.chat.chat_window;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_explore extends AppCompatActivity {

    FirebaseAuth auth;

    FirebaseDatabase database;

    String uid,profile_pic,user_name;

    TextView age_gender,resident,skills,about,username;

    CircleImageView profile;

    Button message_them;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_explore);

        uid=getIntent().getStringExtra("uid");
        profile_pic=getIntent().getStringExtra("profile_pic");
        user_name=getIntent().getStringExtra("user_name");

        age_gender=findViewById(R.id.age_gender);
        resident=findViewById(R.id.resident);
        skills=findViewById(R.id.skills);
        about=findViewById(R.id.about);
        username=findViewById(R.id.user_name);
        profile=findViewById(R.id.account_dp);
        message_them=findViewById(R.id.message_them);

        username.setText(user_name);
        Picasso.get().load(profile_pic).into(profile);


        message_them.setOnClickListener(v -> {
            Intent intent=new Intent(this, chat_window.class);
            intent.putExtra("receiver_uid",uid);
            intent.putExtra("receiver_user_name",user_name);
            intent.putExtra("receiver_profile_pic",profile_pic);
            startActivity(intent);
        });



    }
}