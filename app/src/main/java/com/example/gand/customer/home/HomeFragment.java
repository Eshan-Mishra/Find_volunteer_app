package com.example.gand.customer.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gand.R;
import com.example.gand.postAdapter;
import com.example.gand.postModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;

    postAdapter adapter;

    ArrayList<postModel> postModelArrayList;
    RecyclerView frag_home_recyceler;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        frag_home_recyceler= view.findViewById(R.id.frag_home_recyceler);

        postModelArrayList= new ArrayList<>();
        frag_home_recyceler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new postAdapter(getContext(),postModelArrayList);
        frag_home_recyceler.setAdapter(adapter);



        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference().child("post");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postModelArrayList.clear();
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    for (DataSnapshot postSnapshot: userSnapshot.getChildren()){
                        postModel post= postSnapshot.getValue(postModel.class);
                        postModelArrayList.add(post);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}