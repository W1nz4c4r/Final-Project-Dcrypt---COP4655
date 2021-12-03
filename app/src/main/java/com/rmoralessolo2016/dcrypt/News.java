package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class News extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //initializing Firebase user
        mAuth = FirebaseAuth.getInstance();

        // declare bottom nav view
        BottomNavigationView bottomNavigationView =  (BottomNavigationView) findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.News);
        //add listener to the Bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), Home_Page.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.News:
                        return true;

                    case R.id.Favorites:
                        startActivity(new Intent(getApplicationContext(), Favorites.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });

    } // end of onCreate
} // end of News Activity