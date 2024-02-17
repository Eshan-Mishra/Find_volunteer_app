package com.example.gand.customer.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.gand.Login;
import com.example.gand.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment {

Button acc_logout_btn;
ImageView account_dp;

TextView user_name,user_num,user_city;


    public AccountFragment() {
        // Required empty public constructor
    }

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.accoun2, container, false);

        account_dp=view.findViewById(R.id.account_dp);
        user_name = view.findViewById(R.id.user_name);
        user_city= view.findViewById(R.id.resident);
//        user_num= view.findViewById(R.id.user_num);


        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        String id=auth.getCurrentUser().getUid();

//        SharedPreferences preferences = this.getActivity().getSharedPreferences("my prefrence", Context.MODE_PRIVATE);
//        Stringring id = preferences.getString("userid");

//        Log.d("uid_is","uid is "+id);
        DatabaseReference refrence= database.getReference().child("user").child(id);

        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username=snapshot.child("userName").getValue(String.class);
                String imguri=snapshot.child("profile_pic").getValue(String.class);
                Picasso.get().load(imguri).into(account_dp);
                user_city.setText(snapshot.child("city").getValue(String.class));
//                user_num.setText(snapshot.child("mobile_no").getValue(String.class));
                user_name.setText(username);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        acc_logout_btn = view.findViewById(R.id.acc_logout_btn);

        acc_logout_btn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });






        // Inflate the layout for this fragment
        return view;
    }
}