package com.rmoralessolo2016.dcrypt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity  {

    FirebaseAuth mAuth;
    TextView create_user, forgot_User;
    EditText  email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //declaration of variables
         email = (EditText) findViewById(R.id.email);
         password = (EditText)  findViewById(R.id.password);
         create_user = (TextView) findViewById(R.id.create_user);
         forgot_User = (TextView) findViewById(R.id.forgotPASS);
        ImageView google_login = (ImageView) findViewById(R.id.googleBTN);
        ImageView facebook_login = (ImageView) findViewById(R.id.facebookBTN);
        MaterialButton loginButton = (MaterialButton) findViewById(R.id.LoginBTN);
        //initialize firebase auth
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_User();
            }
        });

        //initialize firebase user
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            //if user signed in redirect to home page
            startActivity(new Intent(getApplicationContext(), Home_Page.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    } // end of on create

    public void login_User(){
        String S_email = email.getText().toString();
        String S_passwd = password.getText().toString();
        if(S_email.isEmpty()){
            Toast.makeText(getApplicationContext(),"Email can't be empty!",Toast.LENGTH_LONG).show();
            email.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(S_email).matches()){
            //check if real email format
            Toast.makeText(getApplicationContext(),"Invalid email. Please enter a valid one!",Toast.LENGTH_LONG).show();
            email.requestFocus();
            return;
        } else if(S_passwd.isEmpty()){
            Toast.makeText(getApplicationContext(),"Password can't be empty!",Toast.LENGTH_LONG).show();
            password.requestFocus();
            return;
        }else{
            //if fields correctly formated OR not empty login
            mAuth.signInWithEmailAndPassword(S_email,S_passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"User Successfully logged in!", Toast.LENGTH_LONG).show();
                        //send user to the corresponding page
                        startActivity(new Intent(getApplicationContext(), Home_Page.class));
                    } else{
                        //loggin Error.
                        Toast.makeText(getApplicationContext(),"Error loggin in. Please try again", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    } //end of login_User

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_user:
                startActivity(new Intent(this, Register_User.class));
                break;
        }
    } //end of on click
} //end login