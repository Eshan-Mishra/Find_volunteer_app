package com.example.gand.customer.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gand.Login;
import com.example.gand.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends Fragment {

Button acc_logout_btn,save;
ImageView account_dp;

TextView user_name,residence;

EditText age_gender,skills,about;


    public AccountFragment() {
        // Required empty public constructor
    }

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view =inflater.inflate(R.layout.fragment_account, container, false);

        account_dp=view.findViewById(R.id.account_dp);
        user_name = view.findViewById(R.id.user_name);
        residence= view.findViewById(R.id.resident);

        age_gender=view.findViewById(R.id.age_gender);
        about=view.findViewById(R.id.about);
        skills=view.findViewById(R.id.skills);
        save=view.findViewById(R.id.save);
        save.setVisibility(View.INVISIBLE);



        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String id=auth.getCurrentUser().getUid();
        DatabaseReference refrence= database.getReference().child("user").child(id);



        age_gender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        about.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                save.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        SharedPreferences preferences = this.getActivity().getSharedPreferences("my prefrence", Context.MODE_PRIVATE);
//        Stringring id = preferences.getString("userid");


        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imguri=snapshot.child("profile_pic").getValue(String.class);
                Picasso.get().load(imguri).into(account_dp);
                residence.setText(snapshot.child("residence").getValue(String.class));
                user_name.setText(snapshot.child("userName").getValue(String.class));
                age_gender.setText(snapshot.child("age_gender").getValue(String.class));
                skills.setText(snapshot.child("skills").getValue(String.class));
                about.setText(snapshot.child("about").getValue(String.class));
//                Log.d("Firebase", "Skills: " + snapshot.child("skills").getValue());



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


        save.setOnClickListener(v -> refrence.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> updates = new HashMap<>();
                updates.put("age_gender", age_gender.getText().toString().trim());
                updates.put("about", about.getText().toString().trim());
                updates.put("skills", skills.getText().toString().trim());

                refrence.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (isAdded()) { // Check if the fragment is currently added to its activity
                            Toast.makeText(getContext(), "Changes saved ", Toast.LENGTH_SHORT).show();
                            save.setVisibility(View.INVISIBLE);
                            age_gender.clearFocus();
                            about.clearFocus();
                            skills.clearFocus();
                        }
                    }
                    else {
                        if (isAdded()) { // Check if the fragment is currently added to its activity
                            Toast.makeText(getContext(), "unable to save changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));




        // Inflate the layout for this fragment
        return view;
    }
}