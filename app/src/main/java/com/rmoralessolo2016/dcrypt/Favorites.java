package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class Favorites extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText userInput;
    Button SearchBTN;
    String url1, url2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //initializing firebase user
        mAuth = FirebaseAuth.getInstance();

        // declare bottom nav view
        BottomNavigationView bottomNavigationView =  (BottomNavigationView) findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Favorites);
        userInput = (EditText) findViewById(R.id.Search_coin);
        SearchBTN = (Button) findViewById(R.id.Search_coin_Button);
        url1 = getString(R.string.coinBase) + "currencies/";
        url2 = getString(R.string.coinBase) + "products/";


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
                        startActivity(new Intent(getApplicationContext(), News.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Favorites:
                        return true;

                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });

        //add listener to the search button
        SearchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling corresponding function
                search_information();

            }
        });

    }//end of onCreate

    public void search_information(){
        Log.e("TAG", "on search button ONCLICK");
        String userInput_value= userInput.getText().toString();
        userInput_value = userInput_value.toUpperCase();
        if (userInput_value.isEmpty()){
            //if input empty show text
            Toast.makeText(getApplicationContext(), "Can't look for an empty value", Toast.LENGTH_LONG).show();
        } else {
            //creating urls
            url1 = url1 +  userInput_value;
            url2 = url2 + userInput_value + "-USD/stats";

            Intent intent =  new Intent(getApplicationContext(), coin_Information_activity.class);
            intent.putExtra("url1", url1);
            intent.putExtra("url2", url2);
            startActivity(intent);
            overridePendingTransition(0,0);
        }
    } //end of search_information
} //end of Favorites Activity