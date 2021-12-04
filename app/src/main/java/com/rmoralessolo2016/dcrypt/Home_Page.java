package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Home_Page extends AppCompatActivity {
    FirebaseAuth mAuth;
    ArrayList<String> coins_check;
    RecyclerView recyclerView;
    List<cryptoMG> crypto;
    Adapter adapter;
    String hand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();

        // declare bottom nav view
        BottomNavigationView bottomNavigationView =  (BottomNavigationView) findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        recyclerView = findViewById(R.id.Recycler_view);
        coins_check = new ArrayList<String>();
        coins_check.clear();
        crypto = new ArrayList<>();
        crypto.clear();


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
        Toast.makeText(getApplicationContext(), "Getting Coins information", Toast.LENGTH_LONG).show();
        launch_prueba();
    }//end of onCreate

    private void launch_prueba() {
        Log.e("TAG", "Inside launch prueba");
        coins_check.clear();

        Log.e("TAG", "before: " +String.valueOf(coins_check));

        //select random coins
        randomize_important_coins();
        randomize_others();

        Log.e("TAG", "after: " + String.valueOf(coins_check));


        //make the request to the server
        //for (int i =0; i < coins_check.size(); i++){
        for(int i = 0; i < coins_check.size(); i++){


            //creating each coin urls
            String jsonURL = getString(R.string.coinBase) + "currencies/" + coins_check.get(i);
            String jsonURL1 = getString(R.string.coinBase)+ "products/" + coins_check.get(i) + "-USD/stats";


            //doing request with volley

            RequestQueue queue1 = Volley.newRequestQueue(Home_Page.this);
            try
            {
                //sleep so it doesn't block all the calls to the api being made
                Thread.sleep(150);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, jsonURL1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response1) {
                    hand = response1;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Something went wrong, please go back and check your input", Toast.LENGTH_LONG).show();
                }
            });

            queue1.add(stringRequest1);

            //---------------------------------
            //doing second request on second URL
            //doing request with volley

            RequestQueue queue = Volley.newRequestQueue(Home_Page.this);
            try
            {
                //sleep so it doesn't block all the calls to the api being made
                Thread.sleep(150);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    handleResponse(response);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter =  new Adapter(getApplicationContext(), crypto);
                    recyclerView.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Something went wrong, please go back and check your input", Toast.LENGTH_LONG).show();
                }
            });

            queue.add(stringRequest);

        }

    }//end of launch_prueba



    private void handleResponse(String response) {
        //check if response is not null
        if (response != null){
            Log.e("TAG", hand);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = new JSONObject(hand);
                cryptoMG cryptoMG = new cryptoMG();
                String name = jsonObject.getString("name");
                String id = jsonObject.getString("id");
                if(name.equals("Ether")){
                    name = "Ethereum";
                }
                if(name.equals("Synthetix")){
                    name = "Synthetix Network Token";
                }
                if (name.equals("ARPA")){
                    name ="ARPA Chain";
                }
                if(name.equals("Fetch.ai")){
                    name = "Fetch ai";
                }

                cryptoMG.setCoin_Name(name);
                cryptoMG.setCoin_short_name(id);
                cryptoMG.setCoin_price(jsonObject1.getString("last"));
                cryptoMG.setCoin_icon_url(getString(R.string.iconsURL) + id.toLowerCase() + "-" + name.replace(" ","-").toLowerCase() + ".png" );

                crypto.add(cryptoMG);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("TAG", "response is empty ");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG","Couldn't get json from server. Check LogCat for possible errors!");
                }
            });
        }
    } //Handle response

    private void randomize_others() {
        String[] Coins;
        Log.e("TAG", "in randomize others");
        //formating the array
        Coins = getString(R.string.coinss).split(",");
        boolean valid = true;
        //selecting other 7 rand coins to show
        for(int i = 0; i < 7; i++){
            int rand = (int) (Math.random() * (122 - 1)) + 1;
            while (valid){
                //if rand in possition of important coins --> generate new rand
                if ( rand == 5 || rand == 120 || rand == 94 || rand == 41 || rand == 50 || rand == 51  || rand == 89){
                    rand = (int) (Math.random() * (122 - 1)) + 1;
                } else{
                    valid = false;
                }

            }
            Log.e("TAG", "rand is : " + Coins);
            coins_check.add(Coins[rand]);
        }


    } //end of randomize_coins

    //String[]
    private  void randomize_important_coins() {
        String[]  Rimp;
        String BTC = "BTC",ETH = "ETH", IMP;
        Rimp = getString(R.string.fav_coins).split(",");
        int IMP_number = (int) (Math.random() * (3 -1)) + 1;
        IMP = Rimp[IMP_number];
        coins_check.add(BTC);
        coins_check.add(IMP);
        coins_check.add(ETH);
    } //end of randomize_coins

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