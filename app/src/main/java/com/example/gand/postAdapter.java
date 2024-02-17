package com.example.gand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
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

    private Context context;
    private ArrayList<postModel> postModelArrayList;


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


        Picasso.get().load(post.post_img).into(holder.post_img);

        reference.child(post.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Picasso.get().load(snapshot.child("profile_pic").getValue().toString()).into(holder.profile_pic);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    @Override
    public int getItemCount() {
        return postModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
