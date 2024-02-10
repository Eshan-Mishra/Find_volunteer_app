package com.example.gand;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.gand.customer.account.AccountFragment;
import com.example.gand.customer.chat.ChatFragment;
import com.example.gand.customer.home.HomeFragment;
import com.example.gand.customer.search.SearchFragment;
import com.example.gand.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {
    private AnimatedBottomBar bnView;
//    TextView appheader;
    FirebaseAuth auth;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser()==null){
            Intent intent= new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }


//        appheader = findViewById(R.id.appheader);


        binding.bnView.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NonNull AnimatedBottomBar.Tab tab1) {

                replace(new HomeFragment());

                if (tab1.getId()==R.id.nav_home){
                    replace(new HomeFragment());
//                    appheader.setText(R.string.home_title);

                }else if (tab1.getId()==R.id.nav_search){
                    replace(new SearchFragment());
//                    appheader.setText(R.string.skills_title);

                }else if (tab1.getId()==R.id.nav_acoount){
                    replace(new AccountFragment() );
//                    appheader.setText(R.string.acoount_title);

                }else if (tab1.getId()==R.id.nav_chat){
                    replace(new ChatFragment());
//                    appheader.setText(R.string.chat_title);
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });


    }//=====================================================================================================================================================


    private void replace(Fragment fragment){
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.FrameLayoutContanier,fragment);
        ft.commit();
    }


}//=