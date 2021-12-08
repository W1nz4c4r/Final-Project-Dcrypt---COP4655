package com.rmoralessolo2016.dcrypt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class News extends AppCompatActivity {

    TextView title1, title2, title3, title4, desc1, desc2, desc3, desc4, link1, link2, link3, link4;
    ImageView img1, img2, img3, img4;
    ArrayList<String> final_IMG_url;
    ArrayList<String> titles;
    ArrayList<String> desc ;
    ArrayList<String> link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        // declare bottom nav view
        BottomNavigationView bottomNavigationView =  (BottomNavigationView) findViewById(R.id.Bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.News);
        title1 = findViewById(R.id.tittle_in_card);
        img1 = findViewById(R.id.image_in_card);
        desc1 = findViewById(R.id.desc_in_card);
        link1 = findViewById(R.id.link_in_card);
        title2 = findViewById(R.id.tittle_in_card2);
        img2 = findViewById(R.id.image_in_card2);
        desc2 = findViewById(R.id.desc_in_card2);
        link2 = findViewById(R.id.link_in_card2);
        title3 = findViewById(R.id.tittle_in_card3);
        img3 = findViewById(R.id.image_in_card3);
        desc3 = findViewById(R.id.desc_in_card3);
        link3 = findViewById(R.id.link_in_card3);
        title4 = findViewById(R.id.tittle_in_card4);
        img4 = findViewById(R.id.image_in_card4);
        desc4 = findViewById(R.id.desc_in_card4);
        link4 = findViewById(R.id.link_in_card4);

        final_IMG_url =  new ArrayList<String>();
        titles= new ArrayList<String>();
        desc= new ArrayList<String>();
        link = new ArrayList<String>();


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

        extractNews();


    } // end of onCreate

    private void extractNews() {
        //clearing the final image list string
        final_IMG_url.clear();
        //getting img url
        RequestQueue queue0_1 = Volley.newRequestQueue(this);
        String img_url = "https://gnews.io/api/v4/search?q=crypto&lang=en&token=ffa855b057679fddbdf85894970c6ef1";
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, img_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if(response!=null){
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String results = jsonObject.getString("articles");
                                JSONArray news_section = new JSONArray(results);
                                Log.e("TAG", String.valueOf(news_section.length()));
                                for(int i = 0; i <6 ; i++){
                                    int rand = (int) (Math.random() * (news_section.length() - 1)) + 1;
                                    JSONObject index_results = news_section.getJSONObject(rand);
                                    final_IMG_url.add(index_results.getString("image"));
                                    Log.e("TAG", final_IMG_url.get(i));


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                });
        queue0_1.add(stringRequest);

        //-------------------///-----------------//------------///

        //get al the news information
        RequestQueue queue =  Volley.newRequestQueue(this);
        String url = getString(R.string.newsKEY) + "&language=en&q=crypto" ;

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                    }
                });

        queue.add(getRequest);
    }

    private void handleResponse(String response) {
        Log.d("Response", response);
        if(response!=null){
            try{
                JSONObject jsonObject = new JSONObject(response);
                String results = jsonObject.getString("results");
                JSONArray JSresults = new JSONArray(results);


                for(int i = 0; i <6 ; i++) {

                    JSONObject index_results = JSresults.getJSONObject(i);
                    titles.add(index_results.getString("title"));
                    desc.add(index_results.getString("description"));
                    link.add(index_results.getString("link"));


                    if(i == 0){
                        title1.setText(String.valueOf(titles.get(i)));
                        desc1.setText(String.valueOf(desc.get(i)));
                        Picasso.get().load(String.valueOf(final_IMG_url.get(i))).into(img1);
                        link1.setText(String.valueOf(link.get(i)));
                    }
                    if(i == 1){
                        title2.setText(String.valueOf(titles.get(i)));
                        desc2.setText(String.valueOf(desc.get(i)));
                        Picasso.get().load(String.valueOf(final_IMG_url.get(i))).into(img2);
                        link2.setText(String.valueOf(link.get(i)));
                    }
                    if(i == 2){
                        title3.setText(String.valueOf(titles.get(i)));
                        desc3.setText(String.valueOf(desc.get(i)));
                        Picasso.get().load(String.valueOf(final_IMG_url.get(i))).into(img3);
                        link3.setText(String.valueOf(link.get(i)));
                    }
                    if(i == 4){
                        title4.setText(String.valueOf(titles.get(i)));
                        desc4.setText(String.valueOf(desc.get(i)));
                        Picasso.get().load(String.valueOf(final_IMG_url.get(i))).into(img4);
                        link4.setText(String.valueOf(link.get(i)));
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }// end of handle response
} // end of News Activity