package com.rmoralessolo2016.dcrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make splashScreen Full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //add animations
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.move_to_top);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.move_to_bottom);

        //assign variables
        TextView powerTextView =  findViewById(R.id.PoweredByText);
        TextView yelpTittle = findViewById(R.id.YelpTextView);
        ImageView yelpImage = findViewById(R.id.LogoImageView);

        //set animations
        powerTextView.setAnimation(animation2);
        yelpTittle.setAnimation(animation2);
        yelpImage.setAnimation(animation1);

        //after some time passed go to a new intent
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent =  new Intent(getApplicationContext(), Login.class);
                startActivity(loginIntent);
                finish();
            }
        }, 4000);
    } //end of onCreate


} //end of MainActivity