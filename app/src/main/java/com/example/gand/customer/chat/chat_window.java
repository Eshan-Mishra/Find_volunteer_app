package com.example.gand.customer.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gand.R;
import com.example.gand.messagesAdapter;
import com.example.gand.msgModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat_window extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String senderRoom, receiverRoom;
    private ArrayList<msgModel> messagesArrayList;
    private messagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        CircleImageView profile_pic = findViewById(R.id.profile_pic);
        TextView user_name = findViewById(R.id.user_name);
        ImageView send_button = findViewById(R.id.send_button);
        EditText sending_message = findViewById(R.id.sending_text);
        RecyclerView message_display = findViewById(R.id.message_display);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // This will make the layout start from the end of the list

        message_display.setLayoutManager(layoutManager);
        messagesArrayList = new ArrayList<>();
        messagesAdapter = new messagesAdapter(chat_window.this, messagesArrayList);
        message_display.setAdapter(messagesAdapter);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        String receiver_uid = getIntent().getStringExtra("receiver_uid");
        String receiver_user_name = getIntent().getStringExtra("receiver_user_name");
        String receiver_profile_pic = getIntent().getStringExtra("receiver_profile_pic");

        senderRoom = auth.getUid() + receiver_uid;
        receiverRoom = receiver_uid + auth.getUid();

        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    msgModel messages = dataSnapshot.getValue(msgModel.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        Picasso.get().load(receiver_profile_pic).into(profile_pic);
        user_name.setText(receiver_user_name);

        send_button.setOnClickListener(v -> {
            String message = sending_message.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(chat_window.this, "Enter message first", Toast.LENGTH_SHORT).show();
                return;
            }
            sending_message.setText("");
            Date date = new Date();
            msgModel msgmodel = new msgModel(message, auth.getUid(), date.getTime());
            database.getReference().child("chats").child(senderRoom).child("messages")
                    .push().setValue(msgmodel).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            database.getReference().child("chats").child(receiverRoom)
                                    .child("messages").push().setValue(msgmodel);
                        }
                    });
        });
    }
}
