package com.example.gand.adapters;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gand.R;
import com.example.gand.model.User;
import com.example.gand.customer.chat.ChatFragment;
import com.example.gand.customer.chat.chat_window;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ChatFragment chatfragment;
    ArrayList<User> usersArrayList;

    public UserAdapter(ChatFragment chatfragment, ArrayList<User> usersArrayList) {

        this.chatfragment=chatfragment;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("UserAdapter", "Creating view holder for viewType: " + viewType);

        View view= LayoutInflater.from(chatfragment.getContext()).inflate(R.layout.user,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user=usersArrayList.get(position);
        holder.sender_name.setText(user.getUserName());
        holder.sender_last_mess.setText(user.getStatus());
        holder.userimg.setImageURI(Uri.parse(user.getProfile_pic()));

        Picasso.get().load(user.getProfile_pic()).into(holder.userimg);


//        Log.d("UserAdapter", "Binding view holder for position: " + position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(chatfragment.getContext(), chat_window.class);
            intent.putExtra("receiver_user_name",user.getUserName());
            intent.putExtra("receiver_uid",user.getUserId());
            intent.putExtra("receiver_profile_pic",user.getProfile_pic());

            chatfragment.startActivity(intent);


        });





    }

    @Override
    public int getItemCount() {
//        Log.d("UserAdapter", "Size of usersArrayList: " + usersArrayList.size());
        return usersArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userimg;
        TextView sender_name;
        TextView sender_last_mess;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userimg=itemView.findViewById(R.id.userimg);
            sender_name=itemView.findViewById(R.id.sender_name);
            sender_last_mess=itemView.findViewById(R.id.sender_last_mess);
        }
    }
}