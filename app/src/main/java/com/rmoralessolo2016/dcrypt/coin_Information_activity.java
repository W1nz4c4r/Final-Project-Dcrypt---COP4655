package com.rmoralessolo2016.dcrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class coin_Information_activity extends AppCompatActivity {
    String url1, url2;
    Button go_backBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_information);

        //get intent information
        Intent intent = getIntent();
        url1 = intent.getStringExtra("url1");
        url2 = intent.getStringExtra("url2");

        //declare variables
        go_backBTN = (Button) findViewById(R.id.search_back_button);
        Log.e("TAG", "url1 : "+ url1);
        Log.e("TAG", "url2 : "+ url2);

        go_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sending user back to search
                startActivity(new Intent(getApplicationContext(), Favorites.class));
            }
        });
    }
}