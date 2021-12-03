package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Page extends AppCompatActivity {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        // declare bottom nav view
        BottomNavigationView bottomNavigationView =  (BottomNavigationView) findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        //add listener to the Bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        return true;

                    case R.id.News:
                        //redirect to news
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0,0);
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


    }//end of onCreate

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }//end of onStart

    private void updateUI(FirebaseUser user) {
        //redirect to the corresponding activity
        if (user != null){
            Log.e("TAG", "user is in");
            //send user to the corresponding page

        }else{
            Log.e("TAG", "User is out!!");
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    } //end of updateUI

} // end of home_Page