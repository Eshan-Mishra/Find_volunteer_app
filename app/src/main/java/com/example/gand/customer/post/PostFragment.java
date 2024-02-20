package com.example.gand.customer.post;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gand.R;
import com.example.gand.model.postModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostFragment extends Fragment {

    CircleImageView profile_pic;
    ImageView post_img;
    EditText body,location;
    Uri imageUri;
    String imageuri;
    TextView user_name;
    Button create_post;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String id;



    public PostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        id=auth.getCurrentUser().getUid();


        DatabaseReference reference= database.getReference().child("post").child(id);
        DatabaseReference user_Refrence= database.getReference().child("user").child(id);

        storage=FirebaseStorage.getInstance();

        StorageReference storageReference=storage.getReference().child("upload").child("post").child(id);

        View view =inflater.inflate(R.layout.fragment_post, container, false);

        profile_pic=view.findViewById(R.id.profile_pic);
        create_post=view.findViewById(R.id.creat_post);
        body=view.findViewById(R.id.body);
        user_name=view.findViewById(R.id.user_name);
        post_img=view.findViewById(R.id.post_img);
        location=view.findViewById(R.id.location);

        user_Refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user_name.setText(snapshot.child("userName").getValue(String.class));
                String imageuri=snapshot.child("profile_pic").getValue(String.class);
                Picasso.get().load(imageuri).into(profile_pic);
//                Log.d("userdetail", "onDataChange: "+user_name.getText()+imageuri);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        create_post.setOnClickListener(v -> {
            String Body=body.getText().toString().trim();
            String Username=user_name.getText().toString();
            String LOCAtion=location.getText().toString().trim();


            if (!Body.isEmpty()&&!LOCAtion.isEmpty()){
             if (imageUri!=null){
                 storageReference.putFile(imageUri).addOnCompleteListener(task -> {

                    if (task.isSuccessful()){
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageuri=uri.toString();
                            postModel postModel=new postModel(id,Username,Body,imageuri,LOCAtion);

                            reference.push().setValue(postModel).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        if (isAdded()) { // Check if the fragment is currently added to its activity
                                            Toast.makeText(requireActivity().getApplicationContext(), "post uploaded", Toast.LENGTH_SHORT).show();
                                            body.setText("");
                                            location.setText("");
                                            post_img.setImageResource(0);
                                        }
                                    }else {
                                        if (isAdded()) { // Check if the fragment is currently added to its activity
                                            Toast.makeText(requireActivity().getApplicationContext(), "Error in uploading post", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                            });

                        });
                    }else {

                        Toast.makeText(getContext(), "Error in uploading post", Toast.LENGTH_SHORT).show();



                    }

                 });

             }else {
                 Toast.makeText(getContext(), "image should added to the post", Toast.LENGTH_SHORT).show();

             }

            }else {
                Toast.makeText(getContext(), "description or location should not be empty", Toast.LENGTH_SHORT).show();

            }


        });







        post_img.setOnClickListener(v -> {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"select picture"),10);

        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10) {
            if (data != null) {
                imageUri = data.getData();
                post_img.setImageURI(imageUri);
            }
        }

    }
}