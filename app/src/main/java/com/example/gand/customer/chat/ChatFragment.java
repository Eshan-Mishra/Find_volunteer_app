package com.example.gand.customer.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gand.R;
import com.example.gand.adapters.UserAdapter;
import com.example.gand.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class ChatFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    RecyclerView Frag_chat_recycler;

    UserAdapter adapter;
    ArrayList<User> usersArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Frag_chat_recycler=view.findViewById(R.id.Frag_chat_recycler);




        Frag_chat_recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        database= FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        DatabaseReference reference = database.getReference().child("user");
        DatabaseReference chats = database.getReference().child("chats");

        usersArrayList  =new ArrayList<>();
        adapter=new UserAdapter(ChatFragment.this,usersArrayList);
        Frag_chat_recycler.setAdapter(adapter);

        chats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String key = dataSnapshot.getKey();
                    if (key != null && key.startsWith(auth.getCurrentUser().getUid())) {
                        String new_key=key.replaceFirst(auth.getCurrentUser().getUid(),"");

                        reference.child(new_key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot user_snapshot) {
                                User user=user_snapshot.getValue(User.class);
                                usersArrayList.add(user);
                                adapter.notifyDataSetChanged();


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

//                        Log.d("chat", "Key starts with user's UID: " + key.replaceFirst(auth.getCurrentUser().getUid(), ""));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        reference.addValueEventListener(new ValueEventListener() {
//           @Override
//           public void onDataChange(@NonNull DataSnapshot snapshot) {
//               for (DataSnapshot dataSnapshot: snapshot.getChildren())
//               {
//                User user=dataSnapshot.getValue(User.class);
//
//                if (user!=null && !user.getUserId().equals(auth.getCurrentUser().getUid()))
//                  usersArrayList.add(user);
//               }
//                adapter.notifyDataSetChanged();
//           }
//
//           @Override
//           public void onCancelled(@NonNull DatabaseError error) {
//
//           }
//       });

        return view;
    }
}