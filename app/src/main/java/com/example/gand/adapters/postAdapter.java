package com.example.gand.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gand.R;
import com.example.gand.customer.home.HomeFragment;
import com.example.gand.model.postModel;
import com.example.gand.profile_explore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class postAdapter extends RecyclerView.Adapter<postAdapter.ViewHolder> {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference= database.getReference().child("user");

    private  Context context;
    private  ArrayList<postModel> postModelArrayList;

    String profile_pic_url;


    public postAdapter(Context context, ArrayList<postModel> postModelArrayList) {
        this.context=context;
        this.postModelArrayList=postModelArrayList;
    }

    @NonNull
    @Override
    public postAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.post_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull postAdapter.ViewHolder holder, int position) {
        postModel post=postModelArrayList.get(position);
        holder.user_name.setText(post.getUsername());
        holder.body.setText(post.getBody());
        holder.location.setText(post.getLocation());


        Picasso.get().load(post.getPost_img()).into(holder.post_img);

        reference.child(post.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    profile_pic_url=snapshot.child("profile_pic").getValue().toString();
                    Picasso.get().load(profile_pic_url).into(holder.profile_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.location.setOnClickListener(v -> {
            String location = holder.location.getText().toString(); // Get the location
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(location)); // Create a Uri from an intent string. Use the result to create an Intent.
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri); // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            mapIntent.setPackage("com.google.android.apps.maps"); // Make the Intent explicit by setting the Google Maps package

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) { // Attempt to start an activity that can handle the Intent
                context.startActivity(mapIntent);
            }
        });

        holder.user_name.setOnClickListener(v -> {
            Intent intent=new Intent(context, profile_explore.class);
            intent.putExtra("uid",post.getUid());
            intent.putExtra("profile_pic",profile_pic_url);
            intent.putExtra("user_name",post.getUsername());
            context.startActivity(intent);

        });






    }

    @Override
    public int getItemCount() {
        return postModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView body,location,user_name;
        ImageView post_img;
        CircleImageView profile_pic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            body= itemView.findViewById(R.id.body);
            location= itemView.findViewById(R.id.location);
            user_name= itemView.findViewById(R.id.user_name);
            post_img= itemView.findViewById(R.id.post_img);
            profile_pic=itemView.findViewById(R.id.profile_pic);
        }
    }
}
