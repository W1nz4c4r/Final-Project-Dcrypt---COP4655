package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_Page extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView profile_name;
    Button logoutBTN;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        profile_name = (TextView) findViewById(R.id.Profile_Name);
        logoutBTN = (Button) findViewById(R.id.LGbutton);

        mAuth = FirebaseAuth.getInstance();

        //logut the user when clicked
        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }//end of onCreate

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            //if usert not autentificated go to login page
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }//end of onStart

} // end of home_Page